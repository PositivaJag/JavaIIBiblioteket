package org.biblioteket.Objects;
import java.time.LocalDate;

/**
 * Handles copies of Objekts. 
 * One Object (Bok, Film or Tidskrift) is a composition of many Kopia
 * @author Jenni
 */
public class Kopia{
    
    public enum AccessKopia {
         AVAILABLE,
         ON_LOAN
     }
    private int streckkod;
    private final int objektID;
    private String loanKategori;
    private String placement;
    private AccessKopia access;
    private LocalDate returnLatest;

    /**
     * Main contstructor
     * @param streckkod
     * @param objektID
     * @param loanKategori
     * @param placement
     * @param access
     * @param returnDate
     */
    public Kopia(int streckkod, int objektID, String loanKategori, 
            String placement, AccessKopia access, LocalDate returnDate){
            this.streckkod = streckkod;
            this.objektID = objektID;
            this.loanKategori = loanKategori;
            this.placement = placement;
            this.access = access;
            this.returnLatest = returnDate;
    }
    
    /**
     * Cnstructor for creating new Kopia. 
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

    public int getObjektID() {
        return objektID;
    }

    public AccessKopia getAccess() {
        return access;
    }

    public LocalDate getReturnLatest() {
        return returnLatest;
    }
}
