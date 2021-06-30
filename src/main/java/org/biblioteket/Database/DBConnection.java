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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.biblioteket.Objects.Objekt;

/**
 *
 * @author Jenni
 */


public class DBConnection {
    private static  DBConnection instance;
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
    public static DBConnection getInstance(){
        if (instance == null){
            try {
                instance = new DBConnection(dbUrl, dbUserName, dbPassword);
            } catch (SQLException ex) {
                System.out.println("Ingen databaskontakt är skapad");
                Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return instance;
    }


    public Statement getStatement() {
        return statement;
    }

    public boolean isConnectedToDB() {
        return connectedToDB;
    }
    
    public String[] getPersonData(String email) throws SQLException{
        
        String[] userData = new String[0];
        
        try{
            
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


        }
        
        catch (SQLException e){
            printSQLExcept(e);
        }
        
        
        
        return userData;
    }
    
    public String[] getLoantagareData(String personID) {
        
     String[] LoantagareData = new String[0];
     
        try{
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
        }
                catch (SQLException e){
            printSQLExcept(e);
        }
        
        return LoantagareData;

    }
    
    public ArrayList<Objekt> getObjectsData(String SQL) throws Exception{
        try {
            //Get objekt from DB
            //String SQL = "Select ObjektID, Titel, Typ from Objekt";
            pState = connection.prepareStatement(SQL);
            ResultSet resultSet = getQuery(pState);
            
            ArrayList<Objekt> result = new ArrayList<>();
            while (resultSet.next()){
                result.add(new Objekt(Integer.toString(resultSet.getInt(1)), 
                        resultSet.getString(2), resultSet.getString(3), 
                        getCreatorsAsString(resultSet.getInt(1), resultSet.getString(3)), 
                        getSearchWordsAsString(resultSet.getInt(1))));
            }
          
            return result;
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
        throw new Exception("Something went wroing in method getAllObjectData "
                + "in Class DBConnection");

    }
    
    public String getCreatorsAsString(int objektID, String type) throws SQLException{
        String creators;
        String SQL;
         try {
            //Get objekt from DB
            if (type.equalsIgnoreCase("Bok"))
                SQL = "select group_concat(Concat(f.fNamn, ' ', f.eNamn, '\\n')) as Skapare from författare f, bokförfattare b, objekt o where f.FörfattareID = b.FörfattareID and o.ObjektID = b.ObjektID and o.objektID = ? group by o.ObjektID;";
            else if (type.equalsIgnoreCase("Film"))
                SQL = "select group_concat(Concat(r.fNamn, ' ', r.eNamn, '\\n')) as Skapare from regisörAktör r, filmregisöraktör f, objekt o where r.RegisörAktörID = f.RegisörAktörID and f.ObjektID = o.ObjektID and o.ObjektID =  ? group by o.ObjektID;";
            else 
                return "";
            
            pState = connection.prepareStatement(SQL);
            pState.setInt(1, objektID);
            ResultSet resultSet = getQuery(pState);
            
            resultSet.next();
            creators = resultSet.getString(1);
//            System.out.println("Författare: " +authors);
            return creators;
         }
         catch (SQLException e){
             printSQLExcept(e);
         }
        return null;
    }
    
    public String getSearchWordsAsString(int objektID){
        String searchWords = "";
        try {
            String SQL = "select Ämnesord from klassificering k, objektklass ok, objekt o where k.KlassificeringID = ok.KategoriID and ok.ObjektID = o.ObjektID and o.ObjektID =? ;";
            pState = connection.prepareStatement(SQL);
            pState.setInt(1, objektID);
            ResultSet resultSet = getQuery(pState);
            
            int nr = 0;
            while (resultSet.next()){
                if (nr == 3){
                    searchWords += "\n";
                    nr = 0;
                }
                else
                    nr++;
                
                if (searchWords.equalsIgnoreCase(""))
                    searchWords += resultSet.getString(1);
                else
                    searchWords += ", "+ resultSet.getString(1);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println(searchWords);
        return searchWords;
    }

     public ResultSet getAllCopiesData(int objektID) throws Exception {
          try {
            //Get objekt from DB
            String SQL = "Select * from Kopia where ObjektID = ?";
            pState = connection.prepareStatement(SQL);
            pState.setInt(1, objektID);
            ResultSet resultSet = getQuery(pState);

            return resultSet;
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
        throw new Exception("Something went wrong in method DBConnection.getAllCopiesData");

        
    }
    
    public boolean chechIfLibrarian(String email) throws SQLException{
        
        try{
        String SQL = "Select PersonTyp from person where eMail = ?";
        pState = connection.prepareStatement(SQL);
        pState.setString(1, email);
        ResultSet resultSet = getQuery(pState);
        resultSet.next();
        //System.out.println(resultSet.getString(1));
        return (resultSet.getString(1).equals("Bibliotekarie"));
        }
        
        catch (SQLException e){
            printSQLExcept(e);
            return false;
        }
    }
    
    
     
     
    private ResultSet getQuery(PreparedStatement pState) throws SQLException{
        //Check if we have contact with database
        if (connectedToDB == true){            
            try {
                ResultSet resultSet = pState.executeQuery();
                metadata = resultSet.getMetaData();
                return resultSet;
            }
            catch(SQLException e){
                printSQLExcept(e);
            }
        }
        else
            System.out.println("Ingen kontakt med databasen");
            return null;
    }
    
    public LoginResult checkUserAndPassword(String email, String pwordIn) throws Exception{
        try{
            LoginResult result;
            
            String SQL = "Select lösenord from person where eMail = ?";
            pState = connection.prepareStatement(SQL);
            pState.setString(1, email);
            ResultSet resultSet = getQuery(pState);

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
    
    public void printSQLExcept(SQLException e){
        System.out.println(e.getMessage());
    }
    
    public ArrayList<String> getObjektTypes(){
        
        ArrayList<String> types = new ArrayList();
        
        try {
            String SQL = "select distinct typ from objekt;";
            pState = connection.prepareStatement(SQL);
            ResultSet resultSet = getQuery(pState);
            
            if (!resultSet.next()){
                return types;
            }
            else{
                 do {
                    types.add(resultSet.getString(1));
                    System.out.println(resultSet.getString(1));
            }while (resultSet.next());
            
                 return types;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
         
    }

}