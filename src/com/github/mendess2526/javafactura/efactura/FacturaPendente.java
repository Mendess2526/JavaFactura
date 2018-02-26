package com.github.mendess2526.javafactura.efactura;

import java.time.LocalDateTime;

public class FacturaPendente extends Factura {

    public FacturaPendente(){
        super();
    }

    public FacturaPendente(String issuerNif, String issuerName, LocalDateTime date, String clientNif,
                           String description, float value){
        super(issuerNif, issuerName, date, clientNif, description, value);
    }

    public FacturaPendente(Factura factura){
        super(factura);
    }

    @Override
    public String getType(){
        return "F0";
    }

    @Override
    public float deducao(){
        throw new UnsupportedOperationException();
    }
}
