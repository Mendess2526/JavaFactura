package com.github.mendess2526.javafactura.efactura;

import java.time.LocalDateTime;

public class FacturaCableireiro extends Factura {

    public FacturaCableireiro(){
        super();
    }

    public FacturaCableireiro(String issuerNif, String issuerName, LocalDateTime date, String clientNif,
                              String description, float value){
        super(issuerNif, issuerName, date, clientNif, description, value);
    }

    public FacturaCableireiro(Factura factura){
        super(factura);
    }

    @Override
    public String getType(){
        return "F2";
    }

    @Override
    public float deducao(){
        return 0;
    }
}
