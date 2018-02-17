package com.github.mendess2526.javafactura.datamodel;

import java.util.EnumSet;
import java.util.Objects;

public class ContribuinteEmpresarial extends Contribuinte{

    private EnumSet<EconActivity> econActivities;
    private double fiscal_coefficient;

    public ContribuinteEmpresarial(String nif, String email, String nome, String address, String password,
                                   EnumSet<EconActivity> econActivities, double fiscal_coefficient){
        super(nif, email, nome, address, password);
        this.econActivities = econActivities;
        this.fiscal_coefficient = fiscal_coefficient;
    }

    public EnumSet<EconActivity> getEconActivities(){
        return econActivities;
    }

    public void setEconActivities(EnumSet<EconActivity> econActivities){
        this.econActivities = econActivities;
    }

    public double getFiscal_coefficient(){
        return fiscal_coefficient;
    }

    public void setFiscal_coefficient(double fiscal_coefficient){
        this.fiscal_coefficient = fiscal_coefficient;
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;

        if(o == null || getClass() != o.getClass()) return false;

        if(! super.equals(o)) return false;

        ContribuinteEmpresarial that = (ContribuinteEmpresarial) o;
        return Double.compare(that.getFiscal_coefficient(), getFiscal_coefficient()) == 0 &&
                Objects.equals(getEconActivities(), that.getEconActivities());
    }

    @Override
    public int hashCode(){

        return Objects.hash(getEconActivities(), getFiscal_coefficient());
    }
}
