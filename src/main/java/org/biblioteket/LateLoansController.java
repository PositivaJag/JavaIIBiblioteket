package org.biblioteket;

import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import org.biblioteket.Database.DBConnection;
import org.biblioteket.Objects.Loan;

/**
 * Prints a list of all loans that are late at the moment.
 *
 * @author jenni
 */
public class LateLoansController extends Controllers {

    @FXML
    private Label lblNoLateLoans;
    @FXML
    private TableView tblLateLoans;
    @FXML
    private Button btnClose;

    private ArrayList<Loan> lateLoans;
    private DBConnection connection;

    /**
     * The method is run automatically when the class is created. Gets info from
     * all the late loans and prints it in a table.
     */
    public void initialize() {
        //Get all the late loans and show them in a table. 
        connection = DBConnection.getInstance();
        lateLoans = connection.getLateLoans();
        if (lateLoans.size() > 0) {
            updateTableView(tblLateLoans, lateLoans);
        } else {
            tblLateLoans.setVisible(false);
            lblNoLateLoans.setVisible(true);
        }
    }

    /**
     * Aborts and closes the pop-up.
     *
     * @param event
     */
    @FXML
    void pressClose(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
}
