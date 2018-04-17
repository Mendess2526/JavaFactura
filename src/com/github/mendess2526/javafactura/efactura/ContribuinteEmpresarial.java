package com.github.mendess2526.javafactura.efactura;

import com.github.mendess2526.javafactura.efactura.econSectors.EconSector;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ContribuinteEmpresarial extends Contribuinte{

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
     * @param nif The NIF
     * @param email The email
     * @param name The Name
     * @param address The Address
     * @param password The Password
     * @param fiscalCoefficient The fiscal coefficient
     * @param econActivities The economic activities
     */
    public ContribuinteEmpresarial(String nif, String email, String name,
                                   String address, String password,
                                   double fiscalCoefficient,
                                   Set<EconSector> econActivities){
        super(nif, email, name, address, password);
        this.econActivities = new ArrayList<>(econActivities);
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
     * @param buyer The buyer
     * @param description The description of the purchase
     * @param value The value of the purchase
     */
    void issueFactura(Contribuinte buyer, String description, float value){
        EconSector econSector;
        if(this.econActivities.size() > 1){
            econSector = EconSector.factory("E00");
        }else{
            econSector = EconSector.factory(this.econActivities.get(0).getType());
        }
        Factura factura = new Factura(this.getNif(), this.getName(), LocalDateTime.now(),
                                        buyer.getNif(), description, value, econSector);
        this.associateFactura(factura);
        buyer.associateFactura(factura);
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;

        if(o == null || getClass() != o.getClass()) return false;

        if(! super.equals(o)) return false;

        ContribuinteEmpresarial that = (ContribuinteEmpresarial) o;
        return  this.fiscalCoefficient == that.getFiscalCoefficient() &&
                this.econActivities.equals(that.getEconActivities());
    }

    @Override
    public String toString(){
        return super.toString() +
                "ContribuinteEmpresarial{" +
                "econActivities=" + this.econActivities +
                ", fiscalCoefficient=" + this.fiscalCoefficient +
                '}';
    }

    public ContribuinteEmpresarial clone(){
        return new ContribuinteEmpresarial(this);
    }
}
