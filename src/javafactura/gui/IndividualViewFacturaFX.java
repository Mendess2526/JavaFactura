package javafactura.gui;

import javafactura.businessLogic.Factura;
import javafactura.businessLogic.JavaFactura;
import javafactura.businessLogic.econSectors.EconSector;
import javafactura.businessLogic.exceptions.InvalidEconSectorException;
import javafactura.businessLogic.exceptions.NotIndividualException;
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

    private ArrayList<Factura> history;
    private int historyIndex;
    private final Button previousButton;
    private final Button nextButton;
    private final MenuButton editSector;
    private final Text issuerNif;
    private final Text issuerName;
    private final Text date;
    private final Text editDate;
    private final Text clientNif;
    private final Text description;
    private final Text value;
    private final Text econSector;

    IndividualViewFacturaFX(JavaFactura javaFactura, Stage primaryStage, Scene previousScene){
        super(javaFactura, primaryStage, previousScene);

        this.historyIndex = 0;
        this.history = new ArrayList<>();

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

        Label editDateLabel = new Label("Last Edit Date:");
        this.editDate = new Text();
        this.gridPane.add(editDateLabel, 0, row);
        this.gridPane.add(this.editDate, 1, row++);

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

        this.editSector = new MenuButton("Mudar Setor");
        this.gridPane.add(editSector, 0, row++);

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
        this.history.clear();
        this.history.addAll(linkedList);
        this.historyIndex = 0;
        this.editSector.getItems().clear();
        for(EconSector e : factura.getPossibleEconSectors()){
            EconMenuItem m = new EconMenuItem(e);
            m.setOnAction(ae->{
                Factura f = this.history.get(0);
                EconSector econSector = ((EconMenuItem) ae.getSource()).getSector();
                try{
                    f = this.javaFactura.changeFactura(f, econSector);
                }catch(NotIndividualException e1){
                    this.goBack(null);
                }catch(InvalidEconSectorException ignored){
                } // using setFactura will fix this if it ever happens
                this.setFactura(f);
            });
            this.editSector.getItems().add(m);
        }
        this.editSector.setDisable(this.editSector.getItems().isEmpty());
        updateFields(this.history.get(this.historyIndex));
        updateButtons();
        return this;
    }

    private void updateFields(Factura factura){
        this.issuerNif.setText(factura.getIssuerNif());
        this.issuerName.setText(factura.getIssuerName());
        this.date.setText(factura.getCreationDate().format(Factura.dateFormat));
        this.editDate.setText(factura.getLastEditDate().format(Factura.dateFormat));
        this.clientNif.setText(factura.getClientNif());
        this.description.setText(factura.getDescription());
        this.value.setText(String.valueOf(factura.getValue()));
        this.econSector.setText(factura.getType().toString());
    }

    @Override
    protected void goBack(ActionEvent event){
        super.goBack(event);
    }

    private class EconMenuItem extends MenuItem{

        private final EconSector sector;

        EconMenuItem(EconSector e){
            super(e.toString());
            this.sector = e;
        }

        public EconSector getSector(){
            return this.sector;
        }
    }
}
