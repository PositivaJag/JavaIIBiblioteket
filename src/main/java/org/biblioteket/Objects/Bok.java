package org.biblioteket.Objects;

import java.util.ArrayList;

/**
 * Objekt of type Bok. 
 * @author Jenni
 */
public class Bok extends Objekt{
    
    private int ISBN;
    private ArrayList<String> authors; 
    private ArrayList<String> SearchWords;
    private ArrayList<Kopia> Kopior;

    /**
     * Constructor 
     * @param objektID
     * @param title
     * @param type
     * @param ISBN
     * @param authors
     * @param sw
     */
    public Bok(int objektID, String title, Type type, int ISBN, 
            ArrayList<String> authors, ArrayList<String> sw) {
        super(objektID, title, type);
        this.ISBN = ISBN;
        this.authors = authors;
        this.SearchWords = sw;
    }


    public int getISBN() {
        return ISBN;
    }

    public void setISBN(int ISBN) {
        this.ISBN = ISBN;
    }
    
    public ArrayList<String> getAuthors() {
        return authors;
    }
    
    public ArrayList<String> getSearchWordsAsList() {
        return SearchWords;
    }
    
}
