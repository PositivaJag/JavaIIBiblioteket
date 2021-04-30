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
        labelMessage.setText("Success!");

    }

}
