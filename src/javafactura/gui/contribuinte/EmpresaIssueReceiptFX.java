package javafactura.gui.contribuinte;

import javafactura.businessLogic.Factura;
import javafactura.businessLogic.JavaFactura;
import javafactura.businessLogic.exceptions.NoSuchIndividualException;
import javafactura.businessLogic.exceptions.NotEmpresaException;
import javafactura.businessLogic.exceptions.NotIndividualException;
import javafactura.gui.FormFX;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * {@inheritDoc}
 * Class that represents the issue {@link Factura} screen
 */
public class EmpresaIssueReceiptFX extends FormFX {

    /**
     * The form's fields
     */
    private static final String[] defaultFields = {
            "Nif do cliente", "Valor", "Descrição"
    };
    /**
     * The table refresher so to refresh a relevant table when going back
     */
    private final EmpresaFX.TableRefresher tableRefresher;
    /**
     * Stores if a {@link Factura} was emitted during this session
     */
    private boolean emitted;

    /**
     * Constructor for a application window
     * @param javaFactura    The business logic instance
     * @param primaryStage   The stage where the window exists
     * @param previousScene  The previous scene (null if this is the root window)
     * @param tableRefresher The table refresher instance
     */
    public EmpresaIssueReceiptFX(JavaFactura javaFactura, Stage primaryStage,
                                 Scene previousScene,
                                 EmpresaFX.TableRefresher tableRefresher){
        super(javaFactura, primaryStage, previousScene, defaultFields);
        this.tableRefresher = tableRefresher;
        this.emitted = false;
    }

    /**
     * {@inheritDoc} and sets emitted to {@code false}
     * @return {@inheritDoc}
     */
    @Override
    public boolean show(){
        this.emitted = false;
        return super.show();
    }

    /**
     * {@inheritDoc} and sets emitted to false
     */
    @Override
    protected void goBack(){
        if(this.emitted) this.tableRefresher.refresh();
        super.goBack();
    }

    /**
     * {@inheritDoc}
     */
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
        }catch(NotIndividualException e){
            this.errorTexts[0].setText("Não é um contribuinte individual");
        }
    }
}
