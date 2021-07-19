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
    
    
    
    @Override
    public String toString(){
        return "Persondata\n"+getPersonID()+"\n"+getfName()+"\n"+getlName()+"\n"+getPassword()+"\n"+getPersonTyp()+"\n\n"+
                "Låntagardata\n"+this.telNr+"\n"+this.gatuAdress+"\n"+this.postNr+"\n"+this.kategori;

    }
    


    }
    
    

