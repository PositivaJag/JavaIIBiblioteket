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

    //Database parameters
    private static String dbUrl = "jdbc:mysql://localhost:3306/javaiibiblioteket";
    private static String dbUserName = "root";
    private static String dbPassword = "B0b1gny";
    private boolean connectedToDB = false;

    //Connection parameters
    private static DBConnection instance;
    private final Connection connection;
    private Statement statement;
    private PreparedStatement pState;
    private ResultSetMetaData metadata;

    //Enum for login
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
        connection.setAutoCommit(false);
        // create Statement to query database                             
        statement = connection.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        // update database connection status
        connectedToDB = true;
    }


//General database SQL functions. 
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

    public boolean isConnectedToDB() {
        return connectedToDB;
    }

    private ResultSet getQuery(PreparedStatement pState) {
        //Check if we have contact with database
        if (connectedToDB == true) {
            try {
                ResultSet resultSet = pState.executeQuery();
                metadata = resultSet.getMetaData();
                return resultSet;
            } catch (SQLException e) {
                System.out.println("Något gick fel i getQuery i DBConnection.java");
            }
        } else {
            System.out.println("Ingen kontakt med databasen");
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

    public ArrayList<Integer> getIntsAsList(String SQL) {
        try {
            ResultSet resultSet = getResultSetFromDB(SQL);

            ArrayList<Integer> words = new ArrayList<>();
            while (resultSet.next()) {
                words.add(resultSet.getInt(1));
            }
            System.out.println(words.toString());
            return words;
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

//Users
    public LoginResult checkUserAndPassword(String email, String pwordIn) {
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
            System.out.println("Något gick fel i checkUserAnPassword i DBConnection.java");
        }
        //System.out.println(99);  
        return null;
    }
    
    public String[] getPersonDataAsList(String email) {

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
            System.out.println("Något gick fel i getPersonDataAsLlist i DBConnection.java");
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
            System.out.println("Något gick fel i GetLoantagareDataAsList i DBConnection.java");;
        }

        return LoantagareData;

    }

    public boolean chechIfLibrarian(String email) {

        try {
            String SQL = "Select PersonTyp from person where eMail = ?";
            pState = connection.prepareStatement(SQL);
            pState.setString(1, email);
            ResultSet resultSet = getQuery(pState);
            resultSet.next();
            //System.out.println(resultSet.getString(1));
            return (resultSet.getString(1).equals("Bibliotekarie"));
        } catch (SQLException e) {
            System.out.println("Något gick fel i checkIfLibrarian i DBConnection.java");
            return false;
        }
    }

    //Objekts and copies
    public ArrayList<Objekt> getObjektsFromDB(String SQL) {
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
        return null;

    }

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
    
    public String getCreatorsAsString(int objektID, Type type) {
        String creators;
        String SQL;
        try {
            if (null == type) {
                return "";
            } else //Get objekt from DB
            {
                switch (type) {
                    case Bok:
                        SQL = "select group_concat(Concat(f.fNamn, ' ', f.eNamn, '\\n')) as Skapare from författare f, bokförfattare b, objekt o where f.FörfattareID = b.FörfattareID and o.ObjektID = b.ObjektID and o.objektID = ? group by o.ObjektID;";
                        break;
                    case Film:
                        SQL = "select group_concat(Concat(r.fNamn, ' ', r.eNamn, '\\n')) as Skapare from regisörAktör r, filmregisöraktör f, objekt o where r.RegisörAktörID = f.RegisörAktörID and f.ObjektID = o.ObjektID and o.ObjektID =  ? group by o.ObjektID;";
                        break;
                    default:
                        return "";
                }
            }

            ResultSet resultSet = getResultSetFromDB(SQL, objektID);
            resultSet.next();
            creators = resultSet.getString(1);
            return creators;
        } catch (SQLException e) {
            System.out.println("Något gick fel i getCreatorsAsString i DBConnection.java");;
        }
        return null;
    }

    public ArrayList<String> getActorsAsList(int objektID) {
        String SQL = "select concat(r.fNamn, ' ', r.eNamn)as Skådis from regisöraktör r, filmregisöraktör f where r.RegisörAktörID = f.RegisörAktörID and f.ObjektID = ? and f.typ = 'Akt';";
        return getStringsAsList(SQL, objektID);
    }

    public ArrayList<String> getAuthorsAsList(int objektID) {
        String SQL = "select concat(f.fNamn, ' ', f.eNamn) as Författare from författare f, bokförfattare b where f.FörfattareID = b.FörfattareID and b.ObjektID = ?;";
        return getStringsAsList(SQL, objektID);
    }

    public ArrayList<String> getDirectorsAsList(int objektID) {
        String SQL = "select concat(r.fNamn, ' ', r.eNamn)as Regissör from regisöraktör r, filmregisöraktör f where r.RegisörAktörID = f.RegisörAktörID and f.ObjektID = ? and f.typ = 'Reg';";
        return getStringsAsList(SQL, objektID);
    }

    private ResultSet getKopiaLoanInformation(int streckkod) {
        String access;
        String SQL = "select * from lån where Datumlån = (select max(DatumLån) from lån where streckkod = ?);";
        return getResultSetFromDB(SQL, streckkod);

    }

    private AccessKopia getKopiaAccess(Date returnDate) {

        if (returnDate == null) {
            return AccessKopia.ON_LOAN;
        } else {
            return AccessKopia.AVAILABLE;
        }
    }

    //Create new
    public int newBok(String title, int ISBN, ArrayList<String> authors, ArrayList<String> searchWords) {

        try {
            insertBok(title, ISBN);
            //Check that the book was added
            int objektID = getObjektIDFromISBN(ISBN);
            //Insert Autors
            insertBokAuthors(authors, objektID);
            //Insert SearchWords
            insertBokSearchWords(searchWords, objektID);
            connection.commit();
            return objektID;

        } catch (SQLException ex) {
            try {
                connection.rollback();
                System.out.println("Misslyckades att spara objekt.\nRollback Ok.");
                Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex1) {
                System.out.println("Misslyckades att spara objekt.\nRollback ej ok.");
                Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex1);
            }

        }

        return -1;
    }
    
    public Boolean newKopior(ArrayList<Kopia> kopior){
        for(int i = 0; i<kopior.size(); i++){
            try {
                Kopia kopia = kopior.get(i);
                int streckkod = kopia.getStreckkod();
                int objektID = kopia.getObjektID();
                String kategori = kopia.getLoanKategori().split(" ")[0];
                String placering = kopia.getPlacement();
                
                insertKopia(streckkod, objektID, kategori, placering);
                connection.commit();
                return true;
                
            } catch (SQLException ex) {
                try {
                    connection.rollback();
                       System.out.println("Misslyckades att spara kopior.\nRollback Ok.");
                    Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex1) {
                    System.out.println("Misslyckades att spara objekt.\nRollback ej ok.");
                    Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
    }
        return false;
    }
    
    private void insertKopia(int streckkod, int objektID, String kategori, String placering) throws SQLException{
        
            String SQL = "INSERT INTO kopia (streckkod, ObjektID, LåneKategori, Placering) VALUES (?, ?, ?, ?);";
            pState = connection.prepareStatement(SQL);
            pState.setInt(1, streckkod);
            pState.setInt(2, objektID);
            pState.setString(3, kategori);
            pState.setString(4, placering);
            pState.executeUpdate();
    }
            
    private void insertBok(String title, int ISBN) throws SQLException {

        String SQLBok = "INSERT INTO objekt (Titel, Typ, BokISBN) VALUES (?,'Bok',?);";
        pState = connection.prepareStatement(SQLBok);
        pState.setString(1, title);
        pState.setInt(2, ISBN);
        pState.executeUpdate();

    }

    public void insertBokAuthors(ArrayList<String> authors, int objektID) throws SQLException {

        for (int i = 0; i < authors.size(); i++) {

            int authorID = getAuthorID(authors.get(i));

            String SQL = "INSERT INTO bokförfattare (FörfattareID, ObjektID)VALUES (?, ?);";
            pState = connection.prepareStatement(SQL);

            pState.setInt(1, authorID);
            pState.setInt(2, objektID);
            pState.executeUpdate();

        }
    }

    public void insertBokSearchWords(ArrayList<String> searchWords, int objektID) throws SQLException {

        for (int i = 0; i < searchWords.size(); i++) {

            int swID = getSearchWordID(searchWords.get(i));

            String SQL = "INSERT INTO objektklass (ObjektID, KategoriID)VALUES (?, ?);";
            pState = connection.prepareStatement(SQL);
            pState.setInt(1, objektID);
            pState.setInt(2, swID);
            pState.executeUpdate();

        }
    }

    public ArrayList<String> getAllAuthors() {
        String SQL = "select concat(fNamn, ' ', eNamn) as authors from Författare;";
        return getStringsAsList(SQL);

    }

    private int getAuthorID(String name) {
        try {
            String SQL = "select författareID from (select FörfattareID, concat(fNamn, ' ', eNamn) as namn from Författare) as T where T.namn = ?;";
            pState = connection.prepareStatement(SQL);
            pState.setString(1, name);
            ResultSet resultSet = getQuery(pState);
            resultSet.next();
            int authorID = resultSet.getInt(1);
            return authorID;

        } catch (SQLException ex) {
            System.out.println("Kunde inte hitta författare " + name + ".");
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
    
    public ArrayList<String> getAllKopiaCategories() {
        String SQL = "select concat(Kategori, ', ', MaxLånetid, ' dagar') as KopiaCategory from maxlånetid;";
        return getStringsAsList(SQL);
    }
    
    public ArrayList<String> getAllSearchWords() {
        String SQL = "select Ämnesord as seachWords from klassificering;";
        return getStringsAsList(SQL);
    }

    private int getSearchWordID(String sw) {
        try {
            String SQL = "select KlassificeringID from klassificering where Ämnesord = ?;";
            pState = connection.prepareStatement(SQL);
            pState.setString(1, sw);
            ResultSet resultSet = getQuery(pState);
            resultSet.next();
            int swID = resultSet.getInt(1);
            return swID;
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Kunde inte hitta sökord " + sw + ".");
        }

        return -1;
    }

    public ArrayList<Integer> getAllISBN() {
        String SQL = "select BokISBN from Objekt where typ = 'Bok';";
        return getIntsAsList(SQL);
       
    }
    
    public ArrayList<Integer> getAllSteckkod() {
        String SQL = "select streckkod from kopia;";
        return getIntsAsList(SQL);
    }

    private int getObjektIDFromISBN(int ISBN) {
        try {
            String SQL = "Select ObjektID from Objekt where BokISBN = ?;";
            pState = connection.prepareStatement(SQL);

            pState.setInt(1, ISBN);
            ResultSet resultSet = getQuery(pState);

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

}
