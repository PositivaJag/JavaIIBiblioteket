package org.biblioteket;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
        Boolean CopyExists = connection.checkIfKopiaExists(streckkod);
        if (CopyExists) {
             if (!addedStreckkoder.contains(streckkod)) {
                 Kopia kopia = (connection.getKopia(streckkod));
                 if (kopia.getAccess() == AccessKopia.ON_LOAN){
                     lblInformation.setText("Kopian är inte tillgänglig för lån,\n"
                             + "vänligen kontakta personalen")
                             }
             else{
                
            if ()
            //Check if kopia avalible
        if ()
            //Check if copy already in list. 
           

                int loanDays = connection.getKopiaMaxLånetid(streckkod);
                String titel = connection.getTitle(streckkod);
                int loantagareID = Integer.parseInt(activeUser.getPersonID());

                loans.add(new Loan(today, loanDays, streckkod, loantagareID, titel));
                addedStreckkoder.add(streckkod);
                updateTableView();
            } else {
                lblInformation.setText("Titeln är redan tillagd i listan.");
            }
        } else {
            lblInformation.setText("Kopian finns inte. Kontrollera "
                    + "streckkoden och försök igen.");
        }
    }

    @FXML
    void pressLoanTitles(ActionEvent event) {

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
