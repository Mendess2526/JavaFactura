package com.github.mendess2526.javafactura.efactura;

import java.util.EnumSet;
import java.util.List;

public class ContribuinteIndividual extends Contribuinte {

    private int dependant_num;
    private List<String> dependants;
    private double fiscal_coefficient;
    private EnumSet<EconActivity> econActivities;



    public ContribuinteIndividual(String nif, String email, String nome, String address,
                                  String password, int dependant_num, List<String> dependants,
                                  double fiscal_coefficient, EnumSet<EconActivity> econActivities){
        super(nif, email, nome, address, password);

        this.dependant_num = dependant_num;
        this.dependants = dependants;
        this.fiscal_coefficient = fiscal_coefficient;
        this.econActivities = econActivities;
    }

    public ContribuinteIndividual(ContribuinteIndividual contribuinteIndividual){
        super(contribuinteIndividual);
        this.dependant_num = contribuinteIndividual.getDependant_num();
        this.dependants = contribuinteIndividual.getDependants();
        this.fiscal_coefficient = contribuinteIndividual.getFiscal_coefficient();
        this.econActivities = contribuinteIndividual.getEconActivities();
    }


    public int getDependant_num(){
        return dependant_num;
    }

    public void setDependant_num(int dependant_num){
        this.dependant_num = dependant_num;
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

    public EnumSet<EconActivity> getEconActivities(){
        return econActivities;
    }

    public void setEconActivities(EnumSet<EconActivity> econActivities){
        this.econActivities = econActivities;
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;

        if(o == null || getClass() != o.getClass()) return false;

        if(! super.equals(o)) return false;

        ContribuinteIndividual that = (ContribuinteIndividual) o;
        return dependant_num == that.getDependant_num() &&
                this.fiscal_coefficient == that.getFiscal_coefficient() &&
                this.dependants.equals(that.getDependants()) &&
                this.econActivities.equals(that.getEconActivities());
    }

    @Override
    public String toString(){
        return new StringBuilder().append(super.toString())
                .append("ContribuinteIndividual{")
                .append("dependant_num=").append(this.dependant_num)
                .append(", dependants=").append(this.dependants)
                .append(", fiscal_coefficient=").append(this.fiscal_coefficient)
                .append(", econActivities=").append(this.econActivities)
                .append('}').toString();
    }
}
