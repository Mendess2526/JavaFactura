package javafactura.businessLogic.exceptions;

import javafactura.businessLogic.Admin;

/**
 * Thrown when an {@link Admin} only action is requested by a non-{@link Admin}
 */
public class NotAdminException extends Exception {

    private static final long serialVersionUID = -341101842744497421L;

    public NotAdminException(String message){
        super(message);
    }
}
