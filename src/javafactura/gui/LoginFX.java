package javafactura.gui;

import javafactura.businessLogic.*;
import javafactura.businessLogic.exceptions.InvalidCredentialsException;
import javafactura.gui.admin.AdminFX;
import javafactura.gui.contribuinte.EmpresaFX;
import javafactura.gui.contribuinte.IndividualFX;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Class that represent the login screen
 */
public class LoginFX extends FX {

    /**
     * The admin sub screen
     */
    private final AdminFX adminScreen;
    /**
     * The individual sub screen
     */
    private final IndividualFX individualScreen;
    /**
     * The empresa sub screen
     */
    private final EmpresaFX empresaScreen;
    /**
     * User name text field
     */
    private final TextField userNameTextField;
    /**
     * Password password field
     */
    private final PasswordField pwField;
    /**
     * Error text
     */
    private final Text errors;

    /**
     * Constructor for a application window
     * @param javaFactura   The business logic instance
     * @param primaryStage  The stage where the window exists
     * @param previousScene The previous scene (null if this is the root window)
     */
    public LoginFX(JavaFactura javaFactura, Stage primaryStage, Scene previousScene){
        super(javaFactura, primaryStage, previousScene);
        this.adminScreen = new AdminFX(this.javaFactura, this.primaryStage, this.scene);
        this.individualScreen = new IndividualFX(this.javaFactura, this.primaryStage, this.scene);
        this.empresaScreen = new EmpresaFX(this.javaFactura, this.primaryStage, this.scene);

        Text sceneTitle = new Text("Login");
        this.gridPane.add(sceneTitle, 0, 0, 2, 1);

        this.userNameTextField = new TextField();
        this.gridPane.add(this.userNameTextField, 1, 1);

        Label userName = new Label("User Name");
        this.gridPane.add(userName, 0, 1);

        this.pwField = new PasswordField();
        this.gridPane.add(this.pwField, 1, 2);

        Label pw = new Label("Password");
        this.gridPane.add(pw, 0, 2);

        Button loginButton = new Button("Login");
        loginButton.setOnAction(event -> login());
        this.gridPane.add(makeHBox(loginButton, Pos.BOTTOM_RIGHT), 1, 4);

        Button exitButton = new Button("Exit");
        exitButton.setOnAction(event -> this.primaryStage.close());
        this.gridPane.add(makeHBox(exitButton, Pos.BOTTOM_LEFT), 0, 4);

        this.errors = new Text();
        this.errors.setFill(Color.RED);
        this.gridPane.add(errors, 1, 6);
    }

    /**
     * Attempts a login with the data in the text fields
     */
    private void login(){
        try{
            this.errors.setText("");
            this.javaFactura.login(userNameTextField.getText(), pwField.getText());
            Class<? extends User> loggedUser = this.javaFactura.getLoggedUserType();
            if(loggedUser == Admin.class){
                if(!this.adminScreen.show())
                    throw new InvalidCredentialsException();

            }else if(loggedUser == ContribuinteEmpresarial.class){
                if(!this.empresaScreen.show())
                    throw new InvalidCredentialsException();

            }else if(loggedUser == ContribuinteIndividual.class){
                if(!this.individualScreen.show())
                    throw new InvalidCredentialsException();

            }else{
                throw new InvalidCredentialsException();
            }
        }catch(InvalidCredentialsException e){
            this.errors.setText("Wrong username or password");
        }
    }
}
