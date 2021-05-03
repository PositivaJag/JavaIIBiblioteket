/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.table.AbstractTableModel;

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
    private ResultSet resultSet;
    
    //Connection parameters
    private static final String DBUrl = "jdbc:mysql://localhost:3306/javaiibiblioteket";
    private static final String DBUserName = "root";
    private static final String DBPassword = "B0b1gny";
    private boolean connectedToDB = false;
    
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
    public static DBConnection getInstance() throws SQLException{
        if (instance == null){
            instance = new DBConnection(DBUrl, DBUserName, DBPassword);
        }
        return instance;
    }


    public Statement getStatement() {
        return statement;
    }

    public boolean isConnectedToDB() {
        return connectedToDB;
    }
    
    public String[] getUserData(String email) throws SQLException{
        
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
    
    public String[] getLoantagareData(String mail) {
        
     String[] LoantagareData = new String[0];
     
        try{
            //get user data
            String[] userDB = getUserData(mail);
            String personID = userDB[0];
            
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
    


    
    public boolean chechIfLibrarian(String email) throws SQLException{
        
        try{
        String SQL = "Select PersonTyp from person where eMail = ?";
        pState = connection.prepareStatement(SQL);
        pState.setString(1, email);
        getQuery(pState);
        resultSet.next();
        System.out.println(resultSet.getString(1));
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
    
    public int checkUserPwor(String email, String pwordIn) throws SQLException{
        try{
            int result;
            
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
                result = 0;
            }
            else{
                System.out.println(resultSet.getString(1));
                if (resultSet.getString(1).equals(pwordIn))
                    result = 1;
                    
                else 
                    result =  2;
            }
            
            System.out.println(result);
            return result;
            
        }
            
        catch (SQLException e){
            printSQLExcept(e);
        }
      System.out.println(99);  
      return 99;  
    }
    
    
    
    
    public void printSQLExcept(SQLException e){
        System.out.println(e.getMessage());
    }

}