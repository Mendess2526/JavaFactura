package com.github.mendess2526.javafactura.gui;

import com.github.mendess2526.javafactura.efactura.Factura;
import com.github.mendess2526.javafactura.efactura.JavaFactura;
import com.github.mendess2526.javafactura.efactura.econSectors.Pendente;
import com.github.mendess2526.javafactura.efactura.exceptions.NotContribuinteException;
import com.sun.javafx.collections.ObservableListWrapper;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.util.ArrayList;

class IndividualFX extends FX{

    private ObservableList<Factura> facturas;
    private long numPendentes;

    IndividualFX(JavaFactura javaFactura, Stage primaryStage, Scene previousScene){
        super(javaFactura, primaryStage, previousScene);
        this.facturas = new ObservableListWrapper<>(new ArrayList<>());
        this.numPendentes = this.facturas
                .stream().filter(f -> f.getType() instanceof Pendente).count();

        ListView<Factura> listView = new ListView<>(this.facturas);
        this.gridPane.add(listView,0,1);

        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(this::logOut);
        this.gridPane.add(makeHBox(logoutButton, Pos.BOTTOM_RIGHT),0,1);
    }

    @Override
    protected boolean show(){
        try{
            this.facturas.clear();
            this.facturas.addAll(this.javaFactura.getLoggedUserFaturas());
        }catch(NotContribuinteException e){
            goBack(null);
            return false;
        }
        super.show();
        return true;
    }

    private void logOut(ActionEvent event){
        this.javaFactura.logout();
        this.primaryStage.setScene(this.previousScene);
    }

    @Override
    protected void goBack(ActionEvent event){
        throw new UnsupportedOperationException();
    }
}
