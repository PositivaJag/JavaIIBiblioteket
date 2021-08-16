/*
 */
package org.biblioteket.Objects;

import java.sql.SQLException;
import java.util.ArrayList;
import OLD.MainControllerOLD;
import org.biblioteket.Database.DBConnection;



/**
 *
 * @author Jenni
 */
public class Objekt {
    
    /**
     *
     */
    public enum Type{

        Bok,
        Film,
        Tidskrift
    }
    
    private int objektID;
    private String titel;
    private  Type type;
    private ArrayList<Integer> copies;
    private String creators;
    private String searchWords;
    
    /**
     *
     * @param ObjektID
     * @param Titel
     * @param type
     * @param creators
     * @param sw
     */
    public Objekt(int ObjektID, String Titel, Type type, String creators, 
             String sw) {
//        this.copies = new ArrayList<>(5);
        this.objektID = ObjektID;
        this.titel = Titel;
        this.type = type;
        this.creators = creators;
        this.searchWords = sw;
        
    }
     
    /**
     *
     * @param ObjektID
     * @param Titel
     * @param type
     */
    public Objekt(int ObjektID, String Titel, Type type) {
//        this.copies = new ArrayList<>(5);
        this.objektID = ObjektID;
        this.titel = Titel;
        this.type = type;

        
    }

    /**
     *
     * @return
     */
    public int getObjektID() {
        return objektID;
    }

    /**
     *
     * @param objectID
     */
    public void setObjektID(int objectID) {
        this.objektID = objectID;
    }

    /**
     *
     * @return
     */
    public Type getType() {
        return type;
    }

    /**
     *
     * @param type
     */
    public void setType(Type type) {
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

    public String getCreators() {
        return creators;
    }

    /**
     *
     * @param authors
     */
    public void setCreators(String authors) {
        this.creators = authors;
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

    /**
     *
     * @return
     */
    public String getSearchWords() {
        return searchWords;
    }
    
    /**
     *
     * @return
     */
    public Objekt getSubclassObject(){
        DBConnection connection = DBConnection.getInstance();
        
        if (this.type == Type.Bok){
        return connection.getBokFromDB(this.objektID);
    }
        else if (this.type == Type.Film){
            return connection.getFilmFromDB(this.objektID);
        }
        else if (this.type == Type.Tidskrift){
            return connection.getTidskriftFromDB(this.objektID);
        }
        else{
            return null;
        }
    }
    
    /**
     *
     * @return
     */
    public String printInfo(){
         return "Denna funktion är inte klar i Objekt.";
    }

    /**
     *
     * @return
     */
    public ArrayList<Integer> getCopies() {
        return copies;
    }

    /**
     *
     * @param copies
     */
    public void setCopies(ArrayList<Integer> copies) {
        this.copies = copies;
    }
       
}
