package org.biblioteket;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App Startar programmet och GIU. Öppnar första sidan dvs meny och
 * startinformation.
 */
public class App extends Application {
    //Instans av controller för meny-sidan. 
    private static MainController mainControll;

    /**
     * Loader for the main page/start page.
     * @param stage
     */
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
            Parent root = loader.load();

            //Sparar referens till instansen av mainController. 
            mainControll = (MainController) loader.getController();

            //Skapar scene och laddar GUI. 
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Biblioteket");
            stage.show();
            
        } catch (IOException ex) {
            Util.generalError(this.getClass().getName());
        }
    }
    
    /**
     * Main-metod för att starta upp programmet. 
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Tillhandahåller referens till MainControll så andra 
     * klasser/objekt kan använda dess funktioner. 
     * @return mainControll
     */
    public static MainController getMainControll() {
        return mainControll;
    }

}
