/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PublicPersons;

/**
 *
 * @author Jenni
 */
public class Bibliotekarie extends Person{
    
    int anstNummer;

    public Bibliotekarie(int personID, String fName, String lName, String email, String password, int anstNummer) {
        super(personID, fName, lName, email, password);
        this.anstNummer = anstNummer;
    }

    public int getAnstNummer() {
        return anstNummer;
    }
    
    
    
    
}
