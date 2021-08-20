package org.biblioteket.Objects;

import java.sql.SQLException;
import java.util.ArrayList;
import org.biblioteket.Database.DBConnection;


/**
 * Handles Objekts, superclass to Bok, Film and Tidskrift. 
 * @author Jenni
 */
public class Objekt {

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
     * Main constructor
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
     * Constructor for creating sub-objects. 
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

    public int getObjektID() {
        return objektID;
    }

    public void setObjektID(int objectID) {
        this.objektID = objectID;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getCreators() {
        return creators;
    }

    public void setCreators(String authors) {
        this.creators = authors;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String Titel) {
        this.titel = Titel;
    }

    public String getStringSearchWords() {
        return searchWords;
    }
    
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
    
    public String printInfo(){
         return "Denna funktion är inte klar i Objekt.";
    }

    public ArrayList<Integer> getCopies() {
        return copies;
    }

    public void setCopies(ArrayList<Integer> copies) {
        this.copies = copies;
    }    
}
