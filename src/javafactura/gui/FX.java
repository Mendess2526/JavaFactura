package javafactura.gui;

import javafactura.businessLogic.JavaFactura;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import static javafactura.gui.MainFX.HEIGHT;
import static javafactura.gui.MainFX.WIDTH;

public abstract class FX {

    /**
     * The business logic instance
     */
    protected final JavaFactura javaFactura;
    /**
     * The primary stage
     */
    protected final Stage primaryStage;
    /**
     * The previous scene
     */
    protected final Scene previousScene;
    /**
     * The current scene
     */
    protected final Scene scene;
    /**
     * The grid pane
     */
    protected final GridPane gridPane;

    /**
     * Constructor for a application window
     * @param javaFactura   The business logic instance
     * @param primaryStage  The stage where the window exists
     * @param previousScene The previous scene (null if this is the root window)
     */
    protected FX(JavaFactura javaFactura, Stage primaryStage, Scene previousScene){
        this.javaFactura = javaFactura;
        this.primaryStage = primaryStage;
        this.previousScene = previousScene;

        this.gridPane = new GridPane();
        this.gridPane.setAlignment(Pos.CENTER);
        this.gridPane.setHgap(10);
        this.gridPane.setVgap(10);
        this.gridPane.setPadding(new Insets(15, 15, 15, 15));
        this.gridPane.setMaxHeight(HEIGHT);
        this.gridPane.setMaxWidth(WIDTH);
//        this.gridPane.setGridLinesVisible(true);
        this.scene = new Scene(this.gridPane, WIDTH, HEIGHT);
    }

    /**
     * Show the window
     * @return {@code true} on success {@code false} otherwise
     */
    public boolean show(){
        this.primaryStage.setScene(this.scene);
        return true;
    }

    /**
     * Show the previous window
     */
    protected void goBack(){
        this.primaryStage.setScene(this.previousScene);
    }

    /**
     * Make a Horizontal box for buttons
     * @param node      The button
     * @param alignment The button's alignment
     * @return The {@link HBox}
     */
    protected HBox makeHBox(Node node, Pos alignment){
        HBox hBox = new HBox(10);
        hBox.setAlignment(alignment);
        hBox.getChildren().add(node);
        return hBox;
    }
}
