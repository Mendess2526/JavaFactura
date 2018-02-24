package com.github.mendess2526.javafactura.efactura;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;

public class ContribuinteIndividual extends Contribuinte {

    /**
     * The number of people dependent of this Contribuinte
     */
    private int dependant_num;
    /**
     * The NIFs of the people dependant of this Contribuinte
     */
    private List<String> dependants;
    /**
     * The fiscal coefficient of this Contribuinte
     */
    private double fiscal_coefficient;
    /**
     * The list of Economic Activities this Contribuinte is eligible to do
     */
    private EnumSet<EconActivity> econActivities;


    /**
     * Fully parametrised constructor for <tt>Contribuinte Individual</tt>
     * @param nif The NIF
     * @param email The email
     * @param nome The name
     * @param address The address
     * @param password The password
     * @param dependant_num The number of dependants on this Contribuinte
     * @param dependants The list of NIF of the dependants
     * @param fiscal_coefficient The fiscal coefficient of the Contribuinte
     * @param econActivities The economic activities eligible
     */
    public ContribuinteIndividual(String nif, String email, String nome, String address,
                                  String password, int dependant_num, Collection<String> dependants,
                                  double fiscal_coefficient, EnumSet<EconActivity> econActivities){
        super(nif, email, nome, address, password);

        this.dependant_num = dependant_num;
        this.dependants = new ArrayList<>(dependants);
        this.fiscal_coefficient = fiscal_coefficient;
        this.econActivities = EnumSet.copyOf(econActivities);
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
