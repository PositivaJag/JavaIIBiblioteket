/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.biblioteket;

import org.biblioteket.Database.DBConnection;
import java.sql.Connection; 
import java.sql.PreparedStatement; 
import java.sql.ResultSet;
import java.sql.*;

//import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;           
import javax.swing.table.TableModel;    
import javax.swing.table.TableRowSorter;


/**
 *
 * @author Jenni
 */
public class DatabasTestMain {
    
    /**
     *
     * @param args
     * @throws SQLException
     */
    public static void main(String[] args)throws SQLException{
        
       DBConnection DB = DBConnection.getInstance();
        
        System.out.println(DB.isConnectedToDB());
        
    }
//        try{  
//            Class.forName("com.mysql.jdbc.Driver");
//            
//            Connection con = DriverManager.getConnection(
//                    "jdbc:mysql://localhost:3306/javaiibiblioteket", "root", "B0b1gny");
// 
//            Statement stmt = con.createStatement();
//            ResultSet rs = stmt.executeQuery("select Titel, Typ, BokISBN from objekt where Typ = 'Bok'");
//            
//            ResultSetMetaData metadata = rs.getMetaData();
//            int noColumns = metadata.getColumnCount();
//            for ( int i = 1; i <=noColumns; i++ ){
//                System.out.print(metadata.getColumnName(i)+"\t");
//            }
//            System.out.println();
//            
//            while (rs.next()) {
//                System.out.println(rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getInt(3));
//            }
//            con.close();
//        } catch (ClassNotFoundException | SQLException e) {
//            System.out.println(e);
//        }
//    }
//}
    }