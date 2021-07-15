package ds;

import java.io.Serializable;

public class Person extends User implements Serializable {
    String name;
    String lastName;
    String email;
    public Person() {

    }
    @Override
    public void setCompanyName(String companyName) {}



    @Override
    public void setFirstName(String name) {
        this.name = name;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public void setContactPerson(String contactPerson) {

    }

    @Override
    public String getContactPerson() {
      return " ";
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getFirstName(){
        return name;
    }

    @Override
    public String getLastName(){
        return lastName;
    }

    @Override
    public String getEmail(){
        return email;
    }

    @Override
    public String getCompanyName(){return "";}


    public Person(String id, String userName, String password, String type, String email, String name, String lastName) {
        super(userName, password, id);
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.type=type;
    }

    @Override
    public String toString() {
        return name + " " + lastName + "  " + email;
    }
}
