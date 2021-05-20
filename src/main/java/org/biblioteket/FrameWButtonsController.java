package org.biblioteket;

import java.awt.Panel;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

public class FrameWButtonsController{
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
    
    private Panel view;
    private MainController mainController;
    private static LoginResult loginResult;
    
    

     public void initialize(){
 

     }
    
    
    @FXML
    void clickButtonLoan(ActionEvent event) {
        try {
            //Check if someone is logged in.
            mainController = MainController.getInstance();
            if (mainController.getPersonTyp() == PersonTyp.NONE){
                loadPopup("Login.fxml");
                
            }
            else
                System.out.println("Ladda sindan");
                loadPage("Loan.fxml");
                
        } catch (SQLException ex) {
            Logger.getLogger(FrameWButtonsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
  
    }
    
    @FXML
    void clickButtonLogout(ActionEvent event) {
        try {
           if( MainController.getInstance().logout() == LoginResult.LOGOUT)
           {
               loadPage("Velcome.fxml");
               setLogoutVisibility(false);
           }
            
        } catch (SQLException ex) {
            Logger.getLogger(FrameWButtonsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void clickButtonReturn(ActionEvent event) {
       
    }
    
    @FXML
    void clickButtonHem(ActionEvent event) {
        try{
        loadPage("Velcome.fxml");
        buttonLogout.setDisable(true);
        } catch (Exception e){
            System.out.println(e.getCause().getClass());
        }
    }

    @FXML
    void klickButtonSearch(ActionEvent event) {
        loadPage("SearchObject.fxml");
    }
    
    private boolean loadPage(String fxml){

        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            borderPane.setRight(root);
            return true;
            
        } catch (IOException ex) {
            System.out.println("Exception i klass FrameWButtonsController.java, i "
                    + "metoden loadPage()");
            Logger.getLogger(FrameWButtonsController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
    }
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
            Logger.getLogger(FrameWButtonsController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public void setLogoutVisibility(Boolean bool){
        buttonLogout.setVisible(bool);
    }


    


}

