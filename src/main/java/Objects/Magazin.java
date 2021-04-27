/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objects;

import java.util.Date;

/**
 *
 * @author Jenni
 */
public class Magazin extends Copy{
    Date magazineDate;
    int magazineNumber;

    public Magazin(Date magazineDate, int magazineNumber, int streckkod, String loanKategori, String placement, int ObjektID, String Titel) {
        super(streckkod, loanKategori, placement, ObjektID, Titel);
        this.magazineDate = magazineDate;
        this.magazineNumber = magazineNumber;
    }

    public Date getMagazineDate() {
        return magazineDate;
    }

    public int getMagazineNumber() {
        return magazineNumber;
    }

 
}
