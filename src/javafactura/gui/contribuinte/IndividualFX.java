package javafactura.gui.contribuinte;

import javafactura.businessLogic.Factura;
import javafactura.businessLogic.JavaFactura;
import javafactura.businessLogic.econSectors.Pendente;
import javafactura.businessLogic.exceptions.NotIndividualException;
import javafx.collections.ListChangeListener;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class IndividualFX extends ShowReceiptsFx {

    private final Label totalDeducted;
    private final Label pendingNum;
    private final Label totalDeductedFamily;

    public IndividualFX(JavaFactura javaFactura, Stage primaryStage, Scene previousScene){
        super(javaFactura, primaryStage, previousScene, true);
        ColumnConstraints cc = new ColumnConstraints();
        cc.setPercentWidth(90);
        this.gridPane.getColumnConstraints().add(cc);

        this.facturas.addListener((ListChangeListener<Factura>) c -> updateTotals());

        IndividualProfileFx individualProfileFx = new IndividualProfileFx(this.javaFactura, this.primaryStage,
                                                                          this.scene);

        int row = 0;
        Button profileButton = new Button("Profile");
        profileButton.setOnAction(e -> individualProfileFx.show());
        this.gridPane.add(makeHBox(profileButton, Pos.CENTER_RIGHT), 0, row++);

        this.pendingNum = new Label();
        this.totalDeducted = new Label();
        this.totalDeductedFamily = new Label();
        HBox topRowHBox = new HBox(this.pendingNum, this.totalDeducted, this.totalDeductedFamily);
        topRowHBox.setSpacing(100);

        this.gridPane.add(topRowHBox, 0, row++);

        this.gridPane.add(receiptsTable, 0, row++);

        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(event -> logOut());
        this.gridPane.add(makeHBox(logoutButton, Pos.BOTTOM_RIGHT), 0, row);

        this.primaryStage.sceneProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.equals(this.scene)){
                this.receiptsTable.refresh();
                updateTotals();
            }
        });
    }

    private void updateTotals(){
        long count = this.facturas
                .stream()
                .filter(f -> f.getType() instanceof Pendente)
                .count();
        this.pendingNum.setText(String.valueOf(count) + " facturas pendente(s)");
        if(count == 0) this.pendingNum.setTextFill(Color.GREEN);
        else this.pendingNum.setTextFill(Color.RED);
        try{
            this.totalDeducted.setText("Total deduzido: " + this.javaFactura.getAccumulatedDeduction());
            this.totalDeductedFamily.setText("Total deduzido pelo agregado familiar: "
                                             + this.javaFactura.getAccumulatedDeductionFamilyAggregate());
        }catch(NotIndividualException e){
            goBack();
        }
    }

    @Override
    protected void goBack(){
        throw new UnsupportedOperationException();
    }

    private void logOut(){
        this.javaFactura.logout();
        this.primaryStage.setScene(this.previousScene);
    }

}
