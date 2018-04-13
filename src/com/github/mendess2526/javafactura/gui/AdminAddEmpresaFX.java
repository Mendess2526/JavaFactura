package com.github.mendess2526.javafactura.gui;

import com.github.mendess2526.javafactura.efactura.JavaFactura;
import javafx.scene.Scene;
import javafx.stage.Stage;

class AdminAddEmpresaFX extends FX{

    private Scene scene;

    AdminAddEmpresaFX(JavaFactura javaFactura, Stage primaryStage, Scene previousScene){
        super(javaFactura, primaryStage, previousScene);
    }

    @Override
    public Scene getScene(){
        return this.scene;
    }
}
