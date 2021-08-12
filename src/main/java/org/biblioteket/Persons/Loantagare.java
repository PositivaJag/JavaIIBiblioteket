/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.biblioteket.Persons;

import java.util.ArrayList;
import org.biblioteket.Database.DBConnection;

/**
 *
 * @author Jenni
 */
public class Loantagare extends Person{
    
    String telNr;
    String gatuAdress;
    String postNr;
    String kategori;
    int noOfLoans;
    ArrayList<Integer> loans;   //lista av lånade streckkoder. 

    //Construktor för alla fält. 
//    public Loantagare(String personID, String fName, String lName, String email, 
//            String password, String personTyp,String telNr, String gatuAdress, 
//            String postNr, String Kategori, int noOfLoan) {
//        super(personID, fName, lName, email, password, personTyp);
//        this.telNr = telNr;
//        this.gatuAdress = gatuAdress;
//        this.postNr = postNr;
//        this.kategori = Kategori;
//        this.loans = new ArrayList<>();
//        this.noOfLoans = noOfLoans;
//    }

    /**
     *
     * @param personID
     * @param fName
     * @param lName
     * @param email
     * @param password
     * @param personTyp
     */
    
    public Loantagare(String personID, String fName, String lName, String email, 
            String password, String personTyp){
        super(personID, fName, lName, email, password, personTyp);
        try{
            DBConnection connection = DBConnection.getInstance();
             
            String[] loantagareDB = connection.getLoantagareDataAsList(personID);
        this.telNr = loantagareDB[1];
        this.gatuAdress = loantagareDB[2];
        this.postNr = loantagareDB[3];
        this.kategori = loantagareDB[4];
        
        }
        catch(Exception e) {
            
        }
    }

    /**
     *
     * @return
     */
    public String getTelNr() {
        return telNr;
    }

    /**
     *
     * @return
     */
    public String getGatuAdress() {
        return gatuAdress;
    }

    /**
     *
     * @return
     */
    public String getPostNr() {
        return postNr;
    }

    /**
     *
     * @return
     */
    public String getKategori() {
        return kategori;
    }

    /**
     *
     * @param telNr
     */
    public void setTelNr(String telNr) {
        this.telNr = telNr;
    }

    /**
     *
     * @param gatuAdress
     */
    public void setGatuAdress(String gatuAdress) {
        this.gatuAdress = gatuAdress;
    }

    /**
     *
     * @param postNr
     */
    public void setPostNr(String postNr) {
        this.postNr = postNr;
    }

    /**
     *
     * @param Kategori
     */
    public void setKategori(String Kategori) {
        this.kategori = Kategori;
    }

    /**
     *
     * @return
     */
    public ArrayList<Integer> getLoans() {
        return loans;
    }

    /**
     *
     * @param loans
     */
    public void setLoans(ArrayList<Integer> loans) {
        this.loans = loans;
    }

    /**
     *
     * @return
     */
    public int getNoOfLoans() {
        return noOfLoans;
    }

    /**
     *
     * @param noOfLoans
     */
    public void setNoOfLoans(int noOfLoans) {
        this.noOfLoans = noOfLoans;
    }
    
    /**
     *
     * @return
     */
    @Override
    public String toString(){
        return "Persondata\n"+getPersonID()+"\n"+getfName()+"\n"+getlName()+"\n"+getPassword()+"\n"+getPersonTyp()+"\n\n"+
                "Låntagardata\n"+this.telNr+"\n"+this.gatuAdress+"\n"+this.postNr+"\n"+this.kategori;

    }
    


    }
    
    

