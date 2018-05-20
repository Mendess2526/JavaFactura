package javafactura.businessLogic.exceptions;

import javafactura.businessLogic.ContribuinteEmpresarial;

/**
 * Thrown when trying to add a {@link ContribuinteEmpresarial} that is already in the system.
 */
public class ContribuinteAlreadyExistsException extends Exception {

    private static final long serialVersionUID = 7316935879046250364L;
}
