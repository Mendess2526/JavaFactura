package com.github.mendess2526.javafactura.gui;

import com.github.mendess2526.javafactura.efactura.JavaFactura;
import javafx.scene.Scene;
import javafx.stage.Stage;

abstract class FX {

    final JavaFactura javaFactura;
    final Stage primaryStage;
    final Scene previousScene;

    FX(JavaFactura javaFactura, Stage primaryStage, Scene previousScene){
        this.javaFactura = javaFactura;
        this.primaryStage = primaryStage;
        this.previousScene = previousScene;
    }
    abstract Scene getScene();
}
