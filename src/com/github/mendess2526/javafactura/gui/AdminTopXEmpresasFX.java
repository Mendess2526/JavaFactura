package com.github.mendess2526.javafactura.gui;

import com.github.mendess2526.javafactura.efactura.ContribuinteEmpresarial;
import com.github.mendess2526.javafactura.efactura.JavaFactura;
import com.github.mendess2526.javafactura.efactura.collections.Pair;
import com.sun.javafx.collections.ObservableListWrapper;
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
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import static com.github.mendess2526.javafactura.gui.Main.HEIGHT;
import static com.github.mendess2526.javafactura.gui.Main.WIDTH;

class AdminTopXEmpresasFX extends FX{

    private final TextField numberPrompt;
    private final Scene scene;
    private Label totalValue;
    private ObservableList<ContribuinteEmpresarial> topX;

    AdminTopXEmpresasFX(JavaFactura javaFactura, Stage primaryStage, Scene previousScene){
        super(javaFactura, primaryStage, previousScene);
        this.numberPrompt = new TextField();

        Button searchButton = new Button("Search");
        searchButton.setOnAction(this::fillList);

        Button goBack = new Button("Back");
        //this.goBack.setOnAction(event -> );
        this.totalValue = new Label(Double.toString(0));

        this.topX = new ObservableListWrapper<>(new ArrayList<>());

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25,25,25,25));
        this.scene = new Scene(gridPane, WIDTH, HEIGHT);

        gridPane.add(new Label("Inserir numero") , 0, 0);

        gridPane.add(this.numberPrompt, 1, 0);

        gridPane.add(searchButton, 3, 0);

        ListView<ContribuinteEmpresarial> topX = new ListView<>();
        gridPane.add(new Label("Total Value: "), 0, 1);
        gridPane.add(totalValue, 1, 1);
        gridPane.add(topX, 0, 2);
    }

    private void fillList(ActionEvent event){
        int num = Integer.parseInt(this.numberPrompt.getText());
        System.out.println("Getting top " + num + " companies");
        Pair<List<ContribuinteEmpresarial>,Double> listDoublePair = this.javaFactura.getTopXEmpresas(num);
        listDoublePair.fst().forEach(e -> System.out.println(e.toString()));
        this.totalValue.setText(Double.toString(listDoublePair.snd()));
        this.topX.clear();
        this.topX.addAll(listDoublePair.fst());
    }

    @Override
    public Scene getScene(){
        return scene;
    }
}