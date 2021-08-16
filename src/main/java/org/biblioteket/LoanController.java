package org.biblioteket;

import Printer.Printer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.biblioteket.Database.DBConnection;
import org.biblioteket.Objects.Kopia;
import org.biblioteket.Objects.Loan;
import org.biblioteket.Persons.Loantagare;
import org.biblioteket.Persons.Person;
import org.biblioteket.Persons.Person.PersonTyp;

/**
 *
 * @author jenni
 */
public class LoanController {

    @FXML
    private Text lblTitel;
    @FXML
    private TextField txtStreckkod;
    @FXML
    private Button btnAddKopia;
    @FXML
    private Label lblInformation;
    @FXML
    private Label lblMaxLoan;
    @FXML
    private Label lblExisitngLoans;
    @FXML
    private Label lblPossibleLoans;
    @FXML
    private TableView tblTitles;
    @FXML
    private Button btnLoanTitles;

    //Info about the logged-in user
    private Loantagare activeUser = null;

    DBConnection connection;
    LocalDate today = LocalDate.now();

    //Lists for handling needed objects. 
    ArrayList<Loan> loans;
    ArrayList<Integer> addedStreckkoder;
    ArrayList<Kopia> kopior;

    //Maximum number of loans for activeUser. 
    int maxPossibleLoans;

    /**
     * Constructor that takes a Loantagare object as input.
     *
     * @param loantagare, the logged-in user.
     */
    public LoanController(Loantagare loantagare) {
        this.activeUser = loantagare;
        PersonTyp personTyp = PersonTyp.LOANTAGARE;
    }

    /**
     * The method is run automatically when the class is created. Connects to DB
     * Gets and sets information about activeUser category, active loans and
     * number of possible new loans.
     *
     */
    public void initialize() {
        connection = DBConnection.getInstance();

        //Gets and sets relevant info about activeUser loans. 
        setUserData();
        printUserData();

        //Initiation of lists.
        loans = new ArrayList<>();
        addedStreckkoder = new ArrayList<>();
        kopior = new ArrayList<Kopia>();

        //There can only be numbers in the Streckkod field.
        //A streckkod can only be added to the list once. 
        txtStreckkod.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                if (!newValue.matches("\\d*")) {
                    txtStreckkod.setText(newValue.replaceAll("[^\\d]", ""));
                }
                //checks if the streckkod is already added to the list. 
                if (addedStreckkoder.contains(Integer.parseInt(txtStreckkod.getText()))) {
                    lblInformation.setText("Titeln är redan tillagd i listan.");
                } else if (!(addedStreckkoder.contains(Integer.parseInt(txtStreckkod.getText())))) {
                    lblInformation.setText("");
                }
            }
        });
    }

    /**
     * Adds selected Kopia to the list if it meets the requirements.
     *
     * @param event
     */
    @FXML
    void pressAddKopia(ActionEvent event) {
        //get streckkod from text field. 
        int streckkod = Integer.parseInt(txtStreckkod.getText());

        //Check if Kopia with that streckkod exists
        if (!connection.checkIfKopiaExists(streckkod)) {
            lblInformation.setText("Det finns ingen titel med streckkod "
                    + streckkod + ".\n Vänligen försök igen.");
            return;
        }
        //check if streckkod is alredy in the list. 
        if (addedStreckkoder.contains(streckkod)) {
            lblInformation.setText("Titeln är redan tillagd i listan.");
            return;
        }

        //Check if Kopia already on loan. 
        if (connection.checkCopyOnLoan(streckkod)) {
            lblInformation.setText("Kopian är redan utlånad,\n"
                    + "vänligen kontakta personalen");
            return;
        }

        //Check if Kopia is referenslitteratur
        int loanDays = connection.getKopiaMaxLånetid(streckkod);
        if (loanDays == 0) {
            lblInformation.setText("Kopian är ett referensexemplar och får inte"
                    + "lånas ut.");
            return;
        }
        //set latest return date. 
        LocalDate latestReturnDate = today.plusDays(loanDays);

        //Create Loan instance and add to lists and update table. 
        String titel = connection.getTitle(streckkod);
        int loantagareID = Integer.parseInt(activeUser.getPersonID());
        loans.add(new Loan(today, latestReturnDate, loanDays, streckkod, 
                loantagareID, titel));
        addedStreckkoder.add(streckkod);
        Util.updateTableView(tblTitles, loans);
        
        //Chech if maximun number of loans has been reached. 
        //Dissable adding more copies if that is tha case. 
        if (loans.size() < this.maxPossibleLoans) {
            btnAddKopia.setDisable(false);
        } else {
            btnAddKopia.setDisable(true);
            lblInformation.setText("Du får inte låna fler kopior nu");
        }
    }

    /**
     * Sends a request to add the Loans to the DB. 
     * If ok, asks if user want to print a reciept. 
     * @param event 
     */
    @FXML
    void pressLoanTitles(ActionEvent event) {
        //Sends a request to the DB class to add the loans. 
        //True = all Loans were added, false = none of the Loans were added. 
        Boolean newLoan = connection.newLoan(loans, 
                Integer.parseInt(activeUser.getPersonID()));
        
       
        Alert alert;
        //If alla Loans were created, send message to user. 
        if (newLoan) {
            alert = new Alert(AlertType.CONFIRMATION, loans.size() + " titlar lånades."
                    + "\nVill du skriva ut en minneslapp?");

            ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Ja, skriv ut");
            ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Nej, avsluta");
            Optional<ButtonType> result = alert.showAndWait();
            
            //If user want a reciept
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Printer printer = new Printer();
                printer.createLoanRecipet(loans, activeUser);
            }
            
            //Reset variables. 
            txtStreckkod.setText("");
            tblTitles.getColumns().clear();
            initialize();
            loans = new ArrayList<>();
            addedStreckkoder = new ArrayList<>();
            kopior = new ArrayList<>();
            
            //If something went wrong and Loans were not added ti DB, 
            //show message
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Något gick fel.\n Inga lån skapades");
            alert.show();
        }
    }

    /**
     * Get låntagarkategori for activeUser to get max number of loans. 
     * Get number of existing loans.
     * Set values to activeUser. 
     */
    private void setUserData() {
        activeUser.setKategori(connection.getLoanCategory(activeUser.getPersonID()));
        activeUser.setNoOfLoans(connection.getMaxNoLoan(activeUser.getKategori()));
        activeUser.setLoans(connection.getLoanID(activeUser.getPersonID()));
    }

    /**
     * Print the information that was gathered in setUserData. 
     * - Max number of loans
     * - Number of existing loans
     * - How many new loans that can be made.
     */
    private void printUserData() {
        lblMaxLoan.setText(Integer.toString(activeUser.getNoOfLoans()));
        lblExisitngLoans.setText(Integer.toString(activeUser.getLoans().size()));
        this.maxPossibleLoans = activeUser.getNoOfLoans() - activeUser.getLoans().size();
        lblPossibleLoans.setText(Integer.toString(this.maxPossibleLoans));
    }
}
