/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.biblioteket.Objects;

import java.sql.SQLException;
import java.util.Date;

/**
 *
 * @author Jenni
 */
public class Magazin extends Kopia{
    Date magazineDate;
    int magazineNumber;

    public Magazin(Date magazineDate, int magazineNumber, int streckkod, String loanKategori, String placement, int ObjektID, String Titel) throws SQLException {
        super(streckkod, loanKategori, placement);
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
