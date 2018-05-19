package javafactura.gui.contribuinte;

import com.sun.javafx.collections.ObservableListWrapper;
import javafactura.businessLogic.Factura;
import javafactura.businessLogic.JavaFactura;
import javafactura.businessLogic.econSectors.EconSector;
import javafactura.gui.FX;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class ShowReceiptsFx extends FX {

    protected ShowReceiptsFx(JavaFactura javaFactura, Stage primaryStage,
                             Scene previousScene, boolean canEdit){
        super(javaFactura, primaryStage, previousScene);
        ColumnConstraints cc = new ColumnConstraints();
        cc.setFillWidth(true);
        this.gridPane.getColumnConstraints().add(cc);
        this.facturas = new ObservableListWrapper<>(new ArrayList<>());

        this.valueSort = SortState.NONE;
        this.dateSort = SortState.NONE;
        Button sortValue = new Button("Ordenar por valor");
        sortValue.setOnAction(e -> {
            this.valueSort = this.valueSort.reverse;
            this.dateSort = SortState.NONE;
            updateReceipts();
        });
        Button sortDate = new Button("Ordenar por data");
        sortDate.setOnAction(e -> {
            this.dateSort = this.dateSort.reverse;
            this.valueSort = SortState.NONE;
            updateReceipts();
        });
        this.sortBox = new HBox(sortDate, sortValue);
        sortBox.setSpacing(100);

        this.from = null;
        this.datePickerFrom = new DatePicker();
        this.datePickerFrom.setOnAction(t -> {
            this.from = datePickerFrom.getValue();
            updateReceipts();
        });
        this.to = null;
        this.datePickerTo = new DatePicker();
        this.datePickerTo.setOnAction(t -> {
            this.to = datePickerTo.getValue();
            updateReceipts();
        });

        this.receiptsTable = new TableView<>();
        makeReceiptsTable();
        this.viewFacturaFX = new ViewFacturaFX(this.javaFactura, this.primaryStage, this.scene, canEdit);
    }

    private SortState valueSort;
    protected final ObservableList<Factura> facturas;
    protected final TableView<Factura> receiptsTable;
    private final ViewFacturaFX viewFacturaFX;
    protected final HBox sortBox;
    protected LocalDate from;
    protected LocalDate to;
    protected final DatePicker datePickerFrom;
    protected final DatePicker datePickerTo;
    private SortState dateSort;

    public enum SortState {
        DESCENDING,
        ASCENDING,
        NONE;

        static{
            DESCENDING.reverse = ASCENDING;
            ASCENDING.reverse = DESCENDING;
            NONE.reverse = DESCENDING;
        }

        private SortState reverse;
    }

    protected SortState getValueSort(){
        return this.valueSort;
    }

    protected SortState getDateSort(){
        return this.dateSort;
    }

    private void makeReceiptsTable(){
        this.receiptsTable.setMinWidth(this.gridPane.getMinWidth());
        this.receiptsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Factura,String> date = new TableColumn<>("Date");
        date.setMinWidth(LocalDateTime.now().format(Factura.dateFormat).length() * 10);
        date.setCellValueFactory(
                param -> new ReadOnlyObjectWrapper<>(param.getValue().getCreationDate().format(Factura.dateFormat)));

        TableColumn<Factura,EconSector> type = new TableColumn<>("Type");
        type.setMinWidth(100);
        type.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getType()));

        TableColumn<Factura,String> name = new TableColumn<>("Empresa");
        name.setMinWidth(100);
        name.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getIssuerName()));

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
        this.receiptsTable.getColumns().add(name);
        this.receiptsTable.getColumns().add(value);
        this.receiptsTable.setItems(this.facturas);

    }

    @Override
    public boolean show(){
        return super.show() && updateReceipts();
    }

    protected abstract boolean updateReceipts();
}
