package javafactura.businessLogic.exceptions;

import javafactura.businessLogic.ContribuinteIndividual;

/**
 * Thrown when a {@link ContribuinteIndividual} only action is requested by a non-{@link ContribuinteIndividual}
 */
public class NotIndividualException extends NotContribuinteException {

    private static final long serialVersionUID = -7913498599217339482L;

    public NotIndividualException(String message){
        super(message);
    }
}
