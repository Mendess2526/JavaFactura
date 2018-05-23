package javafactura.businessLogic.exceptions;

import javafactura.businessLogic.Contribuinte;

/**
 * Thrown when a {@link Contribuinte} only action is requested by a non-{@link Contribuinte}
 */
public class NotContribuinteException extends Exception {

    private static final long serialVersionUID = -9053837124236473386L;

    public NotContribuinteException(String message){
        super(message);
    }
}
