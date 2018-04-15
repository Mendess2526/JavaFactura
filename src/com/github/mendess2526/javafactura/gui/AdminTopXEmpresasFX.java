package com.github.mendess2526.javafactura.gui;

import com.github.mendess2526.javafactura.efactura.ContribuinteEmpresarial;
import com.github.mendess2526.javafactura.efactura.JavaFactura;
import com.github.mendess2526.javafactura.efactura.collections.Pair;
import com.github.mendess2526.javafactura.efactura.exceptions.NotAdminException;
import com.sun.javafx.collections.ObservableListWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.mendess2526.javafactura.gui.Main.HEIGHT;
import static com.github.mendess2526.javafactura.gui.Main.WIDTH;

class AdminTopXEmpresasFX extends FX{

    private final TextField numberPrompt;
    private Label totalValue;
    private ObservableList<String> topX;

    AdminTopXEmpresasFX(JavaFactura javaFactura, Stage primaryStage, Scene previousScene){
        super(javaFactura, primaryStage, previousScene);

        // [LABEL] Insert Numero
        this.gridPane.add(new Label("Inserir numero") , 0, 0);

        // [TEXT_FIELD] Numero de empresas
        this.numberPrompt = new TextField();
        this.numberPrompt.setPromptText("Numero de empresas");
        this.gridPane.add(this.numberPrompt, 1, 0);

        // [BUTTON] Search button
        Button searchButton = new Button("Search");
        searchButton.setOnAction(this::fillList);
        this.gridPane.add(makeHBox(searchButton, Pos.CENTER), 3, 0);

        // [LABEL] Total Value
        this.gridPane.add(new Label("Total Value: "), 0, 1);
        this.totalValue = new Label(Double.toString(0));
        this.gridPane.add(totalValue, 1, 1);

        // [LIST_VIEW] Top X
        this.topX = FXCollections.observableArrayList();
        ListView<String> topX = new ListView<>(this.topX);
        topX.setFocusTraversable(false);
        this.gridPane.add(topX, 0, 2);

        // [BUTTON] Back button
        Button goBackButton = new Button("Back");
        goBackButton.setOnAction(this::goBack);
        this.gridPane.add(makeHBox(goBackButton, Pos.BOTTOM_RIGHT), 4, 2);
    }

    private void fillList(ActionEvent event){
        int num = Integer.parseInt(this.numberPrompt.getText());
        System.out.println("Getting top " + num + " companies");
        Pair<List<ContribuinteEmpresarial>,Double> listDoublePair;
        try{
            listDoublePair = this.javaFactura.getTopXEmpresas(num);
        }catch(NotAdminException e){
            goBack(null);
            return;
        }
        this.totalValue.setText(Double.toString(listDoublePair.snd()));
        this.topX.clear();
        this.topX.addAll(listDoublePair.fst()
                .stream()
                .map(ce -> ce.getNif() +"\t"+ ce.getName()) //TODO formatar isto melhor
                .collect(Collectors.toList()));
    }

    private void goBack(ActionEvent event){
        this.primaryStage.setScene(this.previousScene);
    }
}