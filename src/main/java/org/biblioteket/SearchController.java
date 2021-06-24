package org.biblioteket;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

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
    void pressSearchBtn(ActionEvent event) {

    }

}
