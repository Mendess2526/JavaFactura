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
     * Empty constructor
     */
    public Factura(){
        this.issuerNif = "";
        this.issuerName = "";
        this.date = null;
        this.clientNif = "";
        this.description = "";
        this.type = null;
        this.value = 0.0f;
    }

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
     * @param factura the <tt>Factura</tt> to copy
     */
    public Factura(Factura factura){
        this.issuerNif = factura.getIssuerNif();
        this.issuerName = factura.getIssuerName();
        this.date = factura.getDate();
        this.clientNif = factura.getClientNif();
        this.description = factura.getDescription();
        this.type = factura.getType();
        this.value = factura.getValue();
        this.pending = factura.isPending();
    }

    /**
     * \brief The NIF of the entity that issued this Receipt
     * @return The NIF of the entity that issued this Receipt
     */
    public String getIssuerNif(){
        return this.issuerNif;
    }

    /**
     * Returns the Name of the company that issued the receipt
     * @return The Name of the company that issued the receipt
     */
    public String getIssuerName(){
        return this.issuerName;
    }

    /**
     * Returns the date of the receipt
     * @return The date of the receipt
     */
    public LocalDateTime getDate(){
        return this.date;
    }

    /**
     * Returns the NIF of the client
     * @return The NIF of the client
     */
    public String getClientNif(){
        return this.clientNif;
    }

    /**
     * Returns the description of the purchase
     * @return The description of the purchase
     */
    public String getDescription(){
        return this.description;
    }

    /**
     * Returns the type of the receipt
     * @return The type of the receipt
     */
    public EconActivity getType(){
        return this.type;
    }

    /**
     * Returns the value of the purchase
     * @return The value of the purchase
     */
    public float getValue(){
        return this.value;
    }

    /**
     * Returns if the receipt is pending
     * @return <tt>true</tt> if the receipt is pending
     */
    public boolean isPending(){
        return this.pending;
    }

    /**
     * Changes the type of the receipt
     * @param type the new type
     */
    public void setType(EconActivity type){
        if(this.pending){
            this.type = type;
            this.pending = type == null;
        }
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;

        if(o == null || getClass() != o.getClass()) return false;

        Factura that = (Factura) o;
        return  this.value == that.getValue() &&
                this.issuerNif.equals(that.getIssuerNif()) &&
                this.issuerName.equals(that.getIssuerName()) &&
                this.date.equals(that.getDate()) &&
                this.clientNif.equals(that.getClientNif()) &&
                this.description.equals(that.getDescription()) &&
                this.type == that.getType();
    }

    @Override
    public String toString(){
        return "Factura{" +
                "issuerNif='" + this.issuerNif + '\'' +
                ", issuerName='" + this.issuerName + '\'' +
                ", date=" + this.date +
                ", clientNif='" + this.clientNif + '\'' +
                ", description='" + this.description + '\'' +
                ", type=" + this.type +
                ", value=" + this.value +
                '}';
    }

    /*
    @Override
    public String toString(){
        return new StringBuilder().append("Factura{")
                .append("issuerNif='").append(this.issuerNif).append('\'')
                .append(", issuerName='").append(this.issuerName).append('\'')
                .append(", date=").append(this.date)
                .append(", clientNif='").append(this.clientNif).append('\'')
                .append(", description='").append(this.description).append('\'')
                .append(", type=").append(this.type)
                .append(", value=").append(this.value)
                .append('}').toString();
    }
     */

    public Factura clone(){
        return new Factura(this);
    }

}
