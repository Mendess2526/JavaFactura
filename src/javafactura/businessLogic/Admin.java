package javafactura.businessLogic;

import java.io.Serializable;

/**
 * The administrator user
 */
public class Admin implements User,
                              Serializable {

    private static final long serialVersionUID = 9103824574896562100L;
    /**
     * The name of the user (Should always be "admin")
     */
    private final String name;
    /**
     * The admin password
     */
    private String password;

    /**
     * \brief The default constructor.
     * By default the username/nif and the password are {@code "admin"}
     */
    public Admin(){
        this.name = "admin";
        this.password = "admin";
    }

    /**
     * The copy constructor
     * @param admin The admin to copy
     */
    private Admin(Admin admin){
        this.name = admin.getName();
        this.password = admin.getPassword();
    }

    /**
     * {@inheritDoc}
     * (in the case of the admin the name and nif are the same)
     * @return The nif
     */
    @Override
    public String getNif(){
        return this.name;
    }

    /** {@inheritDoc} */
    @Override
    public String getName(){
        return this.name;
    }

    /** {@inheritDoc} */
    @Override
    public String getPassword(){
        return password;
    }

    /** {@inheritDoc} */
    @Override
    public void setPassword(String password){
        this.password = password;
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;

        if(obj == null || this.getClass() == obj.getClass()) return false;

        Admin admin = (Admin) obj;
        return this.name.equals(admin.getName())
               && this.password.equals(admin.getPassword());
    }

    /** {@inheritDoc} */
    @Override
    public Admin clone(){
        return new Admin(this);
    }

    /** {@inheritDoc} */
    @Override
    public String toString(){
        return "Admin{" +
               "name='" + name + '\''
               + ", password='" + password + '\''
               + '}';
    }
}
