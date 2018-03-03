package com.github.mendess2526.javafactura.efactura.econSectors;

public class Educacao extends EconSector implements Deductible {

    @Override
    public String getType(){
        return "E04";
    }

    @Override
    public float deduction(){
        return 0;
    }
}
