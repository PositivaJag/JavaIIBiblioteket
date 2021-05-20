/*
 */
package org.biblioteket.Objects;

import java.sql.SQLException;
import java.util.ArrayList;
import org.biblioteket.MainController;



/**
 *
 * @author Jenni
 */
public class Objekt {
    private String objektID;
    private String titel;
    private String type;
//    private ArrayList<Integer> copies;
    private String artists;

    /**
     *
     * @param ObjektID
     * @param Titel
     * @param type
     * @param authors
     */
    public Objekt(String ObjektID, String Titel, String type, String authors) {
//        this.copies = new ArrayList<>(5);
        this.objektID = ObjektID;
        this.titel = Titel;
        this.type = type;
        this.artists = authors;
        
    }

    /**
     *
     * @return
     */
    public String getObjektID() {
        return objektID;
    }

    /**
     *
     * @param objectID
     */
    public void setObjektID(String objectID) {
        this.objektID = objectID;
    }

    /**
     *
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

//    public ArrayList<Integer> getCopies() {
//        return copies;
//    }

//    public void setCopies(ArrayList<Integer> copies) {
//        this.copies = copies;
//    }

    /**
     *
     * @return
     */

    public String getArtists() {
        return artists;
    }

    /**
     *
     * @param authors
     */
    public void setArtists(String authors) {
        this.artists = authors;
    }

//    public void addCopy(int kopia){
//        this.copies.add(kopia);
//    }
//    
//    public void removeCopy(int kopia){
//        this.copies.remove(kopia);
//    }

    /**
     *
     * @return
     */
    
    public String getTitel() {
        return titel;
    }

    /**
     *
     * @param Titel
     */
    public void setTitel(String Titel) {
        this.titel = Titel;
    }

    /**
     *
     * @param instance
     * @return
     * @throws SQLException
     */
    public static Boolean checkInstance(Objekt instance) throws SQLException {
            return instance != null;
    }
    
}
