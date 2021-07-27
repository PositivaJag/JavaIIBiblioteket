package org.biblioteket;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class ReturnController {

    @FXML
    private Text lblTitel;

    @FXML
    private TextField txtStreckkod;

    @FXML
    private Button btnAddKopia;

    @FXML
    private Label lblInformation;

    @FXML
    private GridPane lblLateLoans;

    @FXML
    private Label lblNoLoan;

    @FXML
    private Label lblExisitngLoans;

    @FXML
    private Label lblFees;

    @FXML
    private TableView<?> tblTitles;

    @FXML
    private Button btnReturnTitles;

    @FXML
    void pressAddKopia(ActionEvent event) {

    }

    @FXML
    void pressReturnTitles(ActionEvent event) {

    }

}
