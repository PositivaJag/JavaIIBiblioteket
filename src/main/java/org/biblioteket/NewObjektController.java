package org.biblioteket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.biblioteket.Database.DBConnection;
import javafx.event.*;
import org.biblioteket.Objects.Bok;
import org.biblioteket.Objects.Objekt;

/**
 *
 * @author jenni
 */
public class NewObjektController {

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

    DBConnection connection;

    /**
     *
     */
    public void initialize() {
        connection = DBConnection.getInstance();

        authorsList = connection.getAllAuthors();
        Collections.sort(authorsList);
        comboAuthors.getItems().addAll(authorsList);

        swList = connection.getAllSearchWords();
        Collections.sort(swList);
        comboSearchWords.getItems().addAll(swList);

        allISBN = connection.getAllISBN();

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

    //FXML functions
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
        newBok(event);
    }

    //Insert/update functions
    private void newBok(ActionEvent event) {

        newBok = connection.newBok(txtTitle.getText(), Integer.parseInt(txtISBN.getText()), selectAuthors, selectSearchWords);
     
        Alert alert;
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
                loadPageNewKopia(event, objekt );
            }
        } else {
            alert = new Alert(AlertType.ERROR);
            alert.setContentText("Något gick fel.\nObjektet skapades inte");
            alert.show();
        }

//                    if (button == ButtonType.OK) {
//                loadPopup("NewKopia.fxml");
//                Stage stage = App.getMainControll().getSearchController().getNewObjektStage();
//                stage.hide();
        //Get the button that was pressed. 
//            Optional<ButtonType> result = alert.showAndWait();
//            ButtonType button = result.orElse(ButtonType.OK);
//            }
    }

    //Other functions
    private void addComboWordToList(ComboBox selectedWord, ArrayList<String> list, Label text) {
        String word = selectedWord.getValue().toString();
        if (word != null) {
            if (list.contains(word)) {
                System.out.println(word + " aldready in selectSearchWords");
            } else {
                list.add(word);
            }
        }
        text.setText(Util.listToString(list));
    }

    private void removeComboWordFromList(ComboBox selectedWord, ArrayList<String> list, Label text) {
        String word = selectedWord.getValue().toString();
        if (word != null) {
            if (list.contains(word)) {
                list.remove(word);
            }
        }
        text.setText(Util.listToString(list));
    }

    /**
     *
     * @param event
     * @param objekt
     * @return
     */
    public boolean loadPageNewKopia (ActionEvent event, Objekt objekt ){

        try {

             FXMLLoader loader = new FXMLLoader(getClass().getResource("NewKopia.fxml"));
            
            NewKopiaController controller = new NewKopiaController(objekt);
            loader.setController(controller);
            Parent root = loader.load();
            borderPane.setCenter(root);

            return true;

        } catch (IOException ex) {
            System.out.println("Exception i klass NewObjektController.java, i "
                    + "metoden loadPage()");
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

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

    //Setters

    /**
     *
     * @param bool
     */
    public void setDisableBtnCreate(Boolean bool) {
        this.btnCreate.setDisable(bool);
    }

    /**
     *
     * @param text
     */
    public void setTextLblWarning(String text) {
        this.lblWarning.setText(text);
    }

    /**
     *
     * @return
     */
    public int getNewObjektID() {
        return newObjektID;
    }

    /**
     *
     * @param newObjektID
     */
    public void setNewObjektID(int newObjektID) {
        this.newObjektID = newObjektID;
    }
    
    /**
     *
     * @return
     */
    public String getTitle(){
        return txtTitle.getText();
    }
    
}
