/*
 */
package org.biblioteket.Objects;

import java.sql.SQLException;
import java.util.ArrayList;
import OLD.MainControllerOLD;



/**
 *
 * @author Jenni
 */
public class Objekt {
    private int objektID;
    private String titel;
    private String type;
//    private ArrayList<Integer> copies;
    private String creators;
    private String searchWords;
    

    
    
     public Objekt(int ObjektID, String Titel, String type, String creators, String sw) {
//        this.copies = new ArrayList<>(5);
        this.objektID = ObjektID;
        this.titel = Titel;
        this.type = type;
        this.creators = creators;
        this.searchWords = sw;
        
    }
     
          public Objekt(int ObjektID, String Titel, String type) {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

//    public ArrayList<Integer> getCopies() {
//        return copies;
//    }

//    public void setCopies(ArrayList<Integer> copies) {
//        this.copies = copies;
//    }

    public String getCreators() {
        return creators;
    }

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
    
    public String getTitel() {
        return titel;
    }

    public void setTitel(String Titel) {
        this.titel = Titel;
    }

        public static Boolean checkInstance(Objekt instance) throws SQLException {
            return instance != null;
    }

    public String getSearchWords() {
        return searchWords;
    }
    
    
    
   
}
