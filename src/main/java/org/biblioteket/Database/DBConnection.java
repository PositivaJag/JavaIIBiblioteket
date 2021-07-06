/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.biblioteket.Database;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.biblioteket.Objects.Bok;
import org.biblioteket.Objects.Film;
import org.biblioteket.Objects.Kopia;
import org.biblioteket.Objects.Kopia.AccessKopia;
import org.biblioteket.Objects.Objekt;
import org.biblioteket.Objects.Objekt.Type;
import org.biblioteket.Objects.Tidskrift;

/**
 *
 * @author Jenni
 */
public class DBConnection {

    private static DBConnection instance;
    private Connection connection;
    private Statement statement;
    private PreparedStatement pState;
    private ResultSetMetaData metadata;
//    private ResultSet resultSet;

    //Connection parameters
    private static final String dbUrl = "jdbc:mysql://localhost:3306/javaiibiblioteket";
    private static final String dbUserName = "root";
    private static final String dbPassword = "B0b1gny";
    private boolean connectedToDB = false;

    public enum LoginResult {
        LOGIN_OK,
        WRONG_PASSWORD,
        NO_SUCH_USER,
        LOGOUT
    }

    //Constructor of connection
    private DBConnection(String url, String username, String password) throws SQLException {
        // connect to database
        connection = DriverManager.getConnection(url, username, password);

        // create Statement to query database                             
        statement = connection.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        // update database connection status
        connectedToDB = true;

        // set query and execute it
        //setQuery(query);
    }

