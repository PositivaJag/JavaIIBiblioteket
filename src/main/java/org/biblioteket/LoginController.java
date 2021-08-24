package org.biblioteket;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import org.biblioteket.Database.DBConnection.LoginResult;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Node;
import org.biblioteket.Database.DBConnection;
import org.biblioteket.Persons.Person;
import org.biblioteket.Persons.Loantagare;
import org.biblioteket.Persons.Person.PersonTyp;

/**
 *
 * @author jenni
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

    private MainController mainControll;
    private DBConnection connection;

    public void initialize() {
        connection = DBConnection.getInstance();
        mainControll = App.getMainControll();
    }

    @FXML
    void pressButtonLogin(ActionEvent event) {

        String mail = txtEmail.getText();
        String pw = password.getText();

        //check if user is blank
        if (mail.isEmpty()) {
            labelMessage.setTextFill(Color.web("#FE0000"));
            labelMessage.setText("Skriv in din mailadress");
        } //check if password is empty
        else if (pw.isEmpty()) {
            labelMessage.setTextFill(Color.web("#FE0000"));
            labelMessage.setText("Skriv in ditt lösenord");
            //check if mail and password is correct
        } else {

            try {
                LoginResult logginCheck = login(mail, pw);

                if (null == logginCheck) {
                    labelMessage.setTextFill(Color.web("#FE0000"));
                    labelMessage.setText("Något gick fel");
                } else {   //Login successfull
                    switch (logginCheck) {
                        case NO_SUCH_USER:
                        case WRONG_PASSWORD:
                            labelMessage.setTextFill(Color.web("#FE0000"));
                            labelMessage.setText("Användarnamn eller lösenord är fel");
                            break;
                        case LOGIN_OK:
                            ((Node) (event.getSource())).getScene().getWindow().hide();
                            break;
                        default:
                            labelMessage.setTextFill(Color.web("#FE0000"));
                            labelMessage.setText("Något gick fel");
                            break;
                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    void pressButtonAvbryt(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    /**
     * Checks if password and user name is ok and creates instances of Loantagare
     * or Person if it is. 
     * This method needs some work to look good. 
     * @param mail
     * @param password
     * @return
     */
    private LoginResult login(String mail, String password) {
        //Connect to db
        LoginResult checkUserPassw = connection.checkUserAndPassword(mail, password);
        //create loggin object if all is ok
        if (checkUserPassw == LoginResult.LOGIN_OK) {
            //Create librarian
            if (connection.chechIfLibrarian(mail)) {
                
                mainControll.setActiveLibrarian(new Person(mail));
                mainControll.setPersonTyp(Person.PersonTyp.BIBLIOTEKARIE);
                
            } //Create loantagare
            else {
                String[] personDB = connection.getPersonDataAsList(mail);
                mainControll.setActiveUser(new Loantagare(personDB[0], personDB[1], personDB[2], personDB[3], personDB[4], personDB[5]));
                mainControll.setPersonTyp(Person.PersonTyp.LOANTAGARE);
                String label = mainControll.getActiveUser().getfName();
                mainControll.setLabelInloggad(label);
            }
            mainControll.setLabelInloggad(getActiveName());
            mainControll.setLibrarianButtons();
            mainControll.setLogoutVisibility(true);
            mainControll.setLoginVisibility(false);
        }
        return checkUserPassw;
    }

    /**
     * Creates a String with info about the active user to be printet in the GUI. 
     * This could have been done in a better way. 
     * @return
     */
    private String getActiveName() {
        String labelTxt = null;
        if (mainControll.getPersonTyp() == PersonTyp.BIBLIOTEKARIE) {
            labelTxt = "Inloggad som:\n" + mainControll.getActiveLibrarian().getfName() + " " + mainControll.getActiveLibrarian().getlName();

        } else if (mainControll.getPersonTyp() == PersonTyp.LOANTAGARE) {
            labelTxt = "Inloggad som:\n" + mainControll.getActiveUser().getfName() + " " + mainControll.getActiveUser().getlName();
        }

        return labelTxt;
    }
}
