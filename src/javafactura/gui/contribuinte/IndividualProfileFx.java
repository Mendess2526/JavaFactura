package javafactura.gui.contribuinte;

import javafactura.businessLogic.ContribuinteIndividual;
import javafactura.businessLogic.JavaFactura;
import javafactura.businessLogic.User;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class IndividualProfileFx extends ContribuinteProfileFX {

    private final Text numDependants;
    private final Text dependantes;

    public IndividualProfileFx(JavaFactura javaFactura, Stage primaryStage,
                               Scene previousScene){
        super(javaFactura, primaryStage, previousScene);

        this.numDependants = appendField("#dependentes: ");
        this.dependantes = appendField("Agregado Familiar: ");
    }

    @Override
    public boolean show(){
        if(!super.show()) return false;
        User u = this.javaFactura.getLoggedUser();
        if(!(u instanceof ContribuinteIndividual)) return false;
        ContribuinteIndividual ci = (ContribuinteIndividual) u;
        this.numDependants.setText(String.valueOf((ci.getNumDependants())));
        this.dependantes.setText(ci.getFamilyAggregate()
                                   .stream()
                                   .reduce("", (s, s2) -> s + " " + s2));
        return true;
    }
}
