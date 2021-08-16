package org.biblioteket;

import Printer.Printer;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import org.biblioteket.Database.DBConnection;
import org.biblioteket.Objects.Kopia;
import org.biblioteket.Objects.Loan;

/**
 *
 * @author jenni
 */
public class ReturnController {

    @FXML
    private Text lblTitel;

    @FXML
    private TextField txtStreckkod;

    @FXML
    private Button btnAddKopia;

    @FXML
    private Label lblInformation;

//    @FXML
//    private GridPane lblLateLoans;
//
//    @FXML
//    private Label lblNoLoan;
//
//    @FXML
//    private Label lblExisitngLoans;
//
//    @FXML
//    private Label lblFees;

    @FXML
    private TableView tblTitles;

    @FXML
    private Button btnReturnTitles;

    DBConnection connection;

    LocalDate today = LocalDate.now();

    ArrayList<Loan> loans;
    ArrayList<Integer> addedStreckkoder;
    ArrayList<Kopia> kopior;

    /**
     *
     */
    public void initialize() {
        connection = DBConnection.getInstance();

        loans = new ArrayList<>();
        addedStreckkoder = new ArrayList<>();
        kopior = new ArrayList<Kopia>();
        
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
        //Create loan
        loans.add(loan);
        addedStreckkoder.add(streckkod);
        Util.updateTableView(tblTitles, loans);
    }

    @FXML
    void pressReturnTitles(ActionEvent event) {
        Boolean returnLoan = connection.returnLoan(loans);

        Alert alert;
        if (returnLoan) {
            alert = new Alert(Alert.AlertType.INFORMATION, loans.size() + " titlar återlämnade.");
            alert.show();
            txtStreckkod.setText("");
            tblTitles.getColumns().clear();
            initialize();

        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Något gick fel.\n Inga lån skapades");
            alert.show();
        }

    }

//    private void updateTableView() {
//
//        tblTitles.getColumns().clear();
//
//        Field[] fields = loans.get(0).getClass().getDeclaredFields();
//
//        ObservableList<Loan> observableKopior = FXCollections.observableArrayList(loans);
//
//        // För varje fält, skapa en kolumn och lägg till i TableView (fxTable)
//        for (Field field : fields) {
//            System.out.println(field);
//            TableColumn<Map, String> column = new TableColumn<>(field.getName());
//            column.setCellValueFactory(new PropertyValueFactory<>(field.getName()));
//            tblTitles.getColumns().add(column);
//        }
//        tblTitles.setItems(observableKopior);
//
//    }

}
