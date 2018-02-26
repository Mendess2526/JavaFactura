package com.github.mendess2526.javafactura.efactura;

import java.time.LocalDateTime;

public class FacturaLares extends Factura {

    public FacturaLares(){
        super();
    }

    public FacturaLares(String issuerNif, String issuerName, LocalDateTime date, String clientNif,
                        String description, float value){
        super(issuerNif, issuerName, date, clientNif, description, value);
    }

    public FacturaLares(Factura factura){
        super(factura);
    }

    @Override
    public String getType(){
        return "F7";
    }

    @Override
    public float deducao(){
        return 0;
    }
}
