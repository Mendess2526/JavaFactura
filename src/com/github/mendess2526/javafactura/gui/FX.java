package com.github.mendess2526.javafactura.gui;

import com.github.mendess2526.javafactura.efactura.JavaFactura;
import javafx.stage.Stage;

abstract class FX {

    final JavaFactura javaFactura;
    final Stage primaryStage;

    FX(JavaFactura javaFactura, Stage primaryStage){
        this.javaFactura = javaFactura;
        this.primaryStage = primaryStage;
    }
    abstract void run();
}
