package org.biblioteket.Persons;

import org.biblioteket.Database.DBConnection;

/**
 * Handles basic info about people that can log in to the library. 
 * @author Jenni
 */
public class Person {
    private final String personID;
    private String fName;
    private String lName;
    private String email;
    private String password;
    private PersonTyp personTyp;
    
    /**
     * Enum for type of logged in person, librarian or loaner. 
     */
    public enum PersonTyp{
        BIBLIOTEKARIE,
        LOANTAGARE, 
        NONE
    }

    /**
     * Constructor for inputing all info. 
     * @param personID
     * @param fName
     * @param lName
     * @param email
     * @param password
     * @param personTyp
     */
    public Person(String personID, String fName, String lName, String email, 
            String password, PersonTyp personTyp) {
        this.personID = personID;
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.password = password;
        this.personTyp = personTyp;
    }
    
    /**
     *Constructor with email as parameter. Gets the rest of the info from the DB. 
     * @param email
     * 
     */
    public Person (String email){
        //Check connection to DB
        DBConnection connection = DBConnection.getInstance();
        //Get user data
        String[] personDB = connection.getPersonData(email);
        //Set Person object values
        this.personID = personDB[0];
        this.fName = personDB[1];
        this.lName = personDB[2];
        this.email = email;
        this.password = personDB[4];
        setPersonTypFromString(personDB[5]);
    }

    /**
     * Getter
     * @return personID
     */
    public String getPersonID() {
        return personID;
    }

    /**
     * Getter
     * @return first name
     */
    public String getfName() {
        return fName;
    }

    /**
     * Setter 
     * @param fName
     */
    public void setfName(String fName) {
        this.fName = fName;
    }

    /**
     * Getter
     * @return last name
     */
    public String getlName() {
        return lName;
    }

    /**
     * Setter
     * @param lName
     */
    public void setlName(String lName) {
        this.lName = lName;
    }  

    /**
     * Getter
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter
     * @return person type
     */
    public PersonTyp getPersonTyp(){
        return personTyp;
    }
    
    /**
     * Setter for personTyp with string input from DB.
     * @param personTyp 
     */
    public final void setPersonTypFromString(String personTyp){
        if (personTyp.equalsIgnoreCase("Bibliotekarie"))
            this.personTyp = PersonTyp.BIBLIOTEKARIE;
        else if (personTyp.equalsIgnoreCase("Låntagare"))
            this.personTyp = PersonTyp.LOANTAGARE;
        else
            this.personTyp = PersonTyp.NONE;
    }
    
    /**
     * Setter for personTyp with enum as input. 
     * @param personTyp 
     */
    public void setPersonTypFromEnum(PersonTyp personTyp){
        this.personTyp = personTyp;
    }
    
    
    /**
     * Prints Person info as string. 
     * @return String.
     */
    @Override
    public String toString(){
        return "Persondata\n"+personID+"\n"+fName+"\n"+lName+"\n"+password+"\n"+personTyp+"\n";
        
        
    }
    
    
}
