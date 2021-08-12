/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.biblioteket.Objects;

import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import org.biblioteket.Database.DBConnection;

/**
 *
 * @author Jenni
 */
public class Bok extends Objekt{
    
    private int ISBN;
    private ArrayList<String> authors; 
    private ArrayList<String> SearchWords;
    private ArrayList<Kopia> Kopior;

    /**
     *
     * @param objektID
     * @param title
     * @param type
     * @param ISBN
     * @param authors
     * @param sw
     * @throws SQLException
     */
    public Bok(int objektID, String title, Type type, int ISBN, 
            ArrayList<String> authors, ArrayList<String> sw) throws SQLException {
        super(objektID, title, type);
        this.ISBN = ISBN;
        this.authors = authors;
        this.SearchWords = sw;
    }

    /**
     *
     * @return
     */
    public int getISBN() {
        return ISBN;
    }

    /**
     *
     * @param ISBN
     */
    public void setISBN(int ISBN) {
        this.ISBN = ISBN;
    }

    /**
     *
     * @return
     */
    public ArrayList<String> getAuthors() {
        return authors;
    }

    /**
     *
     * @param authors
     */
    public void setAuthors(ArrayList<String> authors) {
        this.authors = authors;
    }
    
    /**
     *
     * @param author
     */
    public void addAuthor(String author){
        this.authors.add(author);
    }

    /**
     *
     * @return
     */
    public ArrayList<String> getSearchWordsAsList() {
        return SearchWords;
    }
    
    /**
     *
     * @return
     */
    public String getSearchWordsAsString(){
        return DBConnection.getInstance().getSearchWordsAsString(super.getObjektID());
    }

}
