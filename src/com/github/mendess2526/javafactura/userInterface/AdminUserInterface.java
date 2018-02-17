package com.github.mendess2526.javafactura.userInterface;

import com.github.mendess2526.javafactura.efactura.JavaFactura;

public class AdminUserInterface implements UserInterface {

    private JavaFactura javaFactura;

    AdminUserInterface(JavaFactura javaFactura){
        this.javaFactura = javaFactura;
    }

    @Override
    public void run(){

    }
}
