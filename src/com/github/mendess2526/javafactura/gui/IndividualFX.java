package com.github.mendess2526.javafactura.gui;

import com.github.mendess2526.javafactura.efactura.Factura;
import com.github.mendess2526.javafactura.efactura.JavaFactura;
import com.github.mendess2526.javafactura.efactura.econSectors.EconSector;
import com.github.mendess2526.javafactura.efactura.econSectors.Pendente;
import com.github.mendess2526.javafactura.efactura.exceptions.NotContribuinteException;
import com.sun.javafx.collections.ObservableListWrapper;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;

class IndividualFX extends FX{

    private ObservableList<FacturaDataModel> facturas;
    private Label pendingNum;

    IndividualFX(JavaFactura javaFactura, Stage primaryStage, Scene previousScene){
        super(javaFactura, primaryStage, previousScene);
        this.facturas = new ObservableListWrapper<>(new ArrayList<>());
        this.facturas.addListener((ListChangeListener<FacturaDataModel>) c->updatePendingNum());

        this.pendingNum = new Label("null");
        this.gridPane.add(this.pendingNum,0,0);

        IndividualViewFacturaFX individualViewFacturaFX = new IndividualViewFacturaFX(this.javaFactura,this.primaryStage,this.previousScene);

        TableView<FacturaDataModel> tableView = new TableView<>();

        TableColumn<FacturaDataModel,String> date = new TableColumn<>("Date");
        date.setMinWidth(100);
        date.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<FacturaDataModel,String> type = new TableColumn<>("Type");
        type.setMinWidth(100);
        type.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<FacturaDataModel,String> value = new TableColumn<>("Value");
        value.setMinWidth(100);
        value.setCellValueFactory(new PropertyValueFactory<>("value"));

        tableView.setRowFactory(t -> {
            TableRow row = new TableRow();
            row.setOnMouseClicked(event -> {
                if(event.getClickCount() > 1){
                    row
                }
            });
        });
        tableView.getColumns().add(date);
        tableView.getColumns().add(type);
        tableView.getColumns().add(value);
        tableView.setItems(this.facturas);

        this.gridPane.add(tableView,0,1);

        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(this::logOut);
        this.gridPane.add(makeHBox(logoutButton, Pos.BOTTOM_RIGHT),0,2);
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
            this.facturas.addAll(this.javaFactura.getLoggedUserFaturas()
                    .stream()
                    .map(FacturaDataModel::new)
                    .collect(Collectors.toList()));
        }catch(NotContribuinteException e){
            goBack(null);
            return false;
        }
        super.show();
        return true;
    }

    private void logOut(ActionEvent event){
        this.javaFactura.logout();
        this.primaryStage.setScene(this.previousScene);
    }

    @Override
    protected void goBack(ActionEvent event){
        throw new UnsupportedOperationException();
    }

    public static class FacturaDataModel{
        private final Factura original;
        private final SimpleStringProperty date;
        private final SimpleObjectProperty<EconSector> type;
        private final SimpleFloatProperty value;

        FacturaDataModel(Factura f){
            this.date = new SimpleStringProperty(f.getDate().toString());
            this.type = new SimpleObjectProperty<>(f.getType());
            this.value = new SimpleFloatProperty(f.getValue());
            this.original = f;
        }

        public String getDate(){
            return date.get();
        }

        public EconSector getType(){
            return type.get();
        }

        public float getValue(){
            return value.get();
        }

        public void setDate(LocalDateTime d){
            this.date.set(d.toString());
        }

        public void setType(EconSector type){
            this.type.set(type);
        }

        public void setValue(float value){
            this.value.set(value);
        }

        public Factura getOriginal(){
            return this.original;
        }
    }
}
