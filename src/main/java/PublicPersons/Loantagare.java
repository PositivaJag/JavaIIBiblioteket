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
public class Loantagare extends Person{
    
    int telNr;
    String gatuAdress;
    int postNr;
    String Kategori;

    //Construktor för alla fält. 
    public Loantagare(int telNr, String gatuAdress, int postNr, String Kategori, int personID, String fName, String lName, String email, String password) {
        super(personID, fName, lName, email, password);
        this.telNr = telNr;
        this.gatuAdress = gatuAdress;
        this.postNr = postNr;
        this.Kategori = Kategori;
    }

    public int getTelNr() {
        return telNr;
    }

    public String getGatuAdress() {
        return gatuAdress;
    }

    public int getPostNr() {
        return postNr;
    }

    public String getKategori() {
        return Kategori;
    }

    public void setTelNr(int telNr) {
        this.telNr = telNr;
    }

    public void setGatuAdress(String gatuAdress) {
        this.gatuAdress = gatuAdress;
    }

    public void setPostNr(int postNr) {
        this.postNr = postNr;
    }

    public void setKategori(String Kategori) {
        this.Kategori = Kategori;
    }
    
    


    }
    
    

