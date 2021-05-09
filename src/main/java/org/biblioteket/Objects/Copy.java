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
    
    public Copy newCopy(int streckkod, String loanKategori, String placement, Objekt objektID) throws SQLException, Exception {
        if (Objekt.checkInstance(objektID)){
            return new Copy(streckkod, loanKategori, placement);
        }
        else
            throw new Exception("Objektet "+objektID.getTitel()+"finns inte.");
    }
        
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
}
