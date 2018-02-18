package com.github.mendess2526.javafactura.userInterface;

import com.github.mendess2526.javafactura.efactura.JavaFactura;

public class ContribuinteEmpresarialUI implements UI {

    private JavaFactura javaFactura;

    public ContribuinteEmpresarialUI(JavaFactura javaFactura){

        this.javaFactura = javaFactura;
    }

    @Override
    public void run(){
        System.out.println("Logged in as Contribuinte Empresarial");
    }
}
