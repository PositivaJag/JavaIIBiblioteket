/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PublicPersons;

/**
 *
 * @author Jenni
 */
public class Person {
    int personID;
    String fName;
    String lName;
    String roles;

    public Person(int personID, String fName, String lName, String Role) {
        this.personID = personID;
        this.fName = fName;
        this.lName = lName;
        this.roles = Role;
    }

    public int getPersonID() {
        return personID;
    }


    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getRole() {
        return roles;
    }

    public void setRole(String Role) {
        this.roles = Role;
    }
    
    
    
    
}
