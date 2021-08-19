package org.biblioteket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import org.biblioteket.Database.DBConnection;
import org.biblioteket.Objects.Bok;
import org.biblioteket.Objects.Kopia;
import org.biblioteket.Objects.Objekt;
import org.biblioteket.Objects.Objekt.Type;

/**
 * The class updates Objekts (Bok, Tidskrift, Film)
 *
 * @author jenni
 */
public class UpdateObjektController extends Controllers {

    //FXML variables
    @FXML
    private BorderPane borderPane;
    @FXML
    private TextField txtTitle;
    @FXML
    private TextField txtISBN;
    @FXML
    private Button btnRemoveSearchWords;
    @FXML
    private Button btnRemoveAuthor;
    @FXML
    private ComboBox comboSearchWords;
    @FXML
    private Button btnAddSearchWords;
    @FXML
    private ComboBox comboAuthors;
    @FXML
    private Button btnAddAuthor;
    @FXML
    private Label lblAuthor;
    @FXML
    private Label lblSearchWord;
    @FXML
    private Label lblObjektID;
    @FXML
    private Button btnUpdate;
    @FXML
    private Label lblWarning;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnDelete;

    //Lists with all authors/search words. 
    private ArrayList<String> authorsList;
    private ArrayList<String> swList;

    //Lists with selected authors/search words. 
    private ArrayList<String> selectAuthors;
    private ArrayList<String> selectSearchWords;

    //Lists with all ISBN numbers. 
    private ArrayList<Integer> allISBN = new ArrayList<>();

    //Info about selected Objekt. 
    private final int objektID;
    private Objekt selectedObjekt;
    private Bok bok;


    DBConnection connection;
    Alert alert;

    /**
     * Construktor
     *
     * @param selectedObjekt
     */
    public UpdateObjektController(Objekt selectedObjekt) {
        this.selectedObjekt = selectedObjekt;
        this.objektID = selectedObjekt.getObjektID();
    }

