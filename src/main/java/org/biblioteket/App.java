package org.biblioteket;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
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

    private static SubScene scene1;
    private static SubScene scene2;

    @Override
    public void start(Stage stage) throws IOException {
        scene1 = new SubScene(loadFXML("primary"), 200 , 200);
        scene2 = new SubScene(loadFXML("secondary"), 400, 200);
        
         HBox root = new HBox(10);
         Separator S1 = new Separator();
         S1.setOrientation(Orientation.VERTICAL);
         //S1.set (Pos.CENTER);
         
        root.setAlignment(Pos.CENTER);
        S1.setMaxWidth(40);
        //S1.setAlignment(Pos.CENTER_LEFT);
        root.getChildren().addAll(scene1,scene2);
        root.getChildren().add(3, S1);
        Scene mainScene = new Scene(root);  
        stage.setScene(mainScene);
        stage.show();
        
//        stage.setScene(scene1);
//        stage.show();
    }

    static void setRoot1(String fxml) throws IOException {
        scene1.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}