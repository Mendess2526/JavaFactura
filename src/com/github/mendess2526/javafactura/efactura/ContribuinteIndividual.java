package com.github.mendess2526.javafactura.efactura;

import com.github.mendess2526.javafactura.efactura.econSectors.EconSector;

import java.util.*;


public class ContribuinteIndividual extends Contribuinte {

    /**
     * The NIFs of the people dependant of this Contribuinte
     */
    private List<String> dependants;
    /**
     * The fiscal coefficient of this Contribuinte
     */
    private double fiscal_coefficient;

    private Set<EconSector> econActivities;

    private ContribuinteIndividual(){
    }

    /**
     * Fully parametrised constructor for <tt>Contribuinte Individual</tt>
     * @param nif The NIF
     * @param email The email
     * @param nome The name
     * @param address The address
     * @param password The password
     * @param dependants The list of NIF of the dependants
     * @param fiscal_coefficient The fiscal coefficient of the Contribuinte
     * @param econActivities The economic activities eligible
     */
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
