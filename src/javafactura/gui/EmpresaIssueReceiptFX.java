package javafactura.gui;

import javafactura.businessLogic.JavaFactura;
import javafactura.businessLogic.exceptions.NoSuchIndividualException;
import javafactura.businessLogic.exceptions.NotEmpresaException;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EmpresaIssueReceiptFX extends FormFX{

    private static final String[] defaultFields = {
            "Nif do cliente", "Valor", "Descrição"
    };

    EmpresaIssueReceiptFX(JavaFactura javaFactura, Stage primaryStage,
                          Scene previousScene){
        super(javaFactura, primaryStage, previousScene, defaultFields);
    }

    protected void submitData(){
        unconfirm();
        if(fieldsNotFilled()) return;
        int field = 0;
        try{
            this.javaFactura.emitirFactura(
                    this.textFields[field++].getText(),
                    Float.parseFloat(textFields[field++].getText()),
                    this.textFields[field++].getText()
            );
            for(TextField t : this.textFields)
                t.clear();
            confirm("Factura emitida");
        }catch(NumberFormatException e){
            this.errorTexts[field - 1].setText(this.textFields[field - 1].getText() + "não é um número");
        }catch(NotEmpresaException e){
            goBack();
        }catch(NoSuchIndividualException e){
            this.errorTexts[0].setText("Contribuinte não existe");
        }
    }
}
