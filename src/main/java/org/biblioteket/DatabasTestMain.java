/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.biblioteket;

import org.biblioteket.Database.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import org.biblioteket.Objects.Bok;

//import javafx.embed.swing.SwingNode;
import org.biblioteket.Objects.Film;
import org.biblioteket.Objects.Objekt;
import org.biblioteket.Objects.Objekt.Type;
import org.biblioteket.Objects.Tidskrift;


/**
 *
 * @author Jenni
 */
public class DatabasTestMain {
    
    public static void main(String[] args)throws SQLException{
        
       DBConnection instance = DBConnection.getInstance();
       int ObjektID = 3;
       
       Objekt k = new Objekt(1, "Kalle", Type.Bok, "", "");
       ArrayList<?> list = instance.getObjectCopies(k, Type.Bok);
       
       }
//       Bok bok = instance.getBokFromDB(ObjektID);
//        Tidskrift tidskrift  =  instance.getTidskriftFromDB(ObjektID);
//       film.printFilm();
       
      
        
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
    