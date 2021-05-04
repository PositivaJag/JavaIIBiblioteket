/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objects;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Jenni
 */
public class Book extends Copy{
    
    private int ISBN;
    private ArrayList<String> authors; 

    public Book(int ISBN, ArrayList<String> authors, int streckkod, String loanKategori, String placement, 
            int ObjektID, String Titel) throws SQLException {
        super(streckkod, loanKategori, placement);
        this.ISBN = ISBN;
        this.authors = authors;
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
