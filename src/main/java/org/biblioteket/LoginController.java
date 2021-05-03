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
import PublicPersons.Loantagare;
import PublicPersons.Person;
import java.sql.SQLException;

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
    void pressButtonLogin(ActionEvent event) throws SQLException {

        String user = txtEmail.getText();
        String pw = password.getText();
        try {
            //check if user is blank
            if (user.isEmpty()) {
                labelMessage.setTextFill(Color.web("#FE0000"));
                labelMessage.setText("Skriv in din mailadress");
            } //check if password is empty
            else if (pw.isEmpty()) {
                labelMessage.setTextFill(Color.web("#FE0000"));
                labelMessage.setText("Skriv in ditt lösenord");
            } else {
                //check if user exist
                DBConnection connection = DBConnection.getInstance();
                int pwCheck = connection.checkUserPwor(user, pw);

                if (pwCheck == 0 || pwCheck == 2) {
                    labelMessage.setTextFill(Color.web("#FE0000"));
                    labelMessage.setText("Användarnamn eller lösenord är fel");

                } //Login successfull
                else if (pwCheck == 1) {
                    labelMessage.setTextFill(Color.web("#008000"));
                    labelMessage.setText("Loggin!");
                    
                    if (connection.chechIfLibrarian(user)) {
                        Person activeLibrarian = new Person(user);

                        for (int i = 0; i < 6; i++) {
                            System.out.println(activeLibrarian.toString());

                        }
                    } else {
                        String[] personDB = connection.getUserData(user);
                        Loantagare activeUser = new Loantagare(personDB[0], personDB[1], personDB[2], personDB[3], personDB[4], personDB[5]);

                        for (int i = 0; i < 6; i++) {
                            System.out.println(activeUser.toString());
                        }

                    }
                } else {
                    labelMessage.setTextFill(Color.web("#FE0000"));
                    labelMessage.setText("Något gick fel");

                }

            }
        } catch (Exception e) {

        }

    }

}
