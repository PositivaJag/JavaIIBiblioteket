package org.biblioteket.Objects;

import java.util.ArrayList;

/**
 * Handles books, extends Copy
 *
 * @author Jenni
 */
public class Book extends Copy {

    private int ISBN;
    private ArrayList<String> authors;

    /**
     * Constructor
     *
     * @param ISBN
     * @param authors
     * @param streckkod
     * @param loanKategori
     * @param placement
     * @param ObjektID
     * @param Titel
     */
    public Book(int ISBN, ArrayList<String> authors, int streckkod, 
            String loanKategori, String placement,
            String ObjektID, String Titel) {
        super(streckkod, ObjektID, loanKategori, placement);
        this.ISBN = ISBN;
        this.authors = authors;
    }

    /**
     * Getter
     *
     * @return ISBN
     */
    public int getISBN() {
        return ISBN;
    }

    /**
     * Setter
     *
     * @param ISBN
     */
    public void setISBN(int ISBN) {
        this.ISBN = ISBN;
    }

    /**
     * Getter
     *
     * @return authors
     */
    public ArrayList<String> getAuthors() {
        return authors;
    }

    /**
     * Setter
     *
     * @param authors
     */
    public void setAuthors(ArrayList<String> authors) {
        this.authors = authors;
    }

    /**
     * Adds author to list.
     *
     * @param author
     */
    public void addAuthor(String author) {
        this.authors.add(author);
    }

}
