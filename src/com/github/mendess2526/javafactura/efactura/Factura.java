package com.github.mendess2526.javafactura.efactura;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public abstract class Factura {

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
     * \brief The value of the purchase
     */
    private final float value;
    /**
     *
     */
    private final List<Factura> history;

    /**
     * Empty constructor
     */
    Factura(){
        this.issuerNif = "";
        this.issuerName = "";
        this.date = null;
        this.clientNif = "";
        this.description = "";
        this.value = 0.0f;
        this.history = new LinkedList<>();
    }

    /**
     * \brief Fully parameterised constructor for <tt>Factura</tt>
     * @param issuerNif The NIF of the entity that issued this Receipt
     * @param issuerName The name of the entity that issued this Receipt
     * @param date The date this Receipt was issued
     * @param clientNif The NIF of the client to whom this Receipt was issued
     * @param description The description of the purchase
     * @param value The value of the purchase
     */
    public Factura(String issuerNif, String issuerName, LocalDateTime date, String clientNif,
                   String description, float value){
        this.issuerNif = issuerNif;
        this.issuerName = issuerName;
        this.date = date;
        this.clientNif = clientNif;
        this.description = description;
        this.value = value;
        this.history = new LinkedList<>();
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
        this.value = factura.getValue();
        this.history = factura.getHistory();
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
     * Returns the value of the purchase
     * @return The value of the purchase
     */
    public float getValue(){
        return this.value;
    }

    /**
     * Returns the history of state of this <tt>Factura</tt>
     * @return The history of state of this <tt>Factura</tt>
     */
    public List<Factura> getHistory(){
        return new LinkedList<>(this.history);
    }

    /**
     * Returns the type of the <tt>Factura</tt>
     * @return The type of the <tt>Factura</tt>
     */
    public abstract String getType();

    public abstract float deducao();

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
                this.description.equals(that.getDescription());
    }

    @Override
    public String toString(){
        return "Factura{" +
                "issuerNif='" + this.issuerNif + '\'' +
                ", issuerName='" + this.issuerName + '\'' +
                ", date=" + this.date +
                ", clientNif='" + this.clientNif + '\'' +
                ", description='" + this.description + '\'' +
                ", value=" + this.value +
                '}';
    }
}
