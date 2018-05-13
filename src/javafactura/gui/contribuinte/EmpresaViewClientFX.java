package javafactura.gui.contribuinte;

import javafactura.businessLogic.ContribuinteIndividual;
import javafactura.businessLogic.JavaFactura;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.stream.Collectors;

public class EmpresaViewClientFX extends ShowReceiptsFx {

    private final Label clientLable;
    private ContribuinteIndividual client;

    public EmpresaViewClientFX(JavaFactura javaFactura, Stage primaryStage,
                               Scene previousScene){
        super(javaFactura, primaryStage, previousScene, false);

        int row = 0;
        this.clientLable = new Label();
        this.gridPane.add(this.clientLable, 0, row++);

        this.gridPane.add(receiptsTable, 0, row++);

        Button goBackButton = new Button("Back");
        goBackButton.setOnAction(e -> goBack());
        this.gridPane.add(makeHBox(goBackButton, Pos.BOTTOM_RIGHT), 0, row);
    }

    @Override
    public boolean show(){
        if(this.client == null) return false;
        this.facturas.clear();
        this.facturas.addAll(this.client.getFacturas()
                                        .stream()
                                        .filter(f -> f.getIssuerNif().equals(this.javaFactura.getLoggedUserNif()))
                                        .collect(Collectors.toList()));
        this.clientLable.setText("Client: " + this.client.getNif());
        return super.show();
    }

    public void setClient(ContribuinteIndividual client){
        this.client = client;
    }
}
