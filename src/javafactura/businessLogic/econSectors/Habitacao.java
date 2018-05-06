package javafactura.businessLogic.econSectors;

public final class Habitacao extends EconSector implements Deductible {

    @Override
    public String getTypeCode(){
        return T_CODE_HABITACAO;
    }

    @Override
    public float deduction(float value){
        return 0;
    }
}
