/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.biblioteket.Objects;

import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author jenni
 */
public class Loan {
    
    public enum Skuld{BETALD, OBETALD, NONE}
    
    private int streckkod;
    private String titel;
    private LocalDate loanDate;
    private int loanDays;
    private int loantagareID;
    private LocalDate latestReturnDate;
    private LocalDate actualReturnDate;
    private int loanID;
    private Skuld skuld;
    
    //construnctor för aktiva lån/ kvitto
    public Loan( int streckkod, int loantagareID, String titel, 
            LocalDate loanDate, LocalDate latestReturnDate, 
            LocalDate actualReturnDate, int loanID){
        this.streckkod = streckkod;
        this.loantagareID = loantagareID;
        this.titel = titel;
        this.loanDate = loanDate;
        this.actualReturnDate = actualReturnDate;
        this.latestReturnDate = latestReturnDate;
        this.loanID = loanID;
        
    }
    
    //Contstruktor för lån. 
    public Loan(LocalDate loanDate, LocalDate latestReturnDate, int loanDays, 
            int streckkod, int loantagareID, String titel){
    this.loanDate = loanDate;
    this.loanDays = loanDays;
    this.streckkod = streckkod;
    this.titel = titel;
    this.loantagareID = loantagareID;
    this.latestReturnDate = latestReturnDate;
    }
    
    
    

    public int getLoanID() {
        return loanID;
    }

    public void setLoanID(int loanID) {
        this.loanID = loanID;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public int getLoanDays() {
        return loanDays;
    }

    public void setLoanDays(int loanDays) {
        this.loanDays = loanDays;
    }

    public LocalDate getLatestReturnDate() {
        return latestReturnDate;
    }

    public void setLatestReturnDate(LocalDate latestReturnDate) {
        this.latestReturnDate = latestReturnDate;
    }

    public LocalDate getActualReturnDate() {
        return actualReturnDate;
    }

    public void setActualReturnDate(LocalDate actualReturnDate) {
        this.actualReturnDate = actualReturnDate;
    }

    public int getStreckkod() {
        return streckkod;
    }

    public void setStreckkod(int streckkod) {
        this.streckkod = streckkod;
    }

    public int getLoantagareID() {
        return loantagareID;
    }

    public void setLoantagareID(int loantagareID) {
        this.loantagareID = loantagareID;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }
    
}
