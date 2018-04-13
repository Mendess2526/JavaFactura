package com.github.mendess2526.javafactura.gui;

import com.github.mendess2526.javafactura.efactura.*;
import com.github.mendess2526.javafactura.efactura.exceptions.InvalidCredentialsException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static com.github.mendess2526.javafactura.gui.Main.HEIGHT;
import static com.github.mendess2526.javafactura.gui.Main.WIDTH;

public class LoginFX extends FX{

    private final AdminFX adminScreen;
    private TextField userNameTextField;
    private PasswordField pwField;
    private Text errors;
    private Scene scene;

    LoginFX(JavaFactura javaFactura, Stage primaryStage, Scene previousScene){
        super(javaFactura, primaryStage, previousScene);
        this.userNameTextField = new TextField();
        this.pwField = new PasswordField();
        this.adminScreen = new AdminFX(this.javaFactura, this.primaryStage, this.scene);

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));

        this.scene = new Scene(gridPane, WIDTH, HEIGHT);

        Text sceneTitle = new Text("Login");
        gridPane.add(sceneTitle, 0, 0, 2, 1);

        Label userName = new Label("User Name");
        gridPane.add(userName,0,1);

        gridPane.add(this.userNameTextField, 1, 1);

        Label pw = new Label("Password");
        gridPane.add(pw,0,2);

        gridPane.add(this.pwField, 1,2);

        Button button = new Button("Login");
        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.BOTTOM_RIGHT);
        hBox.getChildren().add(button);
        gridPane.add(hBox, 1, 4);

        this.errors = new Text();
        gridPane.add(errors, 1, 6);
        button.setOnAction(event->login());
    }

    private void login(){
        try{
            this.javaFactura.login(userNameTextField.getText(),pwField.getText());
            User loggedUser = this.javaFactura.getLoggedUser();
            if(loggedUser instanceof Admin){
                this.primaryStage.setScene(this.adminScreen.getScene());
            }else if(loggedUser instanceof ContribuinteEmpresarial){
                System.out.println("Contrib Empresarial login");
            }else if(loggedUser instanceof ContribuinteIndividual){
                System.out.println("Contrib Individual login");
            }else{
                assert false;
            }
        }catch(InvalidCredentialsException e){
            this.errors.setText("Wrong username or password");
        }
    }

    public Scene getScene(){
        return this.scene;
    }
}
