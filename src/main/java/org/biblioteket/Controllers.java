/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.biblioteket;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import org.biblioteket.Database.DBConnection;
import org.biblioteket.Objects.Bok;

/**
 *
 * @author jenni
 */
public class Controllers {
    
     public static void updateTableView(TableView table, List<?> list) {

        table.getColumns().clear();

        Field[] fields = list.get(0).getClass().getDeclaredFields();

        ObservableList<?> observableList = FXCollections.observableArrayList(list);

        // För varje fält, skapa en kolumn och lägg till i TableView (fxTable)
        for (Field field : fields) {
            System.out.println(field);
            TableColumn<Map, String> column = new TableColumn<>(field.getName());
            column.setCellValueFactory(new PropertyValueFactory<>(field.getName()));
            table.getColumns().add(column);
        }
        table.setItems(observableList);

    }
    /**
     * Loads fxml into new Stage.
     * @param fxml
     * @return 
     */
    public boolean loadPopup(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();

            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            return true;

        } catch (IOException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    /**
     * Loads fxml into new Stage.
     * @param fxml
     * @param controller 
     */
     public void loadPopup(String fxml, Object controller) {
        try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            loader.setController(controller);
            
            Parent root = loader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            System.out.println("Exception i klass Search.java, i "
                    + "metoden loadPage()");
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
     /**
      * Loads fxml into main stage, center pane.
      * @param fxml
      * @param borderPane
      * @return 
      */
      public boolean loadPage(String fxml, BorderPane borderPane) {

        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            borderPane.setCenter(root);
            return true;

        } catch (IOException ex) {
            System.out.println("Exception i klass MainController.java, i "
                    + "metoden loadPage()");
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
/**
 * Loads fxml into main stage, center pane.
 * @param fxml
 * @param controller
 * @param borderPane
 * @return 
 */
      public boolean loadPage(String fxml, Object controller, BorderPane borderPane) {

       
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            loader.setController(controller);
            Parent root = loader.load();
            
            borderPane.setCenter(root);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(Controllers.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
      }
    
       /**
     * Adds chosen word, from a combobox, to a list and prints the list in a
     * label.
     *
     * @param selectedWord, the name of the combobox where a word was chosen.
     * @param list of already selected words.
     * @param text, the lable where the result should be printed out.
     */
    public void addComboWordToList(ComboBox selectedWord, ArrayList<String> list,
            Label text) {
        //get the selected word from the ComboBox. 
        String word = selectedWord.getValue().toString();
        //Check if the word is aldready in the list before adding it. 
        if (word != null) {
            if (list.contains(word)) {
              
            } else {
                list.add(word);
            }
        }
        //Call function  that creates a string from the list
        //Print the list in the text label. 
        text.setText(listToString(list));
    }

    /**
     * Removes chosen word, from a combobox, from a list and prints the list in
     * a label.
     *
     * @param selectedWord, the name of the combobox where a word was chosen.
     * @param list of already selected words.
     * @param text, the lable where the result should be printed out.
     */
    public void removeComboWordFromList(ComboBox selectedWord, ArrayList<String> list, Label text) {
        //get the selected word from the ComboBox.
        String word = selectedWord.getValue().toString();
        //Remove word if it was in the list. 
        if (word != null) {
            if (list.contains(word)) {
                list.remove(word);
            }
        }
        //Call function  that creates a string from the list
        //Print the list in the text label. 
        text.setText(listToString(list));
    }
    
     public ArrayList<Integer> listAllISBN(Bok bok, DBConnection connection) {
       //get all ISBN numbers.
        ArrayList<Integer> allISBN = connection.getAllISBN();
        //then remove the ISBN of bok to make sure that that number is 
        //allowed to be added. 
        allISBN.remove(Integer.valueOf(bok.getISBN()));
        return allISBN;
 }
         /**
     *
     * @param list
     * @return
     */
    public static String listToString(ArrayList<String> list){
        String string = "";
        for (int i = 0; i < list.size(); i++){
            string += list.get(i)+"; ";
        }
        return string;
    }
     
      /**
     *
     * @param name
     */
    public static void generalError(String name){
        System.out.println("Ett fel har uppstått i klass" + name);
    }
    
    public static void simpleInfoAlert(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                alert.showAndWait();
    }

      public static void simpleErrorAlert(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                alert.showAndWait();
    }
     
}
