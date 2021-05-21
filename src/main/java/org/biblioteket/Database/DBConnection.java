package org.biblioteket.Database;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.biblioteket.Persons.Person;
import org.biblioteket.Persons.Person.PersonTyp;

/**
 * Handles all communication with the DB
 *
 * @author Jenni
 */
public class DBConnection {

    private static DBConnection instance;
    private Connection connection;
    private Statement statement;
    private PreparedStatement pState;
    private ResultSetMetaData metadata;
    private ResultSet resultSet;

    //Connection parameters
    private static final String dbUrl = "jdbc:mysql://localhost:3306/javaiibiblioteket";
    private static final String dbUserName = "root";
    private static final String dbPassword = "B0b1gny";
    private boolean connectedToDB = false;

    /**
     * Enum for checking login status.
     */
    public enum LoginResult {
        LOGIN_OK,
        WRONG_PASSWORD,
        NO_SUCH_USER,
        LOGOUT,
        SOMETHING_WENT_WRONG
    }

    /**
     * Constructor
     *
     * @param url
     * @param username
     * @param password
     */
    private DBConnection(String url, String username, String password) {
        try {
            // connect to database
            connection = DriverManager.getConnection(url, username, password);
////            // create Statement to query database
////            statement = connection.createStatement(
////                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            //update database connection status
            connectedToDB = true;
//            
//            // set query and execute it
//            //setQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Singleton implementation. Check if there is an instance available. If
     * yes, return it. If no, create one and return it.
     *
     * @return instance of DBConnection
     */
    public static DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection(dbUrl, dbUserName, dbPassword);
        }
        return instance;
    }

    /**
     * Checks if there is a connection to the darabase Method might be
     * unnecessary.
     *
     * @return
     */
    public boolean isConnectedToDB() {
        return connectedToDB;
    }

    /**
     * Gets data about Person from the DB. Identifies user by email.
     *
     * @param email
     * @return String[] userData
     */
    public String[] getPersonData(String email) {

        String[] userData = new String[0];  //List to return. 

        try {
            //Get data from DB, write it to resultSet
            String SQL = "Select * from person where eMail = ?";
            pState = connection.prepareStatement(SQL);
            pState.setString(1, email);
            getQuery(pState);

            //set data from database to variables
            //This step is to make it easy for humans to understand what happens. 
            resultSet.next();
            String id = Integer.toString(resultSet.getInt(1));
            String fName = resultSet.getString(2);
            String lName = resultSet.getString(3);
            String mail = resultSet.getString(4);
            String pword = resultSet.getString(5);
            String type = resultSet.getString(6);

            //Set Person data to list of DB values. 
            userData = new String[]{id, fName, lName, mail, pword, type};

        } catch (SQLException e) {
            printSQLExcept(e);
        }
        return userData;
    }

    /**
     * Get data about Loantagare from the DB. Identifies Loantagare by personID.
     *
     * @param personID
     * @return String[] LoantagareData
     */
    public String[] getLoantagareData(String personID) {

        String[] LoantagareData = new String[0];   //List to return

        try {
            //Get data about Loantagare from DB
            String SQL = "Select * from låntagare where personID = ?";
            pState = connection.prepareStatement(SQL);
            pState.setString(1, personID);
            getQuery(pState);

            //set data from database to variables
            //This step is to make it easy for humans to understand what happens.
            resultSet.next();
            String id = Integer.toString(resultSet.getInt(1));
            String telNr = Integer.toString(resultSet.getInt(2));
            String adress = resultSet.getString(3);
            String postnr = Integer.toString(resultSet.getInt(4));
            String loantagarKategori = resultSet.getString(5);
            //Set Loantagare data to list of DB values. 
            LoantagareData = new String[]{id, telNr, adress, postnr, loantagarKategori};

        } catch (SQLException e) {
            printSQLExcept(e);
        }
        return LoantagareData;
    }

