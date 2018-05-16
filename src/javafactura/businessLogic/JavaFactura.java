package javafactura.businessLogic;

import javafactura.businessLogic.collections.Pair;
import javafactura.businessLogic.comparators.ContribuinteFacturaCountComparator;
import javafactura.businessLogic.comparators.ContribuinteSpendingComparator;
import javafactura.businessLogic.comparators.FacturaValorComparator;
import javafactura.businessLogic.econSectors.EconSector;
import javafactura.businessLogic.econSectors.Educacao;
import javafactura.businessLogic.econSectors.Familia;
import javafactura.businessLogic.econSectors.Pendente;
import javafactura.businessLogic.exceptions.*;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

public class JavaFactura implements Serializable {

    private static final String SAVE_STATE_FILE = "javaFactura.dat";

    public static JavaFactura loadState(){
        try(ObjectInputStream is = new ObjectInputStream(new FileInputStream(SAVE_STATE_FILE))){
            return (JavaFactura) is.readObject();
        }catch(IOException | ClassNotFoundException e){
            return new JavaFactura();
        }
    }


    private final Map<String,Contribuinte> contribuintes;

    private User loggedInUser;

    private final Admin admin;


    private JavaFactura(){
        this.contribuintes = new HashMap<>();
        //this.contribuintes = generateContribuintes();
        //generateFacturas();
        this.loggedInUser = null;
        this.admin = new Admin();
    }

    public User getLoggedUser(){
        if(this.loggedInUser == null) return null;
        return this.loggedInUser.clone();
    }

    public String getLoggedUserNif(){
        if(this.loggedInUser == null) return null;
        return this.loggedInUser.getNif();
    }

    public void setAdminPassword(String newPassword){
        this.admin.setPassword(newPassword);
    }

    public void login(String nif, String password) throws InvalidCredentialsException{
        if(nif.equals("admin") && password.equals(this.admin.getPassword())){
            this.loggedInUser = this.admin;
        }else{
            Contribuinte user = this.contribuintes.get(nif);
            if(user == null || !user.getPassword().equals(password))
                throw new InvalidCredentialsException();
            this.loggedInUser = user;
        }
    }

    public void logout(){
        this.loggedInUser = null;
    }

    public void registarIndividual(String nif, String email, String nome, String address, String password,
                                   int numDependants, List<String> dependants, double fiscalCoefficient,
                                   Set<EconSector> econSectors) throws
                                                                InvalidNumberOfDependantsException,
                                                                IndividualAlreadyExistsException{
        if(this.contribuintes.containsKey(nif)) throw new IndividualAlreadyExistsException();
        this.contribuintes.put(nif, new ContribuinteIndividual(
                nif,
                email,
                nome,
                address,
                password,
                numDependants, dependants,
                fiscalCoefficient,
                econSectors));
    }

    public void registarEmpresarial(String nif, String email, String nome, String address, String password,
                                    double fiscalCoefficient, Set<EconSector> econSectors) throws
                                                                                           EmpresarialAlreadyExistsException{
        if(this.contribuintes.containsKey(nif)) throw new EmpresarialAlreadyExistsException();
        this.contribuintes.put(nif, new ContribuinteEmpresarial(
                nif,
                email,
                nome,
                address,
                password,
                fiscalCoefficient,
                econSectors));
    }

    public Set<EconSector> getAllSectors(){
        Set<EconSector> allSectors = EconSector.getAllSectors();
        allSectors.remove(Pendente.getInstance());
        return allSectors;
    }

    public EconSector getSectorFromString(String name){
        return EconSector.getFromString(name);
    }

    public void changeEmail(String newEmail){
        if(this.loggedInUser instanceof Contribuinte)
            ((Contribuinte) this.loggedInUser).setEmail(newEmail);
    }

    public void changePassword(String newPassword){
        this.loggedInUser.setPassword(newPassword);
    }

    public void changeAddress(String newAddress){
        if(this.loggedInUser instanceof Contribuinte)
            ((Contribuinte) this.loggedInUser).setAddress(newAddress);
    }

    public Set<EconSector> getLoggedUserSectors() throws NotEmpresaException{
        if(this.loggedInUser instanceof ContribuinteEmpresarial){
            return ((ContribuinteEmpresarial) this.loggedInUser).getEconActivities();
        }
        throw new NotEmpresaException();
    }

