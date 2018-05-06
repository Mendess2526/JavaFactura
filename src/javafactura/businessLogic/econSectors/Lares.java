package javafactura.businessLogic.econSectors;

public final class Lares extends EconSector implements Deductible {

    @Override
    public String getTypeCode(){
        return T_CODE_LARES;
    }

    @Override
    public float deduction(float value){
        return 0;
    }
}
