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

/**
 * Handles all communication with the DB
 * @author Jenni
 */


public class DBConnection {
    private static  DBConnection instance;
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
    private DBConnection(String url, String username, String password){
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
     * @return instance of DBConnection
     */
    public static DBConnection getInstance(){
        if (instance == null){
            instance = new DBConnection(dbUrl, dbUserName, dbPassword);
        }
        return instance;
    }


    /**
     *
     * @return
     */
    public boolean isConnectedToDB() {
        return connectedToDB;
    }
    
    /**
     *
     * @param email
     * @return
     * @throws SQLException
     */
    public String[] getPersonData(String email) throws SQLException{
        
        String[] userData = new String[0];
        
        try{
            
            //Get data from DB
            String SQL = "Select * from person where eMail = ?";
            pState = connection.prepareStatement(SQL);
            pState.setString(1, email);
            getQuery(pState);
            
            resultSet.next();
            String id = Integer.toString(resultSet.getInt(1));
            String fName = resultSet.getString(2);
            String lName = resultSet.getString(3);
            String mail = resultSet.getString(4);
            String pword = resultSet.getString(5);
            String type = resultSet.getString(6);

            userData = new String[]{id, fName, lName, mail, pword, type};

            return userData;


        }
        
        catch (SQLException e){
            printSQLExcept(e);
        }
        
        
        
        return userData;
    }
    
    /**
     *
     * @param personID
     * @return
     */
    public String[] getLoantagareData(String personID) {
        
     String[] LoantagareData = new String[0];
     
        try{
            //Get loantagera data
            String SQL = "Select * from låntagare where personID = ?";
            pState = connection.prepareStatement(SQL);
            pState.setString(1, personID);
            getQuery(pState);
            
            resultSet.next();
            String id = Integer.toString(resultSet.getInt(1));
            String telNr = Integer.toString(resultSet.getInt(2));
            String adress = resultSet.getString(3);
            String postnr = Integer.toString(resultSet.getInt(4));
            String loantagarKategori = resultSet.getString(5);

            LoantagareData = new String[]{id, telNr, adress, postnr, loantagarKategori};

            return LoantagareData;
        }
                catch (SQLException e){
            printSQLExcept(e);
        }
        
        return LoantagareData;

    }
    
    /**
     *
     * @return
     * @throws Exception
     */
    public ResultSet getAllObjectData() throws Exception{
        try {
            //Get objekt from DB
            String SQL = "Select ObjektID, Titel, Typ from Objekt";
            pState = connection.prepareStatement(SQL);
            getQuery(pState);

            return resultSet;
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
        throw new Exception("Something went wroing in method getAllObjectData "
                + "in Class DBConnection");

    }
    
    /**
     *
     * @param objektID
     * @return
     * @throws SQLException
     */
    public String getArtistsAsString(int objektID) throws SQLException{
        String authors;
         try {
            //Get objekt from DB
            String SQL = "select group_concat(Concat(f.fNamn, ' ', f.eNamn, '\\n')) as Författare from författare f, bokförfattare b, objekt o where f.FörfattareID = b.FörfattareID and o.ObjektID = b.ObjektID and o.objektID = ? group by o.ObjektID;";
            pState = connection.prepareStatement(SQL);
            pState.setInt(1, objektID);
            getQuery(pState);
            
            resultSet.next();
            authors = resultSet.getString(1);
            System.out.println("Författare: " +authors);
            return authors;
         }
         catch (SQLException e){
             printSQLExcept(e);
         }
        return null;
    }

    /**
     *
     * @param objektID
     * @return
     * @throws Exception
     */
    public ResultSet getAllCopiesData(int objektID) throws Exception {
          try {
            //Get objekt from DB
            String SQL = "Select * from Kopia where ObjektID = ?";
            pState = connection.prepareStatement(SQL);
            pState.setInt(1, objektID);
            getQuery(pState);

            return resultSet;
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
        throw new Exception("Something went wrong in method DBConnection.getAllCopiesData");

        
    }
    
    /**
     *
     * @param email
     * @return
     * @throws SQLException
     */
    public boolean chechIfLibrarian(String email) throws SQLException{
        
        try{
        String SQL = "Select PersonTyp from person where eMail = ?";
        pState = connection.prepareStatement(SQL);
        pState.setString(1, email);
        getQuery(pState);
        resultSet.next();
        //System.out.println(resultSet.getString(1));
        return (resultSet.getString(1).equals("Bibliotekarie"));
        }
        
        catch (SQLException e){
            printSQLExcept(e);
            return false;
        }
    }
    
    
     
     
    private void getQuery(PreparedStatement pState) throws SQLException{
        //Check if we have contact with database
        if (connectedToDB == true){            
            try {
                resultSet = pState.executeQuery();
                metadata = resultSet.getMetaData();
            }
            catch(SQLException e){
                printSQLExcept(e);
            }
        }
        else
            System.out.println("Ingen kontakt med databasen");
    }
    
    /**
     *
     * @param email
     * @param pwordIn
     * @return
     * @throws Exception
     */
    public LoginResult checkUserPassword(String email, String pwordIn) throws Exception{
        try{
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
            if (!resultSet.next()){
                result = LoginResult.NO_SUCH_USER;
            }
            else{
                //System.out.println(resultSet.getString(1));
                if (resultSet.getString(1).equals(pwordIn))
                    result = LoginResult.LOGIN_OK;
                    
                else 
                    result = LoginResult.WRONG_PASSWORD;
            }
            
            //System.out.println(result);
            return result;
        }
            
        catch (SQLException e){
            printSQLExcept(e);
        }
      //System.out.println(99);  
      throw new Exception("Unknown error");
    }
    
    /**
     *
     * @param e
     */
    public void printSQLExcept(SQLException e){
        System.out.println(e.getMessage());
    }

}