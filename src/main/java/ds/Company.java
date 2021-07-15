package ds;

import java.io.Serializable;

public class Company extends User implements Serializable {
    private String name;
    private String contactEmail;
    private String contactPerson;

    public Company(){

    }

    public Company(String id, String userName, String password, String type, String contactEmail, String name, String contactPerson){
        super(userName, password, id);
        this.name=name;
        this.contactEmail= contactEmail;
        this.contactPerson=contactPerson;

        this.type=type;
    }

    @Override
    public void setCompanyName(String companyName) {
        this.name = companyName;
    }

    @Override
    public void setFirstName(String name) {

    }

    @Override
    public void setLastName(String lastName) {

    }

    @Override
    public void setContactPerson(String contactPerson) {
        this.contactPerson=contactPerson;
    }

    @Override
    public String getContactPerson() {
        return contactPerson;
    }

    @Override
    public void setEmail(String email) {
        this.contactEmail=email;
    }

    @Override
    public String toString() {
        return "Company: " +  name + " " + contactEmail + " Contact person: " + contactPerson;
    }

    @Override
    public String getFirstName(){
        return  "";
    }
    @Override
    public String getLastName(){
        return  "";
    }
    @Override
    public String getEmail(){
        return this.contactEmail;
    }
    @Override
    public String getCompanyName(){return name;}

}
