package com.github.mendess2526.javafactura.userInterface;

import com.github.mendess2526.javafactura.efactura.JavaFactura;

public class ContribuinteIndividualUI implements UI {

    private final JavaFactura javaFactura;

    public ContribuinteIndividualUI(JavaFactura javaFactura){
        this.javaFactura = javaFactura;
    }

    @Override
    public void run(){
        System.out.println("Logged in as Contribuinte Individual");
    }
}
