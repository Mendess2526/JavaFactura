package com.github.mendess2526.javafactura.efactura;

import com.github.mendess2526.javafactura.efactura.econSectors.Deductible;
import com.github.mendess2526.javafactura.efactura.econSectors.EconSector;
import com.github.mendess2526.javafactura.efactura.econSectors.Pendente;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Factura implements Comparable<Factura>, Serializable{

    /** The format the creationDate will be printed with when toString is called on this */
    public static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyy kk:mm:ss");

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
    private final LocalDateTime creationDate;
    /**
     * \brief The date this Factura was last edited
     */
    private LocalDateTime lastEditDate;
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
     * \brief The econ sectors of the company that issued this receipt
     */
    private List<EconSector> possibleEconSectors;

    /**
     * Empty constructor
     */
    private Factura(){
        this.id = lastId++;
        this.issuerNif = "";
        this.issuerName = "";
        this.creationDate = LocalDateTime.now();
        this.lastEditDate = this.creationDate;
        this.clientNif = "";
        this.description = "";
        this.value = 0.0f;
        this.history = new LinkedList<>();
        this.history.add(this);
        this.econSector = new Pendente();
    }

    /**
     * \brief Fully parametrised constructor for <tt>Factura</tt>
     * @param issuerNif The NIF of the entity that issued this Receipt
     * @param issuerName The name of the entity that issued this Receipt
     * @param clientNif The NIF of the client to whom this Receipt was issued
     * @param description The description of the purchase
     * @param value The value of the purchase
     * @param econSector The economic sector of this Factura
     * @param possibleEconSectors The econ sectors of the company that issued this receipt
     */
    Factura(String issuerNif, String issuerName, String clientNif,
            String description, float value, EconSector econSector,
            List<EconSector> possibleEconSectors){
        this.id = lastId++;
        this.issuerNif = issuerNif;
        this.issuerName = issuerName;
        this.creationDate = LocalDateTime.now();
        this.lastEditDate = this.creationDate;
        this.clientNif = clientNif;
        this.description = description;
        this.value = value;
        this.history = new LinkedList<>();
        this.history.add(this);
        this.econSector = econSector;
        this.possibleEconSectors = new ArrayList<>(possibleEconSectors);
    }

    /**
     * Copy constructor for <tt>Factura</tt>
     * @param factura the <tt>Factura</tt> to copy
     */
    public Factura(Factura factura){
        this.id = factura.getId();
        this.issuerNif = factura.getIssuerNif();
        this.issuerName = factura.getIssuerName();
        this.creationDate = factura.getCreationDate();
        this.lastEditDate = factura.getLastEditDate();
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
     * Returns the creation date of the receipt
     * @return The creation date of the receipt
     */
    public LocalDateTime getCreationDate(){
        return this.creationDate;
    }

    /**
     * Returns the last date the receipt was edited
     * @return The last date the receipt was edited
     */
    public LocalDateTime getLastEditDate(){
        return this.lastEditDate;
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
    public List<Factura> getHistory(){
        return this.history
                .stream()
                .map(Factura::clone)
                .collect(Collectors.toCollection(LinkedList::new));
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
    void setEconSector(EconSector econSector){
        ((LinkedList<Factura>) this.history).addFirst(this);
        this.econSector = econSector;
        this.lastEditDate = LocalDateTime.now();
    }

    /**
     * Returns the type of the <tt>Factura</tt>
     * @return The type of the <tt>Factura</tt>
     */
    public EconSector getType(){
        return this.econSector;
    }

    /**
     * Returns the list of possible econ sectors
     * @return The list of possible econ sectors
     */
    public List<EconSector> getPossibleEconSectors(){
        return new ArrayList<>(possibleEconSectors);
    }

    /**
     * Returns if the <tt>Factura</tt> is deductible
     * @return if the <tt>Factura</tt> is deductible
     */
    private boolean isDeductible(){
        return this.econSector instanceof Deductible;
    }

    /**
     * Returns the amount that can be deducted from this
     * @return The amount that can be deducted from this
     */
    public float deducao(){
        if(this.isDeductible()){
            return ((Deductible) this.econSector).deduction(this.value);
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
                this.creationDate.equals(that.getCreationDate()) &&
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
                ", creationDate=" + creationDate.format(dateFormat) +
                ", clientNif='" + clientNif + '\'' +
                ", description='" + description + '\'' +
                ", value=" + value +
                ", history=" + history.size() +
                '}';
    }

    @Override
    public Factura clone(){
        return new Factura(this);
    }

    @Override
    public int compareTo(Factura o){
        if(this.creationDate.isBefore(o.getCreationDate())) return -1;
        if(this.creationDate.isAfter(o.getCreationDate())) return 1;
        return 0;
    }
}
