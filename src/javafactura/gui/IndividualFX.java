package javafactura.gui;

import com.sun.javafx.collections.ObservableListWrapper;
import javafactura.businessLogic.Factura;
import javafactura.businessLogic.JavaFactura;
import javafactura.businessLogic.econSectors.EconSector;
import javafactura.businessLogic.econSectors.Pendente;
import javafactura.businessLogic.exceptions.NotContribuinteException;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;

public class IndividualFX extends FX {

    private final ObservableList<Factura> facturas;
    private final TableView<Factura> table;
    private final Label pendingNum;

    public IndividualFX(JavaFactura javaFactura, Stage primaryStage, Scene previousScene){
        super(javaFactura, primaryStage, previousScene);
        ColumnConstraints cc = new ColumnConstraints();
        cc.setPercentWidth(90);
        this.gridPane.getColumnConstraints().add(cc);

        this.facturas = new ObservableListWrapper<>(new ArrayList<>());
        this.facturas.addListener((ListChangeListener<Factura>) c -> updatePendingNum());

        this.pendingNum = new Label("null");
        this.gridPane.add(this.pendingNum, 0, 0);

        IndividualViewFacturaFX individualViewFacturaFX = new IndividualViewFacturaFX(this.javaFactura,
                                                                                      this.primaryStage, this.scene);

        this.table = new TableView<>();
        this.table.setMinWidth(this.gridPane.getMinWidth());
        this.table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Factura,String> date = new TableColumn<>("Date");
        date.setMinWidth(Factura.dateFormat.toString().length());
        date.setCellValueFactory(
                param -> new ReadOnlyObjectWrapper<>(param.getValue().getCreationDate().format(Factura.dateFormat)));

        TableColumn<Factura,EconSector> type = new TableColumn<>("Type");
        type.setMinWidth(100);
        type.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getType()));

        TableColumn<Factura,Float> value = new TableColumn<>("Value");
        value.setMinWidth(100);
        value.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getValue()));

        this.table.setRowFactory(tv -> {
            TableRow<Factura> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if(event.getButton().equals(MouseButton.PRIMARY)
                   && event.getClickCount() == 2
                   && !row.isEmpty()){
                    individualViewFacturaFX
                            .setFactura(row.getItem())
                            .show();
                }
            });
            return row;
        });
        this.table.getColumns().add(date);
        this.table.getColumns().add(type);
        this.table.getColumns().add(value);
        this.table.setItems(this.facturas);

        this.gridPane.add(table, 0, 1);

        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(event -> logOut());
        this.gridPane.add(makeHBox(logoutButton, Pos.BOTTOM_RIGHT), 0, 2);

        this.primaryStage.sceneProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.equals(this.scene)){
                this.table.refresh();
                updatePendingNum();
            }
        });
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
        }catch(NotContribuinteException e){
            goBack();
            return false;
        }
        super.show();
        return true;
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
