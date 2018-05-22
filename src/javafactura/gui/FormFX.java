package javafactura.gui;

import javafactura.businessLogic.JavaFactura;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Arrays;

/**
 * Class that represents a form
 */
public abstract class FormFX extends FX {

    /**
     * The text fields
     */
    protected final TextField[] textFields;
    /**
     * The error text fields
     */
    protected final Text[] errorTexts;
    /**
     * The labels of the input fields
     */
    private final String[] fields;
    /**
     * The confirmation text
     */
    private final Text confirmText;
    /**
     * The submit button box
     */
    private final HBox submitButtonHBox;
    /**
     * The back button box
     */
    private final HBox goBackButtonHBox;

    /**
     * Constructor for a application window
     * @param javaFactura   The business logic instance
     * @param primaryStage  The stage where the window exists
     * @param previousScene The previous scene (null if this is the root window)
     * @param fields        The text fields of the form
     */
    protected FormFX(JavaFactura javaFactura, Stage primaryStage, Scene previousScene, String[] fields){
        super(javaFactura, primaryStage, previousScene);
        this.fields = Arrays.copyOf(fields, fields.length);
        this.textFields = new TextField[fields.length];
        this.errorTexts = new Text[fields.length];
        int row;
        for(row = 0; row < fields.length; row++){
            this.gridPane.add(new Label(fields[row]), 0, row);
            this.textFields[row] = new TextField();
            this.gridPane.add(this.textFields[row], 1, row);
            this.errorTexts[row] = new Text();
            this.errorTexts[row].setFill(Color.RED);
            this.gridPane.add(this.errorTexts[row], 2, row);
        }
        this.confirmText = new Text();
        this.confirmText.setFill(Color.GREEN);
        this.gridPane.add(this.confirmText, 1, row++);

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(event -> submitData());
        this.submitButtonHBox = makeHBox(submitButton, Pos.BOTTOM_RIGHT);
        this.gridPane.add(this.submitButtonHBox, 0, row++);

        Button goBackButton = new Button("Back");
        goBackButton.setOnAction(event -> goBack());
        this.goBackButtonHBox = makeHBox(goBackButton, Pos.BOTTOM_RIGHT);
        this.gridPane.add(this.goBackButtonHBox, 2, row);
    }

    /**
     * Appends a field to the list of input fields
     * @param label The field label
     * @param node  The input field
     * @param error The error text
     */
    protected void appendField(String label, Node node, Text error){
        int rowIndex = GridPane.getRowIndex(this.confirmText);
        ObservableList<Node> children = this.gridPane.getChildren();
        children.remove(this.confirmText);
        children.remove(this.submitButtonHBox);
        children.remove(this.goBackButtonHBox);
        this.gridPane.add(new Label(label), 0, rowIndex);
        this.gridPane.add(node, 1, rowIndex);
        this.gridPane.add(error, 2, rowIndex);
        this.gridPane.add(this.confirmText, 1, rowIndex + 1);
        this.gridPane.add(this.submitButtonHBox, 0, rowIndex + 2);
        this.gridPane.add(this.goBackButtonHBox, 2, rowIndex + 2);
    }

    /**
     * Checks if any field was left empty
     * @return {@code true} if any field is empty {@code false} otherwise
     */
    protected boolean fieldsFilled(){
        boolean allFiled = true;
        for(int row = 0; row < fields.length; row++){
            if(this.textFields[row].getText().equals("")){
                this.errorTexts[row].setText("Campo obrigatório");
                allFiled = false;
            }
        }
        return allFiled;
    }

    /**
     * Submits the data
     * \return {@code true} if the data was submitted successfully {@code false} otherwise
     */
    protected boolean submitData(){
        unconfirm();
        clearErrors();
        return fieldsFilled();
    }

    /**
     * Sets the confirm text
     * @param text Text string
     */
    protected void confirm(String text){
        this.confirmText.setText(text);
    }

    /**
     * Clears the confirm text
     */
    private void unconfirm(){
        this.confirmText.setText("");
    }

    /**
     * Clears all input fields
     */
    protected void clearFields(){
        Arrays.stream(this.textFields).forEach(TextInputControl::clear);
    }

    /**
     * Clears all error messages
     */
    protected void clearErrors(){
        Arrays.stream(this.errorTexts).forEach(t -> t.setText(""));
    }

    /**
     * Show the window
     * @return {@code true} on success {@code false} otherwise
     */
    @Override
    public boolean show(){
        clearErrors();
        clearFields();
        return super.show();
    }
}
