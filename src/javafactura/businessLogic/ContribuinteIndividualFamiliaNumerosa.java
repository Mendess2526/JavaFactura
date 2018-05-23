package javafactura.businessLogic;

import javafactura.businessLogic.econSectors.EconSector;
import javafactura.businessLogic.exceptions.InvalidNumberOfDependantsException;

import java.util.Collection;

public class ContribuinteIndividualFamiliaNumerosa extends ContribuinteIndividual {

    private static final long serialVersionUID = 8874897563125269086L;

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
    public ContribuinteIndividualFamiliaNumerosa(String nif, String email, String nome, String address, String password,
                                                 int numDependants,
                                                 Collection<String> familyAggregate, float fiscalCoefficient,
                                                 Collection<EconSector> econActivities) throws
                                                                                        InvalidNumberOfDependantsException{
        super(nif, email, nome, address, password, numDependants, familyAggregate, fiscalCoefficient, econActivities);
    }

    /**
     * Returns the deduction accumulated over time
     * @return The deduction accumulated over time
     */
    @Override
    public double getAccumulatedDeduction(){
        return reducaoImposto(super.getAccumulatedDeduction());
    }

    /**
     * Increases the amount deducted by 5% for each dependant beyond 3 and 1% for each purchase made to a
     * interior company
     * @param sum The initial deduction
     * @return The increased deduction
     */
    private double reducaoImposto(double sum){
        sum += sum * 0.05 * (this.getNumDependants() - 3);
        sum += sum * 0.01 * this.facturas.stream().filter(Factura::isEmpresaInterior).count();
        return sum;
    }
}
