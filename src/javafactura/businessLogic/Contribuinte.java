package javafactura.businessLogic;

import javafactura.businessLogic.econSectors.EconSector;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class Contribuinte implements User,
                                              Serializable {

    private static final long serialVersionUID = 2591014538307976339L;
    /**
     * The NIF of the Contribuinte
     */
    private final String nif;
    /**
     * The email of the Contribuinte
     */
    private String email;
    /**
     * The name of the Contribuinte
     */
    private final String name;
    /**
     * The address of the Contribuinte
     */
    private String address;
    /**
     * The password of the Contribuinte
     */
    private String password;
    /**
     * The fiscal coefficient
     */
    private final float fiscalCoefficient;
    /**
     * The Economic Sectors this <tt>Contribuinte</tt> is eligible for
     */
    private final Set<EconSector> econActivities;
    /**
     * The receipts
     */
    protected final LinkedList<Factura> facturas;

    /**
     * Parametrised constructor
     * @param nif      The NIF
     * @param email    The email
     * @param name     The Name
     * @param address  The Address
     * @param password The Password
     * @param fiscalCoefficient The fiscal coefficient
     * @param econActivities The economic sectors
     */
    protected Contribuinte(String nif, String email, String name, String address, String password,
                           float fiscalCoefficient, Collection<EconSector> econActivities){
        this.nif = nif;
        this.email = email;
        this.name = name;
        this.address = address;
        this.password = password;
        this.fiscalCoefficient = fiscalCoefficient;
        this.econActivities = new HashSet<>(econActivities);
        this.facturas = new LinkedList<>();
    }

    /**
     * Copy constructor
     * @param contribuinte The Contribuinte to copy
     */
    protected Contribuinte(Contribuinte contribuinte){
        this.nif = contribuinte.getNif();
        this.email = contribuinte.getEmail();
        this.name = contribuinte.getName();
        this.address = contribuinte.getAddress();
        this.password = contribuinte.getPassword();
        this.fiscalCoefficient = contribuinte.getFiscalCoefficient();
        this.econActivities = contribuinte.getEconActivities();
        this.facturas = contribuinte.getFacturas();
    }


    /**
     * Returns the NIF
     * @return The NIF
     */
    public String getNif(){
        return nif;
    }

    /**
     * Returns the email
     * @return The email
     */
    public String getEmail(){
        return email;
    }

    /**
     * Sets the email
     * @param email the new email
     */
    public void setEmail(String email){
        this.email = email;
    }

    /**
     * Returns the name
     * @return The name
     */
    public String getName(){
        return name;
    }

    /**
     * Returns the address
     * @return The address
     */
    public String getAddress(){
        return address;
    }

    /**
     * Sets the new address
     * @param address the new address
     */
    public void setAddress(String address){
        this.address = address;
    }

    /**
     * Returns the password
     * @return The password
     */
    public String getPassword(){
        return password;
    }

    /**
     * Changes the password
     * @param password The new password
     */
    public void setPassword(String password){
        this.password = password;
    }

    public float getFiscalCoefficient(){
        return this.fiscalCoefficient;
    }

    public Set<EconSector> getEconActivities(){
        return new HashSet<>(this.econActivities);
    }

    /**
     * Returns the list of Facturas associated
     * @return The list of Facturas associated
     */
    public LinkedList<Factura> getFacturas(){
        return this.facturas
                .stream()
                .map(Factura::clone)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    protected void associateFactura(Factura f){
        this.facturas.addFirst(f);
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;

        if(o == null || this.getClass() != o.getClass()) return false;

        Contribuinte that = (Contribuinte) o;
        return this.fiscalCoefficient == that.getFiscalCoefficient()
               && this.nif.equals(that.getNif())
               && this.email.equals(that.getEmail())
               && this.name.equals(that.getName())
               && this.address.equals(that.getAddress())
               && this.password.equals(that.getPassword())
               && this.facturas.equals(that.getFacturas())
               && this.econActivities.equals(that.getEconActivities());
    }

    @Override
    public String toString(){
        return "Contribuinte{" +
               "nif='" + nif + '\''
               + ", email='" + email + '\''
               + ", name='" + name + '\''
               + ", address='" + address + '\''
               + ", password='" + password + '\''
               + ", fiscalCoefficient=" + fiscalCoefficient + '\''
               + ", econActivities=" + econActivities + '\''
               + ", facturas=" + facturas + '\''
               + '}';
    }

    public abstract Contribuinte clone();
}
