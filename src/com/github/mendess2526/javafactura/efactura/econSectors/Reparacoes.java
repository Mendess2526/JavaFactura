package com.github.mendess2526.javafactura.efactura.econSectors;

public class Reparacoes extends EconSector implements Deductible{

    @Override
    public String getType(){
        return "E08";
    }

    @Override
    public float deduction(float value){
        return 0;
    }
}
