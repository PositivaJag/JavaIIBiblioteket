package org.biblioteket.Objects;

import java.util.ArrayList;

/**
 * Objekt of type Bok
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
     *Constructor
     * @param objektID
     * @param title
     * @param type
     * @param sw
     * @param ageRating
     * @param prodCountry
     * @param directors
     * @param actors
     */
    public Film(int objektID, String title, Type type, ArrayList<String> sw, 
            String ageRating, String prodCountry, ArrayList<String> directors, ArrayList<String> actors ){
        super(objektID, title, type);
        this.ageRating = ageRating;
        this.prodCountry = prodCountry;
        this.directors = directors;
        this.actors = actors;
        this.SearchWords = sw;
    }

    public String getAgeRating() {
        return ageRating;
    }

    public String getProdCountry() {
        return prodCountry;
    }

    public void setAgeRating(String ageRating) {
        this.ageRating = ageRating;
    }

    public void setProdCountry(String prodCountry) {
        this.prodCountry = prodCountry;
    }

    public ArrayList<String> getDirectors() {
        return directors;
    }

    public void setDirectors(ArrayList<String> directors) {
        this.directors = directors;
    }
    
    public void addDirector(String director){
        this.directors.add(director);
    }

    public ArrayList<String> getActors() {
        return actors;
    }

    public void setActors(ArrayList<String> actors) {
        this.actors = actors;
    }
    
    public void addActor(String actor){
        this.actors.add(actor);
    }

    public ArrayList<Kopia> getKopior() {
        return Kopior;
    }

    public void setKopior(ArrayList<Kopia> Kopior) {
        this.Kopior = Kopior;
    }
}
