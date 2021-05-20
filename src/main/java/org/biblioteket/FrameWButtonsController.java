package org.biblioteket;

import java.io.IOException;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.biblioteket.Database.DBConnection.LoginResult;
import org.biblioteket.Persons.Person.PersonTyp;

/**The class controlls the menu buttons
 * and border panes. 
 * @author Jenni
 */
public class FrameWButtonsController{
    @FXML
    private BorderPane borderPane;
    @FXML
    private AnchorPane leftPane;
    @FXML
    private Button buttonSearch;
    @FXML
    private Button buttonLoan;
    @FXML
    private Button buttonRetur;
    @FXML
    public Button buttonLogout;
    @FXML
    private Button buttonHem;
    
    private MainController mainController;
    
    
    /**
     * Handles button clicks for the loan button. 
     * One must log on to access the page. Check with Class MainController
     * if someone is logged on or not. If no one is, load login page. 
     */
    @FXML
    void clickButtonLoan(ActionEvent event) {
        try {
            //Get an instance of Main Controller
            mainController = MainController.getInstance();
            //If personTyp == NONE, --> no one is logged in. 
            if (mainController.getPersonTyp() == PersonTyp.NONE){
                loadPopup("Login.fxml");
            }
            else    //If someone alread logged in
                loadPage("Loan.fxml");       
        } 
        catch (SQLException ex) {
           ex.printStackTrace();
        }
    }
    
    /**
     *Handles button clicks for the Logout button.
     * not working at the moment because the function for 
     * making the button visible isn´t working. 
     */
    @FXML
    void clickButtonLogout(ActionEvent event) {
        try {
            
           if( MainController.getInstance().logout() == LoginResult.LOGOUT)
           {
               loadPage("Velcome.fxml");
               setLogoutVisibility(false);
           }
            
        } catch (SQLException ex) {
           ex.printStackTrace();;
        }
    }

    /**
     *Not working at the moment. 
     */
    @FXML
    void clickButtonReturn(ActionEvent event) {
       
    }
    
    /**
     * Handles button clicks for the Home button.
     * Loads the home-page. 
     * 
     */
    @FXML
    void clickButtonHem(ActionEvent event) {
        try{
            loadPage("Velcome.fxml");
        } catch (Exception e){
            System.out.println(e.getCause().getClass());
        }
    }

    /**
     * Handles button clicks for the search button.
     * loads the search page. 
     */
    @FXML
    void klickButtonSearch(ActionEvent event) {
        loadPage("SearchObject.fxml");
    }
    
    /**
     * Loads a fxml-file into the borderPande. 
     * @param fxml the name of an fxml-file including .fxml.
     * @return boolean value to know if everything went well or not. 
     */
    private boolean loadPage(String fxml){
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            borderPane.setRight(root);
            return true;
            
        } catch (IOException ex) {
            System.out.println("Exception i klass FrameWButtonsController.java, i "
                    + "metoden loadPage()");
           ex.printStackTrace();;
            return false;
        }   
    }
    
    /**
     * Loads a fxml-file as a popup.
     * @param fxml the name of an fxml-file including .fxml.
     * @return boolean value to know if everything went well or not. 
     */
    private boolean loadPopup(String fxml){
        try {
            Parent root =
                FXMLLoader.load(getClass().getResource("Login.fxml"));
            Stage stage = new Stage();
            Scene popup = new Scene(root);
            stage.setScene(popup);
            stage.show();
            return true;
        } catch (IOException ex) {
           ex.printStackTrace();;
            return false;
        }
    }
    
    /**
     *Not working. 
     * @param bool
     */
    public void setLogoutVisibility(Boolean bool){
        buttonLogout.setVisible(bool);
    }
}

