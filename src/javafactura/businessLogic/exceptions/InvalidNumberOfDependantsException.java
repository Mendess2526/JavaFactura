package javafactura.businessLogic.exceptions;

public class InvalidNumberOfDependantsException extends Exception {

    private final int numDependants;

    public InvalidNumberOfDependantsException(int numDependants){
        super("Number of dependants" + numDependants);
        this.numDependants = numDependants;
    }

    public int getNumDependants(){
        return this.numDependants;
    }
}
