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

/**
 *
 * @author Jenni
 */
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
                //Create instance of MainController
                MainController useCase = MainController.getInstance();
                //Check if mail and password match
                LoginResult logginCheck = useCase.login(mail, pw);
                
                if (logginCheck == LoginResult.NO_SUCH_USER || logginCheck == LoginResult.WRONG_PASSWORD) {
                    labelMessage.setTextFill(Color.web("#FE0000"));
                    labelMessage.setText("Användarnamn eller lösenord är fel");

                } 
                //Login successfull
                else if (logginCheck == LoginResult.LOGIN_OK) {
//                    labelMessage.setTextFill(Color.web("#008000"));
//                    labelMessage.setText("Loggin!");
//                    FrameWButtonsController FWBControll = new FrameWButtonsController();
//                    FWBControll.setLogoutVisibility(true);
                    ((Node)(event.getSource())).getScene().getWindow().hide();
                }
                else {
                labelMessage.setTextFill(Color.web("#FE0000"));
                labelMessage.setText("Något gick fel");
                }
                }
        } catch (Exception e) {

        }

    }

}
