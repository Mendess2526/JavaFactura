package com.github.mendess2526.javafactura.efactura.econSectors;

public class Saude extends EconSector implements Deductable{

    @Override
    public String getType(){
        return "E09";
    }

    @Override
    public float deduction(){
        return 0;
    }
}
