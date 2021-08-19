package org.biblioteket;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import org.biblioteket.Database.DBConnection.LoginResult;
import org.biblioteket.Persons.Loantagare;
import org.biblioteket.Persons.Person;
import org.biblioteket.Persons.Person.PersonTyp;

/**
 *
 * @author jenni
 */
public class MainController extends Controllers {

    //FXML variables
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
    private Button buttonLogin;
    @FXML
    private Label labelInloggad;

    //Other variables
    //Loantakers and librarians
    private PersonTyp personTyp = PersonTyp.NONE;
    private Person activeLibrarian = null;
    private Loantagare activeUser = null;

    /**
     *
     */
    public void initialize() {
        setLibrarianButtons();
    }

    @FXML
    void klickButtonSearch(ActionEvent event) {
        loadPage("Search.fxml", borderPane);
    }

    @FXML
    void clickButtonLoan(ActionEvent event) {

        switch (personTyp) {
            case LOANTAGARE:
                loadPage("Loan.fxml", new LoanController(activeUser), borderPane);
                break;
            case BIBLIOTEKARIE: //Only Loantagare can  loan Objekts.
            {
                simpleErrorAlert("Bibliotekarier kan inte "
                        + "låna böcker själv utan att logga in som en låntagare.\n"
                        + "gränssnittet för att hjälpa låntagare är inte klart ännu. ");

                break;
            }
            default: {
                simpleErrorAlert("Något gick fel när sidan skulle laddas");
                break;
            }
        }
    }

    @FXML
    void clickButtonReturn(ActionEvent event) {
        loadPage("Return.fxml", borderPane);
    }

    @FXML
    void clickButtonHem(ActionEvent event) {
        loadPage("Home.fxml", borderPane);
    }

    @FXML
    void clickButtonLogin(ActionEvent event) {
        loadPopup("Login.fxml");
    }

    @FXML
    void clickButtonLogout(ActionEvent event) {
        if (logout() == LoginResult.LOGOUT) {
            loadPage("Home.fxml", borderPane);
            setLogoutVisibility(false);
            setLoginVisibility(true);
            labelInloggad.setText("Utloggad");
            setLibrarianButtons();
        }
    }

    public void setLogoutVisibility(Boolean bool) {
        buttonLogout.setVisible(bool);
    }

    public void setLoginVisibility(Boolean bool) {
        buttonLogin.setVisible(bool);
        buttonLoan.setDisable(bool);
    }

    public void setLabelInloggad(String labelInloggad) {
        this.labelInloggad.setText(labelInloggad);
    }

    public LoginResult logout() {
        this.personTyp = PersonTyp.NONE;
        this.activeUser = null;
        this.activeLibrarian = null;
        return LoginResult.LOGOUT;
    }


    public boolean checkIfLibrarianLoggedIn() {
        if (personTyp == PersonTyp.BIBLIOTEKARIE) {
            return true;
        } else {
            return false;
        }
    }

    public void setLibrarianButtons() {
        Boolean bool = checkIfLibrarianLoggedIn();
        if (bool) {
            buttonSearch.setText("Sök, Skapa, Uppdatera");
        } else {
            buttonSearch.setText("Sök");
        }
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

}
