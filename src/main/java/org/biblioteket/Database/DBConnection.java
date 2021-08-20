package org.biblioteket.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.biblioteket.Controllers;
import org.biblioteket.Objects.Bok;
import org.biblioteket.Objects.Film;
import org.biblioteket.Objects.Kopia;
import org.biblioteket.Objects.Kopia.AccessKopia;
import org.biblioteket.Objects.Loan;
import org.biblioteket.Objects.Loan.Skuld;
import org.biblioteket.Objects.Objekt;
import org.biblioteket.Objects.Objekt.Type;
import org.biblioteket.Objects.Tidskrift;

/**
 *
 * @author Jenni
 */
public class DBConnection extends Controllers{

    //Database parameters
    private static final String dbUrl = "jdbc:mysql://localhost:3306/javaiibiblioteket";
    private static final String dbUserName = "root";
    private static final String dbPassword = "B0b1gny";
    private boolean connectedToDB = false;

    //Connection parameters
    private static DBConnection instance;
    private Connection connection;
    
    private final String dataFromDBError = "Något gick fel, kunde inte "
            + "hämta data från databasen.";
    private final String rollbackError = "Kunde inte uppdatera databasen, inga "
            + "data har sparats.";
    private final String rollbackOK = "\n Rollback OK.";
    private final String rollbackFail = "\n Rollback misslyckades.";
            
    

    /**
     *Enum for login
     */
    public enum LoginResult {
        LOGIN_OK,
        WRONG_PASSWORD,
        NO_SUCH_USER,
        LOGOUT
    }

    /**
     * Constructor that connects to DB
     * @param url
     * @param username
     * @param password
     */
    private DBConnection(String url, String username, String password) {
        try {
            // connect to database
            connection = DriverManager.getConnection(url, username, password);
            connection.setAutoCommit(false);
            // update database connection status
            connectedToDB = true;
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
            simpleErrorAlert("Något gick fel\nKunde inte kontakta databasen. ");
        }
        
    }


    /**
     * Singleton that gives all classes access to the same DB connection in order
     * to not overload the DB. 
     * @return
     */
    public static DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection(dbUrl, dbUserName, dbPassword);
            System.out.println("Databaskontakt skapad");
        }
        System.out.println("Databaskontakt: " + instance.toString());
        return instance;
    }

