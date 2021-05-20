/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.biblioteket.Objects;
import org.biblioteket.Objects.Objekt;
import java.sql.SQLException;
/**
 *
 * @author Jenni
 */
public class Copy{
    
    private int streckkod;
    private String loanKategori;
    private String placement;

    Copy(int streckkod, String loanKategori, String placement) throws SQLException {
            this.streckkod = streckkod;
            this.loanKategori = loanKategori;
            this.placement = placement;
    }
    
    /**
     *
     * @param streckkod
     * @param loanKategori
     * @param placement
     * @param objektID
     * @return
     * @throws SQLException
     * @throws Exception
     */
    public Copy newCopy(int streckkod, String loanKategori, String placement, Objekt objektID) throws SQLException, Exception {
        if (Objekt.checkInstance(objektID)){
            return new Copy(streckkod, loanKategori, placement);
        }
        else
            throw new Exception("Objektet "+objektID.getTitel()+"finns inte.");
    }
        
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
}
