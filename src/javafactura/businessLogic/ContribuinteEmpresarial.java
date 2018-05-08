package javafactura.businessLogic;

import javafactura.businessLogic.econSectors.EconSector;
import javafactura.businessLogic.econSectors.Pendente;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ContribuinteEmpresarial extends Contribuinte {

    /**
     * The fiscal coefficient
     */
    private double fiscalCoefficient;
    /**
     * The Economic Sectors this <tt>Contribuinte</tt> is eligible for
     */
    private final List<EconSector> econActivities;

    /**
     * \brief Empty constructor
     */
    public ContribuinteEmpresarial(){
        super();
        econActivities = new ArrayList<>();
        fiscalCoefficient = 0;
    }

    /**
     * \brief
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
                                   double fiscalCoefficient,
                                   Set<EconSector> econActivities){
        super(nif, email, name, address, password);
        this.econActivities = new ArrayList<>(econActivities);
        this.econActivities.remove(Pendente.getInstance());
        this.fiscalCoefficient = fiscalCoefficient;
    }

    /**
     * \brief The copy constructor
     * @param contribuinteEmpresarial The object to clone
     */
    public ContribuinteEmpresarial(ContribuinteEmpresarial contribuinteEmpresarial){
        super(contribuinteEmpresarial);
        this.econActivities = contribuinteEmpresarial.getEconActivities();
        this.fiscalCoefficient = contribuinteEmpresarial.getFiscalCoefficient();
    }

    /**
     * Returns the economic activities
     * @return The economic activities
     */
    public List<EconSector> getEconActivities(){
        return econActivities;
    }

    /**
     * Returns the fiscal coefficient
     * @return The fiscal coefficient
     */
    public double getFiscalCoefficient(){
        return fiscalCoefficient;
    }

    /**
     * Changes the fiscal coefficient
     * @param fiscalCoefficient the new fiscal coefficient
     */
    public void setFiscalCoefficient(double fiscalCoefficient){
        this.fiscalCoefficient = fiscalCoefficient;
    }

    /**
     * \brief Issues a <tt>Factura</tt>
     * @param buyer       The buyer
     * @param description The description of the purchase
     * @param value       The value of the purchase
     */
    Factura issueFactura(Contribuinte buyer, String description, float value){
        EconSector econSector;
        if(this.econActivities.size() > 1){
            econSector = Pendente.getInstance();
        }else{
            econSector = this.econActivities.get(0);
        }
        Factura factura = new Factura(this.getNif(), this.getName(),
                                      buyer.getNif(), description, value, econSector, this.econActivities);
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
