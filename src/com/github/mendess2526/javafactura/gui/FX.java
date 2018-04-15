package com.github.mendess2526.javafactura.gui;

import com.github.mendess2526.javafactura.efactura.JavaFactura;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import static com.github.mendess2526.javafactura.gui.Main.HEIGHT;
import static com.github.mendess2526.javafactura.gui.Main.WIDTH;

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
        this.scene = new Scene(this.gridPane, WIDTH, HEIGHT);
    }

    boolean show(){
        this.primaryStage.setScene(this.scene);
        return true;
    }

    HBox makeHBox(Node node, Pos alignment){
        HBox hBox = new HBox(10);
        hBox.setAlignment(alignment);
        hBox.getChildren().add(node);
        return hBox;
    }
}
