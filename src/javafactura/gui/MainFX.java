package javafactura.gui;

import javafactura.businessLogic.JavaFactura;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainFX extends Application {

    static final int HEIGHT = 700;
    static final int WIDTH = 1300;
    private final JavaFactura javaFactura;

    public static void main(String[] args){
        launch(args);
    }

    public MainFX(){
        this.javaFactura = JavaFactura.loadState();
    }

    @Override
    public void start(Stage primaryStage){
        primaryStage.setTitle("eFactura");

        primaryStage.setMinHeight(HEIGHT);
        primaryStage.setMinWidth(WIDTH);
        primaryStage.centerOnScreen();

        primaryStage.show();

        LoginFX loginScreen = new LoginFX(this.javaFactura, primaryStage, null);
        loginScreen.show();
    }

    @Override
    public void stop() throws Exception{
        this.javaFactura.saveState();
        super.stop();
    }
}