    public void emitirFactura(String clientNif, float value, String description) throws
                                                                                 NotEmpresaException,
                                                                                 NoSuchIndividualException{
        if(!(this.loggedInUser instanceof ContribuinteEmpresarial)) throw new NotEmpresaException();

        Contribuinte client = this.contribuintes.get(clientNif);
        if(client == null) throw new NoSuchIndividualException();

        ((ContribuinteEmpresarial) this.loggedInUser).issueFactura(client, description, value);
    }

    public Factura changeFactura(Factura factura, EconSector econSector) throws NotIndividualException,
                                                                                InvalidEconSectorException{
        try{
            return ((ContribuinteIndividual) this.loggedInUser)
                    .changeFatura(factura, econSector);
        }catch(ClassCastException e){
            throw new NotIndividualException();
        }
    }

    public List<Factura> getLoggedUserFacturas() throws NotContribuinteException{
        if(!(this.loggedInUser instanceof Contribuinte)) throw new NotContribuinteException();
        return ((Contribuinte) this.loggedInUser).getFacturas().stream().sorted().collect(Collectors.toList());
    }

    public List<Factura> getLoggedUserFacturas(Comparator<Factura> comparator) throws NotContribuinteException{
        if(!(this.loggedInUser instanceof Contribuinte)) throw new NotContribuinteException();
        return ((Contribuinte) this.loggedInUser).getFacturas()
                                                 .stream()
                                                 .sorted(comparator)
                                                 .collect(Collectors.toList());
    }

    public List<Factura> getLoggedUserFacturas(LocalDate from, LocalDate to) throws NotContribuinteException{
        return getLoggedUserFacturas().stream()
                                      .filter(f -> f.getCreationDate().isAfter(from.atStartOfDay()))
                                      .filter(f -> f.getCreationDate().isBefore(to.atTime(LocalTime.MAX)))
                                      .collect(Collectors.toList());
    }

    public List<Factura> getFaturasOfIndividual(String clientNif, LocalDate from, LocalDate to) throws
                                                                                                NotEmpresaException,
                                                                                                NotIndividualException{
        if(!(this.loggedInUser instanceof ContribuinteEmpresarial)) throw new NotEmpresaException();
        Contribuinte c = this.contribuintes.get(clientNif);
        if(!(c instanceof ContribuinteIndividual)) throw new NotIndividualException();
        return c.getFacturas().stream()
                .filter(f -> f.getCreationDate().isAfter(from.atStartOfDay()))
                .filter(f -> f.getCreationDate().isBefore(to.atTime(LocalTime.MAX)))
                .filter(f -> f.getIssuerNif().equals(this.loggedInUser.getNif()))
                .sorted(new FacturaValorComparator().reversed()).collect(Collectors.toList());
    }

    public double getAccumulatedDeduction() throws NotIndividualException{
        if(!(this.loggedInUser instanceof ContribuinteIndividual)) throw new NotIndividualException();
        return ((ContribuinteIndividual) this.loggedInUser).getFacturas().stream().mapToDouble(Factura::deducao).sum();
    }

    public double getAccumulatedDeductionFamilyAggregate() throws NotIndividualException{
        if(!(this.loggedInUser instanceof ContribuinteIndividual)) throw new NotIndividualException();
        Set<String> nifList = ((ContribuinteIndividual) this.loggedInUser).getFamilyAggregate();
        double total = 0.0;
        for(String nif : nifList){
            total += this.contribuintes.get(nif).getFacturas().stream().mapToDouble(Factura::deducao).sum();
        }
        return total;
    }

    public Set<ContribuinteIndividual> getClients() throws NotEmpresaException{
        if(!(this.loggedInUser instanceof ContribuinteEmpresarial)) throw new NotEmpresaException();
        return ((ContribuinteEmpresarial) this.loggedInUser).getFacturas()
                                                            .stream()
                                                            .map(f -> this.contribuintes.get(f.getClientNif()))
                                                            .filter(ContribuinteIndividual.class::isInstance)
                                                            .map(ContribuinteIndividual.class::cast)
                                                            .collect(Collectors.toSet());
    }

    public double totalFaturado(LocalDate from, LocalDate to) throws NotEmpresaException{
        if(!(this.loggedInUser instanceof ContribuinteEmpresarial)) throw new NotEmpresaException();
        return ((ContribuinteEmpresarial) this.loggedInUser)
                .getFacturas()
                .stream()
                .filter(f -> f.getCreationDate().isAfter(from.atStartOfDay()))
                .filter(f -> f.getCreationDate().isBefore(to.atTime(LocalTime.MAX)))
                .mapToDouble(Factura::getValue)
                .sum();
    }

