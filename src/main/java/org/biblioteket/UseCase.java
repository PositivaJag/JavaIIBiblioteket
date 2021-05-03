/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.biblioteket;

import Database.DBConnection;
import PublicPersons.Loantagare;
import PublicPersons.Person;
import java.sql.SQLException;
import javafx.scene.paint.Color;

/**
 *
 * @author Jenni
 */
public class UseCase {

    private static UseCase instance;

    //Singleton implementation. 
    public static UseCase getInstance() throws SQLException {
        if (instance == null) {
            instance = new UseCase();
        }
        return instance;
    }

    public int login(String mail, String password) throws SQLException {

        try {
            //Connect to db
            DBConnection connection = DBConnection.getInstance();
            //check loggin mail and password (returns 0, 1, 2, 99)
            int pwCheck = connection.checkUserPwor(mail, password);
            
            //create loggin object if all is ok
            if (pwCheck == 1) {
                //Create librarian
                if (connection.chechIfLibrarian(mail)) {
                    Person activeLibrarian = new Person(mail);

//                    for (int i = 0; i < 6; i++) {
//                        System.out.println(activeLibrarian.toString());
//                    }
                } 
                //Create loantagare
                else {
                    String[] personDB = connection.getPersonData(mail);
                    Loantagare activeUser = new Loantagare(personDB[0], personDB[1], personDB[2], personDB[3], personDB[4], personDB[5]);

//                    for (int i = 0; i < 6; i++) {
//                        System.out.println(activeUser.toString());
//                    }
                }
            } 
            return pwCheck;
        }
        catch (SQLException e) {
            System.out.println("error: "+e);
        }
        return 99;
    }
}
