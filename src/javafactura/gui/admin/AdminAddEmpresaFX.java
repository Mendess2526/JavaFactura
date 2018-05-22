package javafactura.gui.admin;

import com.sun.javafx.collections.ObservableListWrapper;
import javafactura.businessLogic.Conselho;
import javafactura.businessLogic.JavaFactura;
import javafactura.businessLogic.econSectors.EconSector;
import javafactura.businessLogic.exceptions.ContribuinteAlreadyExistsException;
import javafactura.gui.FormFX;
import javafx.scene.Scene;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
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
    private final Text sectorsBoxError;
    private final ComboBox<Conselho> conselhoDropDown;
    private final Text conselhoError;

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
        this.sectorsBoxError = new Text();
        this.sectorsBoxError.setFill(Color.RED);
        appendField("Setores económicos", this.sectorsBox, this.sectorsBoxError);
        this.conselhoDropDown = new ComboBox<>(new ObservableListWrapper<>(Arrays.asList(Conselho.values())));
        this.conselhoError = new Text();
        this.conselhoError.setFill(Color.RED);
        appendField("Conselho", this.conselhoDropDown, this.conselhoError);
    }

    /**
     * Checks if any field was left empty
     * @return {@code true} if all fields are filed {@code false} otherwise
     */
    @Override
    protected boolean fieldsFilled(){
        boolean allFiled = super.fieldsFilled();
        if(this.sectorsBox.getItems()
                          .stream()
                          .map(CheckMenuItem.class::cast)
                          .filter(CheckMenuItem::isSelected)
                          .count() < 1){
            this.sectorsBoxError.setText("Escolha pelo menos um sector");
            allFiled = false;
        }
        if(this.conselhoDropDown.getValue() == null){
            this.conselhoError.setText("Escolha um conselho");
            allFiled = false;
        }
        return allFiled;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean submitData(){
        if(!super.submitData()) return false;
        int field = 0;
        boolean success = true;
        try{
            Set<EconSector> sectors = this.sectorsBox.getItems()
                                                     .stream()
                                                     .map(CheckMenuItem.class::cast)
                                                     .filter(CheckMenuItem::isSelected)
                                                     .map(CheckMenuItem::getText)
                                                     .map(this.javaFactura::getSectorFromString)
                                                     .filter(Objects::nonNull)
                                                     .collect(Collectors.toSet());
            this.javaFactura.registarEmpresarial(
                    this.textFields[field++].getText(),
                    this.textFields[field++].getText(),
                    this.textFields[field++].getText(),
                    this.textFields[field++].getText(),
                    this.textFields[field++].getText(),
                    Float.parseFloat(this.textFields[field].getText()),
                    sectors,
                    this.conselhoDropDown.getValue());
            clearFields();
            confirm("Empresa adicionada");
        }catch(NumberFormatException e){
            this.errorTexts[field].setText("Not a number");
            success = false;
        }catch(ContribuinteAlreadyExistsException e){
            this.errorTexts[0].setText("Nif already exists");
            success = false;
        }
        return success;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void clearFields(){
        super.clearFields();
        this.conselhoDropDown.setValue(null);
        this.sectorsBox.getItems().stream().map(CheckMenuItem.class::cast).forEach(c -> c.setSelected(false));
    }

    /**
     * Clears all error messages
     */
    @Override
    protected void clearErrors(){
        super.clearErrors();
        this.conselhoError.setText("");
        this.sectorsBoxError.setText("");
    }
}