package com.github.mendess2526.javafactura.efactura;

import java.io.Serializable;

public class Admin implements User, Serializable{
    private String name;
    private String password;

    public Admin(){
        this.name = "admin";
        this.password = "admin";
    }

    public Admin(String name, String password){
        this.name = this.name;
        this.password = password;
    }

    public Admin(Admin admin){
        this.name = admin.getName();
        this.password = admin.getPassword();
    }

    @Override
    public String getName(){
        return this.name;
    }

    @Override
    public String getPassword(){
        return password;
    }

    @Override
    public void setPassword(String password){
        this.password = password;
    }

    public Admin clone(){
        return new Admin(this);
    }
}
