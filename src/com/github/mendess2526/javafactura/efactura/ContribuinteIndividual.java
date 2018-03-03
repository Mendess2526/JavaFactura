package com.github.mendess2526.javafactura.efactura;

import com.github.mendess2526.javafactura.efactura.econSectors.EconSector;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ContribuinteIndividual extends Contribuinte {

    private List<String> dependants;
    private double fiscal_coefficient;
    private Set<EconSector> econActivities;

    public ContribuinteIndividual(String nif, String email, String nome, String address,
                                  String password, List<String> dependants,
                                  double fiscal_coefficient, Set<EconSector> econActivities){
        super(nif, email, nome, address, password);
        this.dependants = dependants;
        this.fiscal_coefficient = fiscal_coefficient;
        this.econActivities = new HashSet<>(econActivities);
    }

    public ContribuinteIndividual(ContribuinteIndividual contribuinteIndividual){
        super(contribuinteIndividual);
        this.dependants = contribuinteIndividual.getDependants();
        this.fiscal_coefficient = contribuinteIndividual.getFiscal_coefficient();
        this.econActivities = contribuinteIndividual.getEconActivities();
    }

    public int getDependant_num(){
        return this.dependants.size();
    }

    public List<String> getDependants(){
        return dependants;
    }

    public void setDependants(List<String> dependants){
        this.dependants = dependants;
    }

    public double getFiscal_coefficient(){
        return fiscal_coefficient;
    }

    public void setFiscal_coefficient(double fiscal_coefficient){
        this.fiscal_coefficient = fiscal_coefficient;
    }

    public Set<EconSector> getEconActivities(){
        return econActivities;
    }

    public void setEconActivities(Set<EconSector> econActivities){
        this.econActivities = econActivities;
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;

        if(o == null || getClass() != o.getClass()) return false;

        if(! super.equals(o)) return false;

        ContribuinteIndividual that = (ContribuinteIndividual) o;
        return  this.fiscal_coefficient == that.getFiscal_coefficient() &&
                this.dependants.equals(that.getDependants()) &&
                this.econActivities.equals(that.getEconActivities());
    }

    @Override
    public String toString(){
        return "ContribuinteIndividual{" +
                "dependants=" + dependants +
                ", fiscal_coefficient=" + fiscal_coefficient +
                ", econActivities=" + econActivities +
                '}';
    }
}
