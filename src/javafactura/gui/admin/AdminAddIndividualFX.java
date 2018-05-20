package javafactura.gui.admin;

import javafactura.businessLogic.JavaFactura;
import javafactura.businessLogic.econSectors.EconSector;
import javafactura.businessLogic.exceptions.ContribuinteAlreadyExistsException;
import javafactura.businessLogic.exceptions.InvalidNumberOfDependantsException;
import javafactura.gui.FormFX;
import javafx.scene.Scene;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.*;
import java.util.stream.Collectors;

public class AdminAddIndividualFX extends FormFX {

    private static final String[] defaultFields = new String[]{
            "NIF:", "Email:", "Nome", "Address", "Password", "Fiscal Coefficient", "Number of dependants",
            };

    private final MenuButton sectorsBox;
    private final TextField familyAggregate;

    public AdminAddIndividualFX(JavaFactura javaFactura, Stage primaryStage, Scene previousScene){
        super(javaFactura, primaryStage, previousScene, defaultFields);

        this.familyAggregate = new TextField();
        appendField("Agregado familiar\n(valores separados por virgulas)", this.familyAggregate);

        this.sectorsBox = new MenuButton("Setores Económicos");
        Set<EconSector> econSectors = this.javaFactura.getAllSectors();
        for(EconSector s : econSectors){
            CheckMenuItem checkMenuItem = new CheckMenuItem(s.toString());
            this.sectorsBox.getItems().add(checkMenuItem);
        }
        appendField("Setores económicos", this.sectorsBox);
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
            List<String> familyAggregate = new ArrayList<>();
            StringTokenizer st = new StringTokenizer(this.familyAggregate.getText(), ",");
            while(st.hasMoreTokens()) familyAggregate.add(st.nextToken());
            this.javaFactura.registarIndividual(
                    this.textFields[field++].getText(),
                    this.textFields[field++].getText(),
                    this.textFields[field++].getText(),
                    this.textFields[field++].getText(),
                    this.textFields[field++].getText(),
                    Float.parseFloat(this.textFields[field++].getText().replace(",", ".")),
                    familyAggregate,
                    Integer.parseInt(this.textFields[field++].getText()),
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
        }catch(ContribuinteAlreadyExistsException e){
            this.errorTexts[0].setText("Nif já existe");
        }
    }
}
