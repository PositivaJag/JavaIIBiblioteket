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
public class Book extends Objekt{
    
    private int ISBN;
    private ArrayList<String> authors; 
    private int streckkod;
    private String loandKategori;
    private String placement;

    public Book(int objektID, String title, String type, String creators, String sw, int ISBN, ArrayList<String> authors, 
            int streckkod, String loanKategori, String placement) throws SQLException {
        super(objektID, title, type, creators, sw);
        this.ISBN = ISBN;
        this.authors = authors;
        this.streckkod = streckkod;
        this.loandKategori = loanKategori;
        this.placement = placement;
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

    public int getStreckkod() {
        return streckkod;
    }

    public void setStreckkod(int streckkod) {
        this.streckkod = streckkod;
    }

    public String getLoandKategori() {
        return loandKategori;
    }

    public void setLoandKategori(String loandKategori) {
        this.loandKategori = loandKategori;
    }

    public String getPlacement() {
        return placement;
    }

    public void setPlacement(String placement) {
        this.placement = placement;
    }
    
}
