package javafactura.businessLogic.econSectors;

public final class Cabeleireiro extends EconSector implements Deductible {

    @Override
    public String getTypeCode(){
        return T_CODE_CABELEIREIRO;
    }

    @Override
    public float deduction(float value){
        return 0;
    }
}
