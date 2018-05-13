package javafactura.gui.contribuinte;

import javafactura.businessLogic.ContribuinteIndividual;
import javafactura.businessLogic.Factura;
import javafactura.businessLogic.JavaFactura;
import javafactura.businessLogic.exceptions.NotEmpresaException;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

import java.time.LocalDateTime;

public class EmpresaFX extends ShowReceiptsFx {

    private final Label totalFacturado;
    private final TableView<ContribuinteIndividual> clients;
    private final EmpresaViewClientFX viewClientFX;

    //TODO update facturas tabela
    public EmpresaFX(JavaFactura javaFactura, Stage primaryStage, Scene previousScene){
        super(javaFactura, primaryStage, previousScene, false);

        EmpresaIssueReceiptFX empresaIssueReceiptFX
                = new EmpresaIssueReceiptFX(this.javaFactura, this.primaryStage,
                                            this.scene);

        EmpresaProfileFX empresaProfileFX = new EmpresaProfileFX(this.javaFactura, this.primaryStage, this.scene);

        this.viewClientFX = new EmpresaViewClientFX(this.javaFactura, this.primaryStage, this.scene);

        int row = 0;
        Button profileButton = new Button("Profile");
        profileButton.setOnAction(e -> empresaProfileFX.show());
        this.gridPane.add(makeHBox(profileButton, Pos.TOP_LEFT), 1, row);

        Button issueReceipt = new Button("Emitir Factura");
        issueReceipt.setOnAction(e -> empresaIssueReceiptFX.show());
        this.gridPane.add(makeHBox(issueReceipt, Pos.TOP_LEFT), 0, row++);

        // Receipts Table
        this.gridPane.add(this.receiptsTable, 0, row);

        // Clients Table
        this.clients = new TableView<>();
        makeClientsTable();
        this.gridPane.add(this.clients, 1, row++);

        this.totalFacturado = new Label();
        this.gridPane.add(this.totalFacturado, 0, row);

        Button goBack = new Button("Back");
        goBack.setOnAction(event -> goBack());
        this.gridPane.add(makeHBox(goBack, Pos.BOTTOM_RIGHT), 1, row);
    }

    private void makeClientsTable(){
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
                param.getValue().countFacturas(this.javaFactura.getLoggedUserNif())));
        this.clients.setRowFactory(tv -> {
            TableRow<ContribuinteIndividual> r = new TableRow<>();
            r.setOnMouseClicked(event -> {
                if(event.getButton().equals(MouseButton.PRIMARY)
                   && event.getClickCount() == 2
                   && !r.isEmpty()){
                    this.viewClientFX.setClient(r.getItem());
                    this.viewClientFX.show();
                }
            });
            return r;
        });
        this.clients.getColumns().add(nif);
        this.clients.getColumns().add(name);
        this.clients.getColumns().add(numCompras);
    }

    @Override
    public boolean show(){
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
