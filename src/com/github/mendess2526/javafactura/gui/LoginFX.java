package com.github.mendess2526.javafactura.gui;

import com.github.mendess2526.javafactura.efactura.*;
import com.github.mendess2526.javafactura.efactura.exceptions.InvalidCredentialsException;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

class LoginFX extends FX {

    private final AdminFX adminScreen;
    private final IndividualFX individualScreen;
    private final EmpresaFX empresaScreen;
    private TextField userNameTextField;
    private PasswordField pwField;
    private Text errors;

    LoginFX(JavaFactura javaFactura, Stage primaryStage, Scene previousScene){
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

        this.errors = new Text();
        this.errors.setFill(Color.RED);
        this.gridPane.add(errors, 1, 6);
    }

    private void login(){
        try{
            this.errors.setText("");
            this.javaFactura.login(userNameTextField.getText(), pwField.getText());
            User loggedUser = this.javaFactura.getLoggedUser();
            if(loggedUser instanceof Admin){
                if(!this.adminScreen.show())
                    throw new InvalidCredentialsException();

            }else if(loggedUser instanceof ContribuinteEmpresarial){
                System.out.println("Contrib Empresarial login");

            }else if(loggedUser instanceof ContribuinteIndividual){
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
