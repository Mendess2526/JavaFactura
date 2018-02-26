package com.github.mendess2526.javafactura.efactura;

import java.time.LocalDateTime;

public class FacturaSaude extends Factura{

    public FacturaSaude(){
        super();
    }

    public FacturaSaude(String issuerNif, String issuerName, LocalDateTime date, String clientNif,
                        String description, float value){
        super(issuerNif, issuerName, date, clientNif, description, value);
    }

    public FacturaSaude(Factura factura){
        super(factura);
    }

    @Override
    public String getType(){
        return "F9";
    }

    @Override
    public float deducao(){
        return 0;
    }
}
