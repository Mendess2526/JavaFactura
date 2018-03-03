package com.github.mendess2526.javafactura.efactura;

import com.github.mendess2526.javafactura.efactura.econSectors.EconSector;
import com.github.mendess2526.javafactura.efactura.econSectors.Pendente;
import com.github.mendess2526.javafactura.efactura.exceptions.*;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class JavaFactura{

    private Map<String,Contribuinte> contribuintes;

    private Map<Integer,Factura> faturas;

    private User loggedInUser;

    private String adminPassword;


    public JavaFactura(){
        this.contribuintes = loadContribuintes();
        this.faturas = loadFaturas();
        this.loggedInUser = null;
        this.adminPassword = loadAdminPassword();
    }

    public User getLoggedUser(){
        return this.loggedInUser;
    }

    private String loadAdminPassword(){
        String pass;
        try(BufferedReader reader = new BufferedReader(new FileReader("adminPass"))){
            pass = reader.readLine();
        }catch(IOException e){
            pass = "admin";
        }
        return pass;
    }

    public void setAdminPassword(String adminPassword){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("adminPass"))){
            writer.write(adminPassword);
            this.adminPassword = adminPassword;
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void login(String nif, String password) throws InvalidCredentialsException{
        if(nif.equals("admin") && password.equals(this.adminPassword)){
            this.loggedInUser = new Admin();
        }else{
            Contribuinte user = this.contribuintes.get(nif);
            if(user == null || ! user.getPassword().equals(password))
                throw new InvalidCredentialsException();
            this.loggedInUser = user;
        }
    }

    public void registarIndividual(String nif, String email, String nome, String address, String password,
                                   List<String> dependants, double fiscalCoefficient,
                                   Set<String> econActivities){
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
                dependants,
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

    public void changeFactura(String typeCode, int facturaID) throws InvalidFacturaTypeException{
        this.faturas.get(facturaID).setEconSector(EconSector.factory(typeCode));
    }

    public List<Factura> faturasOfEmpresa(String nifEmpresa, Comparator<Factura> comparator){
        return this.faturas.values()
                .stream()
                .filter(f -> f.getIssuerNif().equals(nifEmpresa))
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
        Map<String,Spending> spendingMap = new HashMap<>();
        this.faturas.values()
                .forEach(f -> {
                    if(spendingMap.containsKey(f.getClientNif()))
                        spendingMap.get(f.getClientNif()).add(f.getValue());
                    else
                        spendingMap.put(f.getClientNif(),new Spending(f.getClientNif(),f.getValue()));
                });
        List<Spending> orderedSpending = new ArrayList<>(spendingMap.values());
        orderedSpending.sort((s1,s2) ->Float.compare(s1.getVal(), s2.getVal()));
        List<Contribuinte> contribuintes = new ArrayList<>();
        int c = 0;
        for(Iterator<Spending> iterator = orderedSpending.iterator(); c < 10 && iterator.hasNext(); ){
            Spending s = iterator.next();
            c++;
            contribuintes.add(this.contribuintes.get(s.getNif()));
        }
        return contribuintes;
    }

    public void saveState(){

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
            contribuintes.put(nif,new ContribuinteIndividual(
                    nif,
                    email,
                    nome,
                    address,
                    password,
                    dependants,
                    fiscal_coefficient,
                    econActivities));

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

    private class Spending{

        private final String nif;
        private float val;

        Spending(String nif, float initialVal){
            this.nif = nif;
            this.val = initialVal;
        }

        String getNif(){
            return nif;
        }

        public float getVal(){
            return val;
        }
        public void add(float addedVal){
            this.val += addedVal;
        }
    }
}
