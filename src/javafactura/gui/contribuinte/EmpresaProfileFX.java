package javafactura.gui.contribuinte;

import javafactura.businessLogic.ContribuinteEmpresarial;
import javafactura.businessLogic.JavaFactura;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Class that represents the {@link ContribuinteEmpresarial} profile screen
 */
public class EmpresaProfileFX extends ContribuinteProfileFX {

    /**
     * Constructor for a application window
     * @param javaFactura   The business logic instance
     * @param primaryStage  The stage where the window exists
     * @param previousScene The previous scene (null if this is the root window)
     */
    public EmpresaProfileFX(JavaFactura javaFactura, Stage primaryStage,
                            Scene previousScene){
        super(javaFactura, primaryStage, previousScene);
    }
}
