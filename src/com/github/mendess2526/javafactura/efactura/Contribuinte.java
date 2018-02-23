package com.github.mendess2526.javafactura.efactura;

import java.util.Objects;

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
     * The empty <i></i>
     */
    public Contribuinte(){
        nif = "";
        email = "";
        name = "";
        address = "";
        password = "";
    }

    public Contribuinte(String nif, String email, String nome, String address, String password){
        this.nif = nif;
        this.email = email;
        this.name = nome;
        this.address = address;
        this.password = password;
    }

    public Contribuinte(Contribuinte contribuinte){
        this.nif = contribuinte.getNif();
        this.email = contribuinte.getEmail();
        this.name = contribuinte.getName();
        this.address = contribuinte.getAddress();
        this.password = contribuinte.getPassword();
    }


    public String getNif(){
        return nif;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getName(){
        return name;
    }

    public String getAddress(){
        return address;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public String getPassword(){
        return password;
    }

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
        return new StringBuilder()
                .append("Contribuinte{")
                .append("nif='").append(nif).append('\'')
                .append(", email='").append(email).append('\'')
                .append(", name='").append(name).append('\'')
                .append(", address='").append(address).append('\'')
                .append(", password='").append(password).append('\'')
                .append('}').toString();
    }
}
