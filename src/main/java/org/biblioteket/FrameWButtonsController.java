package org.biblioteket;

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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.biblioteket.Objects.Objekt;

public class FrameWButtonsController {
    @FXML
    private BorderPane borderPane;
    @FXML
    private AnchorPane leftPane;
    @FXML
    private Button buttonSearch, buttonLoan, buttonRetur, buttonLogout, buttonHem;
    
    private Panel view;
    private Objekt inloggad = null;

    
    
    
    @FXML
    void clickButtonLoan(ActionEvent event) {
//        if (inloggad == null){
//            if (getPopup("Login.fxml"))
//                inloggad = 
        
    }

    @FXML
    void clickButtonLogout(ActionEvent event) {
        

    }

    @FXML
    void clickButtonReturn(ActionEvent event) {

    }
    
    @FXML
    void clickButtonHem(ActionEvent event) {
        loadPage("Velcome.fxml");
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
    private boolean getPopup(String fxml){
        
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

}

