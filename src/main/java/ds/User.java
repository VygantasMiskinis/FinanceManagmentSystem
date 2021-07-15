package ds;

import java.io.*;

public abstract class User implements Serializable {
    protected String userName;
    protected String password;
    protected String id;
    protected String type;

    public User() {

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String pass){
        this.password = pass;
    }

    public abstract void setCompanyName(String companyName);

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type=type;
    }

    public abstract void setFirstName(String name);

    public abstract void setLastName(String lastName);

    public abstract void setContactPerson(String contactPerson);

    public abstract String getContactPerson();

    public abstract void setEmail(String email);


    public abstract String getFirstName();

    public abstract String getLastName();

    public abstract String getEmail();

    public abstract String getCompanyName();




    public User(String userName, String password, String id) {
        this.userName = userName;
        this.password = password;
        this.id = id;
    }

    @Override
    public String toString() {
        return "userName= " + userName;
    }

}
