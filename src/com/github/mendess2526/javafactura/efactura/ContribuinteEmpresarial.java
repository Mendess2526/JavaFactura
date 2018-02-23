package com.github.mendess2526.javafactura.efactura;

import java.util.EnumSet;
import java.util.Objects;

public class ContribuinteEmpresarial extends Contribuinte{

    private EnumSet<EconActivity> econActivities;
    private double fiscalCoefficient;

    public ContribuinteEmpresarial(){
        super();
        econActivities = EnumSet.noneOf(EconActivity.class);
        fiscalCoefficient = 0;
    }

    public ContribuinteEmpresarial(String nif, String email, String nome,
                                   String address, String password,
                                   double fiscalCoefficient,
                                   EnumSet<EconActivity> econActivities){
        super(nif, email, nome, address, password);
        this.econActivities = econActivities;
        this.fiscalCoefficient = fiscalCoefficient;
    }

    public ContribuinteEmpresarial(ContribuinteEmpresarial contribuinteEmpresarial){
        super(contribuinteEmpresarial);
        this.econActivities = contribuinteEmpresarial.getEconActivities();
        this.fiscalCoefficient = contribuinteEmpresarial.getFiscalCoefficient();
    }


    public EnumSet<EconActivity> getEconActivities(){
        return econActivities;
    }

    public void setEconActivities(EnumSet<EconActivity> econActivities){
        this.econActivities = econActivities;
    }

    public double getFiscalCoefficient(){
        return fiscalCoefficient;
    }

    public void setFiscalCoefficient(double fiscalCoefficient){
        this.fiscalCoefficient = fiscalCoefficient;
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
        return new StringBuilder()
                .append(super.toString())
                .append("ContribuinteEmpresarial{")
                .append("econActivities=").append(this.econActivities)
                .append(", fiscalCoefficient=").append(this.fiscalCoefficient)
                .append('}').toString();
    }
}
