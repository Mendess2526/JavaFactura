package com.github.mendess2526.javafactura.efactura;

import java.time.LocalDateTime;

public class FacturaAlojamento extends Factura{

    public FacturaAlojamento(){
        super();
    }

    public FacturaAlojamento(String issuerNif, String issuerName, LocalDateTime date, String clientNif,
                             String description, EconActivity type, float value){
        super(issuerNif, issuerName, date, clientNif, description, value);
    }

    public FacturaAlojamento(Factura factura){
        super(factura);
    }

    @Override
    public String getType(){
        return "F1";
    }

    @Override
    public float deducao(){
        return 0;
    }
}
