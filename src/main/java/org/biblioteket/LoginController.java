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

import Database.DBConnection;
   
public class LoginController {

    @FXML
    private TextField txtEmail;

    @FXML
    private Button buttonLogin;

    @FXML
    private Label labelMessage;

    @FXML
    private PasswordField password;

    @FXML
    private Button buttonAvbryt;

    @FXML
    void pressButtonAvbryt(ActionEvent event) {
        System.exit(0);

    }

    @FXML
    void pressButtonLogin(ActionEvent event) {
        
        String user = txtEmail.getText();
        String pw = password.getText();
        try{
            //check if user is blank
            if (user.isEmpty()){
                labelMessage.setTextFill(Color.web("#FE0000"));
                labelMessage.setText("Skriv in din mailadress");
            }
            //check if password is empty
            else if (pw.isEmpty()){
                labelMessage.setTextFill(Color.web("#FE0000"));
                labelMessage.setText("Skriv in ditt lösenord");
            }
            
            //check if user exist
            checkUserPwor(user, pw);
            
            
            
            
        }
        catch(Exception e){
            
        }

    }

}
