/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.biblioteket;

import Database.DBConnection;
import Objects.Objekt;
import java.lang.reflect.Field;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
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
    private TableColumn<?, ?> columnID;
    @FXML
    private TableColumn<?, ?> columnTitel;
    @FXML
    private TableColumn<?, ?> columnTyp;
    @FXML
    private TableColumn<?, ?> columnAuthor;
    @FXML
    private Button buttonExit;
    
    private List<Objekt> result;
    private ObservableList<Objekt> objektList;
    private UseCase useCase;
            
    public void initialize() throws Exception{
        
        getAllObjekts();
        
        
    }
    
 
    private void getAllObjekts() throws SQLException, Exception{
        //clear table
        searchTable.getColumns().clear();
        
        //Create observableList
        result = UseCase.getInstance().getAllObjekts();
        System.out.println(result.size());
        objektList = FXCollections.observableList(result);
        for (Objekt O : objektList)
            System.out.println(O.getObjektID());
        
 
        //Skapa tableColumns
        TableColumn<Map, String>  column1 = new TableColumn<>("ObjektID");
        TableColumn<Map, String> column2 = new TableColumn<>("Titel");
        TableColumn<Map, String> column3 = new TableColumn<>("Typ");
        TableColumn<Map, String> column4 = new TableColumn<>("Författare");
        
        column1.setCellValueFactory(new PropertyValueFactory<>("objektID"));
        column2.setCellValueFactory(new PropertyValueFactory<>("titel"));
        column3.setCellValueFactory(new PropertyValueFactory<>("type"));
        column4.setCellValueFactory(new PropertyValueFactory<>("authors"));
        
        searchTable.getColumns().addAll(column1, column2, column3, column4);
        searchTable.setItems(objektList);
        
        
//        
//        Field[] fields = result.get(0).getClass().getDeclaredFields();
//        System.out.println(Arrays.toString(fields));
//        for (Field field : fields){
//            TableColumn column = new TableColumn<>(field.getName());
//            column.setCellValueFactory(new PropertyValueFactory<>(field.getName()));
//            searchTable.getColumns().add(column);
//            System.out.println(searchTable.getColumns().toString());
            
//        searchTable.setItems(objektList);
    } 
            
    @FXML
    void clickButtonExit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void clickButtonSearch(ActionEvent event) {
        
    }

}