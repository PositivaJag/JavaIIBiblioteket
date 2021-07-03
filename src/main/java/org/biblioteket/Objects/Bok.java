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
public class Bok extends Objekt{
    
    private int ISBN;
    private ArrayList<String> authors; 
    private ArrayList<String> SearchWords;
    private ArrayList<Kopia> Kopior;


    public Bok(int objektID, String title, Type type, int ISBN, 
            ArrayList<String> authors, ArrayList<String> sw) throws SQLException {
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

    public void setAuthors(ArrayList<String> authors) {
        this.authors = authors;
    }
    
    public void addAuthor(String author){
        this.authors.add(author);
    }

    
}
