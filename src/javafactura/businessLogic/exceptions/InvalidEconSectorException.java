package javafactura.businessLogic.exceptions;

import javafactura.businessLogic.econSectors.EconSector;

/**
 * Thrown when an invalid {@link EconSector} is requested
 */
public class InvalidEconSectorException extends Exception {

    private static final long serialVersionUID = 8843431047738030386L;

    /**
     * Constructs a new exception with the specified detail message
     * @param message The detail message
     */
    public InvalidEconSectorException(String message){
        super(message);
    }
}
