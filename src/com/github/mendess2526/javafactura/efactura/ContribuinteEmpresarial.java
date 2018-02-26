package com.github.mendess2526.javafactura.efactura;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ContribuinteEmpresarial extends Contribuinte{

    private Set<String> econActivities;
    private double fiscalCoefficient;

    public ContribuinteEmpresarial(){
        super();
        econActivities = new HashSet<>();
        fiscalCoefficient = 0;
    }

    public ContribuinteEmpresarial(String nif, String email, String nome,
                                   String address, String password,
                                   double fiscalCoefficient,
                                   Set<String> econActivities){
        super(nif, email, nome, address, password);
        this.econActivities = new HashSet<>(econActivities);
        this.fiscalCoefficient = fiscalCoefficient;
    }

    public ContribuinteEmpresarial(ContribuinteEmpresarial contribuinteEmpresarial){
        super(contribuinteEmpresarial);
        this.econActivities = contribuinteEmpresarial.getEconActivities();
        this.fiscalCoefficient = contribuinteEmpresarial.getFiscalCoefficient();
    }


    public Set<String> getEconActivities(){
        return econActivities;
    }

    public void setEconActivities(Set<String> econActivities){
        this.econActivities = econActivities;
    }

    public double getFiscalCoefficient(){
        return fiscalCoefficient;
    }

    public void setFiscalCoefficient(double fiscalCoefficient){
        this.fiscalCoefficient = fiscalCoefficient;
    }

    public Factura emitirFactura(String nif, String description, float value){
        return new FacturaPendente(
                this.getNif(),
                this.getName(),
                LocalDateTime.now(),
                nif,
                description,
                value);
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
    public int hashCode(){

        return Objects.hash(getEconActivities(), getFiscalCoefficient());
    }

    @Override
    public String toString(){
        return super.toString() +
                "ContribuinteEmpresarial{" +
                "econActivities=" + this.econActivities +
                ", fiscalCoefficient=" + this.fiscalCoefficient +
                '}';
    }
}
