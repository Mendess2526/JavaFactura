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
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class AdminAddIndividualFX extends FormFX {

    /**
     * The form's fields
     */
    private static final String[] defaultFields = new String[]{
            "NIF:", "Email:", "Nome", "Morada", "Password", "Fiscal Coefficient", "Número de dependentes",
            };

    /**
     * Menu button to pick {@link EconSector}s
     */
    private final MenuButton sectorsBox;
    /**
     *
     */
    private final Text sectorsBoxError;
    /**
     * Text field to input the family aggregate
     */
    private final TextField familyAggregate;
    /**
     * Error text for the family aggregate
     */
    private final Text familyAggregateError;

    /**
     * Constructor for a application window
     * @param javaFactura   The business logic instance
     * @param primaryStage  The stage where the window exists
     * @param previousScene The previous scene (null if this is the root window)
     */
    public AdminAddIndividualFX(JavaFactura javaFactura, Stage primaryStage, Scene previousScene){
        super(javaFactura, primaryStage, previousScene, defaultFields);

        this.familyAggregate = new TextField();
        this.familyAggregateError = new Text();
        this.familyAggregateError.setFill(Color.RED);
        appendField("Agregado familiar\n(valores separados por virgulas)", this.familyAggregate,
                    this.familyAggregateError);

        this.sectorsBox = new MenuButton("Setores Económicos");
        Set<EconSector> econSectors = this.javaFactura.getAllSectors();
        for(EconSector s : econSectors){
            CheckMenuItem checkMenuItem = new CheckMenuItem(s.toString());
            this.sectorsBox.getItems().add(checkMenuItem);
        }
        this.sectorsBoxError = new Text();
        this.sectorsBoxError.setFill(Color.RED);
        appendField("Setores económicos", this.sectorsBox, this.sectorsBoxError);
    }

    /**
     * Checks if any field was left empty
     * @return {@code true} if all fields are filled {@code false} otherwise
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
        return allFiled;
    }

    /** {@inheritDoc} */
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
            Set<String> familyAggregate = new HashSet<>();
            StringTokenizer st = new StringTokenizer(this.familyAggregate.getText(), ",");
            while(st.hasMoreTokens()) if(!familyAggregate.add(st.nextToken())) throw new IllegalArgumentException();
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
            clearFields();
            confirm("Individual adicionado");
        }catch(InvalidNumberOfDependantsException e){
            this.errorTexts[6].setText("Demasiados dependentes");
            success = false;
        }catch(NumberFormatException e){
            this.errorTexts[field - 1].setText("Não é um número valido");
            success = false;
        }catch(ContribuinteAlreadyExistsException e){
            this.errorTexts[0].setText("Nif já existe");
            success = false;
        }catch(IllegalArgumentException e){
            this.familyAggregateError.setText("NIFs repetidos");
            success = false;
        }
        return success;
    }

    /**
     * Clears all input fields
     */
    @Override
    protected void clearFields(){
        super.clearFields();
        this.familyAggregate.setText("");
        this.sectorsBox.getItems().stream().map(CheckMenuItem.class::cast).forEach(c -> c.setSelected(false));
    }

    /**
     * Clears all error messages
     */
    @Override
    protected void clearErrors(){
        super.clearErrors();
        this.familyAggregateError.setText("");
    }
}
