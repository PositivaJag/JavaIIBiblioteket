/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.biblioteket;

import Database.DBConnection;
import Database.DBConnection.LoginResult;
import Objects.Objekt;
import PublicPersons.Loantagare;
import PublicPersons.Person;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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

    public LoginResult login(String mail, String password) throws Exception {

        try {
            //Connect to db
            DBConnection connection = DBConnection.getInstance();
            //check loggin mail and password (returns 0, 1, 2, 99)
            LoginResult pwCheck = connection.checkUserPassword(mail, password);
            
            //create loggin object if all is ok
            if (pwCheck == LoginResult.LOGIN_OK) {
                //Create librarian
                if (connection.chechIfLibrarian(mail)) {
                    Person activeLibrarian = new Person(mail);

                    for (int i = 0; i < 6; i++) {
                        System.out.println(activeLibrarian.toString());
                    }
                } 
                //Create loantagare
                else {
                    String[] personDB = connection.getPersonData(mail);
                    Loantagare activeUser = new Loantagare(personDB[0], personDB[1], personDB[2], personDB[3], personDB[4], personDB[5]);

                    for (int i = 0; i < 6; i++) {
                        System.out.println(activeUser.toString());
                    }
                }
            } 
            return pwCheck;
        }
        catch (SQLException e) {
            System.out.println("error: "+e);
        }
        throw new Exception("Unknown error");
    }

    public ArrayList<Objekt> getAllObjekts() throws SQLException, Exception {

        try {
            //Connect to db
            DBConnection connection = DBConnection.getInstance();
            //Get Objekt data from DB
            ResultSet resultSet = connection.getAllObjectData();
            //Create objects,add to resultat
            ArrayList<Objekt> resultat = new ArrayList<>();
            while (resultSet.next()) {
                resultat.add(new Objekt(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3)));
            }
            return resultat;
        } 
        catch (SQLException e) {
            e.printStackTrace();
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        throw new Exception("Something went wrong in UseCase.createAllObjects()");
    }

    public List<Objekt> createAllCopies(int objektID) throws SQLException, Exception {
        
        try {
            //Connect to db
            DBConnection connection = DBConnection.getInstance();
            //Get Objekt data from DB
            ResultSet resultSet = connection.getAllCopiesData(objektID);
            //Create objects,add to resultat
            List<Objekt> resultat = new ArrayList<>();
            while (resultSet.next()) {
                resultat.add(new Objekt(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3)));
            }
            return resultat;
        } 
        catch (SQLException e) {
            e.printStackTrace();
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        throw new Exception("Something went wrong in UseCase.createAllObjects()");
}
}
