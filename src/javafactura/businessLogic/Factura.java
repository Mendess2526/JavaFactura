package javafactura.businessLogic;

import javafactura.businessLogic.econSectors.Deductible;
import javafactura.businessLogic.econSectors.EconSector;
import javafactura.businessLogic.econSectors.Pendente;
import javafactura.businessLogic.exceptions.InvalidEconSectorException;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Representation of a receipt in the application
 * {@link Factura}s are comparable based on their creation date
 */
public class Factura implements Comparable<Factura>,
                                Serializable {

    /** The format the creationDate will be printed with when toString is called on this */
    public static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyy\tkk:mm:ss");
    private static final long serialVersionUID = 9214165720384168988L;
    /**
     * The NIF of the entity that issued this Receipt
     */
    private final String issuerNif;
    /**
     * The name of the entity that issued this Receipt
     */
    private final String issuerName;
    /**
     * The date this Receipt was issued
     */
    private final LocalDateTime creationDate;
    /**
     * The NIF of the client to whom this Receipt was issued
     */
    private final String clientNif;
    /**
     * The description of the purchase
     */
    private final String description;
    /**
     * The value of the purchase
     */
    private final float value;
    /**
     * The {@link EconSector}s of the company that issued this receipt
     */
    private final Set<EconSector> possibleEconSectors;
    /**
     * History of states of this {@link Factura}
     */
    private final LinkedList<Factura> history;
    /**
     * A boolean to store if the company the issued the Receipt is to benefited
     */
    private final boolean isEmpresaInterior;
    /**
     * The size of the aggregate at the time of emission
     */
    private final int aggregateSize;
    /**
     * The fiscal coefficient of the company at the time of emission
     */
    private final float coeffEmpresa;
    /**
     * The fiscal coefficient of the individual at the time of emission
     */
    private final float coeffIndividual;
    /**
     * The economic sectors this is allowed to deduct
     */
    private final Set<EconSector> individualEconSectors;
    /**
     * The date this Factura was last edited
     */
    private LocalDateTime lastEditDate;
    /**
     * The economic sector
     */
    private EconSector econSector;

    /**
     * Fully parametrised constructor for <tt>Factura</tt>
     * @param company     The company that issued the receipt
     * @param individual  The individual that made the purchase
     * @param description The description of the purchase
     * @param value       The value of the purchase
     */
    public Factura(ContribuinteEmpresarial company, ContribuinteIndividual individual,
                   String description, float value){
        this.issuerNif = company.getNif();
        this.issuerName = company.getName();
        this.creationDate = LocalDateTime.now();
        this.lastEditDate = this.creationDate;
        this.clientNif = individual.getNif();
        this.description = description;
        this.value = value;
        this.history = new LinkedList<>();
        if(company.getEconActivities().size() > 1){
            this.econSector = Pendente.getInstance();
            this.possibleEconSectors = new HashSet<>(company.getEconActivities());
            this.possibleEconSectors.remove(Pendente.getInstance());
        }else{
            this.econSector = company.getEconActivities().iterator().next();
            this.possibleEconSectors = new HashSet<>();
        }
        this.isEmpresaInterior = company.getConselho().isInterior();
        this.aggregateSize = individual.getNumDependants();
        this.coeffEmpresa = company.getFiscalCoefficient();
        this.coeffIndividual = individual.getFiscalCoefficient();
        this.individualEconSectors = individual.getEconActivities();
    }

    /**
     * Copy constructor for <tt>Factura</tt>
     * @param factura the <tt>Factura</tt> to copy
     */
    private Factura(Factura factura){
        this.issuerNif = factura.getIssuerNif();
        this.issuerName = factura.getIssuerName();
        this.creationDate = factura.getCreationDate();
        this.lastEditDate = factura.getLastEditDate();
        this.clientNif = factura.getClientNif();
        this.description = factura.getDescription();
        this.value = factura.getValue();
        this.econSector = factura.getEconSector();
        this.possibleEconSectors = factura.getPossibleEconSectors();
        this.history = factura.getHistory();
        this.isEmpresaInterior = factura.isEmpresaInterior();
        this.aggregateSize = factura.getAggregateSize();
        this.coeffEmpresa = factura.getCoeffEmpresa();
        this.coeffIndividual = factura.getCoeffIndividual();
        this.individualEconSectors = factura.getIndividualEconSectors();
    }


    /**
     * The NIF of the entity that issued this Receipt
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
     * Returns the history of state of this <tt>Factura</tt>
     * @return The history of state of this <tt>Factura</tt>
     */
    public LinkedList<Factura> getHistory(){
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
     * Changes the {@link EconSector}
     * @param econSector the new {@link EconSector}
     * @throws InvalidEconSectorException if the requested sector is not among the valid sectors
     */
    public void setEconSector(EconSector econSector) throws InvalidEconSectorException{
        if(!this.possibleEconSectors.contains(econSector)) throw new InvalidEconSectorException(econSector.toString());
        this.possibleEconSectors.remove(econSector);
        if(!(this.econSector instanceof Pendente)){
            this.possibleEconSectors.add(this.econSector);
        }
        this.history.addFirst(this.clone().cleanHistory());
        this.econSector = econSector;
        this.lastEditDate = LocalDateTime.now();
    }

    /**
     * Cleans the history.
     * @return Returns the instance.
     */
    private Factura cleanHistory(){
        this.history.clear();
        this.possibleEconSectors.clear();
        return this;
    }

    /**
     * Returns the type of the <tt>Factura</tt>
     * @return The type of the <tt>Factura</tt>
     */
    public EconSector getType(){
        return this.econSector;
    }

    /**
     * Returns the list of possible {@link EconSector}s
     * @return The list of possible {@link EconSector}s
     */
    public Set<EconSector> getPossibleEconSectors(){
        return new HashSet<>(this.possibleEconSectors);
    }

    /**
     * Returns if the issuing {@link ContribuinteEmpresarial} is to be benefited for being from the interior
     * @return If the issuing {@link ContribuinteEmpresarial} is to be benefited for being from the interior
     */
    public boolean isEmpresaInterior(){
        return this.isEmpresaInterior;
    }

    /**
     * Returns the size of the family aggregate of the {@link ContribuinteIndividual} at the time of emission
     * @return The size of the family aggregate of the {@link ContribuinteIndividual} at the time of emission
     */
    private int getAggregateSize(){
        return this.aggregateSize;
    }

    /**
     * Returns the fiscal coefficient of the issuing {@link ContribuinteEmpresarial}
     * @return The fiscal coefficient of the issuing {@link ContribuinteEmpresarial}
     */
    private float getCoeffEmpresa(){
        return this.coeffEmpresa;
    }

    /**
     * Returns the fiscal coefficient of the {@link ContribuinteIndividual}
     * @return The fiscal coefficient of the {@link ContribuinteIndividual}
     */
    private float getCoeffIndividual(){
        return this.coeffIndividual;
    }

    /**
     * Returns the list of {@link EconSector}s that can be deducted from
     * @return The list of {@link EconSector}s that can be deducted from
     */
    private Set<EconSector> getIndividualEconSectors(){
        return new HashSet<>(this.individualEconSectors);
    }

    /**
     * Checks if it is deductible
     * @return {@code true} if is is deductible {@code false} otherwise
     */
    private boolean isDeductible(){
        return this.econSector instanceof Deductible
               && this.individualEconSectors.contains(this.econSector);
    }

    /**
     * Returns the amount that can be deducted from this
     * @return The amount that can be deducted from this
     */
    public float deducao(){
        if(this.isDeductible()){
            return ((Deductible) this.econSector).deduction(this.value, this.isEmpresaInterior, this.aggregateSize,
                                                            this.coeffEmpresa, this.coeffIndividual);
        }
        return 0;
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;

        if(obj == null || this.getClass() != obj.getClass()) return false;

        Factura factura = (Factura) obj;
        return this.value == factura.getValue()
               && this.isEmpresaInterior == factura.isEmpresaInterior()
               && this.aggregateSize == factura.getAggregateSize()
               && this.coeffEmpresa == factura.getCoeffEmpresa()
               && this.coeffIndividual == factura.getCoeffIndividual()
               && this.issuerNif.equals(factura.getIssuerNif())
               && this.issuerName.equals(factura.getIssuerName())
               && this.creationDate.equals(factura.getCreationDate())
               && this.lastEditDate.equals(factura.getLastEditDate())
               && this.clientNif.equals(factura.getClientNif())
               && this.description.equals(factura.getDescription())
               && this.econSector.equals(factura.getEconSector())
               && this.possibleEconSectors.equals(factura.getPossibleEconSectors())
               && this.history.equals(factura.getHistory())
               && this.individualEconSectors.equals(factura.getIndividualEconSectors());
    }

    /**
     * Returns a deep copy
     * @return a deep copy
     */
    @Override
    public Factura clone(){
        return new Factura(this);
    }

    /** {@inheritDoc} */
    @Override
    public String toString(){
        return "Factura{" +
               "issuerNif='" + issuerNif + '\''
               + ", issuerName='" + issuerName + '\''
               + ", creationDate=" + creationDate
               + ", lastEditDate=" + lastEditDate
               + ", clientNif='" + clientNif + '\''
               + ", description='" + description + '\''
               + ", value=" + value
               + ", econSector=" + econSector
               + ", possibleEconSectors=" + possibleEconSectors
               + ", history=" + history
               + ", isEmpresaInterior=" + isEmpresaInterior
               + ", aggregateSize=" + aggregateSize
               + ", coeffEmpresa=" + coeffEmpresa
               + ", coeffIndividual=" + coeffIndividual
               + ", individualEconSectors=" + individualEconSectors
               + '}';
    }

    /**
     * Compares against another {@link Factura} based on time
     * @param o The factura to compare against
     * @return {@inheritDoc}
     */
    @Override
    public int compareTo(Factura o){
        return this.creationDate.compareTo(o.getCreationDate());
    }
}