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


public class LoginController {

    @FXML
    private TextField txtEmail;

    @FXML
    private Button buttonLogin;

    @FXML
    private Label labelMessage;

    @FXML
    private PasswordField password;
    
    Person.PersonTyp personTyp = Person.PersonTyp.NONE; 
    Person activeLibrarian = null;
    Loantagare activeUser = null;


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
                        //                    labelMessage.setTextFill(Color.web("#008000"));
//                    labelMessage.setText("Loggin!");
//                    FrameWButtonsController FWBControll = new FrameWButtonsController();
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

        try {
            //Connect to db
            DBConnection connection = DBConnection.getInstance();
            //check loggin mail and password (returns 0, 1, 2, 99)
            LoginResult pwCheck = connection.checkUserPassword(mail, password);
            
            //create loggin object if all is ok
            if (pwCheck == LoginResult.LOGIN_OK) {
                //Create librarian
//                Class<? extends Class> FWBControll = FrameWButtonsController.class.getClass();
//                FWBControll.getMethod(setLogoutVisibility());
                if (connection.chechIfLibrarian(mail)) {
                    FrameWButtonsController.setActiveLibrarian(new Person(mail));
                    
////                    for (int i = 0; i < 6; i++) {
////                        System.out.println(activeLibrarian.toString());
//   
//                    }
                     FrameWButtonsController.setPersonTyp(Person.PersonTyp.BIBLIOTEKARIE);
                    
                } 
                //Create loantagare
                else {
                    String[] personDB = connection.getPersonData(mail);
                    FrameWButtonsController.setActiveUser(new Loantagare(personDB[0], personDB[1], personDB[2], personDB[3], personDB[4], personDB[5]));

                    for (int i = 0; i < 6; i++) {
                        System.out.println(activeUser.toString());
                    }
                    FrameWButtonsController.setPersonTyp(Person.PersonTyp.LOANTAGARE);
                }
            } 
            return pwCheck;
        }
        catch (SQLException e) {
            System.out.println("error: "+e);
        }
        throw new Exception("Unknown error");
    }

}
