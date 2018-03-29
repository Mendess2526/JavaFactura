package com.github.mendess2526.javafactura.gui;

import com.github.mendess2526.javafactura.efactura.JavaFactura;
import javafx.stage.Stage;

class AdminAddEmpreFX extends FX{

    AdminAddEmpreFX(JavaFactura javaFactura, Stage primaryStage){
        super(javaFactura, primaryStage);
    }

    @Override
    void run(){
        System.out.println("Add Empresarial");
    }
}
