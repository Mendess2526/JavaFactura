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

    protected final JavaFactura javaFactura;
    protected final Stage primaryStage;
    protected final Scene previousScene;
    protected final Scene scene;
    protected final GridPane gridPane;

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

    public boolean show(){
        this.primaryStage.setScene(this.scene);
        return true;
    }

    protected void goBack(){
        this.primaryStage.setScene(this.previousScene);
    }

    protected HBox makeHBox(Node node, Pos alignment){
        HBox hBox = new HBox(10);
        hBox.setAlignment(alignment);
        hBox.getChildren().add(node);
        return hBox;
    }
}
