package org.biblioteket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
import org.biblioteket.Objects.Objekt;

/**
 *
 * @author jenni
 */
public class NewObjektController extends Controllers {

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
    private Button btnCreate;
    @FXML
    private Label lblWarning;

    //Lists of all the authors/words
    private ArrayList<String> authorsList;
    private ArrayList<String> swList;

    //Lists of selected authors/words
    private final ArrayList<String> selectAuthors = new ArrayList<>();
    private final ArrayList<String> selectSearchWords = new ArrayList<>();

    //List of all ISBN
    private ArrayList<Integer> allISBN = new ArrayList<>();
    
    private int newObjektID;
    private Bok newBok;

    private DBConnection connection;
    private Alert alert;

    
    public void initialize() {
        connection = DBConnection.getInstance();
        
        //authors
        authorsList = connection.getAllAuthors();
        Collections.sort(authorsList);
        comboAuthors.getItems().addAll(authorsList);
       
        //search words
        swList = connection.getAllSearchWords();
        Collections.sort(swList);
        comboSearchWords.getItems().addAll(swList);

        allISBN = connection.getAllISBN();

        //Listener that ensures that the ISBN is on the right format and
        //doesn't already exist. 
        txtISBN.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                if (!newValue.matches("\\d*")) {
                    txtISBN.setText(newValue.replaceAll("[^\\d]", ""));
                }
                if (allISBN.contains(Integer.parseInt(txtISBN.getText()))) {
                    lblWarning.setText("ISBN finns redan");
                    btnCreate.setDisable(true);
                } else if (!(allISBN.contains(Integer.parseInt(txtISBN.getText())))) {
                    lblWarning.setText("");
                    btnCreate.setDisable(false);
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
                btnCreate.setDisable(true);
            } else {
                btnCreate.setDisable(false);
            }
        });

        txtTitle.textProperty().addListener(allFieldsListener);
        txtISBN.textProperty().addListener(allFieldsListener);
        lblAuthor.textProperty().addListener(allFieldsListener);
        lblSearchWord.textProperty().addListener(allFieldsListener);

    }

    @FXML
    void pressAddAuthor(ActionEvent event) {
        addComboWordToList(comboAuthors, selectAuthors, lblAuthor);
    }

    @FXML
    void pressRemoveAuthor(ActionEvent event) {
        removeComboWordFromList(comboAuthors, selectAuthors, lblAuthor);
    }

    @FXML
    void pressAddSearchWord(ActionEvent event) {
        addComboWordToList(comboSearchWords, selectSearchWords, lblSearchWord);
    }

    @FXML
    void pressRemoveSearchWord(ActionEvent event) {
        removeComboWordFromList(comboSearchWords, selectSearchWords, lblSearchWord);
    }

    @FXML
    void pressCreate(ActionEvent event) {
        newBok();
    }

    /**
     * Creates a Bok. 
     * Sends messages to the user. 
     * Can send the user to the page for adding Kopia. 
     * @param event 
     */
    private void newBok() {
        newBok = connection.newBok(txtTitle.getText(), Integer.parseInt(txtISBN.getText()), selectAuthors, selectSearchWords);
     
        if (newBok != null) {
            this.newObjektID = newBok.getObjektID();
            alert = new Alert(AlertType.CONFIRMATION, "Objekt " + this.newObjektID
                    + " skapades\nVill du lägga till kopior?");
            ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Skapa kopior");
            ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Avsluta");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                //NewKopiaController vill ha ett objekt och ingen bok. 
                Objekt objekt = newBok;
                loadPage("NewKopia.fxml", new NewKopiaController(objekt), borderPane);
            }
        } else {
            simpleErrorAlert("Något gick fel.\nObjektet skapades inte");   
        }
    }
}
