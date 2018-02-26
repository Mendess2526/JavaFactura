package com.github.mendess2526.javafactura.efactura;

public abstract class Contribuinte implements User{

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
     * The empty constructor
     */
    public Contribuinte(){
        nif = "";
        email = "";
        name = "";
        address = "";
        password = "";
    }

    /**
     * Parameterized constructor
     * @param nif The NIF
     * @param email The email
     * @param name The Name
     * @param address The Address
     * @param password The Password
     */
    public Contribuinte(String nif, String email, String name, String address, String password){
        this.nif = nif;
        this.email = email;
        this.name = name;
        this.address = address;
        this.password = password;
    }

    /**
     * Copy constructor
     * @param contribuinte The Contribuinte to copy
     */
    public Contribuinte(Contribuinte contribuinte){
        this.nif = contribuinte.getNif();
        this.email = contribuinte.getEmail();
        this.name = contribuinte.getName();
        this.address = contribuinte.getAddress();
        this.password = contribuinte.getPassword();
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

    @Override
    public boolean equals(Object o){
        if(this == o) return true;

        if(o == null || getClass() != o.getClass()) return false;

        Contribuinte that = (Contribuinte) o;
        return this.nif.equals(that.getNif()) &&
                this.email.equals(that.getEmail()) &&
                this.name.equals(that.getName()) &&
                this.address.equals(that.getAddress()) &&
                this.password.equals(that.getPassword());
    }

    @Override
    public String toString(){
        return "Contribuinte{" +
                "nif='" + nif + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
