package javafactura.gui.contribuinte;

import javafactura.businessLogic.ContribuinteEmpresarial;
import javafactura.businessLogic.JavaFactura;
import javafactura.businessLogic.User;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Class that represents the {@link ContribuinteEmpresarial} profile screen
 */
public class EmpresaProfileFX extends ContribuinteProfileFX {

    private final Text concelho;

    /**
     * Constructor for a application window
     * @param javaFactura   The business logic instance
     * @param primaryStage  The stage where the window exists
     * @param previousScene The previous scene (null if this is the root window)
     */
    public EmpresaProfileFX(JavaFactura javaFactura, Stage primaryStage,
                            Scene previousScene){
        super(javaFactura, primaryStage, previousScene);

        this.concelho = appendField("Concelho: ");
    }

    /**
     * {@inheritDoc}
     * Also sets all the field in the base profile
     * @return {@inheritDoc}
     */
    @Override
    public boolean show(){
        if(!super.show()) return false;
        User u = this.javaFactura.getLoggedUser();
        if(!(u instanceof ContribuinteEmpresarial)) return false;
        this.concelho.setText(((ContribuinteEmpresarial) u).getConcelho().toString());
        return true;
    }
}
