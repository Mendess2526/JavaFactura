package com.github.mendess2526.javafactura.efactura;

import java.time.LocalDateTime;

public class FacturaReparacoes extends Factura {

    public FacturaReparacoes(){
        super();
    }

    public FacturaReparacoes(String issuerNif, String issuerName, LocalDateTime date, String clientNif,
                             String description, float value){
        super(issuerNif, issuerName, date, clientNif, description, value);
    }

    public FacturaReparacoes(Factura factura){
        super(factura);
    }

    @Override
    public String getType(){
        return "F8";
    }

    @Override
    public float deducao(){
        return 0;
    }
}
