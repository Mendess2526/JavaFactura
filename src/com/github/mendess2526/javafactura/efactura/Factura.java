package com.github.mendess2526.javafactura.efactura;

import com.github.mendess2526.javafactura.efactura.econSectors.Deductable;
import com.github.mendess2526.javafactura.efactura.econSectors.EconSector;
import com.github.mendess2526.javafactura.efactura.econSectors.Pendente;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class Factura {

    /**
     * The last used id
     */
    private static int lastId = 0;

    /**
     * \brief The unique id the Factura
     */
    private final int id;
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
     * \brief History of states of this <tt>Factura</tt>
     */
    private final List<Factura> history;
    /**
     * \brief The economic sector
     */
    private EconSector econSector;

    /**
     * Empty constructor
     */
    private Factura(){
        this.id = lastId++;
        this.issuerNif = "";
        this.issuerName = "";
        this.date = null;
        this.clientNif = "";
        this.description = "";
        this.value = 0.0f;
        this.history = new LinkedList<>();
        this.history.add(this);
        this.econSector = new Pendente();
    }

    /**
     * \brief Fully parameterised constructor for <tt>Factura</tt>
     * @param issuerNif The NIF of the entity that issued this Receipt
     * @param issuerName The name of the entity that issued this Receipt
     * @param date The date this Receipt was issued
     * @param clientNif The NIF of the client to whom this Receipt was issued
     * @param description The description of the purchase
     * @param value The value of the purchase
     * @param econSector The economic sector of this Factura
     */
    Factura(String issuerNif, String issuerName, LocalDateTime date, String clientNif,
            String description, float value, EconSector econSector){
        this.id = lastId++;
        this.issuerNif = issuerNif;
        this.issuerName = issuerName;
        this.date = date;
        this.clientNif = clientNif;
        this.description = description;
        this.value = value;
        this.history = new LinkedList<>();
        this.history.add(this);
        this.econSector = econSector;
    }

    /**
     * Copy constructor for <tt>Factura</tt>
     * @param factura the <tt>Factura</tt> to copy
     */
    public Factura(Factura factura){
        this.id = factura.getId();
        this.issuerNif = factura.getIssuerNif();
        this.issuerName = factura.getIssuerName();
        this.date = factura.getDate();
        this.clientNif = factura.getClientNif();
        this.description = factura.getDescription();
        this.value = factura.getValue();
        this.history = factura.getHistory();
        this.history.add(this);
        this.econSector = factura.getEconSector();
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
     * Returns the unique id
     * @return The unique id
     */
    public int getId(){
        return this.id;
    }

    /**
     * Returns the history of state of this <tt>Factura</tt>
     * @return The history of state of this <tt>Factura</tt>
     */
    private List<Factura> getHistory(){
        return new LinkedList<>(this.history);
    }

    /**
     * Returns the economic sector
     * @return The economic sector
     */
    private EconSector getEconSector(){
        return this.econSector;
    }

    /**
     * Changes the economic sector of the <tt>Factura</tt>
     * @param econSector the new economic sector
     */
    public void setEconSector(EconSector econSector){
        this.econSector = econSector;
    }

    /**
     * Returns the type of the <tt>Factura</tt>
     * @return The type of the <tt>Factura</tt>
     */
    public String getType(){
        return this.econSector.getType();
    }

    /**
     * Returns if the <tt>Factura</tt> is deductible
     * @return if the <tt>Factura</tt> is deductible
     */
    private boolean isDeductable(){
        return this.econSector instanceof Deductable;
    }
    /**
     * Returns the amount that can be deducted from this
     * @return The amount that can be deducted from this
     */
    public float deducao(){
        if(this.isDeductable()){
            return ((Deductable) this.econSector).deduction();
        }
        return 0.0f;
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
                this.history.equals(that.history) &&
                this.id == that.getId();
    }

    @Override
    public String toString(){
        return "Factura{" +
                "id=" + id +
                ", issuerNif='" + issuerNif + '\'' +
                ", issuerName='" + issuerName + '\'' +
                ", date=" + date +
                ", clientNif='" + clientNif + '\'' +
                ", description='" + description + '\'' +
                ", value=" + value +
                ", history=" + history +
                '}';
    }
}
