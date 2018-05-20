package javafactura.businessLogic.exceptions;

import javafactura.businessLogic.ContribuinteEmpresarial;

/**
 * Thrown when a {@link ContribuinteEmpresarial} only action is requested by a non-{@link ContribuinteEmpresarial}
 */
public class NotEmpresaException extends NotContribuinteException {

    private static final long serialVersionUID = 8761213073125896080L;
}
