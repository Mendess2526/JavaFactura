package com.github.mendess2526.javafactura.efactura.econSectors;

public final class Familia extends EconSector implements Deductible {

    @Override
    public String getTypeCode(){
        return T_CODE_FAMILIA;
    }

    @Override
    public float deduction(float value){
        return 0;
    }
}
