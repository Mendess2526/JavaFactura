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

    private final Map<Integer,Factura> faturas;

    private User loggedInUser;

    private final Admin admin;


    private JavaFactura(){
        this.contribuintes = loadContribuintes();
        this.faturas = loadFaturas();
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

    public void emitirFactura(String companyNif, float value, String description) throws NotEmpresaException,
                                                                                         NotIndividualException{
        Contribuinte company = this.contribuintes.get(companyNif);
        if(company instanceof ContribuinteEmpresarial){
            if(this.loggedInUser instanceof ContribuinteIndividual){
                Factura f = ((ContribuinteEmpresarial) company).issueFactura(
                        ((ContribuinteIndividual) this.loggedInUser).getNif(),
                        description,
                        value
                );
                this.faturas.put(f.getId(),f);
            }else {
                throw new NotIndividualException();
            }
        }else{
            throw new NotEmpresaException();
        }
    }

    public void changeFactura(String typeCode, int facturaID){
        this.faturas.get(facturaID).setEconSector(EconSector.factory(typeCode));
    }

    public List<Factura> faturasOfEmpresa(String nifEmpresa, Comparator<Factura> comparator){
        return this.faturas.values()
                .stream()
                .filter(f ->f.getIssuerNif().equals(nifEmpresa))
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    public List<Factura> getFaturas(String clientNIF, LocalDateTime from, LocalDateTime to) throws NotEmpresaException{
        if(!(this.loggedInUser instanceof ContribuinteEmpresarial)){
            throw new NotEmpresaException();
        }
        return this.faturas.values()
                .stream()
                .filter(f -> f.getClientNif().equals(clientNIF))
                .filter(f -> f.getDate().isAfter(from))
                .filter(f -> f.getDate().isBefore(to))
                .collect(Collectors.toList());
    }

    public List<Factura> getFaturas(String clientNif, Comparator<Factura> comparator) throws NotEmpresaException{
        if(!(this.loggedInUser instanceof ContribuinteEmpresarial))
            throw new NotEmpresaException();
        return this.faturas.values()
                .stream()
                .filter(f -> f.getClientNif().equals(clientNif))
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    public double totalFaturado() throws NotEmpresaException{
        if(!(this.loggedInUser instanceof ContribuinteEmpresarial))
            throw new NotEmpresaException();
        return this.faturas.values()
                .stream()
                .mapToDouble(Factura::getValue)
                .sum();
    }

    public List<Contribuinte> getTop10Contrib() throws NotAdminException{
        if(!(this.loggedInUser instanceof Admin)){
            throw new NotAdminException();
        }
        Map<String,Pair<String,Double>> spendingMap = new HashMap<>();
        this.faturas.values()
                .forEach(f -> {
                    double value = f.getValue();
                    if(spendingMap.containsKey(f.getClientNif())){
                        value = value + spendingMap.get(f.getClientNif()).snd();
                    }
                    spendingMap.put(f.getClientNif(), new Pair<>(f.getClientNif(), value));
                });
        List<Pair<String,Double>> orderedPairNifValue = new ArrayList<>(spendingMap.values());
        orderedPairNifValue.sort(Comparator.comparingDouble(Pair::snd));
        List<Contribuinte> contribuintes = new ArrayList<>();
        int c = 0;
        for(Iterator<Pair<String,Double>> iterator = orderedPairNifValue.iterator(); c < 10 && iterator.hasNext(); ){
            Pair<String,Double> s = iterator.next();
            c++;
            contribuintes.add(this.contribuintes.get(s.fst()));
        }
        return contribuintes;
    }

    /*
    determinar a relação das X empresas que mais facturas emitiram em t_odo o sistema e o montante de
    deduções fiscais que as despesas registadas (dessas empresas) representam;
     */
    //TODO montate total
    public Pair<List<ContribuinteEmpresarial>,Double> getTopXEmpresas(int x){
        // Create a "table" of the receipts of each company
        Map<String,List<Factura>> facturasEmpresas = new HashMap<>();
        this.faturas.values().forEach(f -> {
            if(!facturasEmpresas.containsKey(f.getIssuerNif())){
                facturasEmpresas.put(f.getIssuerNif(),new ArrayList<>());
            }
            facturasEmpresas.get(f.getIssuerNif()).add(f);
        });

        // Create a list of pairs (Company, #Receipts)
        List<Pair<String,Integer>> pairNifValueList = new ArrayList<>();
        facturasEmpresas.forEach((key, value)->pairNifValueList.add(new Pair<>(key, value.size())));

        // Sort it according to the #Receipts
        pairNifValueList.sort(Comparator.comparingInt(Pair::snd));

        // Get the top 10 companies
        List<ContribuinteEmpresarial> topX = new ArrayList<>(x);
        Iterator<Pair<String,Integer>> it = pairNifValueList.iterator();
        int i=0;
        while(i<x && it.hasNext()){
            String nif = it.next().fst();
            try{
                topX.add(((ContribuinteEmpresarial) this.contribuintes.get(nif)).clone());
            }catch(ClassCastException ignored){}
        }


        return new Pair<>(topX,0.0);
    }

    public void saveState() throws IOException{
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(SAVE_STATE_FILE));
        os.writeObject(this);
        os.flush();
        os.close();
    }

    private Map<String,Contribuinte> loadContribuintes(){
        Map<String,Contribuinte> contribuintes = new HashMap<>();
        for(int i=0; i<20; i++){
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
        for(int i=0; i<10; i++){
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

    private Map<Integer,Factura> loadFaturas(){
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
        List<Factura> faturas = new ArrayList<>();
        for(int i=0; i<40; i++){
            String issuerNif = issuers.get(new Random().nextInt(issuers.size()));
            String issuerName = this.contribuintes.get(issuerNif).getName();
            String clientNif = clients.get(new Random().nextInt(clients.size()));
            String description = issuerNif + " -> " + clientNif;
            float value = (float) Math.random() * 100;
            faturas.add(new Factura(
                    issuerNif,
                    issuerName,
                    LocalDateTime.now(),
                    clientNif,
                    description,
                    value,
                    new Pendente()));
        }
        return faturas
                .stream()
                .collect(Collectors.toMap(Factura::getId, f->f, (a, b)->b));
    }

}
