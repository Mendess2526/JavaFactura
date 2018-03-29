package com.github.mendess2526.javafactura.gui;

import com.github.mendess2526.javafactura.efactura.JavaFactura;
import javafx.stage.Stage;

class AdminTop10ContrFX extends FX{

    AdminTop10ContrFX(JavaFactura javaFactura, Stage primaryStage){
        super(javaFactura, primaryStage);
    }

    @Override
    void run(){
        System.out.println("Top 10 contrib");
    }
}
