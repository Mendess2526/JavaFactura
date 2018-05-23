package javafactura.businessLogic.exceptions;

import javafactura.businessLogic.ContribuinteIndividual;

/**
 * Thrown when the requested {@link ContribuinteIndividual} does not exist
 */
public class NoSuchIndividualException extends Exception {

    private static final long serialVersionUID = -4958389397051034003L;

    public NoSuchIndividualException(String message){
        super(message);
    }
}
