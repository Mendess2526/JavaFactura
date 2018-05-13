package javafactura.gui.contribuinte;

import com.sun.javafx.collections.ObservableListWrapper;
import javafactura.businessLogic.Factura;
import javafactura.businessLogic.JavaFactura;
import javafactura.businessLogic.econSectors.EconSector;
import javafactura.businessLogic.exceptions.NotContribuinteException;
import javafactura.gui.FX;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ShowReceiptsFx extends FX {

    protected final ObservableList<Factura> facturas;
    protected final TableView<Factura> receiptsTable;
    private final ViewFacturaFX viewFacturaFX;


    public ShowReceiptsFx(JavaFactura javaFactura, Stage primaryStage,
                          Scene previousScene, boolean canEdit){
        super(javaFactura, primaryStage, previousScene);

        this.facturas = new ObservableListWrapper<>(new ArrayList<>());
        this.receiptsTable = new TableView<>();
        makeReceiptsTable();
        this.viewFacturaFX = new ViewFacturaFX(this.javaFactura, this.primaryStage, this.scene, canEdit);
    }

    private void makeReceiptsTable(){
        this.receiptsTable.setMinWidth(this.gridPane.getMinWidth());
        this.receiptsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

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

        this.receiptsTable.setRowFactory(tv -> {
            TableRow<Factura> r = new TableRow<>();
            r.setOnMouseClicked(event -> {
                if(event.getButton().equals(MouseButton.PRIMARY)
                   && event.getClickCount() == 2
                   && !r.isEmpty()){
                    this.viewFacturaFX.setFactura(r.getItem()).show();
                }
            });
            return r;
        });
        this.receiptsTable.getColumns().add(date);
        this.receiptsTable.getColumns().add(type);
        this.receiptsTable.getColumns().add(value);
        this.receiptsTable.setItems(this.facturas);

    }

    @Override
    public boolean show(){
        try{
            this.facturas.clear();
            this.facturas.addAll(this.javaFactura.getLoggedUserFacturas());
        }catch(NotContribuinteException e){
            goBack();
            return false;
        }
        return super.show();
    }
}
