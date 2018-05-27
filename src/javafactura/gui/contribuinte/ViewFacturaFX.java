package javafactura.gui.contribuinte;

import javafactura.businessLogic.Factura;
import javafactura.businessLogic.JavaFactura;
import javafactura.businessLogic.econSectors.EconSector;
import javafactura.businessLogic.exceptions.InvalidEconSectorException;
import javafactura.businessLogic.exceptions.NotIndividualException;
import javafactura.gui.FX;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.LinkedList;

public class ViewFacturaFX extends FX {

    /**
     * The {@link Factura}'s history
     */
    private final ArrayList<Factura> history;
    /**
     * The previous version button
     */
    private final Button previousButton;
    /**
     * The next version button
     */
    private final Button nextButton;
    /**
     * The edit sector drop down
     */
    private final MenuButton editSector;
    /**
     * The issuer's NIF
     */
    private final Text issuerNif;
    /**
     * The issuer's name
     */
    private final Text issuerName;
    /**
     * The {@link Factura}'s creation date
     */
    private final Text date;
    /**
     * The {@link Factura}'s edit date
     */
    private final Text editDate;
    /**
     * The client's name
     */
    private final Text clientNif;
    /**
     * The {@link Factura}'s description
     */
    private final Text description;
    /**
     * The {@link Factura}'s value
     */
    private final Text value;
    /**
     * The {@link Factura} {@link EconSector}
     */
    private final Text econSector;
    /**
     * The table refresher
     */
    private final ShowReceiptsFx.TableRefresher tableRefresher;
    /**
     * The current point in the history
     */
    private int historyIndex;

    /**
     * Constructor for a application window
     * @param javaFactura    The business logic instance
     * @param primaryStage   The stage where the window exists
     * @param previousScene  The previous scene (null if this is the root window)
     * @param tableRefresher The table refresher of the previous screen's table
     */
    public ViewFacturaFX(JavaFactura javaFactura, Stage primaryStage, Scene previousScene,
                         ShowReceiptsFx.TableRefresher tableRefresher){
        super(javaFactura, primaryStage, previousScene);
        this.tableRefresher = tableRefresher;

        this.historyIndex = 0;
        this.history = new ArrayList<>();

        int row = 0;

        Label issuerNifLabel = new Label("NIF do Emitente:");
        this.issuerNif = new Text();
        this.gridPane.add(issuerNifLabel, 0, row);
        this.gridPane.add(this.issuerNif, 1, row++);

        Label issuerNameLabel = new Label("Nome do Emitente:");
        this.issuerName = new Text();
        this.gridPane.add(issuerNameLabel, 0, row);
        this.gridPane.add(this.issuerName, 1, row++);

        Label dateLabel = new Label("Data de emissão:");
        this.date = new Text();
        this.gridPane.add(dateLabel, 0, row);
        this.gridPane.add(this.date, 1, row++);

        Label editDateLabel = new Label("Data de edição:");
        this.editDate = new Text();
        this.gridPane.add(editDateLabel, 0, row);
        this.gridPane.add(this.editDate, 1, row++);

        Label clientNifLabel = new Label("NIF do Cliente:");
        this.clientNif = new Text();
        this.gridPane.add(clientNifLabel, 0, row);
        this.gridPane.add(this.clientNif, 1, row++);

        Label descriptionLabel = new Label("Descrição:");
        this.description = new Text();
        this.gridPane.add(descriptionLabel, 0, row);
        this.gridPane.add(this.description, 1, row++);

        Label valueLabel = new Label("Valor:");
        this.value = new Text();
        this.gridPane.add(valueLabel, 0, row);
        this.gridPane.add(this.value, 1, row++);

        Label econSectorLabel = new Label("Sector Económico:");
        this.econSector = new Text();
        this.gridPane.add(econSectorLabel, 0, row);
        this.gridPane.add(this.econSector, 1, row++);

        if(tableRefresher != null){
            this.editSector = new MenuButton("Mudar Sector");
            this.gridPane.add(editSector, 0, row++);
        }else{
            this.editSector = null;
        }

        this.previousButton = new Button("Mais Recente");
        this.previousButton.setOnAction(event1 -> previousFactura());
        this.previousButton.setDisable(true);
        this.gridPane.add(makeHBox(this.previousButton, Pos.CENTER_LEFT), 0, row);

        this.nextButton = new Button("Mais Antiga");
        this.nextButton.setOnAction(event -> nextFactura());
        this.gridPane.add(makeHBox(this.nextButton, Pos.CENTER_RIGHT), 1, row++);

        Button goBackButton = new Button("Voltar");
        goBackButton.setOnAction(event -> goBack());
        this.gridPane.add(makeHBox(goBackButton, Pos.BOTTOM_RIGHT), 1, row);
    }

