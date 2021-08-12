/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.biblioteket.Objects;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Jenni
 */
public class Tidskrift extends Objekt{
    Date magazineDate;
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
     * @throws SQLException
     */
    public Tidskrift(int objektID, String title, Type type, Date magazineDate, 
            int magazineNumber, ArrayList<String> sw) throws SQLException {
        super(objektID, title, type);
        this.magazineDate = magazineDate;
        this.magazineNumber = magazineNumber;
        this.SearchWords = sw;
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
