package com.github.mendess2526.javafactura.efactura;

import java.time.LocalDateTime;

public class FacturaHabitacao extends Factura {

    public FacturaHabitacao(){
        super();
    }

    public FacturaHabitacao(String issuerNif, String issuerName, LocalDateTime date, String clientNif,
                            String description, float value){
        super(issuerNif, issuerName, date, clientNif, description, value);
    }

    public FacturaHabitacao(Factura factura){
        super(factura);
    }

    @Override
    public String getType(){
        return "F6";
    }

    @Override
    public float deducao(){
        return 0;
    }
}
