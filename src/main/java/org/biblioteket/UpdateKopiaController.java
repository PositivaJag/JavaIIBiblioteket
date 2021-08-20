package org.biblioteket;

import java.util.ArrayList;
import java.util.Optional;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import org.biblioteket.Database.DBConnection;
import org.biblioteket.Objects.Bok;
import org.biblioteket.Objects.Kopia;
import org.biblioteket.Objects.Objekt;
import org.biblioteket.Objects.Objekt.Type;

/**
 *
 * @author jenni
 */
public class UpdateKopiaController extends Controllers {

    @FXML
    private Text lblTitel;
    @FXML
    private ComboBox comboCategory;
    @FXML
    private Label lblStreckkod;
    @FXML
    private TextField txtPlacement;
    @FXML
    private Label lblWarning;
    @FXML
    private Button btnUpdate;
    @FXML
    private TableView tblAddedCopies;
    @FXML
    private Button btnDeleteKopia;
    @FXML
    private Button btnAvbryt;
    @FXML
    private GridPane grdKopia;

    private final Objekt selectedObjekt;
    private Kopia selectedKopia;
    private final int objektID;
    private final DBConnection connection;
    private final String title;
    private final Type typ;
    private ArrayList<Kopia> listKopior;
    private final Bok bok;

    private ArrayList<Integer> allStreckkod;
    private Alert alert;

    /**
     * Constructor
     *
     * @param objekt
     */
    public UpdateKopiaController(Objekt objekt) {
        connection = DBConnection.getInstance();
        this.selectedObjekt = objekt;
        this.objektID = objekt.getObjektID();
        bok = connection.getBokFromDB(this.objektID);
        this.title = objekt.getTitel();
        this.typ = objekt.getType();
        this.listKopior = connection.getObjektCopies(objekt, typ);
        this.allStreckkod = listAllISBN(bok, connection);
    }

    public void initialize() {

        //Listener to make sure only numbers are added in streckkod filed. 
        lblStreckkod.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                if (!newValue.matches("\\d*")) {
                    lblStreckkod.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        //Listener to make sure all fields are filled in before a copy can be 
        //added to the list
        ChangeListener<String> allFieldsListener = ((observable, oldValue, newValue) -> {
            if (!(lblStreckkod.getText().equalsIgnoreCase(""))
                    && allStreckkod.contains(
                            Integer.parseInt(lblStreckkod.getText()))) {
                btnUpdate.setDisable(true);
                lblWarning.setText("Streckkod finns redan");
            } else if (lblStreckkod.getText() == null
                    || lblStreckkod.getText().equals("")
                    || txtPlacement.getText() == null
                    || txtPlacement.getText().equals("")
                    || comboCategory.getValue() == null
                    || allStreckkod.contains(Integer.parseInt(
                            lblStreckkod.getText()))) {
                btnUpdate.setDisable(true);
                lblWarning.setText("");
            } else {
                btnUpdate.setDisable(false);
                lblWarning.setText("");
            }
        });
        lblStreckkod.textProperty().addListener(allFieldsListener);
        txtPlacement.textProperty().addListener(allFieldsListener);
        comboCategory.valueProperty().addListener(allFieldsListener);

        //Listener to open upp a Kopia for editing when it is chosen. 
        tblAddedCopies.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                this.selectedKopia = (Kopia) tblAddedCopies.getSelectionModel().getSelectedItem();
                grdKopia.setDisable(false);
                lblStreckkod.setText(Integer.toString(selectedKopia.getStreckkod()));
                comboCategory.setValue(selectedKopia.getLoanKategori());
                txtPlacement.setText(selectedKopia.getPlacement());
            } else {
                this.selectedKopia = null;
                grdKopia.setDisable(true);
                lblStreckkod.setText("");
                comboCategory.setValue("");
                txtPlacement.setText("");
            }
        });

        lblTitel.setText(objektID + " - " + title);

        //Get Categories and add them to comboBox. 
        setComboCategories();
        updateTableView(tblAddedCopies, listKopior);
    }

    /**
     * Delets the selected Kopia. Asks for confirmation before deleting.
     *
     * @param event
     */
    @FXML
    void pressDeleteKopia(ActionEvent event) {
        this.selectedKopia = (Kopia) tblAddedCopies.getSelectionModel().getSelectedItem();
        //If Kopia on loan
        if (selectedKopia.getAccess() == Kopia.AccessKopia.ON_LOAN) {
            simpleInfoAlert("Kopian är utlånad och kan därför inte tas bort");
            return;
        }

        //Do you really want to delete?
        alert = new Alert(Alert.AlertType.CONFIRMATION, "Kopia "
                + selectedKopia.getStreckkod()+ " kommer att tas bort permanent.\n"
                + "Lån förknippade med kopian tas bort permanent");
        //Set answers and get answers from user
        ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Ta bort permanent");
        ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Avbryt");
        Optional<ButtonType> result = alert.showAndWait();
        //Delete if answer is OK
        if (result.isPresent() && result.get() == ButtonType.OK) {
            //If Objekt was deleted successfully, show message and close. 
            if (connection.deleteKopia(selectedKopia.getStreckkod())) {
                simpleInfoAlert("Kopian raderades framgångsrikt.");
                listKopior.remove(selectedKopia);
                updateTableView(tblAddedCopies, listKopior);

                //If something went wrong, show message. 
            } else {
                simpleErrorAlert("Något gick fel, objektet raderades inte.");
            }
        }

    }

    /**
     * Updates the chosen Kopia. Does not check if any changes were made.
     *
     * @param event
     */
    @FXML
    void pressUpdate(ActionEvent event) {

        if (connection.updateBokKopia(Integer.parseInt(lblStreckkod.getText()),
                comboCategory.getValue().toString().split(",")[0], txtPlacement.getText())) {
            simpleInfoAlert("Kopian uppdaterades");
            //Uppdate list of copies in the table
            this.listKopior = connection.getObjektCopies(selectedObjekt, typ);
            updateTableView(tblAddedCopies, listKopior);
        } else {
            simpleErrorAlert("Något gick fel\nKopian uppdaterades inte.");
        }
    }

    @FXML
    void pressBtnAvbryt(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    /**
     * Decides what categories the Kopia can have, depending on the Objekt.
     *
     * @param objektID
     * @param title
     */
    private void setComboCategories() {
        if (this.typ == Type.Bok) {
            comboCategory.getItems().addAll("Bok, 30 dagar",
                    "Kurslitteratur, 14 dagar",
                    "Referenslitteratur, 0 dagar");
        } else if (this.typ == Type.Film) {
            comboCategory.getItems().addAll("Film, 7 dagar",
                    "Kurslitteratur, 14 dagar",
                    "Referenslitteratur, 0 dagar");
        } else {
            comboCategory.getItems().addAll("Referenslitteratur, 0 dagar");
        }
    }
}
