package javafactura.gui;

import javafactura.businessLogic.ContribuinteEmpresarial;
import javafactura.businessLogic.JavaFactura;
import javafactura.businessLogic.collections.Pair;
import javafactura.businessLogic.exceptions.NotAdminException;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.List;

public class AdminTopXEmpresasFX extends FX {

    private final TextField numberPrompt;
    private final Label totalValue;
    private final ObservableList<ContribuinteEmpresarial> topX;

    public AdminTopXEmpresasFX(JavaFactura javaFactura, Stage primaryStage, Scene previousScene){
        super(javaFactura, primaryStage, previousScene);

        // [LABEL] Insert Numero
        this.gridPane.add(new Label("Inserir numero"), 0, 0);

        // [TEXT_FIELD] Numero de empresas
        this.numberPrompt = new TextField();
        this.numberPrompt.setPromptText("Numero de empresas");
        this.gridPane.add(this.numberPrompt, 1, 0);

        // [BUTTON] Search button
        Button searchButton = new Button("Search");
        searchButton.setOnAction(event -> fillList());
        this.gridPane.add(makeHBox(searchButton, Pos.CENTER), 2, 0);

        // [LABEL] Total Value
        this.gridPane.add(new Label("Total Value: "), 0, 1);
        this.totalValue = new Label(Double.toString(0));
        this.gridPane.add(totalValue, 1, 1);

        // [LIST_VIEW] Top X
        this.topX = FXCollections.observableArrayList();
        TableView<ContribuinteEmpresarial> topX = new TableView<>(this.topX);
        topX.setMinWidth(this.gridPane.getMinWidth());
        topX.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        topX.setFocusTraversable(false);
        TableColumn<ContribuinteEmpresarial, String> nif = new TableColumn<>("NIF");
        nif.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getNif()));
        TableColumn<ContribuinteEmpresarial,String> name = new TableColumn<>("Nome");
        name.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getName()));
        topX.getColumns().add(nif);
        topX.getColumns().add(name);
        this.gridPane.add(topX, 0, 2);

        // [BUTTON] Back button
        Button goBackButton = new Button("Back");
        goBackButton.setOnAction(event -> goBack());
        this.gridPane.add(makeHBox(goBackButton, Pos.BOTTOM_RIGHT), 2, 2);
    }

    private void fillList(){
        int num = Integer.parseInt(this.numberPrompt.getText());
        System.out.println("Getting top " + num + " companies");
        Pair<List<ContribuinteEmpresarial>,Double> listDoublePair;
        try{
            listDoublePair = this.javaFactura.getTopXEmpresas(num);
        }catch(NotAdminException e){
            goBack();
            return;
        }
        this.totalValue.setText(Double.toString(listDoublePair.snd()));
        this.topX.clear();
        this.topX.addAll(listDoublePair.fst());
    }
}