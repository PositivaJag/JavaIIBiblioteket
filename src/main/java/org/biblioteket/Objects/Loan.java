package org.biblioteket.Objects;

import java.time.LocalDate;

/**
 * Handles loan of Kopia by Loantagare. 
 * @author jenni
 */
public class Loan {
    
    public enum Skuld{ 
        BETALD, 
        OBETALD, 
        NONE}
    
    private int streckkod;
    private String titel;
    private LocalDate loanDate;
    private int loanDays;
    private LocalDate latestReturnDate;
    private LocalDate actualReturnDate;
    private int loantagareID;
    private int loanID;
    

    /**
     * Constructor 1.
     * @param streckkod
     * @param loantagareID
     * @param titel
     * @param loanDate
     * @param latestReturnDate
     * @param actualReturnDate
     * @param loanID
     */
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
    
    /**
     * Constructor for Loans. 
     * @param loanDate
     * @param latestReturnDate
     * @param loanDays
     * @param streckkod
     * @param loantagareID
     * @param titel
     */
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
