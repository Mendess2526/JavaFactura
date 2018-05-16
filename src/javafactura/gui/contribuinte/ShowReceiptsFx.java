package javafactura.gui.contribuinte;

import com.sun.javafx.collections.ObservableListWrapper;
import javafactura.businessLogic.Factura;
import javafactura.businessLogic.JavaFactura;
import javafactura.businessLogic.comparators.FacturaValorComparator;
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
import java.util.Comparator;

public abstract class ShowReceiptsFx extends FX {

    protected final ObservableList<Factura> facturas;
    protected final TableView<Factura> receiptsTable;
    private final ViewFacturaFX viewFacturaFX;
    protected final HBox sortBox;
    protected LocalDate from;
    protected LocalDate to;
    protected DatePicker datePickerFrom;
    protected DatePicker datePickerTo;
    private boolean valueSort;
    private boolean dateSort;


    public ShowReceiptsFx(JavaFactura javaFactura, Stage primaryStage,
                          Scene previousScene, boolean canEdit){
        super(javaFactura, primaryStage, previousScene);
        ColumnConstraints cc = new ColumnConstraints();
        cc.setFillWidth(true);
        this.gridPane.getColumnConstraints().add(cc);
        this.facturas = new ObservableListWrapper<>(new ArrayList<>());

        this.valueSort = false;
        this.dateSort = false;
        Button sortValue = new Button("Ordenar por valor");
        sortValue.setOnAction(e -> {
            if(this.valueSort = !this.valueSort) this.facturas.sort(new FacturaValorComparator());
            else this.facturas.sort(new FacturaValorComparator().reversed());
        });
        Button sortDate = new Button("Ordenar por data");
        sortDate.setOnAction(e -> {
            if(this.dateSort = !this.dateSort) this.facturas.sort(Comparator.reverseOrder());
            else this.facturas.sort(Comparator.reverseOrder());
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
