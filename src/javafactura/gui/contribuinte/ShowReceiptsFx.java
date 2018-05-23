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

/**
 * {@inheritDoc}
 * Shows {@link Factura}s
 */
public abstract class ShowReceiptsFx extends FX {

    /**
     * The {@link Factura}s list
     */
    protected final ObservableList<Factura> facturas;
    /**
     * The {@link Factura} table
     */
    protected final TableView<Factura> receiptsTable;
    /**
     * The sort buttons box
     */
    protected final HBox sortBox;
    /**
     * The from date picker
     */
    protected final DatePicker datePickerFrom;
    /**
     * The to date picker
     */
    protected final DatePicker datePickerTo;
    /**
     * The view {@link Factura} sub screen
     */
    private final ViewFacturaFX viewFacturaFX;
    /**
     * The begin date to filter from
     */
    protected LocalDate from;
    /**
     * The end date to filter to
     */
    protected LocalDate to;
    /**
     * The value sort state
     */
    private SortState valueSort;
    /**
     * The date sort state
     */
    private SortState dateSort;

    /**
     * Constructor for a application window
     * @param javaFactura   The business logic instance
     * @param primaryStage  The stage where the window exists
     * @param previousScene The previous scene (null if this is the root window)
     * @param canEdit       If the {@link Factura}s can be edited
     */
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
        makeReceiptsTable(canEdit);
        this.viewFacturaFX = new ViewFacturaFX(this.javaFactura, this.primaryStage, this.scene,
                                               canEdit ? new TableRefresher() : null);
    }

    /**
     * The comparator picker
     * Picks a different comparator depending on the sort state
     * Meant to be used by subclasses
     * @return The comparator
     */
    protected Comparator<Factura> getFacturaComparator(){
        Comparator<Factura> c;
        if(this.valueSort == SortState.ASCENDING)
            c = new FacturaValorComparator().reversed();
        else if(this.valueSort == SortState.DESCENDING)
            c = new FacturaValorComparator();
        else if(this.dateSort == SortState.ASCENDING)
            c = Comparator.reverseOrder();
        else if(this.dateSort == SortState.DESCENDING)
            c = Comparator.naturalOrder();
        else
            c = null;
        return c;
    }

    /**
     * Handles the creation of the {@link Factura}s table
     *
     * Creates the columns and adds listeners to open the {@link ViewFacturaFX} sub screen
     */
    private void makeReceiptsTable(boolean individual){
        this.receiptsTable.setMinWidth(this.gridPane.getMinWidth());
        this.receiptsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Factura,String> date = new TableColumn<>("Data");
        date.setMinWidth(LocalDateTime.now().format(Factura.dateFormat).length() * 10);
        date.setCellValueFactory(
                param -> new ReadOnlyObjectWrapper<>(param.getValue().getCreationDate().format(Factura.dateFormat)));

        TableColumn<Factura,EconSector> type = new TableColumn<>("Sector");
        type.setMinWidth(100);
        type.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getType()));

        TableColumn<Factura,String> name;
        if(individual){
            name = new TableColumn<>("Empresa");
            name.setMinWidth(100);
            name.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getIssuerName()));
        }else{
            name = new TableColumn<>("Cliente");
            name.setMinWidth(100);
            name.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getClientNif()));
        }

        TableColumn<Factura,String> value = new TableColumn<>("Valor");
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

    /**
     * {@inheritDoc} and updates the {@link Factura}s table
     * @return {@inheritDoc}
     */
    @Override
    public boolean show(){
        return super.show() && updateReceipts();
    }

    /**
     * Updates the receipts table
     * @return {@code true} on success {@code false} otherwise
     */
    protected abstract boolean updateReceipts();

    /**
     * Enum to represent the state the sorting is in
     */
    public enum SortState {
        /** Descending */
        DESCENDING,
        /** Ascending */
        ASCENDING,
        /** Unsorted */
        NONE;

        static{
            DESCENDING.reverse = ASCENDING;
            ASCENDING.reverse = DESCENDING;
            NONE.reverse = DESCENDING;
        }

        /** The inverse of the current value */
        private SortState reverse;
    }

    /**
     * One method class that serves to refresh this table from other
     * screens
     */
    class TableRefresher {

        /**
         * Refreshes the {@link Factura}s table
         */
        void refresh(){
            updateReceipts();
        }
    }
}
