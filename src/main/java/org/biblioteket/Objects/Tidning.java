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
public class Tidning extends Copy{
    Date magazineDate;
    int magazineNumber;

    /**
     *
     * @param magazineDate
     * @param magazineNumber
     * @param streckkod
     * @param loanKategori
     * @param placement
     * @param ObjektID
     * @param Titel
     * @throws SQLException
     */
    public Tidning(Date magazineDate, int magazineNumber, int streckkod, String loanKategori, String placement, int ObjektID, String Titel) throws SQLException {
        super(streckkod, loanKategori, placement);
        this.magazineDate = magazineDate;
        this.magazineNumber = magazineNumber;
    }

    /**
     *
     * @return
     */
    public Date getMagazineDate() {
        return magazineDate;
    }

    /**
     *
     * @return
     */
    public int getMagazineNumber() {
        return magazineNumber;
    }

 
}
