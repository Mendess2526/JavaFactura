package javafactura.gui;

import javafactura.businessLogic.JavaFactura;
import javafactura.businessLogic.exceptions.NotEmpresaException;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class EmpresaProfileFX extends ContribuinteProfileFX {

    private final Text econSectors;

    public EmpresaProfileFX(JavaFactura javaFactura, Stage primaryStage,
                            Scene previousScene){
        super(javaFactura, primaryStage, previousScene);
        this.econSectors = appendField("Sectores Economicos");
    }

    @Override
    protected boolean show(){
        try{
            this.econSectors.setText(this.javaFactura.getLoggedUserSectors()
                                                     .stream()
                                                     .map(econSector -> econSector.toString() + ";\n")
                                                     .reduce("", (s, s2) -> s + "" + s2));
        }catch(NotEmpresaException e){
            return false;
        }
        return super.show();
    }
}
