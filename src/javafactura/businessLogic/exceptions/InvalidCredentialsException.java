package javafactura.businessLogic.exceptions;

/**
 * Thrown when the credentials used for login are invalid
 */
public class InvalidCredentialsException extends Exception {

    private static final long serialVersionUID = -8452339061058850172L;

    public InvalidCredentialsException(String message){
        super(message);
    }
}
