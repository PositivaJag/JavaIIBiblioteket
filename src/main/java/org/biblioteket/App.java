package org.biblioteket;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    private static MainController mainControll;
    
    @Override
    public void start(Stage stage) throws Exception {
    //Set up instance instead of using static load() method
    FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
    Parent root = loader.load();

//    Now we have access to getController() through the instance... don't forget the type cast
    mainControll  = (MainController)loader.getController();
      
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Jennis bibliotek");
        stage.show();       
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static MainController getMainControll() {
        return mainControll;
    }

}
