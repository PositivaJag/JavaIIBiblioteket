package org.biblioteket;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import static javafx.collections.FXCollections.observableList;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
    private CheckBox checkBook;

    @FXML
    private CheckBox checkMagazine;

    @FXML
    private CheckBox checkMovie;

    @FXML
    private ComboBox comboType;

    //List with types of objekts. 
    private ObservableList<String> objektTyp = FXCollections.observableArrayList("Alla");
    //List of Objekts
   private ArrayList<Objekt> result;
    //Observable list with Objekts. 
    private ObservableList<Objekt> observableResult;
    
    public void initialize() throws SQLException {

        setComboType();

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

    private void getObjekts() throws SQLException {
        
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
    }

    private void updateTableView(List<Objekt> result) {
        //Clean table
        tblSearch.getColumns().clear();
        
        //Create ObservableList from list of objects. 
        observableResult = observableList(result);
        //Get felds from first Object. 
        Field[] fields = result.get(0).getClass().getDeclaredFields();
        
         for (Field field : fields) {
            System.out.println(field);
            TableColumn<Map, String> column = new TableColumn<>(field.getName().toUpperCase());
            column.setCellValueFactory(new PropertyValueFactory<>(field.getName()));
            tblSearch.getColumns().add(column);
        }
         tblSearch.setItems(observableResult);
         
        selectFirstEntry();
    }
    
     // select first item in TableView
    private void selectFirstEntry() {
        tblSearch.getSelectionModel().selectFirst();
    }
}
