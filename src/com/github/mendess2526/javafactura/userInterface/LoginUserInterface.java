package com.github.mendess2526.javafactura.userInterface;

import com.github.mendess2526.javafactura.efactura.JavaFactura;
import com.github.mendess2526.javafactura.efactura.exceptions.InvalidCredentialsException;
import com.github.mendess2526.javafactura.userInterface.screens.Form;

import java.util.HashMap;
import java.util.Map;

public class LoginUserInterface implements UserInterface {

    private JavaFactura javaFactura;

    LoginUserInterface(JavaFactura javaFactura){
        this.javaFactura = javaFactura;
    }

    public void run(){
        String[] creds = getCredentials();
        try{
            javaFactura.login(creds[0],creds[1]);
        }catch(InvalidCredentialsException e){
            System.out.println("Invalid credentials");
            run();
            return;
        }
        if(javaFactura.isAdmin()){
            new AdminUserInterface(javaFactura);
        }
    }

    private String[] getCredentials(){
        Map<Integer,String> fields = new HashMap<>();
        fields.put(0,"Nif: ");
        fields.put(1,"Password: ");
        Form login = new Form("Login",fields);
        login.execute();
        Map<Integer,String> answers = login.getAnswers();
        return new String[]{answers.get(0), answers.get(1)};
    }
}
