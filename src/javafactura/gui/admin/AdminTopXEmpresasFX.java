package javafactura.gui.admin;

import javafactura.businessLogic.ContribuinteEmpresarial;
import javafactura.businessLogic.JavaFactura;
import javafactura.businessLogic.collections.Pair;
import javafactura.businessLogic.exceptions.NotAdminException;
import javafactura.gui.FX;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.List;

/**
 * Class that represents the view the top X {@link ContribuinteEmpresarial} screen
 */
public class AdminTopXEmpresasFX extends FX {

    /**
     * The prompt to pick the value of X
     */
    private final TextField numberPrompt;
    /**
     * The top X {@link ContribuinteEmpresarial}
     */
    private final ObservableList<Pair<ContribuinteEmpresarial,Double>> topX;

    /**
     * Constructor for a application window
     * @param javaFactura   The business logic instance
     * @param primaryStage  The stage where the window exists
     * @param previousScene The previous scene (null if this is the root window)
     */
    public AdminTopXEmpresasFX(JavaFactura javaFactura, Stage primaryStage, Scene previousScene){
        super(javaFactura, primaryStage, previousScene);

        // [LABEL] Insert Numero
        this.gridPane.add(new Label("Inserir numero"), 0, 0);

        // [TEXT_FIELD] Numero de empresas
        this.numberPrompt = new TextField();
        this.numberPrompt.setPromptText("Numero de empresas");
        this.gridPane.add(this.numberPrompt, 1, 0);

        // [BUTTON] Search button
        Button searchButton = new Button("Procurar");
        searchButton.setOnAction(event -> fillList());
        this.gridPane.add(makeHBox(searchButton, Pos.CENTER), 2, 0);

        // [TABLE_VIEW] Top X
        this.topX = FXCollections.observableArrayList();
        TableView<Pair<ContribuinteEmpresarial,Double>> topX = new TableView<>(this.topX);
        topX.setMinWidth(this.gridPane.getMinWidth());
        topX.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        topX.setFocusTraversable(false);

        TableColumn<Pair<ContribuinteEmpresarial,Double>,String> nif = new TableColumn<>("NIF");
        nif.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().fst().getNif()));

        TableColumn<Pair<ContribuinteEmpresarial,Double>,String> name = new TableColumn<>("Nome");
        name.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().fst().getName()));

        TableColumn<Pair<ContribuinteEmpresarial,Double>,String> total = new TableColumn<>("Valor");
        total.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(String.format("%.2f", param.getValue().snd())));

        topX.getColumns().add(nif);
        topX.getColumns().add(name);
        topX.getColumns().add(total);
        this.gridPane.add(topX, 0, 2);

        // [BUTTON] Back button
        Button goBackButton = new Button("Voltar");
        goBackButton.setOnAction(event -> goBack());
        this.gridPane.add(makeHBox(goBackButton, Pos.BOTTOM_RIGHT), 2, 2);
    }

    /**
     * Fills the list with the top X {@link ContribuinteEmpresarial}
     */
    private void fillList(){
        int num = Integer.parseInt(this.numberPrompt.getText());
        List<Pair<ContribuinteEmpresarial,Double>> listDoublePair;
        try{
            listDoublePair = this.javaFactura.getTopXEmpresas(num);
        }catch(NotAdminException e){
            goBack();
            return;
        }
        this.topX.setAll(listDoublePair);
    }
}