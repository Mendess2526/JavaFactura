package com.github.mendess2526.javafactura.efactura;

import com.github.mendess2526.javafactura.efactura.econSectors.EconSector;
import com.github.mendess2526.javafactura.efactura.exceptions.InvalidNumberOfDependantsException;

import java.util.*;


public class ContribuinteIndividual extends Contribuinte {


    private int numDependants;
    /**
     * The NIFs of the people dependant of this Contribuinte
     */
    private List<String> familyAggregate;
    /**
     * The fiscal coefficient of this Contribuinte
     */
    private double fiscalCoefficient;

    private Set<EconSector> econActivities;

    private ContribuinteIndividual(){
        this.numDependants = 0;
        this.familyAggregate = new ArrayList<>();
        this.fiscalCoefficient = 0.0;
        this.econSectors = new HashSet<>();
    }

    /**
     * Fully parametrised constructor for <tt>Contribuinte Individual</tt>
     * @param nif The NIF
     * @param email The email
     * @param nome The name
     * @param address The address
     * @param password The password
     * @param numDependants Number of dependents
     * @param familyAggregate The list of NIF of the family aggregate
     * @param fiscalCoefficient The fiscal coefficient of the Contribuinte
     * @param econActivities The economic activities eligible
     *
     * @throws InvalidNumberOfDependantsException if the number of dependants is higher then the size
     *                                            of the family aggregate
     */
    public ContribuinteIndividual(String nif, String email, String nome,
                                  String address, String password, int numDependants,
                                  List<String> familyAggregate, double fiscalCoefficient,
                                  Set<EconSector> econActivities) throws
                                                                  InvalidNumberOfDependantsException{
        super(nif, email, nome, address, password);
        this.setNumDependants(numDependants);
        this.familyAggregate = familyAggregate;
        this.fiscalCoefficient = fiscalCoefficient;
        this.econActivities = new HashSet<>(econActivities);

    }

    public ContribuinteIndividual(ContribuinteIndividual contribuinteIndividual){
        super(contribuinteIndividual);
        this.numDependants = contribuinteIndividual.getNumDependants();
        this.familyAggregate = contribuinteIndividual.getFamilyAggregate();
        this.fiscalCoefficient = contribuinteIndividual.getFiscalCoefficient();
        this.econActivities = contribuinteIndividual.getEconActivities();
    }

    public int getDependant_num(){
        return this.familyAggregate.size();
    }

    public List<String> getFamilyAggregate(){
        return familyAggregate;
    }

    public void setFamilyAggregate(List<String> familyAggregate){
        this.familyAggregate = familyAggregate;
    }

    public double getFiscalCoefficient(){
        return fiscalCoefficient;
    }

    public void setFiscalCoefficient(double fiscalCoefficient){
        this.fiscalCoefficient = fiscalCoefficient;
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
        return  this.fiscalCoefficient == that.getFiscalCoefficient() &&
                this.familyAggregate.equals(that.getFamilyAggregate()) &&
                this.econActivities.equals(that.getEconActivities());
    }

    @Override
    public String toString(){
        return "ContribuinteIndividual{" +
                "familyAggregate=" + familyAggregate +
                ", fiscalCoefficient=" + fiscalCoefficient +
                ", econActivities=" + econActivities +
                '}';
    }

    public int getNumDependants(){
        return numDependants;
    }

    public void setNumDependants(int numDependants) throws InvalidNumberOfDependantsException{
        if(numDependants <= this.familyAggregate.size()){
            this.numDependants = numDependants;
        }else{
            throw new InvalidNumberOfDependantsException(numDependants);
        }
    }

    public ContribuinteIndividual clone(){
        return new ContribuinteIndividual(this);
    }
}
