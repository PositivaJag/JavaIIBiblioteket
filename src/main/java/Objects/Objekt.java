/*
 */
package Objects;

import java.sql.SQLException;
import java.util.ArrayList;
import org.biblioteket.UseCase;



/**
 *
 * @author Jenni
 */
public class Objekt {
    private String objektID;
    private String titel;
    private String type;
//    private ArrayList<Integer> copies;
    private String authors;

    
    
     public Objekt(String ObjektID, String Titel, String type, String authors) {
//        this.copies = new ArrayList<>(5);
        this.objektID = ObjektID;
        this.titel = Titel;
        this.type = type;
        this.authors = authors;
        
    }


    public String getObjektID() {
        return objektID;
    }

    public void setObjektID(String objectID) {
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

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
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
    
}
