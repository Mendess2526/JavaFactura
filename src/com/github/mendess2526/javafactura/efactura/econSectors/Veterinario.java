package com.github.mendess2526.javafactura.efactura.econSectors;

public final class Veterinario extends EconSector implements Deductible{

    @Override
    public String getType(){
        return "E10";
    }

    @Override
    public float deduction(float value){
        return 0;
    }
}
