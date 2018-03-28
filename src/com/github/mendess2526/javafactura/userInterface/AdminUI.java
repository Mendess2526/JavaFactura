package com.github.mendess2526.javafactura.userInterface;

import com.github.mendess2526.javafactura.efactura.JavaFactura;

public class AdminUI implements UI {

    private final JavaFactura javaFactura;

    AdminUI(JavaFactura javaFactura){
        this.javaFactura = javaFactura;
    }

    @Override
    public void run(){
        System.out.println("Logged in as admin");
    }
}
