package com.github.mendess2526.javafactura.efactura.econSectors;

public class Familia extends EconSector implements Deductable{

    @Override
    public String getType(){
        return "E05";
    }

    @Override
    public float deduction(){
        return 0;
    }
}
