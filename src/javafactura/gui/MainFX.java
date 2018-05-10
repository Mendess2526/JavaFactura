package javafactura.gui;

import javafactura.businessLogic.JavaFactura;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainFX extends Application {

    static final int HEIGHT = 800;
    static final int WIDTH = 1000;

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
        loginScreen.show();
    }
}
