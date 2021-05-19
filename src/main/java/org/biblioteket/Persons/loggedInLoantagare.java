/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.biblioteket.Persons;

import org.biblioteket.Objects.Objekt;

/**
 *
 * @author Jenni
 */
public class loggedInLoantagare extends LoggedInUser{
    
    Loantagare loggedInUser = null;

    public loggedInLoantagare(Loantagare loantagare) {
        loggedInUser = loantagare;
        super.type = loantagare.getPersonTyp();
    }
    
    
}
