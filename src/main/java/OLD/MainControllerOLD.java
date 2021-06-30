/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OLD;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.biblioteket.Database.DBConnection;
import org.biblioteket.Database.DBConnection.LoginResult;
import org.biblioteket.Objects.Objekt;
import org.biblioteket.Persons.Loantagare;
import org.biblioteket.Persons.Person;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Color;
import org.biblioteket.Persons.Person.PersonTyp;
import org.biblioteket.MainController;

/**
 *
 * @author Jenni
 */
public class MainControllerOLD{

    private static MainControllerOLD instance;
//    PersonTyp personTyp = PersonTyp.NONE; 
//    Person activeLibrarian = null;
//    Loantagare activeUser = null;
    
   

    //Singleton implementation. 
    public static MainControllerOLD getInstance() throws SQLException {
        if (instance == null) {
            instance = new MainControllerOLD();
            
        }
        
        return instance;
    }

//    public LoginResult login(String mail, String password) throws Exception {
//
//        try {
//            //Connect to db
//            DBConnection connection = DBConnection.getInstance();
//            //check loggin mail and password (returns 0, 1, 2, 99)
//            LoginResult pwCheck = connection.checkUserAndPassword(mail, password);
//            
//            //create loggin object if all is ok
//            if (pwCheck == LoginResult.LOGIN_OK) {
//                //Create librarian
////                Class<? extends Class> FWBControll = MainController.class.getClass();
////                FWBControll.getMethod(setLogoutVisibility());
//                if (connection.chechIfLibrarian(mail)) {
//                    activeLibrarian = new Person(mail);
//                    
//////                    for (int i = 0; i < 6; i++) {
//////                        System.out.println(activeLibrarian.toString());
////   
////                    }
//                    personTyp = PersonTyp.BIBLIOTEKARIE;
//                } 
//                //Create loantagare
//                else {
//                    String[] personDB = connection.getPersonData(mail);
//                    activeUser = new Loantagare(personDB[0], personDB[1], personDB[2], personDB[3], personDB[4], personDB[5]);
//
//                    for (int i = 0; i < 6; i++) {
//                        System.out.println(activeUser.toString());
//                    }
//                    personTyp = PersonTyp.LOANTAGARE;
//                }
//            } 
//            return pwCheck;
//        }
//        catch (SQLException e) {
//            System.out.println("error: "+e);
//        }
//        throw new Exception("Unknown error");
//    }
    
    
//    public LoginResult logout(){
//        personTyp = PersonTyp.NONE;
//        activeUser = null;
//        activeLibrarian = null;
//        return LoginResult.LOGOUT;
//    }
//    

//    public ArrayList<Objekt> getAllObjekts() throws SQLException, Exception {
//
//        try {
//            //Connect to db
//            DBConnection connection = DBConnection.getInstance();
//            //Get Objekt data from DB
//            ResultSet resultSet = connection.getObjectsData();
//            //Create objects,add to resultat
//            ArrayList<Objekt> resultat = new ArrayList<>();
//            while (resultSet.next()) {
//                resultat.add(new Objekt(Integer.toString(resultSet.getInt(1)), resultSet.getString(2),
//                        resultSet.getString(3), connection.getCreatorsAsString(resultSet.getInt(1))));
//            }
//            return resultat;
//        } 
//        catch (SQLException e) {
//            e.printStackTrace();
//        } 
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//        throw new Exception("Something went wrong in UseCase.createAllObjects()");
//    }
//    

//    public List<Objekt> createAllCopies(String objektID) throws SQLException, Exception {
//        
//        try {
//            Connect to db
//            DBConnection connection = DBConnection.getInstance();
//            Get Objekt data from DB
//            ResultSet resultSet = connection.getObjectCopies(Integer.parseInt(objektID));
//            Create objects,add to resultat
//            List<Objekt> resultat = new ArrayList<>();
//            while (resultSet.next()) {
//                resultat.add(new Objekt(Integer.toString(resultSet.getInt(1)), 
//                        resultSet.getString(2),
//                        resultSet.getString(3), 
//                        connection.getCreatorsAsString(resultSet.getInt(1))));
//            }
//            return resultat;
//        } 
//        catch (SQLException e) {
//            e.printStackTrace();
//        } 
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//        throw new Exception("Something went wrong in UseCase.createAllObjects()");
//}
//
//    public PersonTyp getPersonTyp() {
//        return personTyp;
//    }
//
//    public Person getActiveLibrarian() {
//        return activeLibrarian;
//    }
//
//    public Loantagare getActiveUser() {
//        return activeUser;
//    }
//    

   
}
