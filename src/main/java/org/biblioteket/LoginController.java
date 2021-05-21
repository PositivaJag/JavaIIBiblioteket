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
 * Handles the login pane.
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

    /**
     * Handles button clicks for the avbryt button. Closes the popup window.
     */
    @FXML
    void pressButtonAvbryt(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    /**
     * Handles button clicks on the login button Not fully functional.
     */
    @FXML
    void pressButtonLogin(ActionEvent event) {
        //Get input from user. 
        String mail = txtEmail.getText();
        String pw = password.getText();

        try {
            //If user field is empty, send message to user. 
            if (mail.isEmpty()) {
                labelMessage.setTextFill(Color.web("#FE0000"));
                labelMessage.setText("Skriv in din mailadress");
            } //if password field is empty, send message to user. 
            else if (pw.isEmpty()) {
                labelMessage.setTextFill(Color.web("#FE0000"));
                labelMessage.setText("Skriv in ditt lösenord");
            } //check if mail and password is correct
            else {
                //Get instance of MainController
                MainController mainController = MainController.getInstance();
                //Check if mail and password match
                LoginResult logginCheck = mainController.login(mail, pw);
                //If User or Password is wrong, send message to user
                if (logginCheck == LoginResult.NO_SUCH_USER || logginCheck == LoginResult.WRONG_PASSWORD) {
                    labelMessage.setTextFill(Color.web("#FE0000"));
                    labelMessage.setText("Användarnamn och/eller lösenord är fel");
                } //If User and password is correct, login. 
                else if (logginCheck == LoginResult.LOGIN_OK) {
                    //Send message to FrameWButtonsController to
                    //- continue to the next page
                    //- show the logout button
                    //This isnt working yet

                    //Close the popup. 
                    ((Node) (event.getSource())).getScene().getWindow().hide();
                } //A precausion while I'm developing the application. 
                else {
                    labelMessage.setTextFill(Color.web("#FE0000"));
                    labelMessage.setText("Något gick fel");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
