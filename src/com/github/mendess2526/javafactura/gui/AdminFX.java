package com.github.mendess2526.javafactura.gui;

import com.github.mendess2526.javafactura.efactura.JavaFactura;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static com.github.mendess2526.javafactura.gui.Main.*;


public class AdminFX extends FX{

    private Scene scene;
    private final AdminAddIndividualFX addIndividual;
    private final AdminAddEmpresaFX addEmpresa;
    private final AdminTop10ContribuintesFX top10Contribuintes;
    private final AdminTopXEmpresasFX topXEmpresas;

    AdminFX(JavaFactura javaFactura, Stage primaryStage, Scene previousScene){
        super(javaFactura, primaryStage, previousScene);

        Button addIndividualButton = new Button("Add Contribuinte Individual");
        Button addEmpresaButton = new Button("Add Contribuinte Empresarial");
        Button top10ContribuintesButton = new Button("Top 10 Contribuintes");
        Button topXEmpresasButton = new Button("Top X Empresas");
        Button logOutButton = new Button("Log out");

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25,25,25,25));
        this.scene = new Scene(gridPane, WIDTH, HEIGHT);

        this.addIndividual = new AdminAddIndividualFX(this.javaFactura, this.primaryStage, this.scene);
        this.addEmpresa = new AdminAddEmpresaFX(this.javaFactura, this.primaryStage, this.scene);
        this.top10Contribuintes = new AdminTop10ContribuintesFX(this.javaFactura, this.primaryStage, this.scene);
        this.topXEmpresas = new AdminTopXEmpresasFX(this.javaFactura, primaryStage, this.scene);

        addIndividualButton.setOnAction(event -> primaryStage.setScene(this.addIndividual.getScene()));
        addEmpresaButton.setOnAction(event -> primaryStage.setScene(this.addEmpresa.getScene()));
        top10ContribuintesButton.setOnAction(event -> primaryStage.setScene(this.top10Contribuintes.getScene()));
        topXEmpresasButton.setOnAction(event -> primaryStage.setScene(this.topXEmpresas.getScene()));
        logOutButton.setOnAction(event -> logOut());

        Text sceneTitle = new Text("Admin");
        gridPane.add(sceneTitle, 0, 0, 2, 1);

        HBox hBoxAddIndividual = new HBox(10);
        hBoxAddIndividual.setAlignment(Pos.CENTER);
        hBoxAddIndividual.getChildren().add(addIndividualButton);
        gridPane.add(hBoxAddIndividual,0,1);

        HBox hBoxAddEmpresa = new HBox(10);
        hBoxAddEmpresa.setAlignment(Pos.CENTER);
        hBoxAddEmpresa.getChildren().add(addEmpresaButton);
        gridPane.add(hBoxAddEmpresa,0,2);

        HBox hBoxTop10Cont = new HBox(10);
        hBoxTop10Cont.setAlignment(Pos.CENTER);
        hBoxTop10Cont.getChildren().add(top10ContribuintesButton);
        gridPane.add(hBoxTop10Cont,0,3);

        HBox hBoxTopXEmp = new HBox(10);
        hBoxTopXEmp.setAlignment(Pos.CENTER);
        hBoxTopXEmp.getChildren().add(topXEmpresasButton);
        gridPane.add(hBoxTopXEmp,0,4);

        HBox hBoxLogOut = new HBox(10);
        hBoxLogOut.setAlignment(Pos.CENTER);
        hBoxLogOut.getChildren().add(logOutButton);
        gridPane.add(hBoxLogOut,0,5);

    }

    private void logOut(){
        this.primaryStage.setScene(this.previousScene);
    }

    @Override
    public Scene getScene(){
        return this.scene;
    }
}
