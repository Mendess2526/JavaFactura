package javafactura.businessLogic.econSectors;

public final class Educacao extends EconSector implements Deductible {

    @Override
    public String getTypeCode(){
        return T_CODE_EDUCACAO;
    }

    @Override
    public float deduction(float value){
        return 0;
    }
}
