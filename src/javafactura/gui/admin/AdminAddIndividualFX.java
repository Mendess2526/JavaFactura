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

    private static final String[] defaultFields = new String[]{
            "NIF:", "Email:", "Nome", "Address", "Password", "Fiscal Coefficient", "Number of dependants",
            };

    private final MenuButton sectorsBox;
    private final TextField familyAggregate;
    private final Text familyAggregateError;

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
            for(TextField t : this.textFields)
                t.clear();
            this.familyAggregate.setText("");
            this.familyAggregateError.setText("");
            this.sectorsBox.getItems().stream().map(CheckMenuItem.class::cast).forEach(c -> c.setSelected(false));
            confirm("Individual adicionado");
        }catch(InvalidNumberOfDependantsException e){
            this.errorTexts[6].setText("Demasiados dependentes");
        }catch(NumberFormatException e){
            this.errorTexts[field - 1].setText("Não é um número valido");
        }catch(ContribuinteAlreadyExistsException e){
            this.errorTexts[0].setText("Nif já existe");
        }catch(IllegalArgumentException e){
            this.familyAggregateError.setText("NIFs repetidos");
        }
    }
}
