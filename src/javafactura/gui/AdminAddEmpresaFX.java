package javafactura.gui;

import javafactura.businessLogic.JavaFactura;
import javafactura.businessLogic.econSectors.EconSector;
import javafactura.businessLogic.exceptions.EmpresarialAlreadyExistsException;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.HashSet;

class AdminAddEmpresaFX extends FX {

    private static final String[] fields = new String[]{
            "NIF:", "Email:", "Nome", "Address", "Password", "Fiscal Coefficient"
    };

    private final TextField[] textFields;
    private final Text[] errorTexts;
    private final ComboBox<EconSector> sectorsBox;
    private final Text confirmText;

    AdminAddEmpresaFX(JavaFactura javaFactura, Stage primaryStage, Scene previousScene){
        super(javaFactura, primaryStage, previousScene);

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
        Label setoresLabel = new Label("Setores economicos");
        this.gridPane.add(setoresLabel,0,row);

        this.sectorsBox = new ComboBox<>();
        this.sectorsBox.getItems().addAll(this.javaFactura.getAllSectors());
        this.gridPane.add(this.sectorsBox, 1, row++);

        this.confirmText = new Text();
        this.gridPane.add(this.confirmText, 1, row++);
        //TODO familyAggregate

        Button submit = new Button("Submit");
        submit.setOnAction(this::submitData);
        this.gridPane.add(makeHBox(submit, Pos.BOTTOM_RIGHT), 0, row++);

        Button goBackButton = new Button("Back");
        goBackButton.setOnAction(this::goBack);
        this.gridPane.add(makeHBox(goBackButton, Pos.BOTTOM_RIGHT), 2, row);
    }

    @SuppressWarnings("Duplicates")
    private void submitData(ActionEvent event){
        boolean allFilled = true;
        for(int row = 0; row < fields.length; row++){
            if(this.textFields[row].getText().equals("")){
                this.errorTexts[row].setText("Required field");
                allFilled = false;
            }else{
                this.errorTexts[row].setText("");
            }
        }
        if(!allFilled) return;
        int field = 0;
        try{
            HashSet<EconSector> sectors = new HashSet<>();
            sectors.add(this.sectorsBox.getValue());
            this.javaFactura.registarEmpresarial(
                    this.textFields[field++].getText(),
                    this.textFields[field++].getText(),
                    this.textFields[field++].getText(),
                    this.textFields[field++].getText(),
                    this.textFields[field++].getText(),
                    Double.parseDouble(this.textFields[field].getText()),
                    sectors
            );
            for(TextField t : this.textFields)
                t.clear();
            this.sectorsBox.getSelectionModel().clearSelection();
            this.confirmText.setFill(Color.GREEN);
            this.confirmText.setText("Empresa added");
        }catch(NumberFormatException e){
            this.errorTexts[field].setText("Not a number");
        }catch(EmpresarialAlreadyExistsException e){
            this.confirmText.setText("Nif already exists");
            this.confirmText.setFill(Color.RED);
        }
    }
}
