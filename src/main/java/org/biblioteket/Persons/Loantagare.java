/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.biblioteket.Persons;

import org.biblioteket.Database.DBConnection;

/**
 * Handles information about people that can borrow Objekts from the library. 
 * Extends Person.
 * @author Jenni
 */
public class Loantagare extends Person{
    
    String telNr;
    String gatuAdress;
    String postNr;
    LoantagareKategori kategori;
    
    enum LoantagareKategori{
        STUDENT, 
        RESEARCHER, 
        UNIVERSITY_EMPLOYEE,
        OTHER
    }
 

    /**
     * Construktor for inputting all info. 
     * @param personID
     * @param fName
     * @param lName
     * @param email
     * @param password
     * @param personTyp
     * @param telNr
     * @param gatuAdress
     * @param postNr
     * @param Kategori
     */
    public Loantagare(String personID, String fName, String lName, String email, 
            String password, PersonTyp personTyp,String telNr, String gatuAdress, 
            String postNr, LoantagareKategori Kategori) {
        super(personID, fName, lName, email, password, personTyp);
        this.telNr = telNr;
        this.gatuAdress = gatuAdress;
        this.postNr = postNr;
        this.kategori = Kategori;
    }
    
    /**
     * Constructor with Person info as parameters. Gets the rest of the info 
     * from the DB.
     * @param personID
     * @param fName
     * @param lName
     * @param email
     * @param password
     * @param personTyp
     */
    public Loantagare(String personID, String fName, String lName, String email, 
            String password, PersonTyp personTyp){
        //Sets super class variables 
        super(personID, fName, lName, email, password, personTyp);
        try{
            //Gets DB instance
            DBConnection connection = DBConnection.getInstance();
            //Gets Loantagar info from DB
            String[] loantagareDB = connection.getLoantagareData(personID);
            //sets Loantagar variables. 
            this.telNr = loantagareDB[1];
            this.gatuAdress = loantagareDB[2];
            this.postNr = loantagareDB[3];
            setKategoriFromString(loantagareDB[4]);        
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Getter
     * @return telephone number
     */
    public String getTelNr() {
        return telNr;
    }

    /**
     * Getter
     * @return Street Address. 
     */
    public String getGatuAdress() {
        return gatuAdress;
    }

    /**
     * Getter
     * @return Postal number
     */
    public String getPostNr() {
        return postNr;
    }

    /**
     * Getter
     * @return Loaner category. 
     */
  
    public LoantagareKategori getKategori(){
        return kategori;
    }

    /**
     * Setter
     * @param telNr
     */
    public void setTelNr(String telNr) {
        this.telNr = telNr;
    }

    /**
     * Setter
     * @param gatuAdress
     */
    public void setGatuAdress(String gatuAdress) {
        this.gatuAdress = gatuAdress;
    }

    /**
     * Setter
     * @param postNr
     */
    public void setPostNr(String postNr) {
        this.postNr = postNr;
    }

    /**
     * Setter for kategori with string input from DB.
     * @param kategori
     */
    private void setKategoriFromString(String kategori) {
        switch (kategori){
            case "Student":
                this.kategori = LoantagareKategori.STUDENT;
                break;
            case "Forskare":
                this.kategori = LoantagareKategori.RESEARCHER;
                break;
            case "Universitetsanställd":
                this.kategori=  LoantagareKategori.UNIVERSITY_EMPLOYEE;
                break;
            default:
                this.kategori = LoantagareKategori.OTHER;
        }            
    }
    
    /**
     * Setter frok kategori  with enum as input. 
     * @param kategori 
     */
    public void setKategoriFromEnum(LoantagareKategori kategori){
        this.kategori = kategori;
    }
    
    
    /**
     * Prints Loantagare info as string. 
     * @return String. 
     */
    @Override
    public String toString(){
        return "Persondata\n"+getPersonID()+"\n"+getfName()+"\n"+getlName()+"\n"+getPassword()+"\n"+getPersonTypAsString()+"\n\n"+
                "Låntagardata\n"+this.telNr+"\n"+this.gatuAdress+"\n"+this.postNr+"\n"+this.kategori;

    }
    


    }
    
    

