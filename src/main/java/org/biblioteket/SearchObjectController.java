package org.biblioteket;

import Database.DBConnection;
import Objects.Objekt;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class SearchObjectController implements Initializable{

    @FXML private TableView<Objekt> searchTable;
    @FXML private TableColumn<Objekt, Integer> columnID;    
    @FXML private TableColumn<Objekt, String> columnTitel;
    @FXML private TableColumn<Objekt, String> columnType;
    @FXML private TableColumn<Objekt, String> columnAuthor;
    @FXML private TextField searchField;
    
    private ObservableList<Objekt> objektLista;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
     
        //Mappa kolumner mot klassens värden. 
        columnID.setCellValueFactory(new PropertyValueFactory<>("objektID"));
        columnTitel.setCellValueFactory(new PropertyValueFactory<>("titel"));
        columnType.setCellValueFactory(new PropertyValueFactory<>("type"));
        columnAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
       
        //Hämta lista med alla objekt. 
        try {
           UseCase useCase = UseCase.getInstance();
            objektLista = (ObservableList<Objekt>) useCase.getAllObjekts();
        }
        catch (Exception ex) {
            Logger.getLogger(SearchObjectController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        FilteredList<Objekt> filteredData = new FilteredList<>(objektLista, v -> true);
        
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate (Objekt -> {
                
                //if nothing is changed
                if (newValue == null || newValue.isEmpty()){
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                
            })
        })
                
    private void getAllObjects() throws Exception{
        searchTable.getColumns().clear();

    }

}
