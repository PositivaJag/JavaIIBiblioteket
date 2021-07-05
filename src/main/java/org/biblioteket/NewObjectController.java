package org.biblioteket;

import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.biblioteket.Database.DBConnection;

public class NewObjectController {

    @FXML
    private Text lblTitel;

    @FXML
    private TextField txtTitle;

    @FXML
    private TextField txtISBN;

    @FXML
    private TextField txtAutors;

    @FXML
    private ComboBox comboAuthors;

    @FXML
    private TextField txtSearchWords;

    @FXML
    private ComboBox comboSearchWords;
    
    private ArrayList<String> authorsList;
    private ArrayList<String> swList;

    public void initialize(){
        comboAuthors.getItems().addAll(DBConnection.getInstance().getAllAuthors());
        comboSearchWords.getItems().addAll(DBConnection.getInstance().getAllSearchWords());
    }
    
    @FXML
    void checkIfISBNExists(ActionEvent event) {

    }

    @FXML
    void selectAuthorFromList(ActionEvent event) {
        String value = comboAuthors.getValue().toString();
        authorsList.add(value);
//        txtAutors.setText(ListToString(authorsList));

    }

    @FXML
    void selectSeachWordFromList(ActionEvent event) {

    }

    private String ListToString(ArrayList<String> list) {
        String output = "";
        for (int i = 0; i < list.size(); i++) {
            if (output == "") {
                output += list.get(i);
            } else {
                output += ", " + list.get(i);
            }
        }
        return output;
    }
}
