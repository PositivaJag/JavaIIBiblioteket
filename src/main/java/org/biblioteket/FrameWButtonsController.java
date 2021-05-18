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

public class FrameWButtonsController {
    @FXML
    private BorderPane borderPane;
    @FXML
    private AnchorPane leftPane;
    @FXML
    private Button buttonSearch, buttonLoad, buttonRetur, buttonMyPage;
    
    private Panel view;

    
    
    
    @FXML
    void clickButtonLoan(ActionEvent event) {
        
    }

    @FXML
    void clickButtonMyPage(ActionEvent event) {

    }

    @FXML
    void clickButtonReturn(ActionEvent event) {

    }

    @FXML
    void klickButtonSearch(ActionEvent event) {
        loadPage("SearchObject.fxml");
    }
    
    private boolean loadPage(String fxml){

        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            borderPane.setCenter(root);
            return true;
            
        } catch (IOException ex) {
            System.out.println("Exception i klass FrameWButtonsController.java, i "
                    + "metoden loadPage()");
            Logger.getLogger(FrameWButtonsController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
    }

}

