package javafactura.gui.contribuinte;

import javafactura.businessLogic.ContribuinteIndividual;
import javafactura.businessLogic.JavaFactura;
import javafactura.businessLogic.exceptions.NotContribuinteException;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.time.LocalDate;

public class EmpresaViewClientFX extends ShowReceiptsFx {

    private final Label clientLabel;
    private ContribuinteIndividual client;

    public EmpresaViewClientFX(JavaFactura javaFactura, Stage primaryStage,
                               Scene previousScene){
        super(javaFactura, primaryStage, previousScene, false);

        int row = 0;
        this.clientLabel = new Label();
        this.gridPane.add(this.clientLabel, 0, row++);

        HBox datePickerBox = new HBox(datePickerFrom, datePickerTo);
        datePickerBox.setSpacing(10);
        this.gridPane.add(datePickerBox, 0, row++);

        this.gridPane.add(receiptsTable, 0, row++);

        Button goBackButton = new Button("Back");
        goBackButton.setOnAction(e -> goBack());
        this.gridPane.add(makeHBox(goBackButton, Pos.BOTTOM_RIGHT), 0, row);
    }

    @Override
    public boolean show(){
        if(!super.show() || this.client == null) return false;
        this.clientLabel.setText("Client: " + this.client.getNif());
        return updateReceipts();
    }

    public void setClient(ContribuinteIndividual client){
        this.client = client;
    }

    @Override
    protected boolean updateReceipts(){
        try{
            LocalDate from = this.from != null ? this.from : LocalDate.MIN;
            LocalDate to = this.to != null ? this.to : LocalDate.MAX;
            this.facturas.setAll(this.javaFactura.getFaturasOfIndividual(this.client.getNif(), from, to));
        }catch(NotContribuinteException e){
            return false;
        }
        return true;
    }
}