    /**
     * Initialize Creates contact with DB. Reads info from the selected Objekt.
     * Creates listeners.
     */
    public void initialize() {
        connection = DBConnection.getInstance();
        setGeneralSettings();

        //Listener that ensures that the ISBN is on the right format and
        //doesn't areadu exist. 
        txtISBN.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                if (!newValue.matches("\\d*")) {
                    txtISBN.setText(newValue.replaceAll("[^\\d]", ""));
                }
                if (allISBN.contains(Integer.parseInt(txtISBN.getText()))) {
                    lblWarning.setText("ISBN finns redan");
                    btnUpdate.setDisable(true);
                } else if (!(allISBN.contains(Integer.parseInt(txtISBN.getText())))) {
                    lblWarning.setText("");
                    btnUpdate.setDisable(false);
                }
            }
        });

        //Listener that ensures that all fields are filled in before the update
        //button becomes available.     
        ChangeListener<String> allFieldsListener = ((observable, oldValue, newValue) -> {
            if (txtTitle.getText() == null
                    || txtTitle.getText().equals("")
                    || txtISBN.getText() == null
                    || txtISBN.getText().equals("")
                    || selectSearchWords.isEmpty()
                    || selectAuthors.isEmpty()) {
                btnUpdate.setDisable(true);
            } else {
                btnUpdate.setDisable(false);
            }
        });
        txtTitle.textProperty().addListener(allFieldsListener);
        txtISBN.textProperty().addListener(allFieldsListener);
        lblAuthor.textProperty().addListener(allFieldsListener);
        lblSearchWord.textProperty().addListener(allFieldsListener);
    }

    /**
     * Calls the function to add authors.
     *
     * @param event
     */
    @FXML
    void pressAddAuthor(ActionEvent event) {
        addComboWordToList(comboAuthors, selectAuthors, lblAuthor);
    }

    /**
     * Calls the function to remove authors.
     *
     * @param event
     */
    @FXML
    void pressRemoveAuthor(ActionEvent event) {
        removeComboWordFromList(comboAuthors, selectAuthors, lblAuthor);
    }

    /**
     * Calls the function to add search words.
     *
     * @param event
     */
    @FXML
    void pressAddSearchWord(ActionEvent event) {
        addComboWordToList(comboSearchWords, selectSearchWords, lblSearchWord);
    }

    /**
     * Calls the function to remove search words.
     *
     * @param event
     */
    @FXML
    void pressRemoveSearchWord(ActionEvent event) {
        removeComboWordFromList(comboSearchWords, selectSearchWords, lblSearchWord);
    }

    /**
     * Calls function to update the Objekt.
     *
     * @param event
     */
    @FXML
    void pressUpdate(ActionEvent event) {
        
        Bok updateBok = connection.updateBok(objektID, txtTitle.getText(),
                Integer.parseInt(txtISBN.getText()), selectAuthors,
                selectSearchWords);
        
        //if Bok instance was returned aka update succesed.
        //Show message and close down
        if (updateBok != null) { 
            Util.simpleInfoAlert("Objekt "+Integer.toString(objektID)+" uppdaterades");
            ((Node) (event.getSource())).getScene().getWindow().hide();
            
        //if no Bok instance was returned aka update failed
        //Show message.
        } else {
            Util.simpleErrorAlert("Något gick fel.\nObjektet uppdaterades inte");
        }
    }

    /**
     * Calls function to delete Objekt. 
     * @param event 
     */
    @FXML
    void pressDelete(ActionEvent event) {
        //Get all Kopia connected to the objekt. 
        ArrayList<Kopia> listKopia = connection.getObjektCopies(selectedObjekt, Type.Bok);
        int noKopia = 0; 
        // Chekc if there are Kopior connected to the Objekt
        if (listKopia != null) { 
            noKopia = listKopia.size();
            //Check if Kopia is on loan and can´t be deleted.
            if (checkIfCopyOnLoan(listKopia)) { 
                Util.simpleErrorAlert("En kopia är utlånad. Objektet kan därför inte tas bort.");
                return;
            }
        }

        //Do you really want to delete?
        alert = new Alert(AlertType.CONFIRMATION, "Objekt "
                + objektID + ", " + selectedObjekt.getTitel() 
                + ", kommer att tas bort permanent.\n"
                + noKopia + " kopior kommer att tas bort permanent\n"
                + "Lån förknippade med kopiorna tas bort permanent");
        //Set answers and get answers from user
        ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Ta bort permanent");
        ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Avbryt");
        Optional<ButtonType> result = alert.showAndWait();
        //Delete if answer is OK
        if (result.isPresent() && result.get() == ButtonType.OK) {
            //If Objekt was deleted successfully, show message and close. 
            if (connection.deleteBokObjekt(objektID)) {
                Util.simpleInfoAlert("Objektet raderades framgångsrikt.");
                ((Node) (event.getSource())).getScene().getWindow().hide();
                
            //If something went wrong, show message. 
            } else {
                Util.simpleErrorAlert("Något gick fel, objektet raderades inte.");
            }
        }
    }

    /**
     * Aborts and closes the pop-up.
     *
     * @param event
     */
    @FXML
    void pressCancel(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

   

    /**
     * returns the title as string.
     *
     * @return
     */
    public String getTitle() {
        return txtTitle.getText();
    }

    /**
     * Sets variables used it the UI. E.g. Objekt variables, lists of authors,
     * search words and ISBN numbers.
     */
    public void setGeneralSettings() {
        //Create a Bok instance. 
        bok = connection.getBokFromDB(this.objektID);

        // authors
        authorsList = connection.getAllAuthors();
        Collections.sort(authorsList);
        comboAuthors.getItems().addAll(authorsList);

        //search words. 
        swList = connection.getAllSearchWords();
        Collections.sort(swList);
        comboSearchWords.getItems().addAll(swList);

        showBok(bok);

        listAllISBN(bok, connection);
    }

    private void showBok(Bok bok) {

        selectAuthors = bok.getAuthors();
        lblAuthor.setText(Util.listToString(selectAuthors));

        selectSearchWords = bok.getSearchWordsAsList();
        lblSearchWord.setText(Util.listToString(selectSearchWords));

        lblObjektID.setText(Integer.toString(objektID));
        txtTitle.setText(selectedObjekt.getTitel());
        txtISBN.setText(Integer.toString(bok.getISBN()));

        listAllISBN(bok, connection);
    }
    
    private Boolean checkIfCopyOnLoan(ArrayList<Kopia> listKopia) {
        for (int i = 0; i < listKopia.size(); i++) {
            //Check if each Kopia has any active loan. 
            if (connection.getActiveLoan(listKopia.get(i).getStreckkod()) != null) {
                return true;
            }
        }
        //If no Kopia had active loan, return false. 
        return false;
    }

   
}
