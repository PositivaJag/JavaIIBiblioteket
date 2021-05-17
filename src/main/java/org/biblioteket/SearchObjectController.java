/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.biblioteket;

import org.biblioteket.Database.DBConnection;
import org.biblioteket.Objects.Objekt;
import java.lang.reflect.Field;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author Jenni
 */
public class SearchObjectController {

    @FXML
    private TextField searchField;
    

    @FXML
    private Button buttonSearch;

    @FXML
    private TableView searchTable;

    @FXML
    private Button buttonExit;

    @FXML
    private Button buttonUpdate;

    private List<Objekt> result;
    private ObservableList<Objekt> objektList;
    //private ObservableList<Objekt> filterList;
    FilteredList<Objekt> filterList;
    private UseCase useCase;

    public void initialize() throws Exception {

        getAllObjekts();

    }

    private void getAllObjekts() throws SQLException, Exception {
        //clear table
        searchTable.getColumns().clear();

        //Create observableList
        this.result = UseCase.getInstance().getAllObjekts();
        System.out.println(result.size());
        this.objektList = FXCollections.observableList(result);
        for (Objekt O : objektList) {
            System.out.println(O.getObjektID());
        }

        //Skapa tableColumns
        TableColumn<Objekt, String> column1 = new TableColumn<>("ObjektID");
        TableColumn<Objekt, String> column2 = new TableColumn<>("Titel");
        TableColumn<Objekt, String> column3 = new TableColumn<>("Typ");
        TableColumn<Objekt, String> column4 = new TableColumn<>("Författare");

        column1.setCellValueFactory(new PropertyValueFactory<>("objektID"));
        column2.setCellValueFactory(new PropertyValueFactory<>("titel"));
        column3.setCellValueFactory(new PropertyValueFactory<>("type"));
        column4.setCellValueFactory(new PropertyValueFactory<>("authors"));

        searchTable.getColumns().addAll(column1, column2, column3, column4);
        
        filterList = new FilteredList<>(objektList);
        searchTable.setItems(filterList);
//        searchTable.setItems(objektList);
        selectFirstEntry();
        
        searchField.textProperty().addListener((observable, oldValue, newValue) ->
        filterList.setPredicate(createPredicate(newValue)));
        
            

        //FilteredList<Objekt> filteredData = new FilteredList<>(objektList, v -> true);

    }

//    private ObservableList<Objekt> filterObjekt(List<Objekt> list, String text){
//        List<Objekt> filteredList = new ArrayList<>();
//        for (Objekt objekt : list){
//            if(searchObjekt(objekt, text)) 
//                filteredList.add(objekt);
//        }
//        
//        return FXCollections.observableList(filteredList);
//        
//    }
    
     private boolean searchObjekt(Objekt objekt, String text) {
        //Söker inte i typ. Ska ha egna knappar för det. 
        return (objekt.getObjektID().toLowerCase().contains(text)
                || objekt.getTitel().toLowerCase().contains(text)
                || objekt.getAuthors().toLowerCase().contains(text));
    }

    private @FXML
    void clickButtonExit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void clickButtonSearch(ActionEvent event) throws Exception {
//        String searchText = searchField.getText();
//        
//        if (searchText.isEmpty())
//            getAllObjekts();
//        
//        else{
//            this.filterList = filterObjekt(this.result, searchText);
//            FilteredList<Objekt> filteredData = new FilteredList<>(FXCollections.observableList(result));
//searchTable.setItems(filterList);
//        }
        
    }
    
    private Predicate<Objekt> createPredicate(String searchText){
    return objekt -> {
        if (searchText == null || searchText.isEmpty()) return true;
        return searchObjekt(objekt, searchText);
    };
}

    @FXML
    void clickButtonUpdate(ActionEvent event) throws Exception {
        getAllObjekts();
    }

    private void selectFirstEntry() {
        searchTable.getSelectionModel().selectFirst();
    }

    
    @FXML
    void writeSearchField(ActionEvent event) {

    }
}


