package javafactura.gui.contribuinte;

import javafactura.businessLogic.ContribuinteIndividual;
import javafactura.businessLogic.JavaFactura;
import javafactura.businessLogic.exceptions.NotContribuinteException;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.time.LocalDate;

/**
 * Class that represents the view {@link ContribuinteIndividual} screen
 * {@inheritDoc}
 */
public class EmpresaViewClientFX extends ShowReceiptsFx {

    /**
     * The client's NIF label
     */
    private final Label clientLabel;
    /**
     * The client instance
     */
    private ContribuinteIndividual client;

    /**
     * Constructor for a application window
     * @param javaFactura   The business logic instance
     * @param primaryStage  The stage where the window exists
     * @param previousScene The previous scene (null if this is the root window)
     */
    public EmpresaViewClientFX(JavaFactura javaFactura, Stage primaryStage,
                               Scene previousScene){
        super(javaFactura, primaryStage, previousScene, false);

        int row = 0;
        this.clientLabel = new Label();
        this.gridPane.add(this.clientLabel, 0, row++);

        HBox datePickerBox = new HBox(datePickerFrom, datePickerTo);
        datePickerBox.setSpacing(10);
        this.gridPane.add(datePickerBox, 0, row++);

        this.gridPane.add(receiptsTable, 0, row++);

        Button goBackButton = new Button("Voltar");
        goBackButton.setOnAction(e -> goBack());
        this.gridPane.add(makeHBox(goBackButton, Pos.BOTTOM_RIGHT), 0, row);
    }

    /**
     * {@inheritDoc}, sets the client label
     * @return {@inheritDoc}
     */
    @Override
    public boolean show(){
        if(this.client == null || !super.show()) return false;
        this.clientLabel.setText("Cliente: " + this.client.getNif());
        return true;
    }

    /** {@inheritDoc} */
    @Override
    protected boolean updateReceipts(){
        try{
            LocalDate from = this.from != null ? this.from : LocalDate.MIN;
            LocalDate to = this.to != null ? this.to : LocalDate.MAX;
            this.facturas.setAll(this.javaFactura.getFaturasOfIndividual(this.client, from, to));
        }catch(NotContribuinteException e){
            return false;
        }
        return true;
    }

    /**
     * Changes the current client
     * @param client The new client
     */
    public void setClient(ContribuinteIndividual client){
        this.client = client;
    }
}
