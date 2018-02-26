package com.github.mendess2526.javafactura.efactura;

import java.time.LocalDateTime;

public class FacturaEducacao extends Factura {

    public FacturaEducacao(){
        super();
    }

    public FacturaEducacao(String issuerNif, String issuerName, LocalDateTime date, String clientNif,
                           String description, float value){
        super(issuerNif, issuerName, date, clientNif, description, value);
    }

    public FacturaEducacao(Factura factura){
        super(factura);
    }

    @Override
    public String getType(){
        return "F4";
    }

    @Override
    public float deducao(){
        return 0;
    }
}
