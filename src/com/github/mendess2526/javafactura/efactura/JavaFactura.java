package com.github.mendess2526.javafactura.efactura;

import com.github.mendess2526.javafactura.efactura.exceptions.InvalidCredentialsException;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class JavaFactura {

    private Map<String,Contribuinte> contribuintes;

    private List<Factura> facturas;

    private String loggedInUser;

    private String adminPassword;

    private boolean isAdmin;

    public JavaFactura(){
        this.contribuintes = loadContribuintes();
        this.facturas = loadFacturas();
        this.isAdmin = false;
        this.adminPassword = getAdminPassword();

    }

    private String getAdminPassword(){
        return "admin";//TODO read from file
    }

    public void setAdminPassword(String adminPassword){
        this.adminPassword = adminPassword;
        //TODO update file
    }

    public boolean isAdmin(){
        return isAdmin;
    }

    public void login(String nif, String password) throws InvalidCredentialsException{
        if(nif.equals("admin") && password.equals(this.adminPassword)){
            this.isAdmin = true;
        }else{
            Contribuinte user = this.contribuintes.get(nif);
            if(user == null || ! user.getPassword().equals(password))
                throw new InvalidCredentialsException();
            this.loggedInUser = nif;
        }
    }

    private Map<String,Contribuinte> loadContribuintes(){
        Map<String,Contribuinte> contribuintes = new HashMap<>();
        for(int i=0; i<20; i++){
            String nif = "I" + i;
            String email = "ci" + i + "@email.com";
            String nome = "ci" + i;
            String address = "Rua ci" + i;
            String password = "pass";
            int depend_num = i % 4;
            List<String> dependants = new ArrayList<>();//TODO make some stubs here
            double fiscal_coeficient = Math.random();
            EnumSet<EconActivity> econActivities = EnumSet.allOf(EconActivity.class);//TODO maybe randomize this
            contribuintes.put(nif,new ContribuinteIndividual(
                    nif,
                    email,
                    nome,
                    address,
                    password,
                    depend_num,
                    dependants,
                    fiscal_coeficient,
                    econActivities));

        }
        for(int i=0; i<10; i++){
            String nif = "E" + i;
            String email = "ce" + i + "@email.com";
            String nome = "ce" + i;
            String address = "Rua ce" + i;
            String password = "pass";
            double fiscal_coeficient = Math.random();
            EconActivity econActivity = EconActivity.values()[new Random().nextInt(EconActivity.values().length)];
            EnumSet<EconActivity> econActivities = EnumSet.of(econActivity);
            contribuintes.put(nif,new ContribuinteEmpresarial(
                    nif,
                    email,
                    nome,
                    address,
                    password,
                    fiscal_coeficient,
                    econActivities
            ));

        }
        return contribuintes;
    }

    private List<Factura> loadFacturas(){
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
        List<Factura> facturas = new ArrayList<>();
        for(int i=0; i<40; i++){
            String issuerNif = issuers.get(new Random().nextInt(issuers.size()));
            String issuerName = this.contribuintes.get(issuerNif).getName();
            String clientNif = clients.get(new Random().nextInt(clients.size()));
            String description = issuerNif + " -> " + clientNif;
//            EconActivity type = null;
            float value = (float) Math.random() * 100;
            facturas.add(new Factura(
                    issuerNif,
                    issuerName,
                    LocalDateTime.now(),
                    clientNif,
                    description,
                    null,
                    value));
        }
        return facturas;
    }
}
