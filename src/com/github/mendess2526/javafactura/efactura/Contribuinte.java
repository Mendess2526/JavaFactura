package com.github.mendess2526.javafactura.efactura;

import java.util.Objects;

public abstract class Contribuinte {

    private final String nif;
    private String email;
    private final String name;
    private String address;
    private String password;

    public Contribuinte(String nif, String email, String nome, String address, String password){
        this.nif = nif;
        this.email = email;
        this.name = nome;
        this.address = address;
        this.password = password;
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
        return Objects.equals(getNif(), that.getNif()) &&
                Objects.equals(getEmail(), that.getEmail()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getAddress(), that.getAddress()) &&
                Objects.equals(getPassword(), that.getPassword());
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
