package javafactura.businessLogic;

import java.io.Serializable;

public class Admin implements User,
                              Serializable {

    private static final long serialVersionUID = 9103824574896562100L;
    private final String name;
    private String password;

    public Admin(){
        this.name = "admin";
        this.password = "admin";
    }

    private Admin(Admin admin){
        this.name = admin.getName();
        this.password = admin.getPassword();
    }

    public String getNif(){
        return this.name;
    }

    @Override
    public String getName(){
        return this.name;
    }

    @Override
    public String getPassword(){
        return password;
    }

    @Override
    public void setPassword(String password){
        this.password = password;
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;

        if(o == null || this.getClass() == o.getClass()) return false;

        Admin admin = (Admin) o;
        return this.name.equals(admin.getName())
               && this.password.equals(admin.getPassword());
    }

    @Override
    public String toString(){
        return "Admin{" +
               "name='" + name + '\''
               + ", password='" + password + '\''
               + '}';
    }

    public Admin clone(){
        return new Admin(this);
    }
}
