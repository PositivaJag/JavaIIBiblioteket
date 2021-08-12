package org.biblioteket;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.biblioteket.Database.DBConnection.LoginResult;
import org.biblioteket.Persons.Loantagare;
import org.biblioteket.Persons.Person;
import org.biblioteket.Persons.Person.PersonTyp;

/**
 *
 * @author jenni
 */
public class MainController {

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

    /**
     *
     */
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

    private SearchController searchController;
    private Parent searchRoot;  //Makes sure the same window opens every time

    /**
     *
     */
    public void initialize() {

    }

    //FXML functions
    @FXML
    void klickButtonSearch(ActionEvent event) {
        loadPageSearch();
    }

    @FXML
    void clickButtonLoan(ActionEvent event) {

        if (personTyp == PersonTyp.LOANTAGARE) {
            loadPageLoan(activeUser);
        } else if (personTyp == PersonTyp.BIBLIOTEKARIE) {
            Alert alert = new Alert(AlertType.ERROR, "Bibliotekarier kan inte"
                    + "låna böcker själv utan att logga in som en låntagare.\n"
                    + "gränssnittet för att hjälpa låntagare är inte klart ännu. ");
            alert.show();
//                 loadPageLoan(activeUser);
    
        } else {
            Alert alert = new Alert(AlertType.ERROR, "Något gick fel när "
                    + "sidan skulle laddas");
            alert.show();
        }
//        loadPage("Loan.fxml");

    }

    @FXML
    void clickButtonReturn(ActionEvent event) {
        loadPage("Return.fxml");

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
    void clickButtonLogin(ActionEvent event) {
        loadPopup("Login.fxml");

    }

    @FXML
    void clickButtonLogout(ActionEvent event) {
        if (logout() == LoginResult.LOGOUT) {
            loadPage("Home.fxml");
            setLogoutVisibility(false);
            setLoginVisibility(true);
            labelInloggad.setText("Utloggad");
        }
    }

    //Load pages

    /**
     *
     * @param fxml
     * @return
     */
    public boolean loadPage(String fxml) {

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

    /**
     *
     * @param activeUser
     * @return
     */
    public boolean loadPageLoan(Loantagare activeUser){
        try {
            
             FXMLLoader loader = new FXMLLoader(getClass().getResource("Loan.fxml"));
             
            LoanController controller = new LoanController(activeUser);           
            loader.setController(controller);
            Parent root = loader.load();
            borderPane.setCenter(root);

            return true;

        } catch (IOException ex) {
            System.out.println("Exception i klass NewObjektController.java, i "
                    + "metoden loadPage()");
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    
        
    }
    
    
//     public boolean loadPageLoan(Person activeLibrarian){
//        try {
//            
//             FXMLLoader loader = new FXMLLoader(getClass().getResource("Loan.fxml"));
//             
//            LoanController controller = new LoanController(activeLibrarian);           
//            loader.setController(controller);
//            Parent root = loader.load();
//            borderPane.setCenter(root);
//
//            return true;
//
//        } catch (IOException ex) {
//            System.out.println("Exception i klass NewObjektController.java, i "
//                    + "metoden loadPage()");
//            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
//            return false;
//        }
//
//    
//        
//    }
    private boolean loadPageSearch() {

        try {
            System.out.println("searchRoot = " + searchRoot);
            //If no search has been done. 
            if (searchRoot == null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Search.fxml"));
                searchRoot = loader.load();
                App.getMainControll().setSearchController(loader.getController());
                
//            System.out.println(MainController.searchController);
            }
            borderPane.setCenter(searchRoot);
            searchController.setLibrarianButtonsVisibility();
            searchController.updateTableView(searchController.getObjekts());
            return true;

        } catch (IOException ex) {
            System.out.println("Exception i klass MainController.java, i "
                    + "metoden loadPage()");
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    /**
     *
     * @param fxml
     * @return
     */
    public boolean loadPopup(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();

            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            return true;

        } catch (IOException ex) {
            Logger.getLogger(SearchController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    


//Set FXML values    

    /**
     *
     * @param bool
     */
    public void setLogoutVisibility(Boolean bool) {
        buttonLogout.setVisible(bool);
        
    }

    /**
     *
     * @param bool
     */
    public void setLoginVisibility(Boolean bool) {
        buttonLogin.setVisible(bool);
        buttonLoan.setDisable(bool);
        

    }

    /**
     *
     * @param labelInloggad
     */
    public void setLabelInloggad(String labelInloggad) {
        this.labelInloggad.setText(labelInloggad);
    }

    //Övriga

    /**
     *
     * @return
     */
    public LoginResult logout() {
        this.personTyp = PersonTyp.NONE;
        this.activeUser = null;
        this.activeLibrarian = null;
        return LoginResult.LOGOUT;
    }

    /**
     *
     * @return
     */
    public boolean checkIfLibrarianLoggedIn() {
        if (personTyp == PersonTyp.BIBLIOTEKARIE) {
            return true;
        } else {
            return false;
        }
    }

//Getters

    /**
     *
     * @return
     */
    public PersonTyp getPersonTyp() {
        return personTyp;
    }

    /**
     *
     * @return
     */
    public Person getActiveLibrarian() {
        return activeLibrarian;
    }

    /**
     *
     * @return
     */
    public Loantagare getActiveUser() {
        return activeUser;
    }

    /**
     *
     * @return
     */
    public SearchController getSearchController() {
        return searchController;
    }

//Setters

    /**
     *
     * @param personT
     */
    public void setPersonTyp(PersonTyp personT) {
        this.personTyp = personT;
    }

    /**
     *
     * @param activeLib
     */
    public void setActiveLibrarian(Person activeLib) {
        this.activeLibrarian = activeLib;
    }

    /**
     *
     * @param activeU
     */
    public void setActiveUser(Loantagare activeU) {
        this.activeUser = activeU;
    }

    private void setSearchController(SearchController searchController) {
        this.searchController = searchController;
    }

}
