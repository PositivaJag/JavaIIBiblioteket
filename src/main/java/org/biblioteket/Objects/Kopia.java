/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.biblioteket.Objects;
import org.biblioteket.Objects.Objekt;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
/**
 *
 * @author Jenni
 */
public class Kopia{
    
    /**
     *
     */
    public enum AccessKopia {

         /**
          *
          */
         AVAILABLE,

         /**
          *
          */
         ON_LOAN
     }
    private int streckkod;
    private int objektID;
    private String loanKategori;
    private String placement;
    private AccessKopia access;
    private LocalDate returnLatest;

    /**
     *
     * @param streckkod
     * @param objektID
     * @param loanKategori
     * @param placement
     * @param access
     * @param returnDate
     * @throws SQLException
     */
    public Kopia(int streckkod, int objektID, String loanKategori, 
            String placement, AccessKopia access, LocalDate returnDate) throws SQLException {
            this.streckkod = streckkod;
            this.objektID = objektID;
            this.loanKategori = loanKategori;
            this.placement = placement;
            this.access = access;
            this.returnLatest = returnDate;
    }
    
    /**
     *
     * @param streckkod
     * @param objektID
     * @param loanKategori
     * @param placement
     */
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

    /**
     *
     * @return
     */
        
    public int getStreckkod() {
        return streckkod;
    }

    /**
     *
     * @return
     */
    public String getLoanKategori() {
        return loanKategori;
    }

    /**
     *
     * @return
     */
    public String getPlacement() {
        return placement;
    }

    /**
     *
     * @param streckkod
     */
    public void setStreckkod(int streckkod) {
        this.streckkod = streckkod;
    }

    /**
     *
     * @param loanKategori
     */
    public void setLoanKategori(String loanKategori) {
        this.loanKategori = loanKategori;
    }

    /**
     *
     * @param placement
     */
    public void setPlacement(String placement) {
        this.placement = placement;
    }

//    public String[] getCopy(){
//        String[] c = new String[]{Integer.toString(super.getObjektID()), super.getTitel(), 
//            Integer.toString(this.streckkod), this.loanKategori, this.placement };
//        return c;    
//    }

    /**
     *
     * @return
     */

    public int getObjektID() {
        return objektID;
    }

    /**
     *
     * @return
     */
    public AccessKopia getAccess() {
        return access;
    }

    /**
     *
     * @return
     */
    public LocalDate getReturnLatest() {
        return returnLatest;
    }
    
    
}
