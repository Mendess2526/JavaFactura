package com.github.mendess2526.javafactura.gui;

import com.github.mendess2526.javafactura.efactura.Factura;
import com.github.mendess2526.javafactura.efactura.JavaFactura;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class IndividualViewFacturaFX extends FX {

    private Factura factura;

    IndividualViewFacturaFX(JavaFactura javaFactura, Stage primaryStage, Scene previousScene){
        super(javaFactura, primaryStage, previousScene);

    }

    public void setFactura(Factura factura){
        this.factura = factura;
    }
}
