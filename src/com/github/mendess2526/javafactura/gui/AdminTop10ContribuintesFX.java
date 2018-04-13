package com.github.mendess2526.javafactura.gui;

import com.github.mendess2526.javafactura.efactura.JavaFactura;
import javafx.scene.Scene;
import javafx.stage.Stage;

class AdminTop10ContribuintesFX extends FX{

    private Scene scene;

    AdminTop10ContribuintesFX(JavaFactura javaFactura, Stage primaryStage, Scene previousScene){
        super(javaFactura, primaryStage, previousScene);
    }

    @Override
    Scene getScene(){
        return this.scene;
    }

}
