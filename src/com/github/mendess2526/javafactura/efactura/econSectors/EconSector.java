package com.github.mendess2526.javafactura.efactura.econSectors;

public abstract class EconSector {

    public static EconSector factory(String typeCode){
        switch(typeCode){
            case "E00": return new Pendente();
            case "E01": return new Alojamento();
            case "E02": return new Cableireiro();
            case "E03": return new Casa();
            case "E04": return new Educacao();
            case "E05": return new Familia();
            case "E06": return new Habitacao();
            case "E07": return new Lares();
            case "E08": return new Reparacoes();
            case "E09": return new Saude();
            case "E10": return new Veterinario();
            default: return new Pendente();
        }
    }

    public abstract String getType();
}
