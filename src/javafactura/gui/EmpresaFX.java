package javafactura.gui;

import javafactura.businessLogic.ContribuinteEmpresarial;
import javafactura.businessLogic.ContribuinteIndividual;
import javafactura.businessLogic.Factura;
import javafactura.businessLogic.JavaFactura;
import javafactura.businessLogic.econSectors.EconSector;
import javafactura.businessLogic.exceptions.NotEmpresaException;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

import java.time.LocalDateTime;

public class EmpresaFX extends FX {

    private final Label totalFacturado;
    private final TableView<ContribuinteIndividual> clients;
    private final TableView<Factura> facturasClient;
//TODO profile page
    public EmpresaFX(JavaFactura javaFactura, Stage primaryStage, Scene previousScene){
        super(javaFactura, primaryStage, previousScene);

        EmpresaIssueReceiptFX empresaIssueReceiptFX
                = new EmpresaIssueReceiptFX(this.javaFactura, this.primaryStage,
                                            this.scene);
        int row = 0;
        Button issueReceipt = new Button("Emitir Factura");
        issueReceipt.setOnAction(e -> empresaIssueReceiptFX.show());
        this.gridPane.add(makeHBox(issueReceipt, Pos.TOP_LEFT), 0, row);

        this.totalFacturado = new Label();
        this.gridPane.add(this.totalFacturado, 1, row++);

        this.facturasClient = new TableView<>();
        this.facturasClient.setMinWidth(this.gridPane.getMinWidth());
        this.facturasClient.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Factura,String> date = new TableColumn<>("Date");
        date.setMinWidth(Factura.dateFormat.toString().length());
        date.setCellValueFactory(
                param -> new ReadOnlyObjectWrapper<>(param.getValue().getCreationDate().format(Factura.dateFormat)));

        TableColumn<Factura,EconSector> type = new TableColumn<>("Type");
        type.setMinWidth(100);
        type.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getType()));

        TableColumn<Factura,String> value = new TableColumn<>("Value");
        value.setMinWidth(100);
        value.setCellValueFactory(
                param -> new ReadOnlyObjectWrapper<>(String.format("%.2f", param.getValue().getValue())));

        this.facturasClient.getColumns().add(date);
        this.facturasClient.getColumns().add(type);
        this.facturasClient.getColumns().add(value);
        this.gridPane.add(this.facturasClient, 1, row);

        this.clients = new TableView<>();
        this.clients.setMinWidth(this.gridPane.getMinWidth());
        this.clients.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<ContribuinteIndividual,String> nif = new TableColumn<>("NIF");
        nif.setMinWidth(Factura.dateFormat.toString().length());
        nif.setCellValueFactory(
                param -> new ReadOnlyObjectWrapper<>(param.getValue().getNif()));

        TableColumn<ContribuinteIndividual,String> name = new TableColumn<>("Name");
        name.setMinWidth(100);
        name.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getName()));

        TableColumn<ContribuinteIndividual,Long> numCompras = new TableColumn<>("#Compras");
        numCompras.setMinWidth(100);
        numCompras.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(
                param.getValue()
                     .getFacturas()
                     .stream()
                     .filter(f -> f.getIssuerName()
                                   .equals(((ContribuinteEmpresarial) this.javaFactura.getLoggedUser()).getNif()))
                     .count()));

        this.clients.setRowFactory(tv -> {
            TableRow<ContribuinteIndividual> r = new TableRow<>();
            r.setOnMouseClicked(event -> {
                if(event.getButton().equals(MouseButton.PRIMARY)
                   && !r.isEmpty()){
                    this.facturasClient.getItems().clear();
                    this.facturasClient.getItems().addAll(r.getItem().getFacturas());
                }
            });
            return r;
        });
        this.clients.getColumns().add(nif);
        this.clients.getColumns().add(name);
        this.clients.getColumns().add(numCompras);

        this.gridPane.add(this.clients, 0, row++);

        Button goBack = new Button("Back");
        goBack.setOnAction(event -> goBack());
        this.gridPane.add(makeHBox(goBack, Pos.BOTTOM_RIGHT), 1, row);
    }

    @Override
    protected boolean show(){
        try{
            this.totalFacturado.setText(String.format("Total faturado: %.2f€",
                                                      this.javaFactura.totalFaturado(LocalDateTime.MIN,
                                                                                     LocalDateTime.MAX)));
            this.clients.getItems().clear();
            this.clients.getItems().addAll(this.javaFactura.getClients());
        }catch(NotEmpresaException e){
            goBack();
            return false;
        }
        return super.show();
    }
}
