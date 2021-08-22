package org.biblioteket.Persons;

import org.biblioteket.Database.DBConnection;

/**
 * Person that can access the library system. Are either bibliotekarie or
 * loantagare. Loantagare can borrow books. Bibliotekarie can print list of late
 * Kopia and handle Objekt and Kopia.
 *
 * @author Jenni
 */
public class Person {

    private final String personID;
    private String fName;
    private String lName;
    private String email;
    private String password;
    private final String personTyp;

    public enum PersonTyp {
        BIBLIOTEKARIE,
        LOANTAGARE,
        NONE
    }

    /**
     * Constructor called from Loantagare.
     *
     * @param personID
     * @param fName
     * @param lName
     * @param email
     * @param password
     * @param personTyp
     */
    public Person(String personID, String fName, String lName, String email, String password, String personTyp) {
        this.personID = personID;
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.password = password;
        this.personTyp = personTyp;
    }

    /**
     * Constructor with only email input. The constructor collects the rest of
     * the info from DB.
     *
     * @param email
     */
    public Person(String email) {
        //Check connection to DB
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

    /**
     * Can be used as help during development.
     *
     * @return
     */
    @Override
    public String toString() {
        return "Persondata\n" + personID + "\n" + fName + "\n" + lName + "\n" + password + "\n" + personTyp + "\n";
    }
}
