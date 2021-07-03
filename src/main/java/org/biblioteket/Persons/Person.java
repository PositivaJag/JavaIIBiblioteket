/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.biblioteket.Persons;

import org.biblioteket.Database.DBConnection;
import java.sql.SQLException;

/**
 *
 * @author Jenni
 */
public class Person {
    private String personID;
    private String fName;
    private String lName;
    private String email;
    private String password;
    private String personTyp;
    
    public enum PersonTyp{
        BIBLIOTEKARIE,
        LOANTAGARE, 
        NONE
    }

    public Person(String personID, String fName, String lName, String email, String password, String personTyp) {
        this.personID = personID;
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.password = password;
        this.personTyp = personTyp;
    }
    
    public Person (String email) throws SQLException{
        //Check connection to DB
        try{
        DBConnection connection = DBConnection.getInstance();
        
        //Get user data
        String[] personDB = connection.getPersonDataAsList(email);
        this.personID = personDB[0];
        this.fName = personDB[1];
        this.lName = personDB[2];
        this.email = email;
        this.password = personDB[4];
        this.personTyp = personDB[5];
        } 
        catch(SQLException e){
            
        }
        
    }

    public String getPersonID() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPersonTyp() {
        return personTyp;
    }
    
    
    @Override
    public String toString(){
        return "Persondata\n"+personID+"\n"+fName+"\n"+lName+"\n"+password+"\n"+personTyp+"\n";
        
        
    }
    
    
}
