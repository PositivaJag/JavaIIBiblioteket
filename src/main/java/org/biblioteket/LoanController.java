package org.biblioteket;

import Printer.Printer;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import org.biblioteket.Database.DBConnection;
import org.biblioteket.Objects.Kopia;
import org.biblioteket.Objects.Kopia.AccessKopia;
import org.biblioteket.Objects.Loan;
import org.biblioteket.Objects.Objekt;
import org.biblioteket.Persons.Loantagare;
import org.biblioteket.Persons.Person;
import org.biblioteket.Persons.Person.PersonTyp;

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

    private PersonTyp personTyp = Person.PersonTyp.NONE;
//    private Person activeLibrarian = null;
    private Loantagare activeUser = null;

    DBConnection connection;

    LocalDate today = LocalDate.now();

    ArrayList<Loan> loans = new ArrayList<>();
    ArrayList<Integer> addedStreckkoder = new ArrayList<>();
    ArrayList<Kopia> kopior = new ArrayList<Kopia>();

    public LoanController(Loantagare loantagare) {
        this.activeUser = loantagare;
        PersonTyp personTyp = PersonTyp.LOANTAGARE;
    }

//    public LoanController(Person person){
//        this.activeLibrarian = person;
//        PersonTyp personTyp = PersonTyp.BIBLIOTEKARIE;
//    }
    public void initialize() {
        connection = DBConnection.getInstance();
        setUserData();
        printUserData();

        //Bara siffror i fältet för streckkod. 
        txtStreckkod.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                if (!newValue.matches("\\d*")) {
                    txtStreckkod.setText(newValue.replaceAll("[^\\d]", ""));

                }
                if (addedStreckkoder.contains(Integer.parseInt(txtStreckkod.getText()))) {
                    lblInformation.setText("Titeln är redan tillagd i listan.");

                } else if (!(addedStreckkoder.contains(Integer.parseInt(txtStreckkod.getText())))) {
                    lblInformation.setText("");

                }

            }
        });
    }

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
        Kopia kopia = (connection.getKopia(streckkod));
        if (kopia.getAccess() == AccessKopia.ON_LOAN) {
            lblInformation.setText("Kopian är inte tillgänglig för lån,\n"
                    + "vänligen kontakta personalen");
            return;
        }
        
        //Check if referenslitteratur
         int loanDays = connection.getKopiaMaxLånetid(streckkod);
        if(loanDays == 0){
            lblInformation.setText("Kopian är ett referensexemplar och får inte"
                    + "lånas ut.");
            return;
        }
        
        //Create loan
           
            String titel = connection.getTitle(streckkod);
            int loantagareID = Integer.parseInt(activeUser.getPersonID());

            loans.add(new Loan(today, loanDays, streckkod, loantagareID, titel));
            addedStreckkoder.add(streckkod);
            updateTableView();
    
       
}

@FXML
    void pressLoanTitles(ActionEvent event) {
       Boolean newLoan =  connection.newLoan(loans, Integer.parseInt(activeUser.getPersonID()));
        
       Alert alert;
       if (newLoan){
           alert = new Alert(AlertType.CONFIRMATION, loans.size()+" titlar lånades."
                   + "\nVill du skriva ut en minneslapp?");
           
            ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Ja, skriv ut");
            ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Nej, avsluta");

            Optional<ButtonType> result = alert.showAndWait();
             if (result.isPresent() && result.get() == ButtonType.OK) {
                 Printer printer = new Printer();
                 printer.createLoanRecipet(loans, activeUser);
            }
             
//             setUserData();
//             printUserData();
             txtStreckkod.setText("");
             tblTitles.getColumns().clear();
             initialize();
             
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Något gick fel.\n Inga lån skapades");
            alert.show();
        }
           
       }
    

    private void setUserData() {

        activeUser.setKategori(connection.getLoanCategory(activeUser.getPersonID()));
        activeUser.setNoOfLoans(connection.getMaxNoLoan(activeUser.getKategori()));
        activeUser.setLoans(connection.getLoanID(activeUser.getPersonID()));

    }

    private void printUserData() {
        lblMaxLoan.setText(Integer.toString(activeUser.getNoOfLoans()));
        lblExisitngLoans.setText(Integer.toString(activeUser.getLoans().size()));
        lblPossibleLoans.setText(Integer.toString(
                activeUser.getNoOfLoans() - activeUser.getLoans().size()));
    }

    private void updateTableView() {

        tblTitles.getColumns().clear();

        Field[] fields = loans.get(0).getClass().getDeclaredFields();

        ObservableList<Loan> observableKopior = FXCollections.observableArrayList(loans);

        // För varje fält, skapa en kolumn och lägg till i TableView (fxTable)
        for (Field field : fields) {
            System.out.println(field);
            TableColumn<Map, String> column = new TableColumn<>(field.getName());
            column.setCellValueFactory(new PropertyValueFactory<>(field.getName()));
            tblTitles.getColumns().add(column);
        }
        tblTitles.setItems(observableKopior);

    }
}
