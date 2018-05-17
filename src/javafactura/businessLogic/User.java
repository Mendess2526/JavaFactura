package javafactura.businessLogic;

import java.io.Serializable;

public interface User extends Serializable {

    String getNif();

    String getName();

    String getPassword();

    void setPassword(String password);

    User clone();
}
