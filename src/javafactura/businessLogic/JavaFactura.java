package javafactura.businessLogic;

import javafactura.businessLogic.collections.Pair;
import javafactura.businessLogic.comparators.ContribuinteFacturaCountComparator;
import javafactura.businessLogic.comparators.ContribuinteSpendingComparator;
import javafactura.businessLogic.comparators.FacturaValorComparator;
import javafactura.businessLogic.econSectors.EconSector;
import javafactura.businessLogic.econSectors.Pendente;
import javafactura.businessLogic.exceptions.*;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

public class JavaFactura implements Serializable {

    private static final long serialVersionUID = -1056267908102555293L;
    private static final String SAVE_STATE_FILE = "javaFactura.dat";
    private final Map<String,Contribuinte> contribuintes;
    private final Admin admin;
    private User loggedInUser;

    private JavaFactura(){
        this.contribuintes = new HashMap<>();
        generateContribuintes();
        generateFacturas();
        this.loggedInUser = null;
        this.admin = new Admin();
    }

    public static JavaFactura loadState(){
        JavaFactura javaFactura;
        try(ObjectInputStream is = new ObjectInputStream(new FileInputStream(SAVE_STATE_FILE))){
            javaFactura = (JavaFactura) is.readObject();
            javaFactura.loggedInUser = null;
        }catch(IOException | ClassNotFoundException e){
            javaFactura = new JavaFactura();
        }
        javaFactura.contribuintes.values()
                                 .stream()
                                 .sorted(Comparator.comparingInt(c -> Integer.parseInt(c.getNif())))
                                 .forEachOrdered(System.out::println);
        return javaFactura;
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
                                   float fiscalCoefficient, Collection<String> dependants, int numDependants,
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

    public void registarEmpresarial(String nif, String email, String nome,
                                    String address, String password,
                                    float fiscalCoefficient,
                                    Set<EconSector> econSectors) throws
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

    public Factura emitirFactura(String clientNif, float value, String description) throws
                                                                                    NotEmpresaException,
                                                                                    NoSuchIndividualException{
        if(!(this.loggedInUser instanceof ContribuinteEmpresarial)) throw new NotEmpresaException();

        Contribuinte client = this.contribuintes.get(clientNif);
        if(client == null || !(client instanceof ContribuinteIndividual)) throw new NoSuchIndividualException();

        return ((ContribuinteEmpresarial) this.loggedInUser)
                .issueFactura((ContribuinteIndividual) client, description, value);
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

    public List<Factura> getLoggedUserFacturas(Comparator<Factura> comparator, LocalDate from, LocalDate to)
            throws NotContribuinteException{
        if(!(this.loggedInUser instanceof Contribuinte)) throw new NotContribuinteException();
        return ((Contribuinte) this.loggedInUser).getFacturas()
                                                 .stream()
                                                 .filter(f -> f.getCreationDate().isAfter(from.atStartOfDay()))
                                                 .filter(f -> f.getCreationDate().isBefore(to.atTime(LocalTime.MAX)))
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
        os.close(); //Close ja faz flush [ObjectOutputStream.java:740]
    }

    private void generateContribuintes(){
        String[] names = new String[]{
                "Zero", "Um", "Dois", "Três", "Quatro", "Cinco", "Seis", "Sete", "Oito", "Nove",
                };
        Random r = new Random();
        Set<EconSector> allSectors = getAllSectors();
        Iterator<EconSector> it = allSectors.iterator();
        for(int i = 0; i < 100; i++){ //Change this to change the amount of people
            if(!it.hasNext()) it = allSectors.iterator();
            int j = i;
            StringBuilder nameBuilder = new StringBuilder();
            do{
                nameBuilder.insert(0, names[j % 10] + " ");
                j /= 10;
            }
            while(j > 0);
            String name = nameBuilder.toString();
            String nif = String.valueOf(i);
            //noinspection SpellCheckingInspection
            String email = String.format("%s%s%s", i % 2 == 1 ? "indiv." : "empr.",
                                         name.toLowerCase().replace(" ", ""), "@email.com");
            String address = "Rua " + name;
            String pass = "pass";
            float fiscalCoefficient = r.nextFloat() % 0.3f;
            // Escolhe setores aleatoriamente
            Set<EconSector> econSectors = new HashSet<>();
            j = i;
            for(EconSector allSector : allSectors)
                if(r.nextInt(10) > ((i % 2 == 1) ? 5 : j++)) econSectors.add(allSector);
            if(econSectors.isEmpty()) econSectors.add(it.next());
            if(i % 2 == 1){
                try{
                    List<String> aggregate = new ArrayList<>();
                    //Adiciona todos os individuais anteriores ao agregado
                    for(j = i - 2; j > 0; j -= 2) aggregate.add(String.valueOf(j));
                    int numDependants = aggregate.size() > 0 ? r.nextInt(aggregate.size()) : 0;
                    registarIndividual(nif, email, name, address, pass, fiscalCoefficient, aggregate, numDependants,
                                       econSectors);
                }catch(InvalidNumberOfDependantsException | IndividualAlreadyExistsException e){
                    e.printStackTrace();
                }
            }else{
                try{
                    registarEmpresarial(nif, email, name, address, pass, fiscalCoefficient, econSectors);
                }catch(EmpresarialAlreadyExistsException e){
                    e.printStackTrace();
                }
            }
        }
    }

    private void generateFacturas(){
        User u = this.loggedInUser;
        List<String> issuers = this.contribuintes.values()
                                                 .stream()
                                                 .filter(ContribuinteEmpresarial.class::isInstance)
                                                 .map(Contribuinte::getNif)
                                                 .collect(Collectors.toList());
        List<String> clients = this.contribuintes.values()
                                                 .stream()
                                                 .filter(ContribuinteIndividual.class::isInstance)
                                                 .map(Contribuinte::getNif)
                                                 .collect(Collectors.toList());

        Random r = new Random();
        for(int i = 0; i < 40; i++){
            String issuerNif = issuers.get(new Random().nextInt(issuers.size()));
            String clientNif = clients.get(new Random().nextInt(clients.size()));
            String description = issuerNif + " -> " + clientNif;
            float value = r.nextFloat() * 500;

            this.loggedInUser = this.contribuintes.get(issuerNif);
            try{
                Factura f = emitirFactura(clientNif, value, description);
                if(value % 3 == 0) changeFactura(f, (EconSector) f.getPossibleEconSectors().toArray()[0]);
            }catch(NotContribuinteException | NoSuchIndividualException | InvalidEconSectorException e){
                e.printStackTrace();
            }
        }
        this.loggedInUser = u;
    }
}
