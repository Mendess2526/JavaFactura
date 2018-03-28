package com.github.mendess2526.javafactura.efactura.econSectors;

public class AlojamentoRestauracao extends EconSector implements Deductible{

    @Override
    public String getType(){
        return "E01";
    }

    @Override
    public float deduction(float value){
        return 0;
    }
}