/**
 * 
 * @param pState
 * @return 
 */
    private ResultSet getQuery(PreparedStatement pState) {
        //Check if we have contact with database
        if (connectedToDB == true) {
            try {
                ResultSet resultSet = pState.executeQuery();
                return resultSet;
            } catch (SQLException e) {
                simpleErrorAlert("Något gick fel när frågan skulle ställas till "
                        + "databasen\n"+this.getClass().getName()+", getQuery");
            }
        } else {
            simpleErrorAlert("Ingen kontakt med databasen");
        }
        return null;
    }

    /**
     * Calls DB and returns a ResultSet. 
     * Takes in a SQL query (with one variable, objektID) and the objektID.
     * @param SQL
     * @param objektID
     * @return
     */
    public ResultSet getResultSetFromDB(String SQL, int objektID) {

        try {
            PreparedStatement pState =  connection.prepareStatement(SQL);
            pState.setInt(1, objektID);
            ResultSet resultSet = getQuery(pState);
            return resultSet;
       
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
            simpleErrorAlert(dataFromDBError);
        }
        return null;
    }

    /**
     * Calls DB and returns a ResultSet.Takes in a SQL query (with no variables)
     * @param SQL
     * @return
     */
    public ResultSet getResultSetFromDB(String SQL) {
        try {
            PreparedStatement pState =  connection.prepareStatement(SQL);
            ResultSet resultSet = getQuery(pState);
            return resultSet;

        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
            simpleErrorAlert(dataFromDBError);
        }
        return null;
    }

    /**
     * Calls DB and returns a list of Strings. 
     * Takes in a SQL query (with one variable, objektID) and the objektID.
     * @param SQL
     * @param objektID
     * @return 
     */
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
        simpleErrorAlert(dataFromDBError);
        }
        return null;
    }

    /**
     *
     * @param SQL
     * @return
     */
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
        simpleErrorAlert(dataFromDBError);
        }
        return null;
    }

    /**
     *Calls DB and returns a list of Integers.
     * @param SQL
     * @return
     */
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
            simpleErrorAlert(dataFromDBError);
        }
        return null;
    }

    /**
     * Checks if a user with this combination of email and password exists. 
     * @param email
     * @param pwordIn
     * @return
     */
    public LoginResult checkUserAndPassword(String email, String pwordIn) {
        try {
            LoginResult result;
            //Get resultset for user
            String SQL = "Select lösenord from person where eMail = ?";
            PreparedStatement pState =  connection.prepareStatement(SQL);
            pState.setString(1, email);
            ResultSet resultSet = getQuery(pState);

            //Checkif a user witht the mail and password exists. 
            if (!resultSet.next()) {
                result = LoginResult.NO_SUCH_USER;
            } else {
                if (resultSet.getString(1).equals(pwordIn)) {
                    result = LoginResult.LOGIN_OK;
                } else {
                    result = LoginResult.WRONG_PASSWORD;
                }
            }
            return result;
        } catch (SQLException e) {
             Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, e);
            simpleErrorAlert(dataFromDBError);
        }
        //System.out.println(99);  
        return null;
    }

    /**
     * Gets data for user from DB and returns it as a list of Strings. 
     * This method should be reworked. 
     * @param email
     * @return
     */
    public String[] getPersonDataAsList(String email) {

        String[] userData = new String[0];

        try {
            //Get data from DB
            String SQL = "Select * from person where eMail = ?";
            PreparedStatement pState =  connection.prepareStatement(SQL);
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
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, e);
            simpleErrorAlert(dataFromDBError);
        }
        return userData;
    }

    /**
     * Gets data for Loantagare from DB and returns it as a list of Strings. 
     * @param personID
     * @return
     */
    public String[] getLoantagareDataAsList(String personID) {

        String[] LoantagareData = new String[0];

        try {
            //Get loantagera data
            String SQL = "Select * from låntagare where personID = ?";
            PreparedStatement pState =  connection.prepareStatement(SQL);
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
             Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, e);
            simpleErrorAlert(dataFromDBError);
        }
        return LoantagareData;
    }

    /**
     * Returns true if the email belongs to a librarian.
     * @param email
     * @return
     */
    public boolean chechIfLibrarian(String email) {

        try {
            String SQL = "Select PersonTyp from person where eMail = ?";
            PreparedStatement pState =  connection.prepareStatement(SQL);
            pState.setString(1, email);
            ResultSet resultSet = getQuery(pState);
            resultSet.next();
            //System.out.println(resultSet.getString(1));
            return (resultSet.getString(1).equals("Bibliotekarie"));
        } catch (SQLException e) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, e);
            simpleErrorAlert(dataFromDBError);
            return false;
        }
    }

    public String getLoanCategory(String personID) {
        try {
            String SQL = "select låntagareKategori from låntagare where PersonID = ?;";
            PreparedStatement pState =  connection.prepareStatement(SQL);
            pState.setInt(1, Integer.parseInt(personID));
            ResultSet resultSet = getQuery(pState);
            resultSet.next();
            return resultSet.getString(1);

        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
            simpleErrorAlert(dataFromDBError);
        }
        return null;
    }

    /**
     * Returns the maximum number of loans Loantagare in a certain category can
     * have. 
     * @param Category
     * @return int
     */
    public int getMaxNoLoan(String Category) {
        try {
            String SQL = "select simultanaLån from låntagarekategori where LåntagareKategori = ?;";
            PreparedStatement pState =  connection.prepareStatement(SQL);
            pState.setString(1, Category);
            ResultSet resultSet = getQuery(pState);
            resultSet.next();
            return resultSet.getInt(1);

        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
            simpleErrorAlert(dataFromDBError);
        }
        return -1;
    }

    /**
     * Returns the title of a Objekt connected to a Kopia (streckkod).
     * @param streckkod
     * @return
     */
    public String getTitle(int streckkod) {
        try {
            String SQL = "select distinct(titel) from kopia k, objekt o where k.objektID = o.objektID and k.streckkod = ?;";
            PreparedStatement pState =  connection.prepareStatement(SQL);
            pState.setInt(1, streckkod);
            ResultSet resultSet = getQuery(pState);
            resultSet.next();
            return resultSet.getString(1);

        } catch (SQLException ex) {
             Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
            simpleErrorAlert(dataFromDBError);
        }
        return null;
    }

    /**
     * Return loanID for all active loand for a Loantagare. 
     * @param Loantagare
     * @return
     */
    public ArrayList<Integer> getLoanID(String Loantagare) {
        try {
            String SQL = "select lånID from lån where Låntagare = ? and DatumRetur is null;";
            PreparedStatement pState =  connection.prepareStatement(SQL);
            pState.setInt(1, Integer.parseInt(Loantagare));
            ResultSet resultSet = getQuery(pState);
            //resultSet.next();

            ArrayList<Integer> loans = new ArrayList<>();
            while (resultSet.next()) {
                loans.add(resultSet.getInt(1));
            }

            return loans;
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
            simpleErrorAlert(dataFromDBError);
        }
        return null;
    }

    /**
     *Get all Objekts form DB that are of a certain type (or all types).
     * @param typ
     * @return
     */
    public ArrayList<Objekt> getObjektsFromDB(String typ) {

        try {
            String SQL;
            if (typ.equals("Alla")) {
                SQL = "Select ObjektID, Titel, Typ from Objekt";
            } else {
                SQL = "Select ObjektID, Titel, Typ from Objekt "
                        + "Where Typ = '" + typ + "';";
            }
            System.out.println(SQL);
            PreparedStatement pState =  connection.prepareStatement(SQL);
            ResultSet resultSet = getQuery(pState);

            ArrayList<Objekt> result = new ArrayList<>();
            while (resultSet.next()) {
                int objektID = resultSet.getInt(1);
                String title = resultSet.getString(2);
                Type type = Type.valueOf(resultSet.getString(3));
                String creators;
                if (Type.Tidskrift != type){
                creators = getCreatorsAsString(objektID, type);
                }
                else{ creators = "";}
                    
                String sw = getSearchWordsAsString(objektID);

                result.add(new Objekt(objektID, title, type, creators, sw));
            }

            return result;
            
            
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
            simpleErrorAlert(dataFromDBError);
        }
return null;
    }

    /**
     * Returns a Film instance from DB.
     * @param objektID
     * @return
     */
    public Film getFilmFromDB(int objektID) {
        try {
            String SQL = "select ObjektID, Titel, Typ, FilmÅldersbegr, FilmProdLand from Objekt where typ = 'Film' and  ObjektID = ?;";
            PreparedStatement pState =  connection.prepareStatement(SQL);
            pState.setInt(1, objektID);
            ResultSet resultSet = getQuery(pState);

            resultSet.next();
            String title = resultSet.getString(2);
            ArrayList<String> sw = getSearchWordsAsList(objektID);
            String ageRating = resultSet.getString(4);
            String prodCountry = resultSet.getString(5);
            ArrayList<String> directors = getDirectorsAsList(objektID);
            ArrayList<String> actors = getActorsAsList(objektID);

            return new Film(objektID, title, Type.Film, sw, ageRating, prodCountry,
                    directors, actors);

        } catch (SQLException ex) {
             Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
            simpleErrorAlert(dataFromDBError);
        }
        return null;
    }

    /**
     * Returns a Bok instance from DB. 
     * @param objektID
     * @return
     */
    public Bok getBokFromDB(int objektID) {
        try {
            String SQL = "select titel, BokISBN from Objekt where ObjektID = ?;";
            PreparedStatement pState =  connection.prepareStatement(SQL);
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
        simpleErrorAlert(dataFromDBError);
        }
        return null;
    }

    /**
     * Returns a Tidskrift instance from DB.
     * @param objektID
     * @return
     */
    public Tidskrift getTidskriftFromDB(int objektID) {
        try {
            String SQL = "Select Titel, TidskriftDatum, Tidskriftnr from objekt where ObjektId = ?;";
            PreparedStatement pState =  connection.prepareStatement(SQL);
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
        simpleErrorAlert(dataFromDBError);
        }
        return null;
    }

    /**
     * Get an ArrayList with all Kopia that belongs to an Objekt. 
     * @param Objekt
     * @param type
     * @return
     */
    public ArrayList<Kopia> getObjektCopies(Objekt Objekt, Type type) {

        try {
            int objektID = Objekt.getObjektID();
            //Get objekt from DB
            String SQL = "Select streckkod, lånekategori, placering, objektID from Kopia where ObjektID = ?";
            ResultSet resultSet = getResultSetFromDB(SQL, objektID);

            //Check if there are any copies of the book. 
            if (resultSet.next() == false) {
                return null;
            }
            return getKopiorAsList(resultSet);
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        simpleErrorAlert(dataFromDBError);
        }
        return null;
    }
    
    /**
     * Takes a resultSet with copies and returns an ArrayList with Kopia
     * instances. 
     * @param resultSet
     * @return
     */
    public ArrayList<Kopia> getKopiorAsList(ResultSet resultSet) {
        ArrayList<Kopia> result = new ArrayList<>();
        try {
            do {
                int streckkod = resultSet.getInt(1);
                String loanKategori = resultSet.getString(2);
                String placement = resultSet.getString(3);
                int objektID = resultSet.getInt(4);
                AccessKopia access = AccessKopia.AVAILABLE;
                LocalDate latestReturnDate = null;
                
                if (checkCopyOnLoan(streckkod)){
                    access = AccessKopia.ON_LOAN;
                   latestReturnDate = getActiveLoan(streckkod).getLatestReturnDate();
              
                }

                result.add(new Kopia(streckkod, objektID, loanKategori,
                        placement, access, latestReturnDate));
            } while (resultSet.next());
            return result;

        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        simpleErrorAlert(dataFromDBError);
        }
        return null;
    }


    public ArrayList<String> getObjektTypes() {

        ArrayList<String> types = new ArrayList();

        try {
            String SQL = "select distinct typ from objekt;";
            PreparedStatement pState =  connection.prepareStatement(SQL);
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
        simpleErrorAlert(dataFromDBError);
        }
        return null;
    }

    /**
     * returns all search words associated with an Objekt as an ArrayList of 
     * Strings. 
     * @param objektID
     * @return
     */
    public ArrayList<String> getSearchWordsAsList(int objektID) {

        String SQL = "select Ämnesord from klassificering k, objektklass ok, objekt o where k.KlassificeringID = ok.KategoriID and ok.ObjektID = o.ObjektID and o.ObjektID =? ;";
        return getStringsAsList(SQL, objektID);
    }

    /**
     * returns all search words associated with an Objekt as one string of 
     * Strings. 
     * @param objektID
     * @return
     */
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
        simpleErrorAlert(dataFromDBError);
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
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        simpleErrorAlert(dataFromDBError);
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
/**
 * 
 * @param streckkod
 * @return 
 */
    public Boolean checkCopyOnLoan(int streckkod){
        try {
            //Select row where return date is null
            String SQL = "select lånID from Lån where streckkod = ? and DatumRetur is null;";
            ResultSet resultSet = getResultSetFromDB(SQL, streckkod);
            
            //If the ResultSet is empty, the Kopia is available.
            //Otherwise, it is on loan. 
            if (resultSet.next()){
                return true;
            }
            else{
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
            simpleErrorAlert(dataFromDBError);
        }
        return null;
    }
    
    /**
     * Returns an instance of Loan if there is an active loan. 
     * @param streckkod
     * @return
     */
    public Loan getActiveLoan(int streckkod) {

        String SQL = "select * from lån where lånID = (select max(lånID) from lån where streckkod = ?);";
        ResultSet resultSet = getResultSetFromDB(SQL, streckkod);
        
        ArrayList<Loan> loans =  getLoan(resultSet);
       
        return loans.get(0);

    }
    
    public ArrayList<Loan> getLateLoans(){
         String SQL = "select * from lån where DatumRetur is null and ReturSenast < now();";
        ResultSet resultSet = getResultSetFromDB(SQL);
        return getLoan(resultSet);
    }

    /**
     * turns a resultSet into an ArrayList of Loans. 
     * The resultset must contain Loans. 
     * @param resultSet
     * @return
     */
    public ArrayList<Loan> getLoan(ResultSet resultSet) {
        ArrayList<Loan> loans = new ArrayList();
        try {

            while (resultSet.next()) {
                int loantagareID = resultSet.getInt(6);
                int streckkod = resultSet.getInt(5);
                String title = getTitle(streckkod);
                LocalDate loanDate = resultSet.getDate(2).toLocalDate();
                LocalDate actualReturnDate = null;
                if (resultSet.getDate(4) != null) {
                    actualReturnDate = resultSet.getDate(4).toLocalDate();
                }
                LocalDate latestReturnDate = resultSet.getDate(3).toLocalDate();
                int loanID = resultSet.getInt(1);
                loans.add(new Loan(streckkod, loantagareID, title, loanDate,
                        latestReturnDate, actualReturnDate, loanID));
            }
            return loans;

        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        simpleErrorAlert(dataFromDBError);
        }
        return null;
    }

    /**
     * Return number of days (int) that it is allowed to loan a Kopia. 
     * @param streckkod
     * @return
     */
    public int getKopiaMaxLånetid(int streckkod) {
        try {
            String SQL = "select maxLånetid from kopia k, maxlånetid m where k.låneKategori = m.kategori and k.streckkod = ?;";
            PreparedStatement pState =  connection.prepareStatement(SQL);
            pState.setInt(1, streckkod);
            ResultSet resultSet = getQuery(pState);
            resultSet.next();
            return resultSet.getInt(1);

        } catch (SQLException ex) {           
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        simpleErrorAlert(dataFromDBError);
        }
        return -1;
    }

    /**
     * Check if the streckkod actually corresponds to a Kopia in the DB. 
     * @param streckkod
     * @return
     */
    public Boolean checkIfKopiaExists(int streckkod) {
        try {
            String SQL = "select * from kopia where streckkod = ?;";
            PreparedStatement pState =  connection.prepareStatement(SQL);
            pState.setInt(1, streckkod);
            ResultSet resultSet = getQuery(pState);

            //Check if row exists
            if (!resultSet.next()) {
                return false;
            } else {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        simpleErrorAlert(dataFromDBError);
        }
        return false;
    }

    /**
     * Insert a new Bok row to the DB. 
     * @param title
     * @param ISBN
     * @param authors
     * @param searchWords
     * @return
     */
    public Bok newBok(String title, int ISBN, ArrayList<String> authors, ArrayList<String> searchWords) {

        try {
            insertBok(title, ISBN);
            //Check that the book was added
            int objektID = getObjektIDFromISBN(ISBN);
            //Insert Autors
            insertBokAuthors(objektID, authors);
            //Insert SearchWords
            insertBokSearchWords(objektID, searchWords);
            connection.commit();

            return getBokFromDB(objektID);

        } catch (SQLException ex) {
            try {
                connection.rollback();
                Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
                simpleErrorAlert(rollbackError +rollbackOK);
            } catch (SQLException ex1) {
              
                Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex1);
            simpleErrorAlert(rollbackError +rollbackFail);
            }
        }
        return null;
    }

    /**
     * Insert new Kopias to DB. 
     * @param kopior
     * @return
     */
    public Boolean newKopior(ArrayList<Kopia> kopior) {

        try {
            for (int i = 0; i < kopior.size(); i++) {
                Kopia kopia = kopior.get(i);
                int streckkod = kopia.getStreckkod();
                int objektID = kopia.getObjektID();
                String kategori = kopia.getLoanKategori().split(",")[0];
                String placering = kopia.getPlacement();
                insertKopia(streckkod, objektID, kategori, placering);
            }
            connection.commit();
            return true;

        } catch (SQLException ex) {
            try {
                connection.rollback();
                Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
                simpleErrorAlert(rollbackError +rollbackOK);
            } catch (SQLException ex1) {
              
                Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex1);
            simpleErrorAlert(rollbackError +rollbackFail);
            
            }
        }
        return false;
    }

    /**
     * Insert new Loans to DB. 
     * @param loans
     * @param userID
     * @return
     */
    public Boolean newLoan(ArrayList<Loan> loans, int userID) {
        try {
            for (int i = 0; i < loans.size(); i++) {

                Loan loan = loans.get(i);
                LocalDate loanDate = loan.getLoanDate();
                LocalDate returnLatest = loan.getLatestReturnDate();
                int streckkod = loan.getStreckkod();
                int loantagareID = userID;
                insertLoan(loanDate, returnLatest, streckkod, loantagareID);
            }
            connection.commit();
            return true;
            
        } catch (SQLException ex) {
            try {
                connection.rollback();
                Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
                simpleErrorAlert(rollbackError +rollbackOK);
            } catch (SQLException ex1) {
              
                Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex1);
            simpleErrorAlert(rollbackError +rollbackFail);
            }
        }
        return false;

    }

    /**
     * Handles updating returnes Loans. 
     * @param loans
     * @return
     */
    public Boolean returnLoan(ArrayList<Loan> loans) {
        try {
            for (int i = 0; i < loans.size(); i++) {

                Loan loan = loans.get(i);
                Skuld skuld = Skuld.NONE;
                if (loan.getLatestReturnDate().isBefore(LocalDate.now())) {
                    skuld = Skuld.OBETALD;
                }
                updateReturnLoan(loan.getLoanID(), skuld.toString());
            }
            connection.commit();
            return true;
        } catch (SQLException ex) {
            try {
               connection.rollback();
                Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
                simpleErrorAlert(rollbackError +rollbackOK);
            } catch (SQLException ex1) {
                Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex1);
            simpleErrorAlert(rollbackError +rollbackFail);
            }
        }
        return false;
    }

    /**
     * Inserts new Loan to DB.
     * throws exception to the calling class to handle. 
     * @param loanDate
     * @param returnLatest
     * @param streckkod
     * @param loantagare
     * @throws SQLException 
     */
    private void insertLoan(LocalDate loanDate, LocalDate returnLatest,
            int streckkod, int loantagare) throws SQLException {

        String SQL = "INSERT INTO lån (DatumLån, ReturSenast, streckkod, Låntagare) "
                + "VALUES (?, ?, ?, ?);";
        PreparedStatement pState =  connection.prepareStatement(SQL);
        pState.setDate(1, java.sql.Date.valueOf(loanDate));
        pState.setDate(2, java.sql.Date.valueOf(returnLatest));
        pState.setInt(3, streckkod);
        pState.setInt(4, loantagare);
        pState.executeUpdate();
    }

    /**
     * Updates returned Loans in DB. 
     * throws exception to the calling class to handle. 
     * @param loanID
     * @param skuld
     * @throws SQLException 
     */
    private void updateReturnLoan(int loanID, String skuld) throws SQLException {

        String SQL = "UPDATE lån SET DatumRetur = ?, Skuld = ? WHERE `lånID` = ?;";
        PreparedStatement pState =  connection.prepareStatement(SQL);
        pState.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
        pState.setString(2, skuld);
        pState.setInt(3, loanID);
        pState.executeUpdate();
    }
    
