package javafactura.businessLogic;

import java.io.Serializable;

public class Admin implements User,
                              Serializable {

    private final String name;
    private String password;

    public Admin(){
        this.name = "admin";
        this.password = "admin";
    }

    public Admin(String name, String password){
        this.name = name;
        this.password = password;
    }

    private Admin(Admin admin){
        this.name = admin.getName();
        this.password = admin.getPassword();
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
