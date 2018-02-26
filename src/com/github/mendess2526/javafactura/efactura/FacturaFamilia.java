package com.github.mendess2526.javafactura.efactura;

import java.time.LocalDateTime;

public class FacturaFamilia extends Factura{

    public FacturaFamilia(){
        super();
    }

    public FacturaFamilia(String issuerNif, String issuerName, LocalDateTime date, String clientNif,
                          String description, float value){
        super(issuerNif, issuerName, date, clientNif, description, value);
    }

    public FacturaFamilia(Factura factura){
        super(factura);
    }

    @Override
    public String getType(){
        return "F5";
    }

    @Override
    public float deducao(){
        return 0;
    }
}
