package javafactura.gui;

import com.sun.javafx.collections.ObservableListWrapper;
import javafactura.businessLogic.Factura;
import javafactura.businessLogic.JavaFactura;
import javafactura.businessLogic.econSectors.EconSector;
import javafactura.businessLogic.econSectors.Pendente;
import javafactura.businessLogic.exceptions.NotContribuinteException;
import javafactura.businessLogic.exceptions.NotIndividualException;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;

public class IndividualFX extends FX {

    private final ObservableList<Factura> facturas;
    private final Label totalDeducted;
    private final TableView<Factura> receiptsTable;
    private final Label pendingNum;
    private final IndividualViewFacturaFX individualViewFacturaFX;

    public IndividualFX(JavaFactura javaFactura, Stage primaryStage, Scene previousScene){
        super(javaFactura, primaryStage, previousScene);
        ColumnConstraints cc = new ColumnConstraints();
        cc.setPercentWidth(90);
        this.gridPane.getColumnConstraints().add(cc);

        this.facturas = new ObservableListWrapper<>(new ArrayList<>());
        this.facturas.addListener((ListChangeListener<Factura>) c -> updatePendingNum());

        this.individualViewFacturaFX = new IndividualViewFacturaFX(this.javaFactura,
                                                                   this.primaryStage, this.scene);

        IndividualProfileFx individualProfileFx = new IndividualProfileFx(this.javaFactura, this.primaryStage,
                                                                          this.scene);

        int row = 0;
        Button profileButton = new Button("Profile");
        profileButton.setOnAction(e -> individualProfileFx.show());
        this.gridPane.add(makeHBox(profileButton, Pos.CENTER_RIGHT), 0, row++);

        this.pendingNum = new Label();
        this.totalDeducted = new Label();
        HBox topRowHBox = new HBox(this.pendingNum, this.totalDeducted);
        topRowHBox.setSpacing(100);

        this.gridPane.add(topRowHBox, 0, row++);

        this.receiptsTable = new TableView<>();
        makeReceiptsTable();
        this.gridPane.add(receiptsTable, 0, row++);

        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(event -> logOut());
        this.gridPane.add(makeHBox(logoutButton, Pos.BOTTOM_RIGHT), 0, row);

        this.primaryStage.sceneProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.equals(this.scene)){
                this.receiptsTable.refresh();
                updatePendingNum();
            }
        });
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
                    this.individualViewFacturaFX.setFactura(r.getItem()).show();
                }
            });
            return r;
        });
        this.receiptsTable.getColumns().add(date);
        this.receiptsTable.getColumns().add(type);
        this.receiptsTable.getColumns().add(value);
        this.receiptsTable.setItems(this.facturas);

    }

    private void updatePendingNum(){
        long count = this.facturas
                .stream()
                .filter(f -> f.getType() instanceof Pendente)
                .count();
        this.pendingNum.setText(String.valueOf(count) + " facturas pendente(s)");
        if(count == 0) this.pendingNum.setTextFill(Color.GREEN);
        else this.pendingNum.setTextFill(Color.RED);
    }

    @Override
    protected boolean show(){
        try{
            this.facturas.clear();
            this.facturas.addAll(this.javaFactura.getLoggedUserFacturas());
            this.totalDeducted.setText("Total deduzido: " + this.javaFactura.getAccumulatedDeduction());
        }catch(NotContribuinteException | NotIndividualException e){
            goBack();
            return false;
        }
        return super.show();
    }

    private void logOut(){
        this.javaFactura.logout();
        this.primaryStage.setScene(this.previousScene);
    }

    @Override
    protected void goBack(){
        throw new UnsupportedOperationException();
    }

}
