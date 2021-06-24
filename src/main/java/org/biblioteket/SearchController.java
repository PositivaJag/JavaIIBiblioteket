package org.biblioteket;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.biblioteket.Database.DBConnection;

public class SearchController {

    @FXML
    private TextField txtSearch;

    @FXML
    private Button btnSearch;

    @FXML
    private TableView<?> tblSearch;

    @FXML
    private CheckBox checkBook;

    @FXML
    private CheckBox checkMagazine;

    @FXML
    private CheckBox checkMovie;
    
    @FXML
    private ComboBox comboType;
    
    ObservableList<String> objektTyp = FXCollections.observableArrayList("Alla");
    
   public void initialize() throws SQLException {
       
       setComboType();
       
    }

    @FXML
    void pressSearchBtn(ActionEvent event) {
        getObjekts();

    }
    
    
    private void setComboType() throws SQLException{
        comboType.getItems().add("Alla");
        comboType.getItems().addAll(DBConnection.getInstance().getObjektTypes());
        comboType.setValue("Alla");

}
    private void getObjekts(){
        try {
            String SQL;
            if (comboType.getValue() == "Alla"){
                SQL = "Select ObjektID, Titel, Typ from Objekt";
            }
            else {
                SQL = "Select ObjektID, Titel, Typ from Objekt "
                        + "Where Typ = '" + comboType.getValue()+"';";
            }
            System.out.println(SQL);
            
            ResultSet resultSet = DBConnection.getInstance().getObjectsData(SQL);
             while (resultSet.next()) {
                 System.out.println(resultSet.getString(2)+" "+ resultSet.getString(3));
             }
            
        } catch (Exception ex) {
            Logger.getLogger(SearchController.class.getName()).log(Level.SEVERE, null, ex);
        }
}
}
