package com.github.mendess2526.javafactura.efactura;

import java.time.LocalDateTime;

public class FacturaVeterinario extends Factura {

    public FacturaVeterinario(){
        super();
    }

    public FacturaVeterinario(String issuerNif, String issuerName, LocalDateTime date, String clientNif,
                              String description, float value){
        super(issuerNif, issuerName, date, clientNif, description, value);
    }

    public FacturaVeterinario(Factura factura){
        super(factura);
    }

    @Override
    public String getType(){
        return "F10";
    }

    @Override
    public float deducao(){
        return 0;
    }
}
