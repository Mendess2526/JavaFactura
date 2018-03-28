package com.github.mendess2526.javafactura.efactura.econSectors;

public class Habitacao extends EconSector implements Deductible {

    @Override
    public String getType(){
        return "E06";
    }

    @Override
    public float deduction(float value){
        return 0;
    }
}
