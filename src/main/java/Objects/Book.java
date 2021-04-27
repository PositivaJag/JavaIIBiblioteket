/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objects;

/**
 *
 * @author Jenni
 */
public class Book extends Copy{
    
    private int ISBN;

    public Book(int ISBN, int streckkod, String loanKategori, String placement, 
            int ObjektID, String Titel) {
        super(streckkod, loanKategori, placement, ObjektID, Titel);
        this.ISBN = ISBN;
    }

    public int getISBN() {
        return ISBN;
    }

    public void setISBN(int ISBN) {
        this.ISBN = ISBN;
    }
    
    
    
}
