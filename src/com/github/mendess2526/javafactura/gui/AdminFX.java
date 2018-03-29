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


public class AdminFX extends FX{
    private Button addInvid;
    private Button addEmpre;
    private Button top10Contr;
    private Button topXEmpres;

    AdminFX(JavaFactura javaFactura, Stage primaryStage){
        super(javaFactura, primaryStage);
        this.addInvid = new Button("Add Contribuinte Individual");
        this.addInvid.setOnAction(event -> new AdminAddInvidFX(this.javaFactura,this.primaryStage).run());
        this.addEmpre = new Button("Add Contribuinte Empresarial");
        this.addEmpre.setOnAction(event -> new AdminAddEmpreFX(this.javaFactura,this.primaryStage).run());
        this.top10Contr = new Button("Top 10 Contribuintes");
        this.top10Contr.setOnAction(event -> new AdminTop10ContrFX(this.javaFactura,this.primaryStage).run());
        this.topXEmpres = new Button("Top X Empresas");
        this.topXEmpres.setOnAction(event -> new AdminTopXEmpresasFX(this.javaFactura, this.primaryStage).run());
    }

    public void run(){
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25,25,25,25));
        Scene scene = new Scene(gridPane, 300, 275);
        this.primaryStage.setScene(scene);

        Text sceneTitle = new Text("Admin");
        gridPane.add(sceneTitle, 0, 0, 2, 1);

        HBox hBoxAddIndiv = new HBox(10);
        hBoxAddIndiv.setAlignment(Pos.BOTTOM_RIGHT);
        hBoxAddIndiv.getChildren().add(this.addInvid);
        gridPane.add(hBoxAddIndiv,0,1);

        HBox hBoxAddEmpres = new HBox(10);
        hBoxAddEmpres.setAlignment(Pos.BOTTOM_RIGHT);
        hBoxAddEmpres.getChildren().add(this.addEmpre);
        gridPane.add(hBoxAddEmpres,0,2);

        HBox hBoxTop10Cont = new HBox(10);
        hBoxTop10Cont.setAlignment(Pos.BOTTOM_RIGHT);
        hBoxTop10Cont.getChildren().add(this.top10Contr);
        gridPane.add(hBoxTop10Cont,0,3);

        HBox hBoxTopXEmp = new HBox(10);
        hBoxTopXEmp.setAlignment(Pos.BOTTOM_RIGHT);
        hBoxTopXEmp.getChildren().add(this.topXEmpres);
        gridPane.add(hBoxTopXEmp,0,4);
    }
}
