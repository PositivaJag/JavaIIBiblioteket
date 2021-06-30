package org.biblioteket;

import OLD.MainControllerOLD;
import java.awt.Panel;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.biblioteket.Database.DBConnection.LoginResult;
import org.biblioteket.Persons.Loantagare;
import org.biblioteket.Persons.Person;
import org.biblioteket.Persons.Person.PersonTyp;

public class MainController {

    @FXML
    private BorderPane borderPane;
    @FXML
    private AnchorPane leftPane;
    @FXML
    Button buttonSearch;
    @FXML
    Button buttonLoan;
    @FXML
    Button buttonRetur;
    @FXML
    public Button buttonLogout;
    @FXML
    private Button buttonHem;
    @FXML
    private Label labelInloggad;

    private Panel view;
    private MainControllerOLD mainController;
    private static LoginResult loginResult;

    private PersonTyp personTyp = PersonTyp.NONE;
    private Person activeLibrarian = null;
    private Loantagare activeUser = null;

    public void initialize() {

    }

    @FXML
    void clickButtonLoan(ActionEvent event) {
        //Check if someone is logged in.
        if (personTyp == PersonTyp.NONE) {
            loadPopup("Login.fxml");

        } else {
            System.out.println("Ladda sindan");
        }
        loadPage("Loan.fxml");

    }

    @FXML
    void clickButtonLogout(ActionEvent event) {
        if (logout() == LoginResult.LOGOUT) {
            loadPage("Home.fxml");
            setLogoutVisibility(false);
            labelInloggad.setText("Utloggad");
        }
    }

    @FXML
    void clickButtonReturn(ActionEvent event) {

    }

    @FXML
    void clickButtonHem(ActionEvent event) {
        try {
            loadPage("Home.fxml");
        } catch (Exception e) {
            System.out.println(e.getCause().getClass());
        }
    }

    @FXML
    void klickButtonSearch(ActionEvent event) {
        loadPage("SearchObject.fxml");
    }

    private boolean loadPage(String fxml) {

        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            borderPane.setCenter(root);
            return true;

        } catch (IOException ex) {
            System.out.println("Exception i klass MainController.java, i "
                    + "metoden loadPage()");
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    private boolean loadPopup(String fxml) {

        try {
            Parent root
                    = FXMLLoader.load(getClass().getResource("Login.fxml"));
            Stage stage = new Stage();
            Scene popup = new Scene(root);
            stage.setScene(popup);
            stage.show();
            return true;
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public void setLogoutVisibility(Boolean bool) {
        buttonLogout.setVisible(bool);
    }

    public PersonTyp getPersonTyp() {
        return personTyp;
    }

    public Person getActiveLibrarian() {
        return activeLibrarian;
    }

    public Loantagare getActiveUser() {
        return activeUser;
    }

    public void setPersonTyp(PersonTyp personT) {
        this.personTyp = personT;
    }

    public void setActiveLibrarian(Person activeLib) {
        this.activeLibrarian = activeLib;
    }

    public void setActiveUser(Loantagare activeU) {
        this.activeUser = activeU;
    }

    public LoginResult logout() {
        this.personTyp = PersonTyp.NONE;
        this.activeUser = null;
        this.activeLibrarian = null;
        return LoginResult.LOGOUT;
    }

    public void setLabelInloggad(String labelInloggad) {
        this.labelInloggad.setText(labelInloggad);
    }

    
}
