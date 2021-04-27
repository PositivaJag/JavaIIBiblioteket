/*
 */
package Objects;

/**
 *
 * @author Jenni
 */
public class Object {
    private int objectID;
    private String titel;

    
    public Object(int ObjektID, String Titel) {
        this.objectID = ObjektID;
        this.titel = Titel;
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
    
}