    public List<Contribuinte> getTop10Contrib() throws NotAdminException{
        if(!(this.loggedInUser instanceof Admin)){
            throw new NotAdminException();
        }
        PriorityQueue<ContribuinteIndividual> top10
                = new PriorityQueue<>(10, new ContribuinteSpendingComparator().reversed());
        this.contribuintes.values()
                          .stream()
                          .filter(ContribuinteIndividual.class::isInstance)
                          .forEach(c -> top10.add((ContribuinteIndividual) c));
        return top10.stream().limit(10).collect(Collectors.toList());
    }

    /*
    determinar a relação das X empresas que mais facturas emitiram em t_odo o sistema e o montante de
    deduções fiscais que as despesas registadas (dessas empresas) representam;
     */
    public List<Pair<ContribuinteEmpresarial,Double>> getTopXEmpresas(int x) throws NotAdminException{
        if(!(this.loggedInUser instanceof Admin)) throw new NotAdminException();

        PriorityQueue<ContribuinteEmpresarial> topX =
                new PriorityQueue<>(x, new ContribuinteFacturaCountComparator().reversed());

        this.contribuintes.values().stream()
                          .filter(ContribuinteEmpresarial.class::isInstance)
                          .forEach(c -> topX.add((ContribuinteEmpresarial) c));

        return topX.stream()
                   .limit(x)
                   .map(c -> new Pair<>(c, c.getFacturas().stream().mapToDouble(Factura::getValue).sum()))
                   .collect(Collectors.toList());
    }

    public void saveState() throws IOException{
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(SAVE_STATE_FILE));
        os.writeObject(this);
        os.flush();
        os.close();
    }

    private Map<String,Contribuinte> generateContribuintes(){
        Map<String,Contribuinte> contribuintes = new HashMap<>();
        for(int i = 0; i < 5; i++){
            String nif = "I" + i;
            String email = "ci" + i + "@email.com";
            String nome = "ci" + i;
            String address = "Rua ci" + i;
            String password = "pass";
            List<String> dependants = new ArrayList<>();//TODO make some stubs here
            double fiscal_coefficient = Math.random();
            Set<EconSector> econActivities = new HashSet<>();
            try{
                contribuintes.put(nif, new ContribuinteIndividual(
                        nif,
                        email,
                        nome,
                        address,
                        password,
                        dependants.size(),
                        dependants,
                        fiscal_coefficient,
                        econActivities));
            }catch(InvalidNumberOfDependantsException e){
                e.printStackTrace();
            }

        }
        for(int i = 0; i < 3; i++){
            String nif = "E" + i;
            String email = "ce" + i + "@email.com";
            String nome = "ce" + i;
            String address = "Rua ce" + i;
            String password = "pass";
            double fiscal_coefficient = Math.random();
            Set<EconSector> econActivities = new HashSet<>();
            econActivities.add(Familia.getInstance());
            econActivities.add(Educacao.getInstance());
            contribuintes.put(nif, new ContribuinteEmpresarial(
                    nif,
                    email,
                    nome,
                    address,
                    password,
                    fiscal_coefficient,
                    econActivities
            ));

        }
        return contribuintes;
    }

    private void generateFacturas(){
        User u = this.loggedInUser;
        List<String> issuers = this.contribuintes.values()
                                                 .stream()
                                                 .map(Contribuinte::getNif)
                                                 .filter(nif -> nif.startsWith("E"))
                                                 .collect(Collectors.toList());
        List<String> clients = this.contribuintes.values()
                                                 .stream()
                                                 .map(Contribuinte::getNif)
                                                 .filter(nif -> nif.startsWith("I"))
                                                 .collect(Collectors.toList());

        for(int i = 0; i < 40; i++){
            String issuerNif = issuers.get(new Random().nextInt(issuers.size()));
            String clientNif = clients.get(new Random().nextInt(clients.size()));
            String description = issuerNif + " -> " + clientNif;
            float value = (float) Math.random() * 100;

            this.loggedInUser = this.contribuintes.get(issuerNif);
            try{
                /*Factura f =*/
                emitirFactura(clientNif, value, description);
                //changeFactura(f, EconSector.T_CODE_FAMILIA);
            }catch(NotEmpresaException | NoSuchIndividualException e){
                e.printStackTrace();
            }
        }
        this.loggedInUser = u;
    }

}
