package com.github.mendess2526.javafactura.gui;

import com.github.mendess2526.javafactura.efactura.JavaFactura;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {


    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        primaryStage.setTitle("eFactura");

        /*Button b = new Button("Click me");

        StackPane layout = new StackPane();
        layout.getChildren().add(b);

        Scene scene = new Scene(layout, 300, 250);
        primaryStage.setScene(scene);*/
        primaryStage.show();

        new LoginFX(JavaFactura.loadState(), primaryStage).run();

    }
}
