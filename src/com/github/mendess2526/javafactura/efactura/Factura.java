package com.github.mendess2526.javafactura.efactura;

import java.time.LocalDateTime;
import java.util.Objects;

public class Factura {

    /**
     * \brief The NIF of the entity that issued this Receipt
     */
    private final String issuerNif;
    /**
     * \brief The name of the entity that issued this Receipt
     */
    private final String issuerName;
    /**
     * \brief The date this Receipt was issued
     */
    private final LocalDateTime date;
    /**
     * \brief The NIF of the client to whom this Receipt was issued
     */
    private final String clientNif;
    /**
     * \brief The description of the purchase
     */
    private final String description;
    /**
     * \brief The type of economic Activity
     */
    private EconActivity type;
    /**
     * \brief The value of the purchase
     */
    private final float value;
    /**
     * \brief The status of the Receipt. A receipt is pending if it's economic activity is not defined
     */
    private boolean pending;

    /**
     * \brief Fully parameterised constructor for <tt>Factura</tt>
     * @param issuerNif The NIF of the entity that issued this Receipt
     * @param issuerName The name of the entity that issued this Receipt
     * @param date The date this Receipt was issued
     * @param clientNif The NIF of the client to whom this Receipt was issued
     * @param description The description of the purchase
     * @param type The type of economic Activity
     * @param value The value of the purchase
     */
    public Factura(String issuerNif, String issuerName, LocalDateTime date, String clientNif,
                   String description, EconActivity type, float value){
        this.issuerNif = issuerNif;
        this.issuerName = issuerName;
        this.date = date;
        this.clientNif = clientNif;
        this.description = description;
        this.type = type;
        this.value = value;
        this.pending = type == null;
    }

    /**
     * Copy constructor for <tt>Factura</tt>
     * @param other the <tt>Factura</tt> to copy
     */
    public Factura(Factura other){
        this.issuerNif = other.getIssuerNif();
        this.issuerName = other.getIssuerName();
        this.date = other.getDate();
        this.clientNif = other.getClientNif();
        this.description = other.getDescription();
        this.type = other.getType();
        this.value = other.getValue();
        this.pending = other.isPending();
    }

    /**
     * \brief The NIF of the entity that issued this Receipt
     * @return The NIF of the entity that issued this Receipt
     */
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

    public boolean isPending(){
        return pending;
    }

    public void setType(EconActivity type){
        this.type = type;
        this.pending = type == null;
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

    public Factura clone(){
        return new Factura(this);
    }

}
