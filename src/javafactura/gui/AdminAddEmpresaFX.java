package javafactura.gui;

import javafactura.businessLogic.JavaFactura;
import javafactura.businessLogic.econSectors.EconSector;
import javafactura.businessLogic.exceptions.EmpresarialAlreadyExistsException;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.HashSet;

public class AdminAddEmpresaFX extends FormFX {

    private static final String[] defaultFields = new String[]{
            "NIF:", "Email:", "Nome", "Address", "Password", "Fiscal Coefficient"
    };

    private final ComboBox<EconSector> sectorsBox;

    public AdminAddEmpresaFX(JavaFactura javaFactura, Stage primaryStage, Scene previousScene){
        super(javaFactura, primaryStage, previousScene, defaultFields);

        this.sectorsBox = new ComboBox<>();
        this.sectorsBox.getItems().addAll(this.javaFactura.getAllSectors());
        appendField("Setores económicos", this.sectorsBox);
        //TODO familyAggregate
    }

    protected void submitData(){
        unconfirm();
        if(fieldsNotFilled()) return;
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
            confirm("Empresa adicionada");
        }catch(NumberFormatException e){
            this.errorTexts[field].setText("Not a number");
        }catch(EmpresarialAlreadyExistsException e){
            this.errorTexts[0].setText("Nif already exists");
        }
    }
}
