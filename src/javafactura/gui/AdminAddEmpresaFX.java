package javafactura.gui;

import javafactura.businessLogic.JavaFactura;
import javafactura.businessLogic.econSectors.EconSector;
import javafactura.businessLogic.econSectors.Pendente;
import javafactura.businessLogic.exceptions.EmpresarialAlreadyExistsException;
import javafx.scene.Scene;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextInputControl;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AdminAddEmpresaFX extends FormFX {

    private static final String[] defaultFields = new String[]{
            "NIF:", "Email:", "Nome", "Address", "Password", "Fiscal Coefficient"
    };

    private final MenuButton sectorsBox;

    public AdminAddEmpresaFX(JavaFactura javaFactura, Stage primaryStage, Scene previousScene){
        super(javaFactura, primaryStage, previousScene, defaultFields);

        this.sectorsBox = new MenuButton();
        List<CheckMenuItem> list = new ArrayList<>();
        Set<EconSector> econSectors = this.javaFactura.getAllSectors();
        for(EconSector s : econSectors){
            CheckMenuItem checkMenuItem = new CheckMenuItem(s.toString());
            list.add(checkMenuItem);
        }
        this.sectorsBox.getItems().addAll(list);
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
                                                     .map(c -> Pendente.getInstance()) //TODO finish this
                                                     .collect(Collectors.toSet());
            this.javaFactura.registarEmpresarial(
                    this.textFields[field++].getText(),
                    this.textFields[field++].getText(),
                    this.textFields[field++].getText(),
                    this.textFields[field++].getText(),
                    this.textFields[field++].getText(),
                    Double.parseDouble(this.textFields[field].getText()),
                    sectors
            );
            Arrays.stream(this.textFields).forEach(TextInputControl::clear);
            this.sectorsBox.getItems().stream().map(CheckMenuItem.class::cast).forEach(c -> c.setSelected(false));
            confirm("Empresa adicionada");
        }catch(NumberFormatException e){
            this.errorTexts[field].setText("Not a number");
        }catch(EmpresarialAlreadyExistsException e){
            this.errorTexts[0].setText("Nif already exists");
        }
    }
}