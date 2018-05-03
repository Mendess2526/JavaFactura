package com.github.mendess2526.javafactura.gui;

import com.github.mendess2526.javafactura.efactura.JavaFactura;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import static com.github.mendess2526.javafactura.gui.MainFX.HEIGHT;
import static com.github.mendess2526.javafactura.gui.MainFX.WIDTH;

abstract class FX {

    final JavaFactura javaFactura;
    final Stage primaryStage;
    final Scene previousScene;
    final Scene scene;
    final GridPane gridPane;

    FX(JavaFactura javaFactura, Stage primaryStage, Scene previousScene){
        this.javaFactura = javaFactura;
        this.primaryStage = primaryStage;
        this.previousScene = previousScene;

        this.gridPane = new GridPane();
        this.gridPane.setAlignment(Pos.CENTER);
        this.gridPane.setHgap(10);
        this.gridPane.setVgap(10);
        this.gridPane.setPadding(new Insets(25,25,25,25));
//        ColumnConstraints constraints = new ColumnConstraints();
//        constraints.setPercentWidth(50);
//        this.gridPane.getColumnConstraints().add(constraints);
        this.scene = new Scene(this.gridPane, WIDTH, HEIGHT);
    }

    protected boolean show(){
        this.primaryStage.setScene(this.scene);
        return true;
    }

    protected void goBack(ActionEvent event){
        this.primaryStage.setScene(this.previousScene);
    }

    HBox makeHBox(Node node, Pos alignment){
        HBox hBox = new HBox(10);
        hBox.setAlignment(alignment);
        hBox.getChildren().add(node);
        return hBox;
    }
}
