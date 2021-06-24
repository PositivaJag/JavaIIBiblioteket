/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.biblioteket;

import java.sql.ResultSet;
import org.biblioteket.Objects.Objekt;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.biblioteket.Database.DBConnection;

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
    private Label labelNoHits;
//    @FXML
//    private TableColumn<?, ?> columnID;
//    @FXML
//    private TableColumn<?, ?> columnTitel;
//    @FXML
//    private TableColumn<?, ?> columnTyp;
//    @FXML
//    private TableColumn<?, ?> columnAuthor;
//    @FXML
//    private Button buttonExit;
    
    private List<Objekt> result;
    private ObservableList<Objekt> objektList;
            
    public void initialize() throws Exception{
        
        getObjekts();
        
        
    }
    
 
    private void getObjekts() throws SQLException, Exception{
        //clear table
        searchTable.getColumns().clear();
        
        //Create observableList
//        result = getAllObjekts();
        System.out.println(result.size());
        objektList = FXCollections.observableList(result);
        for (Objekt O : objektList)
            System.out.println(O.getObjektID());
        
 
        //Skapa tableColumns
        TableColumn<Objekt, String>  column1 = new TableColumn<>("ObjektID");
        TableColumn<Objekt, String> column2 = new TableColumn<>("Titel");
        TableColumn<Objekt, String> column3 = new TableColumn<>("Typ");
        TableColumn<Objekt, String> column4 = new TableColumn<>("Författare");
        
        column1.setCellValueFactory(new PropertyValueFactory<>("objektID"));
        column2.setCellValueFactory(new PropertyValueFactory<>("titel"));
        column3.setCellValueFactory(new PropertyValueFactory<>("type"));
        column4.setCellValueFactory(new PropertyValueFactory<>("authors"));
        
        searchTable.getColumns().addAll(column1, column2, column3, column4);
        searchTable.setItems(objektList);
        
    } 
            
    @FXML
    void clickButtonExit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void clickButtonSearch(ActionEvent event) {
        
    }
    
//     public ArrayList<Objekt> getAllObjekts() throws SQLException, Exception {
//
//        try {
//            //Connect to db
//            DBConnection connection = DBConnection.getInstance();
//            //Get Objekt data from DB
//            ResultSet resultSet = connection.getObjectsData("Strig");
//            //Create objects,add to resultat
//            ArrayList<Objekt> resultat = new ArrayList<>();
//            while (resultSet.next()) {
//                resultat.add(new Objekt(Integer.toString(resultSet.getInt(1)), resultSet.getString(2),
//                        resultSet.getString(3), connection.getArtistsAsString(resultSet.getInt(1))));
//            }
//            return resultat;
//        }   ArrayList<Objekt> resultat = new ArrayList<>();
//            while (resultSet.next()) {
//                resultat.add(new Objekt(Integer.toString(resultSet.getInt(1)), resultSet.getString(2),
//                        resultSet.getString(3), connection.getArtistsAsString(resultSet.getInt(1))));
//            }
//        catch (SQLException e) {
//        } 
//        catch (Exception e) {
//        }
//        throw new Exception("Something went wrong in UseCase.createAllObjects()");
//    }
//    

}