package org.biblioteket;

import Database.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.SubScene;
import javafx.scene.control.Separator;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException, SQLException {
        
        try{
        Parent root = 
         FXMLLoader.load(getClass().getResource("SearchObject.fxml"));
      
        Scene scene = new Scene(root);
        //stage.setTitle("Display Query Results");
        stage.setScene(scene);
        stage.show();

        }
        catch (IOException e){
            System.out.println("IO Exception: "+e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}