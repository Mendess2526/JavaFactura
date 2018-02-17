package com.github.mendess2526.javafactura.efactura;

import java.time.LocalDateTime;
import java.util.Objects;

public class Factura {

    private final String issuerNif;
    private final String issuerName;
    private final LocalDateTime date;
    private final String clientNif;
    private final String description;
    private final EconActivity type;
    private final float value;

    public Factura(String issuerNif, String issuerName, LocalDateTime date, String clientNif,
                   String description, EconActivity type, float value){
        this.issuerNif = issuerNif;
        this.issuerName = issuerName;
        this.date = date;
        this.clientNif = clientNif;
        this.description = description;
        this.type = type;
        this.value = value;
    }

    public Factura(Factura other){
        this.issuerNif = other.getIssuerNif();
        this.issuerName = other.getIssuerName();
        this.date = other.getDate();
        this.clientNif = other.getClientNif();
        this.description = other.getDescription();
        this.type = other.getType();
        this.value = other.getValue();
    }

    public String getIssuerNif(){
        return issuerNif;
    }

    public String getIssuerName(){
        return issuerName;
    }

    public LocalDateTime getDate(){
        return date;
    }

    public String getClientNif(){
        return clientNif;
    }

    public String getDescription(){
        return description;
    }

    public EconActivity getType(){
        return type;
    }

    public float getValue(){
        return value;
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;

        if(o == null || getClass() != o.getClass()) return false;

        Factura factura = (Factura) o;
        return factura.getValue() == this.value &&
                Objects.equals(this.issuerNif, factura.issuerNif) &&
                Objects.equals(this.issuerName, factura.issuerName) &&
                Objects.equals(this.date, factura.date) &&
                Objects.equals(this.clientNif, factura.clientNif) &&
                Objects.equals(this.description, factura.description) &&
                this.type == factura.getType();
    }

    @Override
    public String toString(){
        return "Factura{" +
                "issuerNif='" + issuerNif + '\'' +
                ", issuerName='" + issuerName + '\'' +
                ", date=" + date +
                ", clientNif='" + clientNif + '\'' +
                ", description='" + description + '\'' +
                ", type=" + type +
                ", value=" + value +
                '}';
    }


}
