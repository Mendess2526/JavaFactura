package com.github.mendess2526.javafactura.userInterface;

import com.github.mendess2526.javafactura.efactura.JavaFactura;

public class Main {

    public static void main(String[] args){
        new LoginUI(new JavaFactura()).run();
    }
}
