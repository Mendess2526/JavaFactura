package javafactura.businessLogic;

public interface User {

    String getNif();

    String getName();

    String getPassword();

    void setPassword(String password);

    User clone();
}
