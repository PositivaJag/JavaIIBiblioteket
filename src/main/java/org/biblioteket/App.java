package org.biblioteket;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    /**
     * Starts the GUI at FrameWButtons.fxml.
     */
    @Override
    public void start(Stage stage) {

        try {
            //Get first scene. 
            Parent root
                    = FXMLLoader.load(getClass().getResource("FrameWButtons.fxml"));
            //Set scene and stage. 
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * main function to start the program.
     */
    public static void main(String[] args) {
        launch(args);
    }

}
