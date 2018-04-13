package com.github.mendess2526.javafactura.gui;

import com.github.mendess2526.javafactura.efactura.JavaFactura;
import javafx.scene.Scene;
import javafx.stage.Stage;

class AdminAddIndividualFX extends FX{

    private Scene scene;

    AdminAddIndividualFX(JavaFactura javaFactura, Stage primaryStage, Scene previousScene){
        super(javaFactura, primaryStage, previousScene);
    }

    void run(){
        System.out.println("Add individual");
    }

    @Override
    public Scene getScene(){
        return this.scene;
    }
}
