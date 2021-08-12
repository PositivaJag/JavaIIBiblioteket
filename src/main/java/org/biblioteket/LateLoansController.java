package org.biblioteket;

import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import org.biblioteket.Database.DBConnection;
import org.biblioteket.Objects.Loan;

/**
 *
 * @author jenni
 */
public class LateLoansController {

    @FXML
    private Text lblTitel;

    @FXML
    private TableView<?> tblLateLoans;

    @FXML
    private Button btnClose;
    
    ArrayList<Loan> lateLoans;
    
    /**
     *
     */
    public void initialize() {
        DBConnection connection = DBConnection.getInstance();
        lateLoans = connection.getLateLoans();
        Util.updateTableView(tblLateLoans, lateLoans);
     }

    @FXML
    void pressClose(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    
}
