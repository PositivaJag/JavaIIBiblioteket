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
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;

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
    private Button buttonUpdate;

    @FXML
    private Button buttonExit;

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab tabAlla;

    @FXML
    private TableView tableAlla;

    @FXML
    private Tab tabBok;

    @FXML
    private TableView tableBok;

    @FXML
    private Tab tabFilm;

    @FXML
    private TableView tableFilm;

    @FXML
    private Tab tabTidning;

    @FXML
    private TableView tableTidning;

    private List<Objekt> listAlla, listBok, listFilm, listTidning;
    private ObservableList<Objekt> objektList;
    //private ObservableList<Objekt> filterList;
    FilteredList<Objekt> filterList;
    private UseCase useCase;

    public void initialize() throws Exception {

        useCase = UseCase.getInstance();
        getAllObjekts();

    }

    private void getAllObjekts() throws SQLException, Exception {
        //clear table
        tableAlla.getColumns().clear();
        tableBok.getColumns().clear();
        tableFilm.getColumns().clear();
        tableTidning.getColumns().clear();

        //Create observableList
        this.listAlla = useCase.getAllObjekts();

        //Dela upp i flera listor. Gör egen funktion. 
        String temp;
        for (int L = 0; L < listAlla.size(); L++) {
            temp = listAlla.get(L).getType();
            if (temp.equalsIgnoreCase("Bok")) {
                listBok.add(listAlla.get(L));
            } else if (temp.equalsIgnoreCase("Film")) {
                listFilm.add(listAlla.get(L));
            } else if (temp.equalsIgnoreCase("Tidskrift")) {
                listTidning.add(listAlla.get(L));
            }
        }

        System.out.println(listAlla.size());
        this.objektList = FXCollections.observableList(listAlla);
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

        tableAlla.getColumns().addAll(column1, column2, column3, column4);

        filterList = new FilteredList<>(objektList);
        tableAlla.setItems(filterList);
//        tableAlla.setItems(objektList);
        selectFirstEntry();

        searchField.textProperty().addListener((observable, oldValue, newValue)
                -> filterList.setPredicate(createPredicate(newValue)));

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
//tableAlla.setItems(filterList);
//        }

    }

    private Predicate<Objekt> createPredicate(String searchText) {
        return objekt -> {
            if (searchText == null || searchText.isEmpty()) {
                return true;
            }
            return searchObjekt(objekt, searchText);
        };
    }

    @FXML
    void clickButtonUpdate(ActionEvent event) throws Exception {
        getAllObjekts();
    }

    private void selectFirstEntry() {
        tableAlla.getSelectionModel().selectFirst();
    }

    @FXML
    void writeSearchField(ActionEvent event) {

    }
}
