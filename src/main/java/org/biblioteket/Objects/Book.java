/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.biblioteket.Objects;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Jenni
 */
public class Book extends Copy{
    
    private int ISBN;
    private ArrayList<String> authors; 

    /**
     *
     * @param ISBN
     * @param authors
     * @param streckkod
     * @param loanKategori
     * @param placement
     * @param ObjektID
     * @param Titel
     * @throws SQLException
     */
    public Book(int ISBN, ArrayList<String> authors, int streckkod, String loanKategori, String placement, 
            int ObjektID, String Titel) throws SQLException {
        super(streckkod, loanKategori, placement);
        this.ISBN = ISBN;
        this.authors = authors;
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
    
}
