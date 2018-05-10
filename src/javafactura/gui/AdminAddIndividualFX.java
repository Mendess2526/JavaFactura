package javafactura.gui;

import javafactura.businessLogic.JavaFactura;
import javafactura.businessLogic.econSectors.EconSector;
import javafactura.businessLogic.exceptions.IndividualAlreadyExistsException;
import javafactura.businessLogic.exceptions.InvalidNumberOfDependantsException;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashSet;

class AdminAddIndividualFX extends FormFX {

    private static final String[] defaultFields = new String[]{
            "NIF:", "Email:", "Nome", "Address", "Password", "Number of dependants", "Fiscal Coefficient"
    };

    private final ComboBox<EconSector> sectorsBox;

    AdminAddIndividualFX(JavaFactura javaFactura, Stage primaryStage, Scene previousScene){
        super(javaFactura, primaryStage, previousScene, defaultFields);

        this.sectorsBox = new ComboBox<>();
        this.sectorsBox.getItems().addAll(this.javaFactura.getAllSectors());
        appendField("Setores económicos", this.sectorsBox);
        //TODO econActivities
    }

    protected void submitData(){
        unconfirm();
        if(fieldsNotFilled()) return;
        int field = 0;
        try{
            HashSet<EconSector> sectors = new HashSet<>();
            sectors.add(this.sectorsBox.getValue());
            this.javaFactura.registarIndividual(
                    this.textFields[field++].getText(),
                    this.textFields[field++].getText(),
                    this.textFields[field++].getText(),
                    this.textFields[field++].getText(),
                    this.textFields[field++].getText(),
                    Integer.parseInt(this.textFields[field++].getText()),
                    new ArrayList<>(), //TODO
                    Double.parseDouble(this.textFields[field++].getText()),
                    sectors
            );
            for(TextField t : this.textFields)
                t.clear();
            this.sectorsBox.getSelectionModel().clearSelection();
            confirm("Individual adicionado");
        }catch(InvalidNumberOfDependantsException e){
            this.errorTexts[5].setText("Too many dependents");
        }catch(NumberFormatException e){
            this.errorTexts[field - 1].setText("Not a number");
        }catch(IndividualAlreadyExistsException e){
                this.errorTexts[0].setText("Nif já existe");
        }
    }
}
