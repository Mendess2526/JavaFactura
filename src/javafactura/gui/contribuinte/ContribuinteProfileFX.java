package javafactura.gui.contribuinte;

import javafactura.businessLogic.Contribuinte;
import javafactura.businessLogic.JavaFactura;
import javafactura.businessLogic.User;
import javafactura.gui.FX;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;


public abstract class ContribuinteProfileFX extends FX {

    private final Text nif;
    private final Text email;
    private final TextField setEmail;
    private final Text name;
    private final Text address;
    private final PasswordField setPassword;
    private final TextField setAddress;
    private final HBox saveChangesBox;
    private final HBox backButtonBox;

    public ContribuinteProfileFX(JavaFactura javaFactura, Stage primaryStage,
                                 Scene previousScene){
        super(javaFactura, primaryStage, previousScene);

        int row = 0;
        this.gridPane.add(new Label("NIF"), 0, row);
        this.nif = new Text();
        this.gridPane.add(this.nif, 1, row++);

        this.gridPane.add(new Label("Email"), 0, row);
        this.email = new Text();
        this.gridPane.add(this.email, 1, row);
        this.setEmail = new TextField();
        this.gridPane.add(this.setEmail, 2, row++);

        this.gridPane.add(new Label("Name"), 0, row);
        this.name = new Text();
        this.gridPane.add(this.name, 1, row++);

        this.gridPane.add(new Label("Address"), 0, row);
        this.address = new Text();
        this.gridPane.add(this.address, 1, row);
        this.setAddress = new TextField();
        this.gridPane.add(this.setAddress, 2, row++);

        this.gridPane.add(new Label("Password"), 0, row);
        this.setPassword = new PasswordField();
        this.gridPane.add(this.setPassword, 2, row++);

        Button saveChangesButton = new Button("Gravar alterações");
        saveChangesButton.setOnAction(e -> saveChanges());
        this.saveChangesBox = makeHBox(saveChangesButton, Pos.BOTTOM_RIGHT);
        this.gridPane.add(this.saveChangesBox, 2, row++);

        Button goBackButton = new Button("Back");
        goBackButton.setOnAction(e -> goBack());
        this.backButtonBox = makeHBox(goBackButton, Pos.BOTTOM_RIGHT);
        this.gridPane.add(this.backButtonBox, 2, row);
    }

    @Override
    public boolean show(){
        User u = this.javaFactura.getLoggedUser();
        if(!(u instanceof Contribuinte)) return false;
        Contribuinte c = (Contribuinte) u;
        this.nif.setText(c.getNif());
        this.name.setText(c.getName());
        this.email.setText(c.getEmail());
        this.address.setText(c.getAddress());
        return super.show();
    }

    private void saveChanges(){
        if(this.setEmail.getText().length() > 0){
            this.javaFactura.changeEmail(this.setEmail.getText());
            this.email.setText(this.setEmail.getText());
            this.setEmail.setText("");
        }
        if(this.setAddress.getText().length() > 0){
            this.javaFactura.changeAddress(this.setAddress.getText());
            this.address.setText(this.setAddress.getText());
            this.setAddress.setText("");
        }
        if(this.setPassword.getText().length() > 4){
            this.javaFactura.changePassword(this.setPassword.getText());
            this.setPassword.setText("");
        }
    }

    protected Text appendField(String labelString){
        int saveChangesIdx = GridPane.getRowIndex(this.saveChangesBox);
        int backButtonIdx = GridPane.getRowIndex(this.backButtonBox);
        GridPane.setRowIndex(this.saveChangesBox, saveChangesIdx + 1);
        GridPane.setRowIndex(this.backButtonBox, backButtonIdx + 1);
        Label label = new Label(labelString);
        label.setAlignment(Pos.TOP_LEFT);
        label.setTextAlignment(TextAlignment.CENTER);
        this.gridPane.add(label, 0, saveChangesIdx);
        Text value = new Text();
        value.setTextOrigin(VPos.BOTTOM);
        this.gridPane.add(value, 1, saveChangesIdx);
        return value;
    }
}
