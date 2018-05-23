package javafactura.gui.admin;

import javafactura.businessLogic.ContribuinteIndividual;
import javafactura.businessLogic.JavaFactura;
import javafactura.businessLogic.exceptions.NotAdminException;
import javafactura.gui.FX;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.util.stream.Collectors;

/**
 * Class that represents the view the top 10 {@link ContribuinteIndividual} screen
 */
public class AdminTop10ContribuintesFX extends FX {

    /**
     * The top 10 NIFs
     */
    private final ObservableList<String> top10;

    /**
     * Constructor for a application window
     * @param javaFactura   The business logic instance
     * @param primaryStage  The stage where the window exists
     * @param previousScene The previous scene (null if this is the root window)
     */
    public AdminTop10ContribuintesFX(JavaFactura javaFactura, Stage primaryStage, Scene previousScene){
        super(javaFactura, primaryStage, previousScene);

        this.top10 = FXCollections.observableArrayList();
        ListView<String> top10ListView = new ListView<>(this.top10);
        top10ListView.setFocusTraversable(false);
        this.gridPane.add(top10ListView, 0, 1);

        Button goBack = new Button("Voltar");
        goBack.setOnAction(event -> goBack());
        this.gridPane.add(makeHBox(goBack, Pos.BOTTOM_RIGHT), 0, 2);
    }

    /**
     * {@inheritDoc}
     *
     * Also gets and updates the top 10 {@link ContribuinteIndividual} list
     * @return {@inheritDoc}
     */
    @Override
    public boolean show(){
        try{
            this.top10.setAll(this.javaFactura.getTop10Contrib().stream()
                                              .map(c -> c.getNif() + " " + c.getName())
                                              .collect(Collectors.toList()));
        }catch(NotAdminException e){
            return false;
        }
        super.show();
        return true;
    }
}
