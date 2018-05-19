package javafactura.businessLogic.exceptions;

public class InvalidNumberOfDependantsException extends Exception {

    private static final long serialVersionUID = -4058603082985533113L;
    private final int numDependants;

    public InvalidNumberOfDependantsException(int numDependants){
        super("Number of dependants" + numDependants);
        this.numDependants = numDependants;
    }

    public int getNumDependants(){
        return this.numDependants;
    }
}
