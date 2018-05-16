package javafactura.gui.admin;

import javafactura.businessLogic.JavaFactura;
import javafactura.businessLogic.econSectors.EconSector;
import javafactura.businessLogic.exceptions.IndividualAlreadyExistsException;
import javafactura.businessLogic.exceptions.InvalidNumberOfDependantsException;
import javafactura.gui.FormFX;
import javafx.scene.Scene;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class AdminAddIndividualFX extends FormFX {

    private static final String[] defaultFields = new String[]{
            "NIF:", "Email:", "Nome", "Address", "Password", "Number of dependants", "Fiscal Coefficient"
    };

    private final MenuButton sectorsBox;

    public AdminAddIndividualFX(JavaFactura javaFactura, Stage primaryStage, Scene previousScene){
        super(javaFactura, primaryStage, previousScene, defaultFields);

        this.sectorsBox = new MenuButton("Setores Económicos");
        Set<EconSector> econSectors = this.javaFactura.getAllSectors();
        for(EconSector s : econSectors){
            CheckMenuItem checkMenuItem = new CheckMenuItem(s.toString());
            this.sectorsBox.getItems().add(checkMenuItem);
        }
        appendField("Setores económicos", this.sectorsBox);
        //TODO familyAggregate
    }

    protected void submitData(){
        unconfirm();
        if(fieldsNotFilled()) return;
        int field = 0;
        try{
            Set<EconSector> sectors = this.sectorsBox.getItems()
                                                     .stream()
                                                     .map(CheckMenuItem.class::cast)
                                                     .filter(CheckMenuItem::isSelected)
                                                     .map(CheckMenuItem::getText)
                                                     .map(this.javaFactura::getSectorFromString)
                                                     .filter(Objects::nonNull)
                                                     .collect(Collectors.toSet());
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
            this.sectorsBox.getItems().stream().map(CheckMenuItem.class::cast).forEach(c -> c.setSelected(false));
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
