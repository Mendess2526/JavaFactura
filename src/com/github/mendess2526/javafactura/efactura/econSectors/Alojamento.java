package com.github.mendess2526.javafactura.efactura.econSectors;

import java.io.Serializable;

public class Alojamento extends EconSector implements Serializable{

    @Override
    public String getType(){
        return "E01";
    }
}
