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
    private Connection connection;
    private Statement statement;
    private PreparedStatement pState;
    private ResultSetMetaData metadata;
    private ResultSet resultSet;
    
    private boolean connectedToDB = false;
    
     public DBConnection(String url, String username, String password) throws SQLException {
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


    public Statement getStatement() {
        return statement;
    }

    public boolean isConnectedToDB() {
        return connectedToDB;
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
            
            String SQL = "Select password from låntagare where eMail = ?";
            pState = connection.prepareStatement(SQL);
            pState.setString(1, email);
            getQuery(pState);
            
            //check results
            if (!resultSet.next())
                result = 0;
            else if (resultSet.getString(1).equals(pwordIn))
                result = 1;
            else 
                result =  2;
            
            return result;
        }
            
        catch (SQLException e){
            printSQLExcept(e);
        }
        
      return 99;  
    }
    
    private void printSQLExcept(SQLException e){
        System.out.println(e.getMessage());
    }
    
}
