package com.github.mendess2526.javafactura.efactura.econSectors;

public final class Reparacoes extends EconSector implements Deductible {

    @Override
    public String getTypeCode(){
        return T_CODE_REPARACOES;
    }

    @Override
    public float deduction(float value){
        return 0;
    }
}
