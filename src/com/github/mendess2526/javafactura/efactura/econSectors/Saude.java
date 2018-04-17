package com.github.mendess2526.javafactura.efactura.econSectors;

public final class Saude extends EconSector implements Deductible {

    @Override
    public String getType(){
        return "E09";
    }

    @Override
    public float deduction(float value){
        return 0;
    }
}
