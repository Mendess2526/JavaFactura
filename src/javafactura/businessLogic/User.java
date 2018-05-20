package javafactura.businessLogic;

import java.io.Serializable;

/**
 * Interface that defines a application user
 */
public interface User extends Serializable {

    /**
     * Returns the nif
     * @return The nif
     */
    String getNif();

    /**
     * Returns the name
     * @return The name
     */
    String getName();

    /**
     * Returns the password
     * @return The password
     */
    String getPassword();

    /**
     * Changes the password
     * @param password the new password
     */
    void setPassword(String password);

    /**
     * Creates a deep copy of the instance
     * @return a deep copy of the instance
     */
    User clone();
}
