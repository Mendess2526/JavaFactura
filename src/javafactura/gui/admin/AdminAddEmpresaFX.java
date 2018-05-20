package javafactura.gui.admin;

import javafactura.businessLogic.JavaFactura;
import javafactura.businessLogic.econSectors.EconSector;
import javafactura.businessLogic.exceptions.ContribuinteAlreadyExistsException;
import javafactura.gui.FormFX;
import javafx.scene.Scene;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextInputControl;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class AdminAddEmpresaFX extends FormFX {

    /**
     * The form's fields
     */
    private static final String[] defaultFields = new String[]{
            "NIF:", "Email:", "Nome", "Address", "Password", "Fiscal Coefficient"
    };

    /**
     * Menu button to pick {@link EconSector}s
     */
    private final MenuButton sectorsBox;

    /**
     * Constructor for a application window
     * @param javaFactura   The business logic instance
     * @param primaryStage  The stage where the window exists
     * @param previousScene The previous scene (null if this is the root window)
     */
    public AdminAddEmpresaFX(JavaFactura javaFactura, Stage primaryStage, Scene previousScene){
        super(javaFactura, primaryStage, previousScene, defaultFields);

        this.sectorsBox = new MenuButton("Setores Económicos");
        Set<EconSector> econSectors = this.javaFactura.getAllSectors();
        for(EconSector s : econSectors){
            CheckMenuItem checkMenuItem = new CheckMenuItem(s.toString());
            this.sectorsBox.getItems().add(checkMenuItem);
        }
        appendField("Setores económicos", this.sectorsBox);
    }

    /**
     * {@inheritDoc}
     */
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
            System.out.println(sectors);
            this.javaFactura.registarEmpresarial(
                    this.textFields[field++].getText(),
                    this.textFields[field++].getText(),
                    this.textFields[field++].getText(),
                    this.textFields[field++].getText(),
                    this.textFields[field++].getText(),
                    Float.parseFloat(this.textFields[field].getText()),
                    sectors
            );
            Arrays.stream(this.textFields).forEach(TextInputControl::clear);
            this.sectorsBox.getItems().stream().map(CheckMenuItem.class::cast).forEach(c -> c.setSelected(false));
            confirm("Empresa adicionada");
        }catch(NumberFormatException e){
            this.errorTexts[field].setText("Not a number");
        }catch(ContribuinteAlreadyExistsException e){
            this.errorTexts[0].setText("Nif already exists");
        }
    }
}