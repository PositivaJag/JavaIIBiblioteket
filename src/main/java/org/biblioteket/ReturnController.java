package org.biblioteket;

import java.util.ArrayList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.biblioteket.Database.DBConnection;
import org.biblioteket.Objects.Loan;

/**
 *Handles returns 
 * @author jenni
 */
public class ReturnController extends Controllers{
    @FXML
    private Text lblTitel;
    @FXML
    private TextField txtStreckkod;
    @FXML
    private Button btnAddKopia;
    @FXML
    private Label lblInformation;
    @FXML
    private TableView tblTitles;
    @FXML
    private Button btnReturn;
    
    private DBConnection connection;
    private ArrayList<Loan> loans;
    private ArrayList<Integer> addedStreckkoder;

    public void initialize() {
        connection = DBConnection.getInstance();

        loans = new ArrayList<>();
        addedStreckkoder = new ArrayList<>();
        
        //Listener to make sure only numbers are added in streckkod filed. 
        txtStreckkod.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                if (!newValue.equals("")){
                if (!newValue.matches("\\d*")) {
                    txtStreckkod.setText(newValue.replaceAll("[^\\d]", ""));
                }
                if (addedStreckkoder.contains(Integer.parseInt(txtStreckkod.getText()))) {
                    lblInformation.setText("Titeln är redan tillagd i listan.");

                } else if (!(addedStreckkoder.contains(Integer.parseInt(txtStreckkod.getText())))) {
                    lblInformation.setText("");

                }}
            }
        });
    }

    /**
     * Checks some critera, creates a Loan instance and adds to table. 
     * @param event 
     */
    @FXML
    void pressAddKopia(ActionEvent event) {
        int streckkod = Integer.parseInt(txtStreckkod.getText());

        //Check if copy exists
        if (!connection.checkIfKopiaExists(streckkod)) {
            lblInformation.setText("Det finns ingen titel med streckkod "
                    + streckkod + ".\n Vänligen försök igen.");
            return;
        }
        //check if streckkod redan i listan
        if (addedStreckkoder.contains(streckkod)) {
            lblInformation.setText("Titeln är redan tillagd i listan.");
            return;
        }
        //Check if Kopia utlånad
        Loan loan = (connection.getActiveLoan(streckkod));
        if (loan == null || loan.getActualReturnDate() != null) {
            lblInformation.setText("Kopian är inte utlånad,\n"
                    + "vänligen kontakta personalen");
            return;
        }
        //Create loan and update table
        loans.add(loan);
        addedStreckkoder.add(streckkod);
        updateTableView(tblTitles, loans);
    }

    /**
     * Ends loans by updating the DB
     * @param event 
     */
    @FXML
    void pressReturn(ActionEvent event) {

        Boolean returnLoan = connection.returnLoan(loans);
        
        if (returnLoan) {
            simpleInfoAlert(loans.size() + " titlar återlämnade.");
            txtStreckkod.setText("");
            tblTitles.getColumns().clear();
            initialize();

        } else {
            simpleErrorAlert("Något gick fel.\n Inga lån skapades");
        }
    }
}
