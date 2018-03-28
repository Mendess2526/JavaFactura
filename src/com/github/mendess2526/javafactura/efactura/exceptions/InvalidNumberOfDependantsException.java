package com.github.mendess2526.javafactura.efactura.exceptions;

public class InvalidNumberOfDependantsException extends Throwable {

    private final int numDependants;

    public InvalidNumberOfDependantsException(int numDependants){
        this.numDependants = numDependants;
    }

    public int getNumDependants(){
        return numDependants;
    }
}
