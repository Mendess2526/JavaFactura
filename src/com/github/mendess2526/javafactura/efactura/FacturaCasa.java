package com.github.mendess2526.javafactura.efactura;

import java.time.LocalDateTime;

public class FacturaCasa extends Factura {

    public FacturaCasa(){
        super();
    }

    public FacturaCasa(String issuerNif, String issuerName, LocalDateTime date, String clientNif,
                       String description, float value){
        super(issuerNif, issuerName, date, clientNif, description, value);
    }

    public FacturaCasa(Factura factura){
        super(factura);
    }

    @Override
    public String getType(){
        return "F3";
    }

    @Override
    public float deducao(){
        return 0;
    }
}
