package com.github.mendess2526.javafactura.gui;

import com.github.mendess2526.javafactura.efactura.Factura;
import com.github.mendess2526.javafactura.efactura.JavaFactura;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ListIterator;

public class IndividualViewFacturaFX extends FX {

    private Factura factura;
    private ListIterator<Factura> history;
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
        this.historyIndex = -1;
        int row = 0;

        Label issuerNifLabel = new Label("IssuerNif:");
        this.issuerNif = new Text();
        this.gridPane.add(issuerNifLabel,0,row);
        this.gridPane.add(this.issuerNif,1,row++);

        Label issuerNameLabel = new Label("IssuerName:");
        this.issuerName = new Text();
        this.gridPane.add(issuerNameLabel,0,row);
        this.gridPane.add(this.issuerName,1,row++);

        Label dateLabel = new Label("Date:");
        this.date = new Text();
        this.gridPane.add(dateLabel,0,row);
        this.gridPane.add(this.date,1,row++);

        Label clientNifLabel = new Label("ClientNif:");
        this.clientNif = new Text();
        this.gridPane.add(clientNifLabel,0,row);
        this.gridPane.add(this.clientNif,1,row++);

        Label descriptionLabel = new Label("Description:");
        this.description = new Text();
        this.gridPane.add(descriptionLabel,0,row);
        this.gridPane.add(this.description,1,row++);

        Label valueLabel = new Label("Value:");
        this.value = new Text();
        this.gridPane.add(valueLabel,0,row);
        this.gridPane.add(this.value,1,row++);

        Label econSectorLabel = new Label("Economic Sector:");
        this.econSector = new Text();
        this.gridPane.add(econSectorLabel,0,row);
        this.gridPane.add(this.econSector,1,row++);

        this.previousButton = new Button("Previous");
        this.previousButton.setOnAction(this::previousFactura);
        this.previousButton.setDisable(true);
        this.gridPane.add(makeHBox(this.previousButton, Pos.CENTER_LEFT), 0, row);

        this.nextButton = new Button("Next");
        this.nextButton.setOnAction(this::nextFactura);
        this.gridPane.add(makeHBox(this.nextButton, Pos.CENTER_RIGHT), 0, row++);

        Button goBackButton = new Button("Back");
        goBackButton.setOnAction(this::goBack);
        this.gridPane.add(makeHBox(goBackButton, Pos.BOTTOM_RIGHT),1,row);
    }

    private void previousFactura(ActionEvent event){
        this.historyIndex--;
        if(this.historyIndex > -1)
            updateFields();
        else
            updateFields();
        updateButtons();
    }

    private void nextFactura(ActionEvent event){
        ++this.historyIndex;
        updateFields();
        updateButtons();
    }

    private void updateButtons(){
        this.previousButton.setDisable(this.historyIndex == -1);
        this.nextButton.setDisable(this.historyIndex == this.factura.getHistory().size()-1);
    }

    public IndividualViewFacturaFX setFactura(Factura factura){
        this.factura = factura;
        this.history = factura.getHistory().listIterator();
        updateFields();
        return this;
    }

    private void updateFields(){
        Factura factura;
        if(this.historyIndex == -1)
            factura = this.factura;
        else
            factura = this.factura.getHistory().get(this.historyIndex);

        //TODO history
        this.issuerNif.setText(factura.getIssuerNif());
        this.issuerName.setText(factura.getIssuerName());
        this.date.setText(factura.getCreationDate().toString());
        this.clientNif.setText(factura.getClientNif());
        this.description.setText(factura.getDescription());
        this.value.setText(String.valueOf(factura.getValue()));
        this.econSector.setText(factura.getType().toString());
    }
}
