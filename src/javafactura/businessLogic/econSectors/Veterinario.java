package javafactura.businessLogic.econSectors;

public final class Veterinario extends EconSector implements Deductible {

    @Override
    public String getTypeCode(){
        return T_CODE_VETERINARIO;
    }

    @Override
    public float deduction(float value){
        return 0;
    }
}
