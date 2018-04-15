package com.github.mendess2526.javafactura.gui;

import com.github.mendess2526.javafactura.efactura.JavaFactura;
import com.github.mendess2526.javafactura.efactura.exceptions.NotAdminException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;

class AdminTop10ContribuintesFX extends FX{

    private final ObservableList<String> top10;

    AdminTop10ContribuintesFX(JavaFactura javaFactura, Stage primaryStage, Scene previousScene){
        super(javaFactura, primaryStage, previousScene);

        this.top10 = FXCollections.observableArrayList();
        ListView<String> top10ListView = new ListView<>(this.top10);
        top10ListView.setFocusTraversable(false);
        this.gridPane.add(top10ListView,0,1);

        Button goBack = new Button("Back");
        goBack.setOnAction(this::goBack);
        this.gridPane.add(makeHBox(goBack, Pos.BOTTOM_RIGHT), 0, 2);
    }

    @Override
    boolean show(){
        this.top10.clear();
        List<String> contribuintes;
        try{
            contribuintes = this.javaFactura.getTop10Contrib().stream()
                    .map(c->c.getNif() + " " + c.getName())
                    .collect(Collectors.toList());
        }catch(NotAdminException e){
            return false;
        }
        this.top10.addAll(contribuintes);
        this.primaryStage.setScene(this.scene);
        return true;
    }

    private void goBack(ActionEvent event){
        this.primaryStage.setScene(this.previousScene);
    }
}