    /**
     * Goes back in time
     */
    private void nextFactura(){
        if(this.hasNext()){
            updateFields(this.history.get(++this.historyIndex));
        }
        updateButtons();
    }

    /**
     * Goes forward in time
     */
    private void previousFactura(){
        if(this.hasPrevious()){
            updateFields(this.history.get(--this.historyIndex));
        }
        updateButtons();
    }

    /**
     * Enables and disables the Next and Previous buttons as needed
     */
    private void updateButtons(){
        this.nextButton.setDisable(!this.hasNext());
        this.previousButton.setDisable(!this.hasPrevious());
    }

    /**
     * Checks if there is an older version of the current {@link Factura}
     * @return {@code true} if yes {@code false} otherwise
     */
    private boolean hasNext(){
        return this.historyIndex < (this.history.size() - 1);
    }

    /**
     * Checks if there is an earlier version of the current {@link Factura}
     * @return {@code true} if yes {@code false} otherwise
     */
    private boolean hasPrevious(){
        return this.historyIndex > 0;
    }

    /**
     * Changes the current {@link Factura}
     * @param factura The new factura
     * @return The instance for chaining
     */
    public ViewFacturaFX setFactura(Factura factura){
        LinkedList<Factura> linkedList = factura.getHistory();
        linkedList.addFirst(factura);
        this.history.clear();
        this.history.addAll(linkedList);
        this.historyIndex = 0;
        if(this.editSector != null){
            this.editSector.getItems().clear();
            for(EconSector e : factura.getPossibleEconSectors()){
                EconMenuItem m = new EconMenuItem(e);
                m.setOnAction(ae -> {
                    Factura f = this.history.get(0);
                    EconSector econSector = ((EconMenuItem) ae.getSource()).getSector();
                    try{
                        f = this.javaFactura.changeFactura(f, econSector);
                    }catch(NotIndividualException e1){
                        this.goBack();
                    }catch(InvalidEconSectorException ignored){
                    } // using setFactura will fix this if it ever happens
                    this.setFactura(f);
                });
                this.editSector.getItems().add(m);
            }
            this.editSector.setDisable(this.editSector.getItems().isEmpty());
        }
        updateFields(this.history.get(this.historyIndex));
        updateButtons();
        if(this.tableRefresher != null) this.tableRefresher.refresh();
        return this;
    }

    /**
     * Update the fields
     * @param factura The {@link Factura} from where the values will be extracted
     */
    private void updateFields(Factura factura){
        this.issuerNif.setText(factura.getIssuerNif());
        this.issuerName.setText(factura.getIssuerName());
        this.date.setText(factura.getCreationDate().format(Factura.dateFormat));
        this.editDate.setText(factura.getLastEditDate().format(Factura.dateFormat));
        this.clientNif.setText(factura.getClientNif());
        this.description.setText(factura.getDescription());
        this.value.setText(String.valueOf(factura.getValue()));
        this.econSector.setText(factura.getType().toString());
    }

    /**
     * Extension of a {@link MenuItem} to include an {@link EconSector} for later retrieval
     */
    private class EconMenuItem extends MenuItem {

        /** The {@link EconSector} */
        private final EconSector sector;

        /**
         * The constructor
         * @param e The {@link EconSector}
         */
        EconMenuItem(EconSector e){
            super(e.toString());
            this.sector = e;
        }

        /**
         * Returns the {@link EconSector}
         * @return The {@link EconSector}
         */
        private EconSector getSector(){
            return this.sector;
        }
    }
}
