package javafactura.gui.contribuinte;

import javafactura.businessLogic.ContribuinteIndividual;
import javafactura.businessLogic.JavaFactura;
import javafactura.businessLogic.User;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Class that represents a {@link ContribuinteIndividual}'s profile
 */
public class IndividualProfileFx extends ContribuinteProfileFX {

    /**
     * The number of dependants of the family aggregate
     */
    private final Text numDependants;
    /**
     * The dependants
     */
    private final Text dependants;

    /**
     * Constructor for a application window
     * @param javaFactura   The business logic instance
     * @param primaryStage  The stage where the window exists
     * @param previousScene The previous scene (null if this is the root window)
     */
    public IndividualProfileFx(JavaFactura javaFactura, Stage primaryStage,
                               Scene previousScene){
        super(javaFactura, primaryStage, previousScene);

        this.numDependants = appendField("#dependentes: ");
        this.dependants = appendField("Agregado Familiar: ");
    }

    /**
     * {@inheritDoc} sets the number of dependants and dependants list
     * @return {@inheritDoc}
     */
    @Override
    public boolean show(){
        if(!super.show()) return false;
        User u = this.javaFactura.getLoggedUser();
        if(!(u instanceof ContribuinteIndividual)) return false;
        ContribuinteIndividual ci = (ContribuinteIndividual) u;
        this.numDependants.setText(String.valueOf((ci.getNumDependants())));
        this.dependants.setText(ci.getFamilyAggregate()
                                  .stream()
                                  .reduce("", (s, s2) -> s + " " + s2));
        return true;
    }
}
