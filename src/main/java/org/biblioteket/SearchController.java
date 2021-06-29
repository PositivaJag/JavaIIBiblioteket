package org.biblioteket;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import static javafx.collections.FXCollections.observableList;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.biblioteket.Database.DBConnection;
import org.biblioteket.Objects.Objekt;

public class SearchController {

    @FXML
    private TextField txtSearch;

    @FXML
    private Button btnSearch;

    @FXML
    private TableView tblSearch;

    @FXML
    private ComboBox comboType;
    
    @FXML
    private Button btnDetails;

    //List with types of objekts. 
    private ObservableList<String> objektTyp = FXCollections.observableArrayList("Alla");
    private ArrayList<Objekt> result;      //List of Objekts    
    private ObservableList<Objekt> observableResult;  //Observable list with Objekts. 
    
    public void initialize() throws SQLException {

        setComboType();
        updateTableView(getObjekts());
    }
    
    @FXML
    void pressDetalis(ActionEvent event) {
        Objekt item = (Objekt) tblSearch.getSelectionModel().getSelectedItem();
        System.out.println(item);

    }

    @FXML
    void pressSearchBtn(ActionEvent event) throws SQLException {
        getObjekts();
        updateTableView(result);

    }
    //Gets all the alternative types from the DB and makes it possible to choose 
    //them. 
    private void setComboType() throws SQLException {
        comboType.getItems().add("Alla");
        comboType.getItems().addAll(DBConnection.getInstance().getObjektTypes());
        comboType.setValue("Alla");
    }
    
     private void updateTableView(List<Objekt> result) {
        //Clean table
        tblSearch.getColumns().clear();
        
        //Create ObservableList from list of objects. 
        observableResult = observableList(result);
//        for (int i = 0; i <observableResult.size(); i++){
//            System.out.println(observableResult.get(i));
//        }
        //Get felds from first Object. 
         //Skapa tableColumns
        TableColumn<Objekt, String>  colObjektID = new TableColumn<>("ObjektID");
        TableColumn<Objekt, String> colTitel = new TableColumn<>("Titel");
        TableColumn<Objekt, String> colTyp = new TableColumn<>("Typ");
        TableColumn<Objekt, String> colCreator = new TableColumn<>("Författare/artister");
        TableColumn<Objekt, String> colSearchWord = new TableColumn<>("Sökord");
        
        colObjektID.setCellValueFactory(new PropertyValueFactory<>("objektID"));
        colTitel.setCellValueFactory(new PropertyValueFactory<>("titel"));
        colTyp.setCellValueFactory(new PropertyValueFactory<>("type"));
        colCreator.setCellValueFactory(new PropertyValueFactory<>("creators"));
        colSearchWord.setCellValueFactory(new PropertyValueFactory<>("searchWords"));
        
        tblSearch.getColumns().addAll(colObjektID, colTitel, colTyp, colCreator, colSearchWord);
         tblSearch.setItems(observableResult);
         
        selectFirstEntry();
    }

    private ArrayList<Objekt> getObjekts() throws SQLException {
        
        DBConnection connection = DBConnection.getInstance();
        try {
            String SQL;
            if (comboType.getValue() == "Alla") {
                SQL = "Select ObjektID, Titel, Typ from Objekt";
            } else {
                SQL = "Select ObjektID, Titel, Typ from Objekt "
                        + "Where Typ = '" + comboType.getValue() + "';";
            }
            System.out.println(SQL);
            
            //gets an arraylist with objects
           result= connection.getObjectsData(SQL);
           

//            System.out.println(result.size());
//            for (Objekt i : result){
//                System.out.println(i.getTitel()+" "+i.getType());
//            }
           
        } catch (Exception ex) {
            Logger.getLogger(SearchController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

   
    
     // select first item in TableView
    private void selectFirstEntry() {
        tblSearch.getSelectionModel().selectFirst();
    }
}
