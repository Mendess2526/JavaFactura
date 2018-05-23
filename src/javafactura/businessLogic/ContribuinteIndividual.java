package javafactura.businessLogic;

import javafactura.businessLogic.econSectors.EconSector;
import javafactura.businessLogic.exceptions.InvalidEconSectorException;
import javafactura.businessLogic.exceptions.InvalidNumberOfDependantsException;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a individual in the application
 */
public class ContribuinteIndividual extends Contribuinte {

    private static final long serialVersionUID = -325271230861013347L;
    /**
     * The NIFs of the people dependant of this Contribuinte
     */
    private final Set<String> familyAggregate;
    /**
     * Number of dependants
     */
    private int numDependants;

    /**
     * Fully parametrised constructor
     * @param nif               The NIF
     * @param email             The email
     * @param nome              The name
     * @param address           The address
     * @param password          The password
     * @param numDependants     Number of dependents
     * @param familyAggregate   The list of NIF of the family aggregate
     * @param fiscalCoefficient The fiscal coefficient of the Contribuinte
     * @param econActivities    The economic activities eligible
     * @throws InvalidNumberOfDependantsException if the number of dependants is higher then the size
     *                                            of the family aggregate
     */
    public ContribuinteIndividual(String nif, String email, String nome,
                                  String address, String password, int numDependants,
                                  Collection<String> familyAggregate, float fiscalCoefficient,
                                  Collection<EconSector> econActivities) throws
                                                                         InvalidNumberOfDependantsException{
        super(nif, email, nome, address, password, fiscalCoefficient, econActivities);
        this.familyAggregate = new HashSet<>(familyAggregate);
        this.setNumDependants(numDependants);

    }

    /**
     * The copy constructor
     * @param contribuinteIndividual The instance to copy
     */
    private ContribuinteIndividual(ContribuinteIndividual contribuinteIndividual){
        super(contribuinteIndividual);
        this.familyAggregate = contribuinteIndividual.getFamilyAggregate();
        this.numDependants = contribuinteIndividual.getNumDependants();
    }

    /**
     * Returns the family aggregate
     * @return The family aggregate
     */
    public Set<String> getFamilyAggregate(){
        return new HashSet<>(familyAggregate);
    }

    /**
     * Returns the number of dependants
     * @return The number of dependants
     */
    public int getNumDependants(){
        return this.numDependants;
    }

    /**
     * Sets the number of dependents, preforming a check against the size of the aggregate
     * @param numDependants The number of dependents
     * @throws InvalidNumberOfDependantsException if the numDependants is higher then the size of the aggregate
     */
    private void setNumDependants(int numDependants) throws InvalidNumberOfDependantsException{
        if(numDependants <= this.familyAggregate.size()){
            this.numDependants = numDependants;
        }else{
            throw new InvalidNumberOfDependantsException(numDependants);
        }
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;

        if(obj == null || this.getClass() != obj.getClass()) return false;

        ContribuinteIndividual that = (ContribuinteIndividual) obj;
        return super.equals(obj)
               && this.numDependants == that.getNumDependants()
               && this.familyAggregate.equals(that.getFamilyAggregate());
    }

    /**
     * Creates a deep copy of the instance
     * @return a deep copy of the instance
     */
    @Override
    public ContribuinteIndividual clone(){
        return new ContribuinteIndividual(this);
    }

    /** {@inheritDoc} */
    @Override
    public String toString(){
        return "ContribuinteIndividual{" +
               "familyAggregate=" + familyAggregate + '\''
               + ", numDependants=" + numDependants + '\''
               + "} " + super.toString();
    }

    /**
     * Change the {@link EconSector} of a {@link Factura}
     * @param f The factura to change
     * @param e The new sector
     * @return A copy of the changed f
     *
     * @throws InvalidEconSectorException if the sector is not in the list of valid sectors
     */
    public Factura changeFatura(Factura f, EconSector e) throws InvalidEconSectorException{
        Factura changedF = this.facturas.get(this.facturas.indexOf(f));
        changedF.setEconSector(e);
        return changedF.clone();
    }

    /**
     * Counts the number of {@link Factura}s emitted by a given {@link ContribuinteEmpresarial}
     * @param companyNif The company nif
     * @return The number of {@link Factura}s emitted by a given {@link ContribuinteEmpresarial}
     */
    public long countFacturas(String companyNif){
        return this.facturas.stream().filter(f -> f.getIssuerNif().equals(companyNif)).count();
    }

    /**
     * Returns the deduction accumulated over time
     * @return The deduction accumulated over time
     */
    public double getAccumulatedDeduction(){
        return this.facturas.stream().mapToDouble(Factura::deducao).sum();
    }
}
