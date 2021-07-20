/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.biblioteket.Objects;
import org.biblioteket.Objects.Objekt;
import java.sql.SQLException;
import java.util.Date;
/**
 *
 * @author Jenni
 */
public class Kopia{
    
     public enum AccessKopia {
         AVAILABLE,
         ON_LOAN
    }
    private int streckkod;
    private int objektID;
    private String loanKategori;
    private String placement;
    private AccessKopia access;
    private Date returnLatest;

    public Kopia(int streckkod, int objektID, String loanKategori, 
            String placement, AccessKopia access, Date returnDate) throws SQLException {
            this.streckkod = streckkod;
            this.objektID = objektID;
            this.loanKategori = loanKategori;
            this.placement = placement;
            this.access = access;
            this.returnLatest = returnDate;
    }
    
      public Kopia(int streckkod, int objektID, String loanKategori, 
            String placement) {
            this.streckkod = streckkod;
            this.objektID = objektID;
            this.loanKategori = loanKategori;
            this.placement = placement;
    }
    
//    public Kopia newCopy(int streckkod, String loanKategori, String placement, AccessKopia access, Objekt objektID) throws SQLException, Exception {
//        if (Objekt.checkInstance(objektID)){
//            return new Kopia(streckkod, loanKategori, placement, access);
//        }
//        else
//            throw new Exception("Objektet "+objektID.getTitel()+"finns inte.");
//    }
        
    public int getStreckkod() {
        return streckkod;
    }

    public String getLoanKategori() {
        return loanKategori;
    }

    public String getPlacement() {
        return placement;
    }

    public void setStreckkod(int streckkod) {
        this.streckkod = streckkod;
    }

    public void setLoanKategori(String loanKategori) {
        this.loanKategori = loanKategori;
    }

    public void setPlacement(String placement) {
        this.placement = placement;
    }

//    public String[] getCopy(){
//        String[] c = new String[]{Integer.toString(super.getObjektID()), super.getTitel(), 
//            Integer.toString(this.streckkod), this.loanKategori, this.placement };
//        return c;    
//    }

    public int getObjektID() {
        return objektID;
    }

    public AccessKopia getAccess() {
        return access;
    }

    public Date getReturnLatest() {
        return returnLatest;
    }
    
    
}
