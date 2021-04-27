/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objects;

/**
 *
 * @author Jenni
 */
public class Film extends Copy{
    private String ageRating;
    private String prodCountry;

    public Film(String ageRating, String prodCountry, int streckkod, 
            String loanKategori, String placement, int ObjektID, String Titel) {
        super(streckkod, loanKategori, placement, ObjektID, Titel);
        this.ageRating = ageRating;
        this.prodCountry = prodCountry;
    }

    public String getAgeRating() {
        return ageRating;
    }

    public String getProdCountry() {
        return prodCountry;
    }

    public void setAgeRating(String ageRating) {
        this.ageRating = ageRating;
    }

    public void setProdCountry(String prodCountry) {
        this.prodCountry = prodCountry;
    }
    
    
    
    
    
}
