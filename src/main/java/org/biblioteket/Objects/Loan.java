/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.biblioteket.Objects;

import java.time.LocalDate;

/**
 *
 * @author jenni
 */
public class Loan {
    
    /**
     *
     */
    public enum Skuld{ 

        /**
         *
         */
        BETALD, 

        /**
         *
         */
        OBETALD, 

        /**
         *
         */
        NONE}
    
    private int streckkod;
    private String titel;
    private LocalDate loanDate;
    private int loanDays;
    private LocalDate latestReturnDate;
    private LocalDate actualReturnDate;
    private int loantagareID;
    private int loanID;
    private Skuld skuld;
    
    
    
    //construnctor för aktiva lån/ kvitto samt lista förseningar

    /**
     *
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
    
    //Contstruktor för lån. 

    /**
     *
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
    
    /**
     *
     * @return
     */
    public int getLoanID() {
        return loanID;
    }

    /**
     *
     * @param loanID
     */
    public void setLoanID(int loanID) {
        this.loanID = loanID;
    }

    /**
     *
     * @return
     */
    public LocalDate getLoanDate() {
        return loanDate;
    }

    /**
     *
     * @param loanDate
     */
    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    /**
     *
     * @return
     */
    public int getLoanDays() {
        return loanDays;
    }

    /**
     *
     * @param loanDays
     */
    public void setLoanDays(int loanDays) {
        this.loanDays = loanDays;
    }

    /**
     *
     * @return
     */
    public LocalDate getLatestReturnDate() {
        return latestReturnDate;
    }

    /**
     *
     * @param latestReturnDate
     */
    public void setLatestReturnDate(LocalDate latestReturnDate) {
        this.latestReturnDate = latestReturnDate;
    }

    /**
     *
     * @return
     */
    public LocalDate getActualReturnDate() {
        return actualReturnDate;
    }

    /**
     *
     * @param actualReturnDate
     */
    public void setActualReturnDate(LocalDate actualReturnDate) {
        this.actualReturnDate = actualReturnDate;
    }

    /**
     *
     * @return
     */
    public int getStreckkod() {
        return streckkod;
    }

    /**
     *
     * @param streckkod
     */
    public void setStreckkod(int streckkod) {
        this.streckkod = streckkod;
    }

    /**
     *
     * @return
     */
    public int getLoantagareID() {
        return loantagareID;
    }

    /**
     *
     * @param loantagareID
     */
    public void setLoantagareID(int loantagareID) {
        this.loantagareID = loantagareID;
    }

    /**
     *
     * @return
     */
    public String getTitel() {
        return titel;
    }

    /**
     *
     * @param titel
     */
    public void setTitel(String titel) {
        this.titel = titel;
    }
    
}
