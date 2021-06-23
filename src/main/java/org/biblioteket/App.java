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

    static MainController mainControll;
    
    @Override
    public void start(Stage stage) throws Exception {
    //Set up instance instead of using static load() method
    FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
    Parent root = loader.load();

    //Now we have access to getController() through the instance... don't forget the type cast
    mainControll  = (MainController)loader.getController();
      
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

    public static MainController getMainControll() {
        return mainControll;
    }

    
}