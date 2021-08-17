package org.biblioteket;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class UpdateKopiaController {

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
    private int objektID;
    private Objekt selectedObjekt;
    private Bok bok;

    //Connections to DB.
    DBConnection connection;

    /**
     * Construktor
     *
     * @param selectedObjekt
     */
    public UpdateKopiaController(Objekt selectedObjekt) {
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
         Alert alert;
        if (updateBok != null) {
           
            alert = new Alert(AlertType.INFORMATION, "Objekt " + Integer.toString(objektID)
                    + " uppdaterades");
            alert.showAndWait();
            ((Node) (event.getSource())).getScene().getWindow().hide();

        } else {
            alert = new Alert(AlertType.ERROR);
            alert.setContentText("Något gick fel.\nObjektet uppdaterades inte");
            alert.show();
        }

    }

    @FXML
    void pressDelete(ActionEvent event) {
        //Check if Objekt is on loan and can´t be deleted. 
        //Get all Kopia connected to the objekt. 
        ArrayList<Kopia> listKopia = connection.getObjektCopies(selectedObjekt, Type.Bok);
        int noKopia = 0;
        if (listKopia != null) { // If there are Kopia connected to the Objekt
            noKopia = listKopia.size();
            if (checkIfCopyOnLoan(listKopia)) { // Is any Kopia on loan?
                Alert alert = new Alert(AlertType.ERROR, "En kopia är utlånad. "
                        + "Objektet kan därför inte tas bort.");
                alert.showAndWait();
                return;
            }
        }

       String title = selectedObjekt.getTitel();

        //Do you really want to delete.
        Alert alert = new Alert(AlertType.CONFIRMATION, "Objekt "
                +objektID+", "+title+", kommer att tas bort permanent.\n"
                +noKopia+" kopior kommer att tas bort permanent");
            ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Ta bort permanent");
            ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Avbryt");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                //Delete objekt
                if (connection.deleteBokObjekt(objektID)){
                   alert = new Alert(AlertType.INFORMATION, "Objektet "
                    + "raderades framgångsrikt.");
                   alert.showAndWait();
                   ((Node) (event.getSource())).getScene().getWindow().hide();
                }
                else{
                    alert = new Alert(AlertType.ERROR, "Något gick fel, "
                            + "objektet raderades inte.");
                   alert.show();}
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

//        Alert alert;
//        //Om 
//        if (selectedObjekt != null) {
//            this.newObjektID = selectedObjekt.getObjektID();
//            alert = new Alert(AlertType.CONFIRMATION, "Objekt " + this.newObjektID
//                    + " skapades\nVill du lägga till kopior?");
//            ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Skapa kopior");
//            ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Avsluta");
//
//            Optional<ButtonType> result = alert.showAndWait();
//            if (result.isPresent() && result.get() == ButtonType.OK) {
//                //NewKopiaController vill ha ett objekt och ingen bok. 
//                Objekt objekt = selectedObjekt;
//                loadPageNewKopia(event, objekt );
//            }
//        } else {
//            alert = new Alert(AlertType.ERROR);
//            alert.setContentText("Något gick fel.\nObjektet skapades inte");
//            alert.show();
//        }
//                    if (button == ButtonType.OK) {
//                loadPopup("NewKopia.fxml");
//                Stage stage = App.getMainControll().getSearchController().getNewObjektStage();
//                stage.hide();
    //Get the button that was pressed. 
//            Optional<ButtonType> result = alert.showAndWait();
//            ButtonType button = result.orElse(ButtonType.OK);
//            }
    /**
     * Adds chosen word, from a combobox, to a list and prints the list in a
     * label.
     *
     * @param selectedWord, the name of the combobox where a word was chosen.
     * @param list of already selected words.
     * @param text, the lable where the result should be printed out.
     */
    private void addComboWordToList(ComboBox selectedWord, ArrayList<String> list,
            Label text) {
        //get the selected word from the ComboBox. 
        String word = selectedWord.getValue().toString();
        //Check if the word is aldready in the list before adding it. 
        if (word != null) {
            if (list.contains(word)) {
                System.out.println(word + " aldready in selectSearchWords");
            } else {
                list.add(word);
            }
        }
        //Call function  that creates a string from the list
        //Print the list in the text label. 
        text.setText(Util.listToString(list));
    }

    /**
     * Removes chosen word, from a combobox, from a list and prints the list in
     * a label.
     *
     * @param selectedWord, the name of the combobox where a word was chosen.
     * @param list of already selected words.
     * @param text, the lable where the result should be printed out.
     */
    private void removeComboWordFromList(ComboBox selectedWord, ArrayList<String> list, Label text) {
        //get the selected word from the ComboBox.
        String word = selectedWord.getValue().toString();
        //Remove word if it was in the list. 
        if (word != null) {
            if (list.contains(word)) {
                list.remove(word);
            }
        }
        //Call function  that creates a string from the list
        //Print the list in the text label. 
        text.setText(Util.listToString(list));
    }

//    /**
//     *
//     * @param event
//     * @param objekt
//     * @return
//     */
//    public boolean loadPageNewKopia(ActionEvent event, Objekt objekt) {
//
//        try {
//
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("NewKopia.fxml"));
//
//            NewKopiaController controller = new NewKopiaController(objekt);
//            loader.setController(controller);
//            Parent root = loader.load();
//            borderPane.setCenter(root);
//
//            return true;
//
//        } catch (IOException ex) {
//            System.out.println("Exception i klass NewObjektController.java, i "
//                    + "metoden loadPage()");
//            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
//            return false;
//        }
//
//    }
//    public boolean loadPopup(String fxml) {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
//            Parent root = loader.load();
//
//            Stage stage = new Stage();
//            Scene scene = new Scene(root);
//            stage.setScene(scene);
//            stage.show();
//            return true;
//
//        } catch (IOException ex) {
//            Logger.getLogger(SearchController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return false;
//    }
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
    private void setGeneralSettings() {
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

        listAllISBN(bok);
    }

    private void listAllISBN(Bok bok) {
        //get all ISBN numbers.
        allISBN = connection.getAllISBN();
        //then remove the ISBN of bok to make sure that that number is 
        //allowed to be added. 
        allISBN.remove(Integer.valueOf(bok.getISBN()));

    }

    private void showBok(Bok bok) {

        selectAuthors = bok.getAuthors();
        lblAuthor.setText(Util.listToString(selectAuthors));

        selectSearchWords = bok.getSearchWordsAsList();
        lblSearchWord.setText(Util.listToString(selectSearchWords));

        lblObjektID.setText(Integer.toString(objektID));
        txtTitle.setText(selectedObjekt.getTitel());
        txtISBN.setText(Integer.toString(bok.getISBN()));

        listAllISBN(bok);
    }

    private void UpdateBok() {

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
