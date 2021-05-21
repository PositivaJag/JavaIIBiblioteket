package org.biblioteket;

import org.biblioteket.Objects.Objekt;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Handles the GUI functionality for the Objekt list and search functions.
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

    private List<Objekt> result;
    private ObservableList<Objekt> objektList;
    private MainController mainController;

    /**
     * Initializes the TableView at the start of the GUI.
     */
    public void initialize() throws Exception {
        getAllObjekts();
    }

    /**
     * Gets Objekt from db and displays them in TableView.
     */
    private void getAllObjekts() {
        //clear table
        searchTable.getColumns().clear();

        //Get all Objekt´ from the db
        result = MainController.getInstance().getAllObjekts();
        //Create observableList to dispay Objekt in TableView
        objektList = FXCollections.observableList(result);

        //Create tableColumns
        TableColumn<Objekt, String> column1 = new TableColumn<>("ObjektID");
        TableColumn<Objekt, String> column2 = new TableColumn<>("Titel");
        TableColumn<Objekt, String> column3 = new TableColumn<>("Typ");
        TableColumn<Objekt, String> column4 = new TableColumn<>("Författare");

        column1.setCellValueFactory(new PropertyValueFactory<>("objektID"));
        column2.setCellValueFactory(new PropertyValueFactory<>("titel"));
        column3.setCellValueFactory(new PropertyValueFactory<>("type"));
        column4.setCellValueFactory(new PropertyValueFactory<>("artists"));

        //Set TableView to read observableList.
        searchTable.getColumns().addAll(column1, column2, column3, column4);
        searchTable.setItems(objektList);
    }

    /**
     * Handles button clicks for search button. 
     * Not yet functional
     */
    @FXML
    void clickButtonSearch(ActionEvent event) {
    }
}
