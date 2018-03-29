package com.github.mendess2526.javafactura.gui;

import com.github.mendess2526.javafactura.efactura.JavaFactura;
import javafx.stage.Stage;

class AdminTopXEmpresasFX extends FX{

    AdminTopXEmpresasFX(JavaFactura javaFactura, Stage primaryStage){
        super(javaFactura, primaryStage);
    }

    @Override
    void run(){
        System.out.println("Top X Empresas");
    }
}
