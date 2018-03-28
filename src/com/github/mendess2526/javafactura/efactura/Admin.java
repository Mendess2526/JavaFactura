package com.github.mendess2526.javafactura.efactura;

import java.io.Serializable;

public class Admin implements User, Serializable{
    private String password;

    public Admin(){
        this.password = "admin";
    }

    public Admin(String password){
        this.password = password;
    }

    public Admin(Admin admin){
        this.password = admin.getPassword();
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public Admin clone(){
        return new Admin(this);
    }
}