    //Singleton implementation
    public static DBConnection getInstance() {
        if (instance == null) {
            try {
                instance = new DBConnection(dbUrl, dbUserName, dbPassword);
                System.out.println("Databaskontakt skapad");
            } catch (SQLException ex) {
                System.out.println("Ingen databaskontakt är skapad");
                Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("Databaskontakt: " + instance.toString());
        return instance;
    }

    public Statement getStatement() {
        return statement;
    }

    public boolean isConnectedToDB() {
        return connectedToDB;
    }

    public String[] getPersonDataAsList(String email) throws SQLException {

        String[] userData = new String[0];

        try {

            //Get data from DB
            String SQL = "Select * from person where eMail = ?";
            pState = connection.prepareStatement(SQL);
            pState.setString(1, email);
            ResultSet resultSet = getQuery(pState);

            resultSet.next();
            String id = Integer.toString(resultSet.getInt(1));
            String fName = resultSet.getString(2);
            String lName = resultSet.getString(3);
            String mail = resultSet.getString(4);
            String pword = resultSet.getString(5);
            String type = resultSet.getString(6);

            userData = new String[]{id, fName, lName, mail, pword, type};

            return userData;

        } catch (SQLException e) {
            printSQLExcept(e);
        }

        return userData;
    }

    public String[] getLoantagareDataAsList(String personID) {

        String[] LoantagareData = new String[0];

        try {
            //Get loantagera data
            String SQL = "Select * from låntagare where personID = ?";
            pState = connection.prepareStatement(SQL);
            pState.setString(1, personID);
            ResultSet resultSet = getQuery(pState);

            resultSet.next();
            String id = Integer.toString(resultSet.getInt(1));
            String telNr = Integer.toString(resultSet.getInt(2));
            String adress = resultSet.getString(3);
            String postnr = Integer.toString(resultSet.getInt(4));
            String loantagarKategori = resultSet.getString(5);

            LoantagareData = new String[]{id, telNr, adress, postnr, loantagarKategori};
            return LoantagareData;

        } catch (SQLException e) {
            printSQLExcept(e);
        }

        return LoantagareData;

    }

    public ArrayList<Objekt> getObjektsFromDB(String SQL) throws Exception {
        try {

            pState = connection.prepareStatement(SQL);
            ResultSet resultSet = getQuery(pState);

            ArrayList<Objekt> result = new ArrayList<>();
            while (resultSet.next()) {
                int objektID = resultSet.getInt(1);
                String title = resultSet.getString(2);
                Type type = Type.valueOf(resultSet.getString(3));
                String creators = getCreatorsAsString(objektID, type);
                String sw = getSearchWordsAsString(objektID);

                result.add(new Objekt(objektID, title, type, creators, sw));
            }

            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new Exception("Something went wroing in method getAllObjectData "
                + "in Class DBConnection");

    }

    public ArrayList<Kopia> getObjectCopies(Objekt Objekt, Type type) {

        try {
            int objektID = Objekt.getObjektID();
            //Get objekt from DB
            String SQL = "Select streckkod, lånekategori, placering from Kopia where ObjektID = ?";

            ResultSet resultSet = getResultSetFromDB(SQL, objektID);

            ArrayList<Kopia> result = new ArrayList<>();

            //Check if there are any copies of the book. 
            if (resultSet.next() == false) {
                return result;
            }

            do {
                int streckkod = resultSet.getInt(1);
                String loanKategori = resultSet.getString(2);
                String placement = resultSet.getString(3);
                AccessKopia access = AccessKopia.AVAILABLE;
                Date returnLatest = null;

                ResultSet loanResultSet = getKopiaLoanInformation(streckkod);
                if (loanResultSet.next()) {
                    access = getKopiaAccess(loanResultSet.getDate(4));
                }

                if (access == AccessKopia.ON_LOAN) {
                    returnLatest = loanResultSet.getDate(3);
                }

                result.add(new Kopia(streckkod, objektID, loanKategori,
                        placement, access, returnLatest));

            } while (resultSet.next());

            return result;
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
//    public Object getObjectSubclass(int objektID, String type) {
//        //Get Objekt subclass info
//        if (type.equalsIgnoreCase("Film")) {
//            return getFilmFromDB(objektID);
//        } //        else if (type.equalsIgnoreCase("Bok")){
//        //            return getBokFromDB(objektID);
//        //        }
//        //        else if (type.equalsIgnoreCase("Tidskrift")){
//        //           return getTidskriftFromDB(obejktID);
//        //        }
//        else {
//            return null;
//        }
//    }

//    public Object getObjectSubclass(int objektID, String type) {
//        //Get Objekt subclass info
//        if (type.equalsIgnoreCase("Film")) {
//            return getFilmFromDB(objektID);
//        } //        else if (type.equalsIgnoreCase("Bok")){
//        //            return getBokFromDB(objektID);
//        //        }
//        //        else if (type.equalsIgnoreCase("Tidskrift")){
//        //           return getTidskriftFromDB(obejktID);
//        //        }
//        else {
//            return null;
//        }
//    }
    public Film getFilmFromDB(int objektID) {
        try {
            String SQL = "select ObjektID, Titel, Typ, FilmÅldersbegr, FilmProdLand from Objekt where typ = 'Film' and  ObjektID = ?;";
            pState = connection.prepareStatement(SQL);
            pState.setInt(1, objektID);
            ResultSet resultSet = getQuery(pState);

            resultSet.next();
            String title = resultSet.getString(2);
            ArrayList<String> sw = getSearchWordsAsList(objektID);
            String ageRating = resultSet.getString(4);
            String prodCountry = resultSet.getString(5);
            ArrayList<String> directors = getDirectorsAsList(objektID);
            ArrayList<String> actors = getActorsAsList(objektID);

            System.out.println(title + " " + sw + " " + ageRating + " " + directors + " " + actors);

            return new Film(objektID, title, Type.Film, sw, ageRating, prodCountry,
                    directors, actors);

        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Bok getBokFromDB(int objektID) {
        try {
            String SQL = "select titel, BokISBN from Objekt where ObjektID = ?;";
            pState = connection.prepareStatement(SQL);
            pState.setInt(1, objektID);
            ResultSet resultSet = getQuery(pState);

            resultSet.next();
            String title = resultSet.getString(1);
            int ISBN = resultSet.getInt(2);
            ArrayList<String> authors = getAuthorsAsList(objektID);
            ArrayList<String> sw = getSearchWordsAsList(objektID);

            return new Bok(objektID, title, Type.Bok, ISBN, authors, sw);

        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Tidskrift getTidskriftFromDB(int objektID) {
        try {
            String SQL = "Select Titel, TidskriftDatum, Tidskriftnr from objekt where ObjektId = ?;";
            pState = connection.prepareStatement(SQL);
            pState.setInt(1, objektID);
            ResultSet resultSet = getQuery(pState);

            resultSet.next();
            String title = resultSet.getString(1);
            Date datum = resultSet.getDate(2);
            int nummer = resultSet.getInt(3);
            ArrayList<String> sw = getSearchWordsAsList(objektID);

            return new Tidskrift(objektID, title, Type.Tidskrift, datum, nummer, sw);

        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String getCreatorsAsString(int objektID, Type type) throws SQLException {
        String creators;
        String SQL;
        try {
            //Get objekt from DB
            if (type == (type.Bok)) {
                SQL = "select group_concat(Concat(f.fNamn, ' ', f.eNamn, '\\n')) as Skapare from författare f, bokförfattare b, objekt o where f.FörfattareID = b.FörfattareID and o.ObjektID = b.ObjektID and o.objektID = ? group by o.ObjektID;";
            } else if (type == (type.Film)) {
                SQL = "select group_concat(Concat(r.fNamn, ' ', r.eNamn, '\\n')) as Skapare from regisörAktör r, filmregisöraktör f, objekt o where r.RegisörAktörID = f.RegisörAktörID and f.ObjektID = o.ObjektID and o.ObjektID =  ? group by o.ObjektID;";
            } else {
                return "";
            }

            ResultSet resultSet = getResultSetFromDB(SQL, objektID);
            resultSet.next();
            creators = resultSet.getString(1);
//            System.out.println("Författare: " +authors);
            return creators;
        } catch (SQLException e) {
            printSQLExcept(e);
        }
        return null;
    }

    public ArrayList<String> getStringsAsList(String SQL, int objektID) {
        try {
            ResultSet resultSet = getResultSetFromDB(SQL, objektID);

            ArrayList<String> words = new ArrayList<>();
            while (resultSet.next()) {
                words.add(resultSet.getString(1));
            }
            System.out.println(words.toString());
            return words;
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public ArrayList<String> getStringsAsList(String SQL) {
        try {
            ResultSet resultSet = getResultSetFromDB(SQL);

            ArrayList<String> words = new ArrayList<>();
            while (resultSet.next()) {
                words.add(resultSet.getString(1));
            }
            System.out.println(words.toString());
            return words;
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    

    public ResultSet getResultSetFromDB(String SQL, int objektID) {

        try {

            pState = connection.prepareStatement(SQL);
            pState.setInt(1, objektID);
            ResultSet resultSet = getQuery(pState);
            return resultSet;

        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
        public ResultSet getResultSetFromDB(String SQL) {

        try {

            pState = connection.prepareStatement(SQL);
            ResultSet resultSet = getQuery(pState);
            return resultSet;

        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<String> getSearchWordsAsList(int objektID) {

        String SQL = "select Ämnesord from klassificering k, objektklass ok, objekt o where k.KlassificeringID = ok.KategoriID and ok.ObjektID = o.ObjektID and o.ObjektID =? ;";
        return getStringsAsList(SQL, objektID);
    }

    public String getSearchWordsAsString(int objektID) {
        try {
            String searchWords = "";
            String SQL = "select Ämnesord from klassificering k, objektklass ok, objekt o where k.KlassificeringID = ok.KategoriID and ok.ObjektID = o.ObjektID and o.ObjektID =? ;";
            ResultSet resultSet = getResultSetFromDB(SQL, objektID);

            int nr = 0;
            while (resultSet.next()) {
                if (nr == 3) {
                    searchWords += "\n";
                    nr = 0;
                } else {
                    nr++;
                }

                if (searchWords.equalsIgnoreCase("")) {
                    searchWords += resultSet.getString(1);
                } else {
                    searchWords += ", " + resultSet.getString(1);
                }
            }

            System.out.println(searchWords);
            return searchWords;
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<String> getDirectorsAsList(int objektID) {
        String SQL = "select concat(r.fNamn, ' ', r.eNamn)as Regissör from regisöraktör r, filmregisöraktör f where r.RegisörAktörID = f.RegisörAktörID and f.ObjektID = ? and f.typ = 'Reg';";
        return getStringsAsList(SQL, objektID);
    }

    public ArrayList<String> getActorsAsList(int objektID) {
        String SQL = "select concat(r.fNamn, ' ', r.eNamn)as Skådis from regisöraktör r, filmregisöraktör f where r.RegisörAktörID = f.RegisörAktörID and f.ObjektID = ? and f.typ = 'Akt';";
        return getStringsAsList(SQL, objektID);
    }

    public ArrayList<String> getAuthorsAsList(int objektID) {
        String SQL = "select concat(f.fNamn, ' ', f.eNamn) as Författare from författare f, bokförfattare b where f.FörfattareID = b.FörfattareID and b.ObjektID = ?;";
        return getStringsAsList(SQL, objektID);
    }

    private ResultSet getKopiaLoanInformation(int streckkod) {
        String access;
        String SQL = "select * from lån where Datumlån = (select max(DatumLån) from lån where streckkod = ?);";
        return getResultSetFromDB(SQL, streckkod);

    }

    public boolean chechIfLibrarian(String email) throws SQLException {

        try {
            String SQL = "Select PersonTyp from person where eMail = ?";
            pState = connection.prepareStatement(SQL);
            pState.setString(1, email);
            ResultSet resultSet = getQuery(pState);
            resultSet.next();
            //System.out.println(resultSet.getString(1));
            return (resultSet.getString(1).equals("Bibliotekarie"));
        } catch (SQLException e) {
            printSQLExcept(e);
            return false;
        }
    }

    private ResultSet getQuery(PreparedStatement pState) throws SQLException {
        //Check if we have contact with database
        if (connectedToDB == true) {
            try {
                ResultSet resultSet = pState.executeQuery();
                metadata = resultSet.getMetaData();
                return resultSet;
            } catch (SQLException e) {
                printSQLExcept(e);
            }
        } else {
            System.out.println("Ingen kontakt med databasen");
        }
        return null;
    }

    public LoginResult checkUserAndPassword(String email, String pwordIn) throws Exception {
        try {
            LoginResult result;

            String SQL = "Select lösenord from person where eMail = ?";
            pState = connection.prepareStatement(SQL);
            pState.setString(1, email);
            ResultSet resultSet = getQuery(pState);

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

    public void printSQLExcept(SQLException e) {
        System.out.println(e.getMessage());
    }

    public ArrayList<String> getObjektTypes() {

        ArrayList<String> types = new ArrayList();

        try {
            String SQL = "select distinct typ from objekt;";
            pState = connection.prepareStatement(SQL);
            ResultSet resultSet = getQuery(pState);

            if (!resultSet.next()) {
                return types;
            } else {
                do {
                    types.add(resultSet.getString(1));
                    System.out.println(resultSet.getString(1));
                } while (resultSet.next());

                return types;
            }

        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    private AccessKopia getKopiaAccess(Date returnDate) {

        if (returnDate == null) {
            return AccessKopia.ON_LOAN;
        } else {
            return AccessKopia.AVAILABLE;
        }
    }
    
    public ArrayList<String> getAllAuthors(){
        String SQL = "select concat(fNamn, ' ', eNamn) as authors from Författare;";
       return getStringsAsList(SQL);

    }
    
    public ArrayList<String> getAllSearchWords(){
        String SQL = "select Ämnesord as seachWords from klassificering;";
       return getStringsAsList(SQL);
    }
    
    public void newBok(String titel, int ISBN ){
        
        try {
            String SQL = "INSERT INTO objekt (Titel, Typ, BokISBN) VALUES (?,'Bok',?);";
            pState = connection.prepareStatement(SQL);

            pState.setString(1, titel);
            pState.setInt(2,ISBN);
            pState.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    

}
