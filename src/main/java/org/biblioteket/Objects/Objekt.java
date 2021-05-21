package org.biblioteket.Objects;

import java.sql.SQLException;



/**
 * Handles Objekts. 
 * @author Jenni
 */
public class Objekt {
    private String objektID;
    private String titel;
    private String type;
//    private ArrayList<Integer> copies; Will be implemented later. 
    private String artists;

    /**
     * Constructor
     * @param ObjektID
     * @param Titel
     * @param type
     * @param authors
     */
    public Objekt(String ObjektID, String Titel, String type, String authors) {
        this.objektID = ObjektID;
        this.titel = Titel;
        this.type = type;
        this.artists = authors;        
    }

    /**
     * Getter
     * @return objektID
     */
    public String getObjektID() {
        return objektID;
    }

    /**
     * Setter
     * @param objectID
     */
    public void setObjektID(String objectID) {
        this.objektID = objectID;
    }

    /**
     *Getter
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     *Setter
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Getter
     * @return artists
     */
    public String getArtists() {
        return artists;
    }

    /**
     * Setter
     * @param artists
     */
    public void setArtists(String artists) {
        this.artists = artists;
    }

    /**
     * Getter
     * @return titel
     */
    
    public String getTitel() {
        return titel;
    }

    /**
     * Setter
     * @param titel
     */
    public void setTitel(String titel) {
        this.titel = titel;
    }

    /**
     * Not in use at the moment. 
     * @param instance
     * @return
     * @throws SQLException
     */
    public Boolean checkInstance(Objekt instance) throws SQLException {
            return instance != null;
    }
    
}
