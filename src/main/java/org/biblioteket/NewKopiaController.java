package org.biblioteket;

import java.util.Collections;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.biblioteket.Database.DBConnection;

public class NewKopiaController {

    @FXML
    private Text lblTitel;

    @FXML
    private ComboBox comboCategory;

    @FXML
    private TextField txtStreckkod;

    @FXML
    private TextField txtPlacement;

    @FXML
    private Label lblWarning;

    @FXML
    private Button btnAdd;

    @FXML
    private TableView<?> tblAddedCopies;

    @FXML
    private Button btnCreateCopy;
    
    public void initialize(){
        setComboCategories();
    }

    @FXML
    void pressAddToDB(ActionEvent event) {

    }

    @FXML
    void pressAddToList(ActionEvent event) {

    }

    private void setComboCategories(){
        List<String> list = DBConnection.getInstance().getAllKopiaCategories();
        Collections.sort(list);
        comboCategory.getItems().addAll(list);
    }

}
