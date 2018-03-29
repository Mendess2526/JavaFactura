package com.github.mendess2526.javafactura.gui;

import com.github.mendess2526.javafactura.efactura.JavaFactura;
import javafx.stage.Stage;

class AdminAddInvidFX extends FX{

    AdminAddInvidFX(JavaFactura javaFactura, Stage primaryStage){
        super(javaFactura, primaryStage);
    }

    void run(){
        System.out.println("Add individual");
    }
}