/**
 * Updates Kopia of Bok Objekt in DB. 
 * @param streckkod
 * @param kategori
 * @param placering
 * @return 
 */
    public Boolean updateBokKopia(int streckkod, String kategori, String placering) {

        try {
            String SQL = "update kopia set Lånekategori = ?, placering = ? where streckkod = ?;";
            PreparedStatement pState =  connection.prepareStatement(SQL);
            pState.setString(1, kategori);
            pState.setString(2, placering);
            pState.setInt(3, streckkod);
            pState.executeUpdate();
            connection.commit();
            return true;

        } catch (SQLException ex) {
            try {
                connection.rollback();
                Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
                simpleErrorAlert(rollbackError +rollbackOK);
            } catch (SQLException ex1) {
              
                Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex1);
            simpleErrorAlert(rollbackError +rollbackFail);
            }
        }
        return false;
    }
    /**
     * Handles updating Bok objekts in the DB. 
     * @param objektID
     * @param title
     * @param ISBN
     * @param authors
     * @param searchWords
     * @return 
     */
    public Bok updateBok(int objektID, String title, int ISBN, 
            ArrayList<String> authors, ArrayList<String> searchWords){
        try {
            //Uppdatera Objekt
            updateBokObjekt(objektID, title, ISBN);
            //Delete Authors
            deleteBokAuthors(objektID, authors);
            //Add authors
            insertBokAuthors(objektID, authors);
            //Delete search words
            deleteBokSearchWords(objektID, searchWords);
            //add searchWords
            insertBokSearchWords(objektID, searchWords);
            connection.commit();
            return getBokFromDB(objektID);
            
        } catch (SQLException ex) {
            try {
               connection.rollback();
                Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
                simpleErrorAlert(rollbackError +rollbackOK);
            } catch (SQLException ex1) {
              
                Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex1);
            simpleErrorAlert(rollbackError +rollbackFail);
            }
        }
        return null;
    }
    
    /**
     * Updates Bok objekt in DB.
     * throws exception to the calling class to handle. 
     * @param objektID
     * @param title
     * @param ISBN
     * @return
     * @throws SQLException 
     */
    public Boolean updateBokObjekt(int objektID, String title, int ISBN) throws SQLException{
        
            String SQL = "UPDATE objekt SET Titel = ?, BokISBN = ? WHERE ObjektID = ?;";
            PreparedStatement pState =  connection.prepareStatement(SQL);
            pState.setString(1,title );
            pState.setInt(2, ISBN);
            pState.setInt(3, objektID);
            pState.executeUpdate();
            return true;
    } 
    
    public Boolean deleteKopia(int streckkod){
        try {
            String SQL = "DELETE from kopia WHERE streckkod = ?";
            PreparedStatement pState =  connection.prepareStatement(SQL);
            pState.setInt(1, streckkod);
            pState.executeUpdate();
            connection.commit();
            return true;
            
        } catch (SQLException ex) {
            try {
               connection.rollback();
                Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
                simpleErrorAlert(rollbackError +rollbackOK);
            } catch (SQLException ex1) {
              
                Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex1);
            simpleErrorAlert(rollbackError +rollbackFail);
            }
        }
        return false;
    }
    
    
    public Boolean deleteBokObjekt(int objektID) {
       
        try {
            String SQL = "DELETE from objekt WHERE objektID = ?;";
            PreparedStatement pState =  connection.prepareStatement(SQL);
            pState.setInt(1, objektID);
            pState.execute();
            connection.commit();
            return true;
        } catch (SQLException ex) {
            try {
               connection.rollback();
                Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
                simpleErrorAlert(rollbackError +rollbackOK);
            } catch (SQLException ex1) {
              
                Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex1);
            simpleErrorAlert(rollbackError +rollbackFail);
            }
        }
        return false;
    }

    /**
     * Isert new Kopia in DB.
     * throws exception to the calling class to handle. 
     * @param streckkod
     * @param objektID
     * @param kategori
     * @param placering
     * @throws SQLException 
     */
    private void insertKopia(int streckkod, int objektID, String kategori, String placering) throws SQLException {

        String SQL = "INSERT INTO kopia (streckkod, ObjektID, LåneKategori, Placering) VALUES (?, ?, ?, ?);";
        PreparedStatement pState =  connection.prepareStatement(SQL);
        pState.setInt(1, streckkod);
        pState.setInt(2, objektID);
        pState.setString(3, kategori);
        pState.setString(4, placering);
        pState.executeUpdate();
    }

    /**
     * INserts new Bok in DB. 
     * throws exception to the calling class to handle. 
     * @param title
     * @param ISBN
     * @throws SQLException 
     */
    private void insertBok(String title, int ISBN) throws SQLException {

        String SQLBok = "INSERT INTO objekt (Titel, Typ, BokISBN) VALUES (?,'Bok',?);";
        PreparedStatement pState =  connection.prepareStatement(SQLBok);
        pState.setString(1, title);
        pState.setInt(2, ISBN);
        pState.executeUpdate();

    }

    /**
     * Adds a new connection between an author and a Bok in DB. 
     * throws exception to the calling class to handle. 
     * @param authors
     * @param objektID
     * @throws SQLException
     */
    public void insertBokAuthors(int objektID, ArrayList<String> authors) throws SQLException {
        ArrayList<String> existing = getAuthorsAsList(objektID);

        for (int i = 0; i < authors.size(); i++) {
            //Only add author if it doesn´t already exist. 
            if (!existing.contains(authors.get(i))) {
                
                int authorID = getAuthorID(authors.get(i));
                String SQL = "INSERT INTO bokförfattare (FörfattareID, ObjektID)VALUES (?, ?);";
                PreparedStatement pState =  connection.prepareStatement(SQL);
                pState.setInt(1, authorID);
                pState.setInt(2, objektID);
                pState.executeUpdate();
            }
        }
    }
    /**
     * Deletes a connection between an author and a Bok in DB.
     * throws exception to the calling class to handle. 
     * @param objektID
     * @param authors
     * @throws SQLException 
     */
    public void deleteBokAuthors(int objektID, ArrayList<String> authors) throws SQLException{
        
            //Get existing authors
            ArrayList<String> existingAuthors = getAuthorsAsList(objektID);
            
        for (int i = 0; i < existingAuthors.size(); i++) {
            String existingAuthor = existingAuthors.get(i);
            //If the author isnt in list authors, it should be deleted. 
            if (!authors.contains(existingAuthor)) {
                String SQL = "DELETE FROM bokförfattare WHERE FörfattareID = ? and ObjektID = ?;";
                PreparedStatement pState = connection.prepareStatement(SQL);
                pState.setInt(1, getAuthorID(existingAuthor));
                pState.setInt(2, objektID);
                pState.executeUpdate();
            }
        }

    }
    
    /**
     * Deletes a connection between a search word and a Bok in DB.
     * throws exception to the calling class to handle. 
     * @param objektID
     * @param sw
     * @throws SQLException 
     */
    public void deleteBokSearchWords(int objektID, ArrayList<String> sw) throws SQLException {

        //Get existing search words
        ArrayList<String> existingSw = getSearchWordsAsList(objektID);

        for (int i = 0; i < existingSw.size(); i++) {
            String existing = existingSw.get(i);
            //If the search word isnt in list authors, it should be deleted. 
            if (!sw.contains(existing)) {
                String SQL = "DELETE FROM objektklass WHERE KategoriID = ? and ObjektID = ?;";
                PreparedStatement pState =  connection.prepareStatement(SQL);
                pState.setInt(1, getSearchWordID(existing));
                pState.setInt(2, objektID);
                pState.executeUpdate();
            }
        }

    }

    /**
     * Inserts a connection between a search word and a Bok in DB.
     * throws exception to the calling class to handle. 
     * @param searchWords
     * @param objektID
     * @throws SQLException
     */
    public void insertBokSearchWords(int objektID, ArrayList<String> searchWords) throws SQLException {

        ArrayList<String> existing = getSearchWordsAsList(objektID);
        for (int i = 0; i < searchWords.size(); i++) {
            //Only add search word if it doesn´t already exist. 
            if (!existing.contains(searchWords.get(i))) {

                int swID = getSearchWordID(searchWords.get(i));

                String SQL = "INSERT INTO objektklass (ObjektID, KategoriID)VALUES (?, ?);";
                PreparedStatement pState =  connection.prepareStatement(SQL);
                pState.setInt(1, objektID);
                pState.setInt(2, swID);
                pState.executeUpdate();

            }
        }
    }

    /**
     *Returns an ArrayList of Strings with all authors in the DB. 
     * @return
     */
    public ArrayList<String> getAllAuthors() {
        String SQL = "select concat(fNamn, ' ', eNamn) as authors from Författare;";
        return getStringsAsList(SQL);

    }

    /**
     * Returns the ID of an author from a String with first and last names. 
     * @param name
     * @return 
     */
    private int getAuthorID(String name) {
        try {
            String SQL = "select författareID from (select FörfattareID, concat(fNamn, ' ', eNamn) as namn from Författare) as T where T.namn = ?;";
            PreparedStatement pState =  connection.prepareStatement(SQL);
            pState.setString(1, name);
            ResultSet resultSet = getQuery(pState);
            resultSet.next();
            int authorID = resultSet.getInt(1);
            return authorID;

        } catch (SQLException ex) {
            simpleErrorAlert(dataFromDBError);
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public ArrayList<String> getAllSearchWords() {
        String SQL = "select Ämnesord as seachWords from klassificering;";
        return getStringsAsList(SQL);
    }

     /**
     * Returns the ID of a search word from a String. 
     * @param sw
     * @return 
     */
    private int getSearchWordID(String sw) {
        try {
            String SQL = "select KlassificeringID from klassificering where Ämnesord = ?;";
            PreparedStatement pState =  connection.prepareStatement(SQL);
            pState.setString(1, sw);
            ResultSet resultSet = getQuery(pState);
            resultSet.next();
            int swID = resultSet.getInt(1);
            return swID;
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
         simpleErrorAlert(dataFromDBError);
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
            PreparedStatement pState =  connection.prepareStatement(SQL);

            pState.setInt(1, ISBN);
            ResultSet resultSet = getQuery(pState);

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
         simpleErrorAlert(dataFromDBError);
        }
        return -1;
    }
}
