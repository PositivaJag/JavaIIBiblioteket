package org.biblioteket;

import Database.DBConnection;
import Objects.Objekt;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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

public class SearchObjectController implements Initializable{

    @FXML private TableView<Objekt> searchTable;
    @FXML private TableColumn<Objekt, Integer> columnID;    
    @FXML private TableColumn<Objekt, String> columnTitel;
    @FXML private TableColumn<Objekt, String> columnType;
    @FXML private TableColumn<Objekt, String> columnAuthor;
    @FXML private TextField searchField;
    @FXML private Button buttonExit;

    private List<?> result;
    private ObservableList<?> objektList;
    private UseCase useCase;
    

    public void initialize() {
     getAllAuthors();
//        //Mappa kolumner mot klassens värden. 
//        columnID.setCellValueFactory(new PropertyValueFactory<>("objektID"));
//        columnTitel.setCellValueFactory(new PropertyValueFactory<>("titel"));
//        columnType.setCellValueFactory(new PropertyValueFactory<>("type"));
//        columnAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
       
        //Hämta lista med alla objekt. 
//        try {
//            useCase = UseCase.getInstance();
//            objektList = FXCollections.observableList(useCase.getAllObjekts());
////            searchTable.setItems(FXCollections.observableList(useCase.getAllObjekts()));
//        }
//        catch (Exception ex) {
//            Logger.getLogger(SearchObjectController.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
        
        FilteredList<Objekt> filteredData = new FilteredList<>(objektList, v -> true);
        
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate (Objekt -> {
                
                //if nothing is changed
                if (newValue == null || newValue.isEmpty()){
                    return true;
                }
                //Jämför om förändringar har skett
                String lowerCaseFilter = newValue.toLowerCase();
                
                if (String.valueOf(Objekt.getObjektID()).toLowerCase().indexOf(lowerCaseFilter) != -1){
                    return true;
                }
                else if (Objekt.getTitel().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    return true;
                
                }
                else if (Objekt.getType().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    return true;
                }
                else if (Objekt.getAuthors().toLowerCase().indexOf(lowerCaseFilter) != -1)
                    return true;
                    else
                    return false;
            });
        });
        
//        filteredData.comparatorProperty().bind(searchTable.comparatorProperty());
        searchTable.setItems(filteredData);
        
    }
      
    
    private void getAllAuthors() throws Exception{
        //clear tables
        searchTable.getColumns().clear();
        
        result = useCase.getAllObjekts();
        objektList = FXCollections.observableList(result);
        Field[] fields = result.get(0).getClass().getDeclaredFields();
    }
        
                
     @FXML
    void clickButtonExit(ActionEvent event) {
        System.exit(0);
    }

    private void getAllObjects() throws Exception{
        searchTable.getColumns().clear();

    }

}
