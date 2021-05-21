package org.biblioteket.Objects;

import java.sql.SQLException;
import java.util.Date;

/**
 * Handles Tidskrift.
 * @author Jenni
 */
public class Tidskrift extends Copy{
    
    Date magazineDate;
    int magazineNumber;

    /**
     * Constructor
     * @param magazineDate
     * @param magazineNumber
     * @param streckkod
     * @param loanKategori
     * @param placement
     * @param ObjektID
     * @param Titel
     * @throws SQLException
     */
    public Tidskrift(Date magazineDate, int magazineNumber, int streckkod, String loanKategori, String placement, String objektID, String Titel) throws SQLException {
        super(streckkod, objektID, loanKategori, placement);
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
