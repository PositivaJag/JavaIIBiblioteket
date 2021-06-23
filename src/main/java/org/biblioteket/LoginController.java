/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.biblioteket;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import org.biblioteket.Database.DBConnection.LoginResult;
import java.sql.SQLException;
import javafx.scene.Node;
import org.biblioteket.Database.DBConnection;
import org.biblioteket.Persons.Person;
import org.biblioteket.Persons.Loantagare;
import org.biblioteket.Persons.Person.PersonTyp;


public class LoginController {

    @FXML
    private TextField txtEmail;

    @FXML
    private Button buttonLogin;

    @FXML
    private Label labelMessage;

    @FXML
    private PasswordField password;
    
//    Person.PersonTyp personTyp = Person.PersonTyp.NONE; 
//    Person activeLibrarian = null;
//    Loantagare activeUser = null;


    @FXML
    void pressButtonAvbryt(ActionEvent event) {
        ((Node)(event.getSource())).getScene().getWindow().hide();

    }

    @FXML
    void pressButtonLogin(ActionEvent event) throws SQLException {

        String mail = txtEmail.getText();
        String pw = password.getText();

        
        try {
            //check if user is blank
            if (mail.isEmpty()) {
                labelMessage.setTextFill(Color.web("#FE0000"));
                labelMessage.setText("Skriv in din mailadress");
            } //check if password is empty
            else if (pw.isEmpty()) {
                labelMessage.setTextFill(Color.web("#FE0000"));
                labelMessage.setText("Skriv in ditt lösenord");
                //check if mail and password is correct
            } 
            else {
                
                LoginResult logginCheck = login(mail, pw);
                
                if (null == logginCheck) {
                    labelMessage.setTextFill(Color.web("#FE0000"));
                    labelMessage.setText("Något gick fel");
                } 
                //Login successfull
                else switch (logginCheck) {
                    case NO_SUCH_USER:
                    case WRONG_PASSWORD:
                        labelMessage.setTextFill(Color.web("#FE0000"));
                        labelMessage.setText("Användarnamn eller lösenord är fel");
                        break;
                    case LOGIN_OK:
                        //labelMessage.setTextFill(Color.web("#008000"));
//                    labelMessage.setText("Loggin!");
//                    MainController FWBControll = new MainController();
//                    FWBControll.setLogoutVisibility(true);
                        ((Node)(event.getSource())).getScene().getWindow().hide();
                        break;
                    default:
                        labelMessage.setTextFill(Color.web("#FE0000"));
                        labelMessage.setText("Något gick fel");
                        break;
                }
                }
        } catch (Exception e) {

        }

    }
    
     public LoginResult login(String mail, String password) throws Exception {
         MainController mainControll;
        try {
            //Connect to db
            DBConnection connection = DBConnection.getInstance();
            
            LoginResult checkUserPassw = connection.checkUserAndPassword(mail, password);
            
            //create loggin object if all is ok
            if (checkUserPassw == LoginResult.LOGIN_OK) {                
                mainControll = App.getMainControll();             
                if (connection.chechIfLibrarian(mail)) {
                    //System.out.println(mainControll.getActiveLibrarian().toString());
                    mainControll.setActiveLibrarian(new Person(mail));
                    mainControll.setPersonTyp(Person.PersonTyp.BIBLIOTEKARIE);                   
                } 
                //Create loantagare
                else {
                    String[] personDB = connection.getPersonData(mail);
                    mainControll.setActiveUser(new Loantagare(personDB[0], personDB[1], personDB[2], personDB[3], personDB[4], personDB[5]));
                    mainControll.setPersonTyp(Person.PersonTyp.LOANTAGARE);
                    String label = mainControll.getActiveUser().getfName();
                    mainControll.setLabelInloggad(label);
                }
                mainControll.setLabelInloggad(getActiveName(mainControll));
                mainControll.setLogoutVisibility(true);
            }
            return checkUserPassw;
        }
        catch (SQLException e) {
            System.out.println("error: "+e);
        }
        throw new Exception("Unknown error");
    }
     
     public String getActiveName(MainController mainControll){
         String labelTxt = null;
         if (mainControll.getPersonTyp() == PersonTyp.BIBLIOTEKARIE){
             labelTxt = "Inloggad som:\n"+mainControll.getActiveLibrarian().getfName()+" "+mainControll.getActiveLibrarian().getlName();
                    
         }
         else if (mainControll.getPersonTyp() == PersonTyp.LOANTAGARE){
               labelTxt = "Inloggad som:\n"+mainControll.getActiveUser().getfName()+" "+mainControll.getActiveUser().getlName();
         }
             
         return labelTxt;
     }
}
