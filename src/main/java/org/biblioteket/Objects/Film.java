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
public class Film extends Objekt{
    private String ageRating;
    private String prodCountry;
    private ArrayList<String> directors;
    private ArrayList<String> actors;
    private ArrayList<Kopia> Kopior;
    private ArrayList<String> SearchWords;

    /**
     *
     * @param objektID
     * @param title
     * @param type
     * @param sw
     * @param ageRating
     * @param prodCountry
     * @param directors
     * @param actors
     * @throws SQLException
     */
    public Film(int objektID, String title, Type type, ArrayList<String> sw, 
            String ageRating, String prodCountry, ArrayList<String> directors, ArrayList<String> actors ) throws SQLException {
        super(objektID, title, type);
        this.ageRating = ageRating;
        this.prodCountry = prodCountry;
        this.directors = directors;
        this.actors = actors;
        this.SearchWords = sw;
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

    /**
     *
     * @return
     */
    public ArrayList<Kopia> getKopior() {
        return Kopior;
    }

    /**
     *
     * @param Kopior
     */
    public void setKopior(ArrayList<Kopia> Kopior) {
        this.Kopior = Kopior;
    }
    
    /**
     *
     */
    public void printFilm(){
        System.out.println(super.getObjektID());
        System.out.println(super.getTitel());
        System.out.println(super.getType());
        System.out.println(this.ageRating);
        System.out.println(this.prodCountry);
        System.out.println(this.directors);
        System.out.println(this.actors);
        System.out.println(this.SearchWords);
        System.out.println(this.Kopior);
        System.out.println();
        
    }
    
    
    
    
}
