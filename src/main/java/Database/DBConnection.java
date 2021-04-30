/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
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
    
    private boolean connectedToDatabase = false;
    
     public DBConnection(String url, String username, String password) throws SQLException {
      // connect to database
      connection = DriverManager.getConnection(url, username, password);

      // create Statement to query database                             
      statement = connection.createStatement(                           
         ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

      // update database connection status
      connectedToDatabase = true;         

      // set query and execute it
      //setQuery(query);
  } 


    public Statement getStatement() {
        return statement;
    }

    public boolean isConnectedToDatabase() {
        return connectedToDatabase;
    }
    
//    public String getPassword(String email){
//        String SQL = "select password from låntagare where eMail = "
//    }
//    
}
