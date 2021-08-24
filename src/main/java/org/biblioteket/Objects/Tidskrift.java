package org.biblioteket.Objects;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author Jenni
 */
public class Tidskrift extends Objekt{
    LocalDate magazineDate;
    int magazineNumber;
    private ArrayList<Kopia> Kopior;
    private ArrayList<String> SearchWords;
    
    /**
     *
     * @param objektID
     * @param title
     * @param type
     * @param magazineDate
     * @param magazineNumber
     * @param sw
     */
    public Tidskrift(int objektID, String title, Type type, LocalDate magazineDate, 
            int magazineNumber, ArrayList<String> sw) {
        super(objektID, title, type);
        this.magazineDate = magazineDate;
        this.magazineNumber = magazineNumber;
        this.SearchWords = sw;
    }
    
    public LocalDate getMagazineDate() {
        return magazineDate;
    }

    public int getMagazineNumber() {
        return magazineNumber;
    }

}
