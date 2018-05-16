package javafactura.gui;

import javafactura.businessLogic.JavaFactura;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Arrays;

public abstract class FormFX extends FX {

    private final String[] fields;
    protected final TextField[] textFields;
    protected final Text[] errorTexts;
    private final Text confirmText;
    private final HBox submitButtonHBox;
    private final HBox goBackButtonHBox;

    public FormFX(JavaFactura javaFactura, Stage primaryStage, Scene previousScene, String[] fields){
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

    protected boolean fieldsNotFilled(){
        boolean allFilled = false;
        for(int row = 0; row < fields.length; row++){
            if(this.textFields[row].getText().equals("")){
                this.errorTexts[row].setText("Required field");
                allFilled = true;
            }else{
                this.errorTexts[row].setText("");
            }
        }
        return allFilled;
    }

    //TODO fix ordering of tab navigation
    protected void appendField(@SuppressWarnings("SameParameterValue") String label, Node node){
        int rowIndex = GridPane.getRowIndex(this.confirmText);
        GridPane.setRowIndex(this.confirmText, rowIndex + 1);
        GridPane.setRowIndex(this.submitButtonHBox, rowIndex + 2);
        GridPane.setRowIndex(this.goBackButtonHBox, rowIndex + 2);
        this.gridPane.add(new Label(label), 0, rowIndex);
        this.gridPane.add(node, 1, rowIndex);
    }

    protected abstract void submitData();

    protected void confirm(String text){
        this.confirmText.setText(text);
    }

    protected void unconfirm(){
        this.confirmText.setText("");
    }
}
