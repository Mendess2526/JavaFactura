package com.github.mendess2526.javafactura.userInterface;

import com.github.mendess2526.javafactura.efactura.*;
import com.github.mendess2526.javafactura.efactura.exceptions.InvalidCredentialsException;
import com.github.mendess2526.javafactura.userInterface.screens.Form;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class LoginUI implements UI {

    private final JavaFactura javaFactura;

    /**
     * \brief Constructor for the loginUI
     * @param javaFactura The instance of the business logic
     */
    LoginUI(JavaFactura javaFactura){
        this.javaFactura = javaFactura;
        try{
            javaFactura.saveState();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * \brief Runs the user interface
     */
    public void run(){
        try{
            Credentials credentials = getCredentials();
            javaFactura.login(credentials.getNif(), credentials.getPass());
        }catch(NoSuchElementException | InvalidCredentialsException e){
            System.out.println("Invalid credentials");
            return;
        }
        User u = this.javaFactura.getLoggedUser();
        if(u instanceof Admin){
            new AdminUI(javaFactura).run();
        }else if(u instanceof ContribuinteIndividual){
            new ContribuinteIndividualUI(javaFactura).run();
        }else if(u instanceof ContribuinteEmpresarial){
            new ContribuinteEmpresarialUI(javaFactura).run();
        }
    }

    /**
     * \brief Gets the users credentials with a form
     * @return the users credentials
     */
    private Credentials getCredentials(){
        Map<Integer,String> fields = new HashMap<>();
        fields.put(0, "Nif: ");
        fields.put(1, "Password: ");
        Form login = new Form("Login", fields);
        login.execute();
        Map<Integer,String> answers = login.getResult();
        return new Credentials(answers.get(0), answers.get(1));
    }

    /**
     * \brief Wrapper class to pass the credentials gotten from the form
     */
    private final class Credentials {

        private final String nif;
        private final String pass;

        /**
         * \brief Parametrised constructor
         * @param nif  nif
         * @param pass password
         */
        private Credentials(String nif, String pass){
            this.nif = nif;
            this.pass = pass;
        }

        /**
         * \brief Returns the NIF
         * @return The NIF
         */
        private String getNif(){
            return this.nif;
        }

        /**
         * \brief Returns the password
         * @return The password
         */
        private String getPass(){
            return this.pass;
        }
    }
}
