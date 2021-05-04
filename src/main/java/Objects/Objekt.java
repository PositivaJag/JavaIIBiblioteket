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
    private int objectID;
    private String titel;
    private String type;
    private ArrayList<Integer> Copies;

    
    public Objekt(int ObjektID, String Titel, String type) {
        this.Copies = new ArrayList<Integer>(5);
        this.objectID = ObjektID;
        this.titel = Titel;
        this.type = type;
    }

    public void addCopy(int kopia){
        this.Copies.add(kopia);
    }
    
    public void removeCopy(int kopia){
        this.Copies.remove(kopia);
    }
    
    public String getTitel() {
        return titel;
    }

    public void setTitel(String Titel) {
        this.titel = Titel;
    }

    public int getObjektID() {
        return objectID;
    }
    
        public static Boolean checkInstance(Objekt instance) throws SQLException {
            return instance != null;
    }
    
}
