package javafactura.gui;

import javafactura.businessLogic.JavaFactura;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The main class
 */
public class MainFX extends Application {

    /**
     * Application window minimum height
     */
    static final int HEIGHT = 700;
    /**
     * Application window minimum width
     */
    static final int WIDTH = 1300;
    /**
     * The business logic instance
     */
    private final JavaFactura javaFactura;

    /**
     * Constructor for main class
     */
    public MainFX(){
        this.javaFactura = JavaFactura.loadState();
    }

    public static void main(String[] args){
        launch(args);
    }

    /** {@inheritDoc} */
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

    /** {@inheritDoc} */
    @Override
    public void stop() throws Exception{
        this.javaFactura.saveState();
        super.stop();
    }
}
