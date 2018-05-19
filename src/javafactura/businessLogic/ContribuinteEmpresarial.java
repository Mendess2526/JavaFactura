package javafactura.businessLogic;

import javafactura.businessLogic.econSectors.EconSector;
import javafactura.businessLogic.econSectors.Pendente;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ContribuinteEmpresarial extends Contribuinte {

    private static final long serialVersionUID = 6172164103937149552L;
    /**
     * The fiscal coefficient
     */
    private float fiscalCoefficient;
    /**
     * The Economic Sectors this <tt>Contribuinte</tt> is eligible for
     */
    private final Set<EconSector> econActivities;

    /**
     * \brief Empty constructor
     */
    private ContribuinteEmpresarial(){
        super();
        econActivities = new HashSet<>();
        fiscalCoefficient = 0;
    }

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
        super(nif, email, name, address, password);
        this.econActivities = new HashSet<>(econActivities);
        this.econActivities.remove(Pendente.getInstance());
        this.fiscalCoefficient = fiscalCoefficient;
    }

    /**
     * \brief The copy constructor
     * @param contribuinteEmpresarial The object to clone
     */
    private ContribuinteEmpresarial(ContribuinteEmpresarial contribuinteEmpresarial){
        super(contribuinteEmpresarial);
        this.econActivities = contribuinteEmpresarial.getEconActivities();
        this.fiscalCoefficient = contribuinteEmpresarial.getFiscalCoefficient();
    }

    /**
     * Returns the economic activities
     * @return The economic activities
     */
    public Set<EconSector> getEconActivities(){
        return new HashSet<>(econActivities);
    }

    /**
     * Returns the fiscal coefficient
     * @return The fiscal coefficient
     */
    public float getFiscalCoefficient(){
        return this.fiscalCoefficient;
    }

    /**
     * Changes the fiscal coefficient
     * @param fiscalCoefficient the new fiscal coefficient
     */
    public void setFiscalCoefficient(float fiscalCoefficient){
        this.fiscalCoefficient = fiscalCoefficient;
    }

    /**
     * \brief Issues a <tt>Factura</tt>
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

    @Override
    public boolean equals(Object o){
        if(this == o) return true;

        if(o == null || this.getClass() != o.getClass()) return false;

        ContribuinteEmpresarial that = (ContribuinteEmpresarial) o;
        return super.equals(o)
               && this.fiscalCoefficient == that.getFiscalCoefficient()
               && this.econActivities.equals(that.getEconActivities());
    }

    @Override
    public String toString(){
        return "ContribuinteEmpresarial{" +
               "fiscalCoefficient=" + fiscalCoefficient
               + ", econActivities=" + econActivities
               + "} " + super.toString();
    }

    public ContribuinteEmpresarial clone(){
        return new ContribuinteEmpresarial(this);
    }
}
