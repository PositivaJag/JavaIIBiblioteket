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
public class Film extends Copy{
    private String ageRating;
    private String prodCountry;
    private ArrayList<String> directors;
    private ArrayList<String> actors;

    /**
     *
     * @param ageRating
     * @param prodCountry
     * @param streckkod
     * @param loanKategori
     * @param placement
     * @param ObjektID
     * @param Titel
     * @param directors
     * @param actors
     * @throws SQLException
     */
    public Film(String ageRating, String prodCountry, int streckkod, 
            String loanKategori, String placement, String objektID, String Titel, ArrayList<String> directors, ArrayList<String> actors) throws SQLException {
        super(streckkod, objektID, loanKategori, placement);
        this.ageRating = ageRating;
        this.prodCountry = prodCountry;
        this.directors = directors;
        this.actors = actors;
    }

    /**
     *
     * @return
     */
    public String getAgeRating() {
        return ageRating;
    }

    /**
     *
     * @return
     */
    public String getProdCountry() {
        return prodCountry;
    }

    /**
     *
     * @param ageRating
     */
    public void setAgeRating(String ageRating) {
        this.ageRating = ageRating;
    }

    /**
     *
     * @param prodCountry
     */
    public void setProdCountry(String prodCountry) {
        this.prodCountry = prodCountry;
    }

    /**
     *
     * @return
     */
    public ArrayList<String> getDirectors() {
        return directors;
    }

    /**
     *
     * @param directors
     */
    public void setDirectors(ArrayList<String> directors) {
        this.directors = directors;
    }
    
    /**
     *
     * @param director
     */
    public void addDirector(String director){
        this.directors.add(director);
    }

    /**
     *
     * @return
     */
    public ArrayList<String> getActors() {
        return actors;
    }

    /**
     *
     * @param actors
     */
    public void setActors(ArrayList<String> actors) {
        this.actors = actors;
    }
    
    /**
     *
     * @param actor
     */
    public void addActor(String actor){
        this.actors.add(actor);
    }
    
    
    
    
    
}