    /**
     * Get basic Objekt data from DB
     *
     * @return resultSet
     */
    public ResultSet getAllObjectData() {

        resultSet = null; //Empty resultset. 
        try {
            //Get objekt data from DB
            String SQL = "Select ObjektID, Titel, Typ from Objekt";
            pState = connection.prepareStatement(SQL);
            getQuery(pState);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    /**
     * Get authors or actors as string to show in the search table.
     *
     * @param objektID
     * @param typ (Film, Bok or Tidskrift)
     * @return Name of artists in one String. 
     */
    public String getArtistsAsString(int objektID, String typ) {
        String artists = null;
        String SQL;
        try {
            //Select diferent SQL query depending on Objekt type.
                switch (typ) {
                    case "Bok":
                        SQL = "select group_concat(Concat(f.fNamn, ' ', f.eNamn, '\\n')) as Artister from författare f, bokförfattare b, objekt o where f.FörfattareID = b.FörfattareID and o.ObjektID = b.ObjektID and o.objektID = ? group by o.ObjektID;";
                        break;
                    case "Film":
                        SQL = "select group_concat(Concat(r.fNamn, ' ', r.eNamn, '\\n')) as Artister from regisöraktör r, filmregisöraktör f, Objekt o where r.RegisörAktörID = f.RegisörAktörID and o.ObjektID = f.ObjektID and o.objektID = ? group by o.ObjektID;";
                        break;
                    default:
                        return " ";
                }
            //Get data from DB
            pState = connection.prepareStatement(SQL);
            pState.setInt(1, objektID);
            getQuery(pState);

            resultSet.next();
            artists = resultSet.getString(1);

        } catch (SQLException e) {
            printSQLExcept(e);
        }
        return artists;
    }

    /**
     * Get info about all copies for one Objekt from the DB. 
     * @param objektID
     * @return resultSet with the copies. 
     *
     */
    public ResultSet getAllCopiesData(int objektID) {
        resultSet = null;
        try {
            //Get objekt from DB
            String SQL = "Select * from Kopia where ObjektID = ?";
            pState = connection.prepareStatement(SQL);
            pState.setInt(1, objektID);
            getQuery(pState);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    /**
     * Check if the mail used to logg in belongs to a librarian (Person) or 
     * loantagare (Loantagare).
     * @param email
     * @return PersonTyp
     *
     */
    public PersonTyp chechIfLibrarian(String email) {
            PersonTyp personTyp = PersonTyp.NONE;
        try {
            //get PersonTyp from DB
            String SQL = "Select PersonTyp from person where eMail = ?";
            pState = connection.prepareStatement(SQL);
            pState.setString(1, email);
            getQuery(pState);
            
            //check if PersonTyp is Librarian
            resultSet.next();
            if (resultSet.getString(1).equals("Bibliotekarie"))
                personTyp = PersonTyp.BIBLIOTEKARIE;
            else if (resultSet.getString(1).equals("Låntagare"))
                personTyp = PersonTyp.LOANTAGARE;
        } catch (SQLException e) {
            printSQLExcept(e);
        }
        return personTyp;
    }

    private void getQuery(PreparedStatement pState) throws SQLException {
        //Check if we have contact with database
        if (connectedToDB == true) {
            try {
                resultSet = pState.executeQuery();
                metadata = resultSet.getMetaData();
            } catch (SQLException e) {
                printSQLExcept(e);
            }
        } else {
            System.out.println("Ingen kontakt med databasen");
        }
    }

    /**
     *
     * @param email
     * @param pwordIn
     * @return
     * @throws Exception
     */
    public LoginResult checkUserPassword(String email, String pwordIn) throws Exception {
        try {
            LoginResult result;

            String SQL = "Select lösenord from person where eMail = ?";
            pState = connection.prepareStatement(SQL);
            pState.setString(1, email);
            getQuery(pState);

            //check results
            //0 = No user found
            //1 = Password correct
            //2 = Password not correct
            //99 = Error
            //Check if row exists
            if (!resultSet.next()) {
                result = LoginResult.NO_SUCH_USER;
            } else {
                //System.out.println(resultSet.getString(1));
                if (resultSet.getString(1).equals(pwordIn)) {
                    result = LoginResult.LOGIN_OK;
                } else {
                    result = LoginResult.WRONG_PASSWORD;
                }
            }

            //System.out.println(result);
            return result;
        } catch (SQLException e) {
            printSQLExcept(e);
        }
        //System.out.println(99);  
        throw new Exception("Unknown error");
    }

    /**
     *
     * @param e
     */
    public void printSQLExcept(SQLException e) {
        System.out.println(e.getMessage());
    }

}
