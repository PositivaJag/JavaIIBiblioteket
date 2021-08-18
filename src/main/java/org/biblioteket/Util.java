/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.biblioteket;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.ResultSet;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import org.biblioteket.Objects.Loan;

/**
 *
 * @author Jenni
 */
public class Util {
    
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
     * @param table
     * @param list
     */
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
