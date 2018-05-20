package javafactura.businessLogic;

import javafactura.businessLogic.econSectors.EconSector;

import java.util.Collection;

/**
 * The class that represents a company in the application.
 */
public class ContribuinteEmpresarial extends Contribuinte {

    private static final long serialVersionUID = 6172164103937149552L;

    /**
     * \brief Parameterized constructor
     * @param nif               The NIF
     * @param email             The email
     * @param name              The Name
     * @param address           The Address
     * @param password          The Password
     * @param fiscalCoefficient The fiscal coefficient
     * @param econActivities    The economic activities
     */
    public ContribuinteEmpresarial(String nif, String email, String name,
                                   String address, String password,
                                   float fiscalCoefficient,
                                   Collection<EconSector> econActivities){
        super(nif, email, name, address, password, fiscalCoefficient, econActivities);
    }

    /**
     * \brief The copy constructor
     * @param contribuinteEmpresarial The object to clone
     */
    private ContribuinteEmpresarial(ContribuinteEmpresarial contribuinteEmpresarial){
        super(contribuinteEmpresarial);
    }

    /**
     * \brief Issues a {@link Factura}
     * @param buyer       The buyer
     * @param description The description of the purchase
     * @param value       The value of the purchase
     */
    Factura issueFactura(ContribuinteIndividual buyer, String description, float value){
        Factura factura = new Factura(this, buyer, description, value);
        this.associateFactura(factura);
        buyer.associateFactura(factura);
        return factura.clone();
    }

    /** {@inheritDoc} */
    @Override
    public String toString(){
        return "ContribuinteEmpresarial{}" + super.toString();
    }

    /**
     * Creates a deep copy of the instance
     * @return a deep copy of the instance
     */
    @Override
    public ContribuinteEmpresarial clone(){
        return new ContribuinteEmpresarial(this);
    }
}
