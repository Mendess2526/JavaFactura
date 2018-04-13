package com.github.mendess2526.javafactura.gui;

import com.github.mendess2526.javafactura.efactura.JavaFactura;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    static final int HEIGHT = 480;
    static final int WIDTH = 600;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        primaryStage.setTitle("eFactura");

        primaryStage.setMinHeight(HEIGHT);
        primaryStage.setMinWidth(WIDTH);
        primaryStage.centerOnScreen();

        primaryStage.show();

        JavaFactura javaFactura = JavaFactura.loadState();
        LoginFX loginScreen = new LoginFX(javaFactura, primaryStage, null);
        primaryStage.setScene(loginScreen.getScene());
    }
}
