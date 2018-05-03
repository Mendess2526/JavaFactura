package com.github.mendess2526.javafactura.efactura;

import com.github.mendess2526.javafactura.efactura.collections.Pair;
import com.github.mendess2526.javafactura.efactura.econSectors.EconSector;
import com.github.mendess2526.javafactura.efactura.econSectors.Pendente;
import com.github.mendess2526.javafactura.efactura.exceptions.*;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class JavaFactura implements Serializable{

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
        this.contribuintes = generateContribuintes();
        generateFacturas();
        this.loggedInUser = null;
        this.admin = new Admin();
    }

    public User getLoggedUser(){
        return this.loggedInUser;
    }

    public void setAdminPassword(String newPassword){
        this.admin.setPassword(newPassword);
    }

    public void login(String nif, String password) throws InvalidCredentialsException{
        if(nif.equals("admin") && password.equals(this.admin.getPassword())){
            this.loggedInUser = this.admin;
        }else{
            Contribuinte user = this.contribuintes.get(nif);
            if(user == null || ! user.getPassword().equals(password))
                throw new InvalidCredentialsException();
            this.loggedInUser = user;
        }
    }

    public void logout(){
        this.loggedInUser = null;
    }

    public void registarIndividual(String nif, String email, String nome, String address, String password,
                                   int numDependants, List<String> dependants, double fiscalCoefficient,
                                   Set<String> econActivities) throws InvalidNumberOfDependantsException{
        Set<EconSector> econSectors = new HashSet<>();
        for(String econActivity: econActivities){
            EconSector econSector = EconSector.factory(econActivity);
            if(!(econSector instanceof Pendente)){
                econSectors.add(EconSector.factory(econActivity));
            }
        }
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
                                   double fiscalCoefficient, Set<String> econActivities){

        Set<EconSector> econSectors = new HashSet<>();
        for(String econActivity: econActivities){
            EconSector econSector = EconSector.factory(econActivity);
            if(!(econSector instanceof Pendente)){
                econSectors.add(EconSector.factory(econActivity));
            }
        }
        this.contribuintes.put(nif, new ContribuinteEmpresarial(
                nif,
                email,
                nome,
                address,
                password,
                fiscalCoefficient,
                econSectors));
    }

    public Factura emitirFactura(String companyNif, float value, String description) throws NotEmpresaException,
                                                                                         NotIndividualException{
        Factura f;
        try{
            ContribuinteEmpresarial company =
                    (ContribuinteEmpresarial) this.contribuintes.get(companyNif);
            try{
                f = company.issueFactura(
                        (ContribuinteIndividual) this.loggedInUser,
                        description,
                        value
                );
            }catch(ClassCastException e){
                throw new NotIndividualException();
            }
        }catch(ClassCastException e){
            throw new NotEmpresaException();
        }
        return f;
    }

    public void changeFactura(Factura factura, String typeCode) throws NotIndividualException{
        try{
            ((ContribuinteIndividual) this.loggedInUser)
                    .changeFatura(factura, EconSector.factory(typeCode));
        }catch(ClassCastException e){
            throw new NotIndividualException();
        }

    }

    public List<Factura> getLoggedUserFaturas() throws NotContribuinteException{
        if(!(this.loggedInUser instanceof Contribuinte))
            throw new NotContribuinteException();
        Contribuinte c = this.contribuintes.get(((Contribuinte) this.loggedInUser).getNif());
        return c.getFacturas();
    }

    public List<Factura> getFaturasOfEmpresa(String nifEmpresa, Comparator<Factura> comparator){
        return this.contribuintes.get(nifEmpresa).getFacturas()
                .stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    public List<Factura> getFaturasOfIndividual(String clientNIF, LocalDateTime from, LocalDateTime to) throws NotEmpresaException{
        if(!(this.loggedInUser instanceof ContribuinteEmpresarial)){
            throw new NotEmpresaException();
        }
        return this.contribuintes.get(clientNIF).getFacturas()
                .stream()
                .filter(f -> f.getCreationDate().isAfter(from))
                .filter(f -> f.getCreationDate().isBefore(to))
                .sorted()
                .collect(Collectors.toList());
    }

    public List<Factura> getFaturasOfIndividual(String clientNif, Comparator<Factura> comparator) throws NotEmpresaException{
        if(!(this.loggedInUser instanceof ContribuinteEmpresarial))
            throw new NotEmpresaException();
        return this.contribuintes.get(clientNif).getFacturas()
                .stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    public double totalFaturado(String companyNif, LocalDateTime from, LocalDateTime to) throws NotEmpresaException{
        return this.contribuintes.get(companyNif).getFacturas()
                .stream()
                .mapToDouble(Factura::getValue)
                .sum();
    }

    public List<Contribuinte> getTop10Contrib() throws NotAdminException{
        if(!(this.loggedInUser instanceof Admin)){
            throw new NotAdminException();
        }
        PriorityQueue<ContribuinteIndividual> top10
                = new PriorityQueue<>(10,new ComtribuinteSpendingComparator().reversed());
        this.contribuintes.values()
                .stream()
                .filter(c -> c instanceof ContribuinteIndividual)
                .forEach(c -> top10.add((ContribuinteIndividual) c));
        return top10.stream().limit(10).collect(Collectors.toList());
    }

    /*
    determinar a relação das X empresas que mais facturas emitiram em t_odo o sistema e o montante de
    deduções fiscais que as despesas registadas (dessas empresas) representam;
     */
    //TODO montate total
    public Pair<List<ContribuinteEmpresarial>,Double> getTopXEmpresas(int x) throws NotAdminException{
        if(!(this.loggedInUser instanceof Admin)) throw new NotAdminException();

        PriorityQueue<ContribuinteEmpresarial> topX =
                new PriorityQueue<>(x, new ContribuinteFacturaCountComparator().reversed());

        this.contribuintes.values().stream()
                .filter(c -> c instanceof ContribuinteEmpresarial)
                .forEach(c -> topX.add((ContribuinteEmpresarial) c));

        return new Pair<>(topX.stream().limit(x).collect(Collectors.toList()), 0.0);
    }

    public void saveState() throws IOException{
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(SAVE_STATE_FILE));
        os.writeObject(this);
        os.flush();
        os.close();
    }

    private Map<String,Contribuinte> generateContribuintes(){
        Map<String,Contribuinte> contribuintes = new HashMap<>();
        for(int i=0; i<5; i++){
            String nif = "I" + i;
            String email = "ci" + i + "@email.com";
            String nome = "ci" + i;
            String address = "Rua ci" + i;
            String password = "pass";
            List<String> dependants = new ArrayList<>();//TODO make some stubs here
            double fiscal_coefficient = Math.random();
            Set<EconSector> econActivities = new HashSet<>();
            try{
                contribuintes.put(nif,new ContribuinteIndividual(
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
        for(int i=0; i<3; i++){
            String nif = "E" + i;
            String email = "ce" + i + "@email.com";
            String nome = "ce" + i;
            String address = "Rua ce" + i;
            String password = "pass";
            double fiscal_coefficient = Math.random();
            Set<EconSector> econActivities = new HashSet<>();
            econActivities.add(new Pendente());
            contribuintes.put(nif,new ContribuinteEmpresarial(
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
                .filter(nif->nif.startsWith("E"))
                .collect(Collectors.toList());
        List<String> clients = this.contribuintes.values()
                .stream()
                .map(Contribuinte::getNif)
                .filter(nif->nif.startsWith("I"))
                .collect(Collectors.toList());

        for(int i=0; i<40; i++){
            String issuerNif = issuers.get(new Random().nextInt(issuers.size()));
            String clientNif = clients.get(new Random().nextInt(clients.size()));
            String description = issuerNif + " -> " + clientNif;
            float value = (float) Math.random() * 100;

            this.loggedInUser = this.contribuintes.get(clientNif);
            try{
                Factura f = emitirFactura(issuerNif,value,description);
                changeFactura(f, "E01");
            }catch(NotEmpresaException | NotIndividualException e){
                e.printStackTrace();
            }
        }
        this.loggedInUser = u;
    }

}
