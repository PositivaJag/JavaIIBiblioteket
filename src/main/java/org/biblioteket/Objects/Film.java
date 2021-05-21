package org.biblioteket.Objects;

import java.util.ArrayList;

/**
 * Handles films and movies, extends Copy.
 *
 * @author Jenni
 */
public class Film extends Copy {

    private AgeRating ageRating;
    private String prodCountry;
    private ArrayList<String> directors;
    private ArrayList<String> actors;

    private enum AgeRating {
        BARNTILLÅTEN,
        FRÅN_7_ÅR,
        FRÅN_15_ÅR
    }

    /**
     * Constructor
     *
     * @param ageRating
     * @param prodCountry
     * @param streckkod
     * @param loanKategori
     * @param placement
     * @param objektID
     * @param Titel
     * @param directors
     * @param actors
     */
    public Film(String ageRating, String prodCountry, int streckkod,
            String loanKategori, String placement, String objektID, String Titel,
            ArrayList<String> directors, ArrayList<String> actors) {

        //Set superclass values
        super(streckkod, objektID, loanKategori, placement);

        //Set ageRating enum value
        setAngeRatingFromString(ageRating);
        this.prodCountry = prodCountry;
        this.directors = directors;
        this.actors = actors;
    }

    /**
     * Getter
     *
     * @return ageRating
     */
    public AgeRating getAgeRating() {
        return ageRating;
    }

    /**
     * Getter
     *
     * @return prodCountry
     */
    public String getProdCountry() {
        return prodCountry;
    }

    /**
     * Setter
     *
     * @param ageRating
     */
    public void setAgeRatingfromEnum(AgeRating ageRating) {
        this.ageRating = ageRating;
    }

    /**
     * Setter for when the input is a string.
     *
     * @param ageRating
     */
    public final void setAngeRatingFromString(String ageRating) {
        if (ageRating.equalsIgnoreCase("Från 7 år")) {
            this.ageRating = AgeRating.FRÅN_7_ÅR;
        } else if (ageRating.equalsIgnoreCase("Barntillåten")) {
            this.ageRating = AgeRating.BARNTILLÅTEN;
        } else if (ageRating.equalsIgnoreCase("Från 15 år")) {
            this.ageRating = AgeRating.FRÅN_15_ÅR;
        }
    }

    /**
     * Setter
     *
     * @param prodCountry
     */
    public void setProdCountry(String prodCountry) {
        this.prodCountry = prodCountry;
    }

    /**
     * Getter
     *
     * @return directors
     */
    public ArrayList<String> getDirectors() {
        return directors;
    }

    /**
     * Setter for the whole list of directors.
     *
     * @param directors
     */
    public void setDirectors(ArrayList<String> directors) {
        this.directors = directors;
    }

    /**
     * Adds a director to the list of directors.
     *
     * @param director
     */
    public void addDirector(String director) {
        this.directors.add(director);
    }

    /**
     * Getter
     *
     * @return actors
     */
    public ArrayList<String> getActors() {
        return actors;
    }

    /**
     * Setter for the whole list of actors.
     *
     * @param actors
     */
    public void setActors(ArrayList<String> actors) {
        this.actors = actors;
    }

    /**
     * Adds an actor to the list of directors.
     *
     * @param actor
     */
    public void addActor(String actor) {
        this.actors.add(actor);
    }
}
