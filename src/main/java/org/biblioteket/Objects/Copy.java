package org.biblioteket.Objects;

/**
 * Handles Copes of Objekts
 *
 * @author Jenni
 */
public class Copy {

    private int streckkod;
    private final String objektID;
    private String loanKategori;
    private String placement;

    /**
     * Constructor
     *
     * @param streckkod
     * @param objektID
     * @param loanKategori
     * @param placement
     */
    public Copy(int streckkod, String objektID, String loanKategori, String placement) {
        this.streckkod = streckkod;
        this.objektID = objektID;
        this.loanKategori = loanKategori;
        this.placement = placement;
    }

    /**
     * Getter
     *
     * @return streckkod
     */
    public int getStreckkod() {
        return streckkod;
    }

    /**
     * Getter
     *
     * @return loanKategori
     */
    public String getLoanKategori() {
        return loanKategori;
    }

    /**
     * Getter
     *
     * @return Placement
     */
    public String getPlacement() {
        return placement;
    }

    /**
     * Setter
     *
     * @param streckkod
     */
    public void setStreckkod(int streckkod) {
        this.streckkod = streckkod;
    }

    /**
     * Setter
     *
     * @param loanKategori
     */
    public void setLoanKategori(String loanKategori) {
        this.loanKategori = loanKategori;
    }

    /**
     * Setter
     *
     * @param placement
     */
    public void setPlacement(String placement) {
        this.placement = placement;
    }

}
