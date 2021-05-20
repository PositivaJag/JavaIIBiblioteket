/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.biblioteket;

import org.biblioteket.Database.DBConnection;
import org.biblioteket.Database.DBConnection.LoginResult;
import org.biblioteket.Objects.Objekt;
import org.biblioteket.Persons.Loantagare;
import org.biblioteket.Persons.Person;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.biblioteket.Persons.Person.PersonTyp;

/**
 * Controlls the messages between GIU and database.
 *
 * @author Jenni
 */
public class MainController {

    private static MainController instance; //Single instance
    Person activeLibrarian = null;  //Person object if Librarian is logged in. 
    Loantagare activeUser = null;   //Loantagare object if Loantagare is logged in. 

    //Enum type of person that is logged in
    //Source of enum in Class Person
    PersonTyp personTyp = PersonTyp.NONE;

    /**
     * Singleton implementation. Check if there is an instance available. If
     * yes, return it. If no, create one and return it.
     *
     * @return MainController
     */
    public static MainController getInstance() {
        if (instance == null) {
            instance = new MainController();

        }
        return instance;
    }

    /**
     *
     * @param mail adress used as user name.
     * @param password
     * @return enum LoginResult
     */
    public LoginResult login(String mail, String password) {
        try {
            //Connect to db
            DBConnection connection = DBConnection.getInstance();
            //check loggin mail and password (returns enum)
            LoginResult checkCredentials = connection.checkUserPassword(mail, password);

            //create loggin object if all is ok
            if (checkCredentials == LoginResult.LOGIN_OK) {
                //If a librarian has logged in. 
                if (connection.chechIfLibrarian(mail)) {
                    activeLibrarian = new Person(mail);
                    personTyp = PersonTyp.BIBLIOTEKARIE;
                } //If a loantagare has logged in.
                else {
                    //gets the data needed to create a Loantagare. 
                    //Not a splendid implementation, could do with an overhaul. 
                    String[] personDB = connection.getPersonData(mail);
                    activeUser = new Loantagare(personDB[0], personDB[1], personDB[2], personDB[3], personDB[4], personDB[5]);
                    personTyp = PersonTyp.LOANTAGARE;
                }
            }
            return checkCredentials;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
        
        
        /**
         *
         * @return
         */
    public LoginResult logout() {
        personTyp = PersonTyp.NONE;
        activeUser = null;
        activeLibrarian = null;
        return LoginResult.LOGOUT;
    }

    /**
     *
     * @return @throws SQLException
     * @throws Exception
     */
    public ArrayList<Objekt> getAllObjekts() throws SQLException, Exception {

        try {
            //Connect to db
            DBConnection connection = DBConnection.getInstance();
            //Get Objekt data from DB
            ResultSet resultSet = connection.getAllObjectData();
            //Create objects,add to resultat
            ArrayList<Objekt> resultat = new ArrayList<>();
            while (resultSet.next()) {
                resultat.add(new Objekt(Integer.toString(resultSet.getInt(1)), resultSet.getString(2),
                        resultSet.getString(3), connection.getArtistsAsString(resultSet.getInt(1))));
            }
            return resultat;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new Exception("Something went wrong in UseCase.createAllObjects()");
    }

    /**
     *
     * @param objektID
     * @return
     * @throws SQLException
     * @throws Exception
     */
    public List<Objekt> createAllCopies(String objektID) throws SQLException, Exception {

        try {
            //Connect to db
            DBConnection connection = DBConnection.getInstance();
            //Get Objekt data from DB
            ResultSet resultSet = connection.getAllCopiesData(Integer.parseInt(objektID));
            //Create objects,add to resultat
            List<Objekt> resultat = new ArrayList<>();
            while (resultSet.next()) {
                resultat.add(new Objekt(Integer.toString(resultSet.getInt(1)),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        connection.getArtistsAsString(resultSet.getInt(1))));
            }
            return resultat;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new Exception("Something went wrong in UseCase.createAllObjects()");
    }

    /**
     *
     * @return
     */
    public PersonTyp getPersonTyp() {
        return personTyp;
    }

    /**
     *
     * @return
     */
    public Person getActiveLibrarian() {
        return activeLibrarian;
    }

    /**
     *
     * @return
     */
    public Loantagare getActiveUser() {
        return activeUser;
    }

}
