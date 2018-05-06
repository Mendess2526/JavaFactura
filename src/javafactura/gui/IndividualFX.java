package javafactura.gui;

import javafactura.businessLogic.Factura;
import javafactura.businessLogic.JavaFactura;
import javafactura.businessLogic.econSectors.EconSector;
import javafactura.businessLogic.econSectors.Pendente;
import javafactura.businessLogic.exceptions.NotContribuinteException;
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
import javafx.scene.input.MouseButton;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;

class IndividualFX extends FX {

    private ObservableList<FacturaDataModel> facturas;
    private final TableView<FacturaDataModel> table;
    private Label pendingNum;

    IndividualFX(JavaFactura javaFactura, Stage primaryStage, Scene previousScene){
        super(javaFactura, primaryStage, previousScene);
        ColumnConstraints cc = new ColumnConstraints();
        cc.setPercentWidth(90);
        this.gridPane.getColumnConstraints().add(cc);

        this.facturas = new ObservableListWrapper<>(new ArrayList<>());
        this.facturas.addListener((ListChangeListener<FacturaDataModel>) c -> updatePendingNum());

        this.pendingNum = new Label("null");
        this.gridPane.add(this.pendingNum, 0, 0);

        IndividualViewFacturaFX individualViewFacturaFX = new IndividualViewFacturaFX(this.javaFactura,
                                                                                      this.primaryStage, this.scene);

        this.table = new TableView<>();
        this.table.setMinWidth(this.gridPane.getMinWidth());
        this.table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<FacturaDataModel,String> date = new TableColumn<>("Date");
        date.setMinWidth(Factura.dateFormat.toString().length());
        date.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<FacturaDataModel,String> type = new TableColumn<>("Type");
        type.setMinWidth(100);
        type.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<FacturaDataModel,String> value = new TableColumn<>("Value");
        value.setMinWidth(100);
        value.setCellValueFactory(new PropertyValueFactory<>("value"));

        this.table.setRowFactory(tv->{
            TableRow<FacturaDataModel> row = new TableRow<>();
            row.setOnMouseClicked(event->{
                if(event.getButton().equals(MouseButton.PRIMARY)
                        && event.getClickCount() == 2
                        && ! row.isEmpty()){
                    individualViewFacturaFX
                            .setFactura(row.getItem().getSource())
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
        logoutButton.setOnAction(this::logOut);
        this.gridPane.add(makeHBox(logoutButton, Pos.BOTTOM_RIGHT), 0, 2);
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

    void refresh(){
        this.table.refresh();
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

    @SuppressWarnings("unused")
    public static class FacturaDataModel {

        private final Factura source;
        private final SimpleStringProperty date;
        private final SimpleObjectProperty<EconSector> type;
        private final SimpleFloatProperty value;

        FacturaDataModel(Factura f){
            this.date = new SimpleStringProperty(f.getCreationDate().format(Factura.dateFormat));
            this.type = new SimpleObjectProperty<>(f.getType());
            this.value = new SimpleFloatProperty(f.getValue());
            this.source = f;
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

        public Factura getSource(){
            return this.source;
        }
    }
}
