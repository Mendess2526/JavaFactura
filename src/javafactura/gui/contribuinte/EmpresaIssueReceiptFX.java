package javafactura.gui.contribuinte;

import javafactura.businessLogic.JavaFactura;
import javafactura.businessLogic.exceptions.NoSuchIndividualException;
import javafactura.businessLogic.exceptions.NotEmpresaException;
import javafactura.gui.FormFX;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EmpresaIssueReceiptFX extends FormFX {

    private static final String[] defaultFields = {
            "Nif do cliente", "Valor", "Descrição"
    };
    private final EmpresaFX.TableRefresher tableRefresher;
    private boolean emitted;

    public EmpresaIssueReceiptFX(JavaFactura javaFactura, Stage primaryStage,
                                 Scene previousScene,
                                 EmpresaFX.TableRefresher tableRefresher){
        super(javaFactura, primaryStage, previousScene, defaultFields);
        this.tableRefresher = tableRefresher;
        this.emitted = false;
    }

    @Override
    protected void goBack(){
        if(this.emitted) this.tableRefresher.refresh();
        super.goBack();
    }

    protected void submitData(){
        unconfirm();
        if(fieldsNotFilled()) return;
        int field = 0;
        try{
            this.javaFactura.emitirFactura(
                    this.textFields[field++].getText(),
                    Float.parseFloat(textFields[field++].getText().replace(",", ".")),
                    this.textFields[field++].getText()
            );
            for(TextField t : this.textFields)
                t.clear();
            confirm("Factura emitida");
            this.emitted = true;
        }catch(NumberFormatException e){
            this.errorTexts[field - 1].setText(this.textFields[field - 1].getText() + " não é um número");
        }catch(NotEmpresaException e){
            goBack();
        }catch(NoSuchIndividualException e){
            this.errorTexts[0].setText("Contribuinte não existe");
        }
    }
}
