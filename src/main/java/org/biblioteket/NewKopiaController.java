package org.biblioteket;

import java.util.ArrayList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.biblioteket.Database.DBConnection;
import org.biblioteket.Objects.Kopia;
import org.biblioteket.Objects.Objekt;
import org.biblioteket.Objects.Objekt.Type;

/**
 *
 * @author jenni
 */
public class NewKopiaController extends Controllers{
    @FXML
    private Text lblTitel;
    @FXML
    private ComboBox comboCategory;
    @FXML
    private TextField txtStreckkod;
    @FXML
    private TextField txtPlacement;
    @FXML
    private Label lblWarning;
    @FXML
    private Button btnAdd;
    @FXML
    private TableView tblAddedCopies;
    @FXML
    private Button btnCreateCopy;
    @FXML
    private Button btnAvbryt;
    
    private int newObjektID;
    private DBConnection connection;
    private final String title;
    private final Type typ;
    private ArrayList<Kopia> listKopior;
    private ArrayList<Integer> allStreckkod;

    /**
     *Constructor
     * @param objekt
     */
    public NewKopiaController(Objekt objekt) {
        this.newObjektID = objekt.getObjektID();
        this.title = objekt.getTitel();
        this.typ = objekt.getType();
    }

    public void initialize() {
        //Set titel
        lblTitel.setText(newObjektID + " - " + title);

        //Get Categories and add them to comboBox. 
        setComboCategories();

        connection = DBConnection.getInstance();
        allStreckkod = connection.getAllSteckkod();
        listKopior = new ArrayList<>();

        //Listener to make sure only numbers are added in streckkod filed. 
        txtStreckkod.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                if (!newValue.matches("\\d*")) {
                    txtStreckkod.setText(newValue.replaceAll("[^\\d]", ""));

                }
            }
        });

        //Listener to make sure all fields are filled in before a copy can be 
        //added to the list
        ChangeListener<String> allFieldsListener = ((observable, oldValue, newValue) -> {
            if (!(txtStreckkod.getText().equalsIgnoreCase(""))
                    && allStreckkod.contains(
                            Integer.parseInt(txtStreckkod.getText()))) {
                btnAdd.setDisable(true);
                lblWarning.setText("Streckkod finns redan");
            } else if (txtStreckkod.getText() == null
                    || txtStreckkod.getText().equals("")
                    || txtPlacement.getText() == null
                    || txtPlacement.getText().equals("")
                    || comboCategory.getValue() == null
                    || allStreckkod.contains(Integer.parseInt(
                            txtStreckkod.getText()))) {
                btnAdd.setDisable(true);
                lblWarning.setText("");
            } else {
                btnAdd.setDisable(false);
                lblWarning.setText("");
            }
        });

        txtStreckkod.textProperty().addListener(allFieldsListener);
        txtPlacement.textProperty().addListener(allFieldsListener);
        comboCategory.valueProperty().addListener(allFieldsListener);
    }

    /**
     * Adds multiple new Kopia to DB. 
     * @param event 
     */
    @FXML
    void pressAddToDB(ActionEvent event) {
        //Create Kopior, get boolean as answer. 
        Boolean newKopior = connection.newKopior(listKopior);
        //Show messages depending on anser.
        if (newKopior) {
            simpleInfoAlert("Kopiorna skapades\n Fönstret stängs.");
            ((Node) (event.getSource())).getScene().getWindow().hide();
            
        } else {
            simpleErrorAlert("Något gick fel.\nKopiorna skapades inte");
        }
    }
/**
 * Creates a Kopia object from the added values and adds it to the table. 
 * @param event 
 */
    @FXML
    void pressAddToList(ActionEvent event) {
        //Create and add Kopia to list. 
        listKopior.add(new Kopia(Integer.parseInt(txtStreckkod.getText()), newObjektID,
                comboCategory.getValue().toString(), txtPlacement.getText()));
        allStreckkod.add(Integer.parseInt(txtStreckkod.getText()));
        //Enabled the create button when there is a Kopia in the list. 
        btnCreateCopy.setDisable(false);
        updateTableView(tblAddedCopies, listKopior);
        //Makes sure that one can´t add another Kopia with the same streckkod. 
        lblWarning.setText("Streckkod finns redan");
        btnAdd.setDisable(true);
    }

    @FXML
    void pressBtnAvbryt(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    /**
     * Decides what categories the Kopia can have, depending on the Objekt. 
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
