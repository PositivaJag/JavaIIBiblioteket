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
import org.biblioteket.Objects.Copy;
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
    PersonTyp activeUserType = PersonTyp.NONE;

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
     * Checks mail and password with DB. If ok, creates an active user object
     * (Person or Loantagare) and sets personTyp to BIBLIOTEKARIE or LOANTAGARE.
     *
     * @param mail adress used as user name.
     * @param password
     * @return enum LoginResult
     */
    public LoginResult login(String mail, String password) {
        try {
            //Connect to DB
            DBConnection connection = DBConnection.getInstance();
            //check loggin credentias with DB, mail and password (returns enum)
            LoginResult checkCredentials = connection.checkUserAndPassword(mail, password);

            //create loggin object if all is ok
            if (checkCredentials == LoginResult.LOGIN_OK) {
                //If a librarian has logged in. 
                if (connection.chechIfLibrarian(mail) == PersonTyp.BIBLIOTEKARIE) {
                    activeLibrarian = new Person(mail);
                    activeUserType = PersonTyp.BIBLIOTEKARIE;
                } //If a loantagare has logged in.
                else if (connection.chechIfLibrarian(mail) == PersonTyp.LOANTAGARE){
                    //gets the data needed to create a Loantagare. 
                    //Not a splendid implementation, could do with an overhaul. 
                    String[] personDB = connection.getPersonData(mail);
                    activeUser = new Loantagare(personDB[0], personDB[1], personDB[2], personDB[3], personDB[4], PersonTyp.LOANTAGARE);
                    activeUserType = PersonTyp.LOANTAGARE;
                }
            }
            return checkCredentials;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return LoginResult.SOMETHING_WENT_WRONG;
    }

    /**
     * Makes sure that no one is logged in by settin activeUserType to NONE and
     * activeUser/activeLibrarian to null.
     *
     * @return enum LoginResult.LOGOUT
     */
    public LoginResult logout() {
        activeUserType = PersonTyp.NONE;
        activeUser = null;
        activeLibrarian = null;
        return LoginResult.LOGOUT;
    }

    /**
     * Fetches all objects from DB, creates an ArrayList with Objekts and
     * returns it.
     *
     * @return ArrayList
     */
    public ArrayList<Objekt> getAllObjekts() {
        ArrayList<Objekt> resultat = new ArrayList<>();
        try {
            //Connect to db
            DBConnection connection = DBConnection.getInstance();
            //Get Objekt data from DB
            ResultSet resultSet = connection.getAllObjectData();
            //Create objects,add to ArrayList resultat
            while (resultSet.next()) {
                resultat.add(new Objekt(Integer.toString(resultSet.getInt(1)), resultSet.getString(2),
                        resultSet.getString(3), connection.getArtistsAsString(resultSet.getInt(1), resultSet.getString(3))));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultat;
    }

    /**
     * Creates all copies belonging to a certain Objekt. Not in use yet.
     *
     * @param objektID
     * @return List of Copy objects.
     */
    public List<Copy> createAllCopies(String objektID) {
        List<Copy> resultat = new ArrayList<>();
        try {
            //Connect to db
            DBConnection connection = DBConnection.getInstance();
            //Get Objekt data from DB
            ResultSet resultSet = connection.getAllCopiesData(Integer.parseInt(objektID));
            //Create objects,add to resultat
            while (resultSet.next()) {
                resultat.add(new Copy(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultat;
    }

    /**
     * Getter for activeUserType
     * @return PersonTyp
     */
    public PersonTyp getActiveUserType() {
        return activeUserType;
    }

    /**
     * Getter for active Librarian.
     * @return Person Objekt
     */
    public Person getActiveLibrarian() {
        return activeLibrarian;
    }

    /**
     * Getter for active Loantagare
     * @return Loantagare object
     */
    public Loantagare getActiveUser() {
        return activeUser;
    }

}
