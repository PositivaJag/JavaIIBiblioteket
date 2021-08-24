package org.biblioteket.Persons;

import java.util.ArrayList;
import org.biblioteket.Database.DBConnection;

/**
 * Subclass of Person, a Loantagare can borrow copys of Objekt. 
 * @author Jenni
 */
public class Loantagare extends Person {

    private String telNr;
    private String gatuAdress;
    private String postNr;
    private String kategori;
    private int noOfLoans;
    private ArrayList<Integer> loans;   //lista av lånade streckkoder. 

    /**
     * Constructor
     *
     * @param personID
     * @param fName
     * @param lName
     * @param email
     * @param password
     * @param personTyp
     */
    public Loantagare(String personID, String fName, String lName, String email,
            String password, String personTyp) {
        super(personID, fName, lName, email, password, personTyp);
        
            DBConnection connection = DBConnection.getInstance();

            String[] loantagareDB = connection.getLoantagareDataAsList(personID);
            this.telNr = loantagareDB[1];
            this.gatuAdress = loantagareDB[2];
            this.postNr = loantagareDB[3];
            this.kategori = loantagareDB[4];

    }

    public String getTelNr() {
        return telNr;
    }

    public String getGatuAdress() {
        return gatuAdress;
    }

    public String getPostNr() {
        return postNr;
    }

    public String getKategori() {
        return kategori;
    }

    public void setTelNr(String telNr) {
        this.telNr = telNr;
    }

    public void setGatuAdress(String gatuAdress) {
        this.gatuAdress = gatuAdress;
    }

    public void setPostNr(String postNr) {
        this.postNr = postNr;
    }

    public void setKategori(String Kategori) {
        this.kategori = Kategori;
    }

    public ArrayList<Integer> getLoans() {
        return loans;
    }

    public void setLoans(ArrayList<Integer> loans) {
        this.loans = loans;
    }

    public int getNoOfLoans() {
        return noOfLoans;
    }

    public void setNoOfLoans(int noOfLoans) {
        this.noOfLoans = noOfLoans;
    }

    /**
     * Can be used as help during development. 
     * @return 
     */
    @Override
    public String toString() {
        return "Persondata\n" + getPersonID() + "\n" + getfName() + "\n" + getlName() + "\n" + getPassword() + "\n" + getPersonTyp() + "\n\n"
                + "Låntagardata\n" + this.telNr + "\n" + this.gatuAdress + "\n" + this.postNr + "\n" + this.kategori;

    }

}
