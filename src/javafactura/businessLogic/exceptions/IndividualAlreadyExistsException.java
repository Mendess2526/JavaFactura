package javafactura.businessLogic.exceptions;

import javafactura.businessLogic.ContribuinteIndividual;

/**
 * Thrown when trying to add a {@link ContribuinteIndividual} already registered in the system
 */
public class IndividualAlreadyExistsException extends Exception {

    private static final long serialVersionUID = -3572578182649314883L;
}
