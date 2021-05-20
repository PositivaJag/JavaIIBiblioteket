package org.biblioteket;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException, SQLException {
        
//        try{
        Parent root = 
         FXMLLoader.load(getClass().getResource("FrameWButtons.fxml"));
      
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        


//        }
//        catch (IOException e){
//            System.out.println("IO Exception: "+e.getMessage());
//        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}