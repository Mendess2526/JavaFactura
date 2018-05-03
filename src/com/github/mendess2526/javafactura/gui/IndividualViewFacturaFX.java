package com.github.mendess2526.javafactura.gui;

import com.github.mendess2526.javafactura.efactura.Factura;
import com.github.mendess2526.javafactura.efactura.JavaFactura;
import com.github.mendess2526.javafactura.efactura.econSectors.*;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.LinkedList;

public class IndividualViewFacturaFX extends FX {

    private static final MenuItem[] sectors = {
            new MenuItem(new AlojamentoRestauracao().toString()),
            new MenuItem(new Cabeleireiro().toString()),
            new MenuItem(new Educacao().toString()),
            new MenuItem(new Familia().toString()),
            new MenuItem(new Habitacao().toString()),
            new MenuItem(new Lares().toString()),
            new MenuItem(new Reparacoes().toString()),
            new MenuItem(new Saude().toString()),
            new MenuItem(new Veterinario().toString())
    };
    private ArrayList<Factura> history;
    private int historyIndex;
    private final Button previousButton;
    private final Button nextButton;
    private final Text issuerNif;
    private final Text issuerName;
    private final Text date;
    private final Text clientNif;
    private final Text description;
    private final Text value;
    private final Text econSector;

    IndividualViewFacturaFX(JavaFactura javaFactura, Stage primaryStage, Scene previousScene){
        super(javaFactura, primaryStage, previousScene);
        int row = 0;

        Label issuerNifLabel = new Label("IssuerNif:");
        this.issuerNif = new Text();
        this.gridPane.add(issuerNifLabel, 0, row);
        this.gridPane.add(this.issuerNif, 1, row++);

        Label issuerNameLabel = new Label("IssuerName:");
        this.issuerName = new Text();
        this.gridPane.add(issuerNameLabel, 0, row);
        this.gridPane.add(this.issuerName, 1, row++);

        Label dateLabel = new Label("Date:");
        this.date = new Text();
        this.gridPane.add(dateLabel, 0, row);
        this.gridPane.add(this.date, 1, row++);

        Label clientNifLabel = new Label("ClientNif:");
        this.clientNif = new Text();
        this.gridPane.add(clientNifLabel, 0, row);
        this.gridPane.add(this.clientNif, 1, row++);

        Label descriptionLabel = new Label("Description:");
        this.description = new Text();
        this.gridPane.add(descriptionLabel, 0, row);
        this.gridPane.add(this.description, 1, row++);

        Label valueLabel = new Label("Value:");
        this.value = new Text();
        this.gridPane.add(valueLabel, 0, row);
        this.gridPane.add(this.value, 1, row++);

        Label econSectorLabel = new Label("Economic Sector:");
        this.econSector = new Text();
        this.gridPane.add(econSectorLabel, 0, row);
        this.gridPane.add(this.econSector, 1, row++);

        MenuButton editSector = new MenuButton("Mudar Setor");
        for(MenuItem m : sectors)
            editSector.getItems().add(m);

        this.previousButton = new Button("Previous");
        this.previousButton.setOnAction(this::previousFactura);
        this.previousButton.setDisable(true);
        this.gridPane.add(makeHBox(this.previousButton, Pos.CENTER_LEFT), 0, row);

        this.nextButton = new Button("Next");
        this.nextButton.setOnAction(this::nextFactura);
        this.gridPane.add(makeHBox(this.nextButton, Pos.CENTER_RIGHT), 1, row++);

        Button goBackButton = new Button("Back");
        goBackButton.setOnAction(this::goBack);
        this.gridPane.add(makeHBox(goBackButton, Pos.BOTTOM_RIGHT), 1, row);
    }

    private void nextFactura(ActionEvent event){
        if(this.hasNext()){
            updateFields(this.history.get(++this.historyIndex));
        }
        updateButtons();
    }

    private void previousFactura(ActionEvent event){
        if(this.hasPrevious()){
            updateFields(this.history.get(--this.historyIndex));
        }
        updateButtons();
    }

    private void updateButtons(){
        this.nextButton.setDisable(!this.hasNext());
        this.previousButton.setDisable(!this.hasPrevious());
    }

    private boolean hasNext(){
        return this.historyIndex < (this.history.size() - 1);
    }

    private boolean hasPrevious(){
        return this.historyIndex > 0;
    }

    public IndividualViewFacturaFX setFactura(Factura factura){
        LinkedList<Factura> linkedList = factura.getHistory();
        linkedList.addFirst(factura);
        this.history = new ArrayList<>(linkedList);
        this.historyIndex = 0;
        updateFields(this.history.get(this.historyIndex));
        return this;
    }

    private void updateFields(Factura factura){
        this.issuerNif.setText(factura.getIssuerNif());
        this.issuerName.setText(factura.getIssuerName());
        this.date.setText(factura.getCreationDate().toString());
        this.clientNif.setText(factura.getClientNif());
        this.description.setText(factura.getDescription());
        this.value.setText(String.valueOf(factura.getValue()));
        this.econSector.setText(factura.getType().toString());
    }
}
