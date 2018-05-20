package javafactura.businessLogic.exceptions;

/**
 * Thrown when the number of dependants is higher then the size of the family aggregate
 */
public class InvalidNumberOfDependantsException extends Exception {

    private static final long serialVersionUID = -4058603082985533113L;
    /**
     * The requested number of dependants
     */
    private final int numDependants;

    /**
     * Constructs a new exception with the number of dependants requested
     * @param numDependants The number of dependants
     */
    public InvalidNumberOfDependantsException(int numDependants){
        super("Number of dependants" + numDependants);
        this.numDependants = numDependants;
    }

    /**
     * Returns the number of dependants
     * @return the number of dependants
     */
    public int getNumDependants(){
        return this.numDependants;
    }
}
