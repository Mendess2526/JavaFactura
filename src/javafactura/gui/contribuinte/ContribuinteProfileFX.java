package javafactura.gui.contribuinte;

import javafactura.businessLogic.Contribuinte;
import javafactura.businessLogic.JavaFactura;
import javafactura.businessLogic.User;
import javafactura.businessLogic.econSectors.EconSector;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 * Class that represents a {@link Contribuinte} profile page
 */
public abstract class ContribuinteProfileFX extends FX {

    /**
     * The nif
     */
    private final Text nif;
    /**
     * The email
     */
    private final Text email;
    /**
     * The set email field
     */
    private final TextField setEmail;
    /**
     * The name field
     */
    private final Text name;
    /**
     * The address field
     */
    private final Text address;
    /**
     * The fiscal Coefficient field
     */
    private final Text fiscalCoefficient;
    /**
     * The {@link EconSector}s list
     */
    private final Text econSectors;
    /**
     * The change password field
     */
    private final PasswordField setPassword;
    /**
     * The password changing error field
     */
    private final Text passwordError;
    /**
     * The change address field
     */
    private final TextField setAddress;
    /**
     * The save changes button box
     */
    private final HBox saveChangesBox;
    /**
     * The back button box
     */
    private final HBox backButtonBox;

    /**
     * Constructor for a application window
     * @param javaFactura   The business logic instance
     * @param primaryStage  The stage where the window exists
     * @param previousScene The previous scene (null if this is the root window)
     */
    protected ContribuinteProfileFX(JavaFactura javaFactura, Stage primaryStage,
                                    Scene previousScene){
        super(javaFactura, primaryStage, previousScene);

        int row = 0;
        this.gridPane.add(new Label("NIF: "), 0, row);
        this.nif = new Text();
        this.gridPane.add(this.nif, 1, row++);

        this.gridPane.add(new Label("Email: "), 0, row);
        this.email = new Text();
        this.gridPane.add(this.email, 1, row);
        this.setEmail = new TextField();
        this.gridPane.add(this.setEmail, 2, row++);

        this.gridPane.add(new Label("Name: "), 0, row);
        this.name = new Text();
        this.gridPane.add(this.name, 1, row++);

        this.gridPane.add(new Label("Address: "), 0, row);
        this.address = new Text();
        this.gridPane.add(this.address, 1, row);
        this.setAddress = new TextField();
        this.gridPane.add(this.setAddress, 2, row++);

        this.gridPane.add(new Label("Password: "), 0, row);
        this.setPassword = new PasswordField();
        this.gridPane.add(this.setPassword, 2, row++);
        this.passwordError = new Text();
        this.gridPane.add(this.passwordError, 2, row);

        this.gridPane.add(new Label("Coeficiente Fiscal: "), 0, row);
        this.fiscalCoefficient = new Text();
        this.gridPane.add(this.fiscalCoefficient, 1, row++);


        Label label = new Label("Setores Económicos");
        label.setAlignment(Pos.TOP_LEFT);
        label.setTextAlignment(TextAlignment.CENTER);
        this.gridPane.add(label, 0, row++);
        this.econSectors = new Text();
        this.econSectors.setTextOrigin(VPos.BOTTOM);
        this.gridPane.add(this.econSectors, 1, row++);

        Button saveChangesButton = new Button("Gravar alterações");
        saveChangesButton.setOnAction(e -> saveChanges());
        this.saveChangesBox = makeHBox(saveChangesButton, Pos.BOTTOM_RIGHT);
        this.gridPane.add(this.saveChangesBox, 2, row++);

        Button goBackButton = new Button("Back");
        goBackButton.setOnAction(e -> goBack());
        this.backButtonBox = makeHBox(goBackButton, Pos.BOTTOM_RIGHT);
        this.gridPane.add(this.backButtonBox, 2, row);
    }

    /**
     * {@inheritDoc}
     * Also sets all the field in the base profile
     * @return {@inheritDoc}
     */
    @Override
    public boolean show(){
        if(!super.show()) return false;
        User u = this.javaFactura.getLoggedUser();
        if(!(u instanceof Contribuinte)) return false;
        Contribuinte c = (Contribuinte) u;
        this.nif.setText(u.getNif());
        this.name.setText(u.getName());
        this.email.setText(c.getEmail());
        this.address.setText(c.getAddress());
        this.fiscalCoefficient.setText(String.valueOf(c.getFiscalCoefficient()));
        this.econSectors.setText(c.getEconActivities()
                                  .stream()
                                  .map(econSector -> econSector.toString() + ";\n")
                                  .reduce("", (s, s2) -> s + "" + s2));
        return true;
    }

    /**
     * Saves the changes inserted in the change fields
     */
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
            this.passwordError.setText("");
        }else{
            this.passwordError.setFill(Color.RED);
            this.passwordError.setText("Mínimo 4 caracteres");
        }
    }

    /**
     * Appends a new field to the profile
     * @param labelString The name of the field
     * @return The text where the value will appear
     */
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
