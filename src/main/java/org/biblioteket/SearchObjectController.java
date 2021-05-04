package org.biblioteket;

import Database.DBConnection;
import Objects.Objekt;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class SearchObjectController {

    @FXML
    private TableView<?> searchTable;
    @FXML
    private TableColumn<?, ?> columnID;
    @FXML
    private TableColumn<?, ?> columnTitel;
    @FXML
    private TextField searchField;
    
    private ObservableList<?> objektList;
    
    private void getAllObjects() throws Exception{
        searchTable.getColumns().clear();
        ArrayList<Objekt> result = UseCase.getAllObjekts();
    }

}
