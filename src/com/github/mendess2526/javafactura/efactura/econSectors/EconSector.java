package com.github.mendess2526.javafactura.efactura.econSectors;

import java.io.Serializable;

public abstract class EconSector implements Serializable {

    public static EconSector factory(String typeCode){
        switch(typeCode){
            case "E00":
                return new Pendente();
            case "E01":
                return new AlojamentoRestauracao();
            case "E02":
                return new Cabeleireiro();
            case "E03":
                return new Educacao();
            case "E04":
                return new Familia();
            case "E05":
                return new Habitacao();
            case "E06":
                return new Lares();
            case "E07":
                return new Reparacoes();
            case "E08":
                return new Saude();
            case "E09":
                return new Veterinario();
            default:
                return new Pendente();
        }
    }

    public abstract String getType();

    @Override
    public boolean equals(Object obj){
        return obj != null
               && this.getClass() == obj.getClass();
    }

    @Override
    public String toString(){
        return this.getClass().getSimpleName();
    }
}
