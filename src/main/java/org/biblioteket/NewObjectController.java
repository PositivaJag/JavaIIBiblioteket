package org.biblioteket;

import java.util.ArrayList;
import java.util.Collections;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.biblioteket.Database.DBConnection;
import org.biblioteket.Objects.Objekt;

public class NewObjectController {

    @FXML
    private Text lblTitel;

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

    
    //Lists of all the authors/words
    private ArrayList<String> authorsList;
    private ArrayList<String> swList;
    //Lists of selected authors/words
    private ArrayList<String> selectAuthors = new ArrayList<>();
    private ArrayList<String> selectSearchWords = new ArrayList<>();
    DBConnection connection;

    public void initialize(){
       connection = DBConnection.getInstance();
       
       authorsList = connection.getAllAuthors();
       Collections.sort(authorsList);
       comboAuthors.getItems().addAll(authorsList);
       
       swList = connection.getAllSearchWords();
       Collections.sort(swList);
       comboSearchWords.getItems().addAll(swList);
      
       txtISBN.textProperty().addListener(new ChangeListener<String>() {
    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, 
        String newValue) {
        if (!newValue.matches("\\d*")) {
            txtISBN.setText(newValue.replaceAll("[^\\d]", ""));
        }
    }
});
       
    }
    
    @FXML
    void checkIfISBNExists(ActionEvent event) {

    }

//    @FXML
//    void selectAuthorFromList(ActionEvent event) {
//        String value = comboAuthors.getValue().toString();
//        authorsList.add(value);
//        txtAutors.setText(ListToString(authorsList));

//    }

     @FXML
    void pressAddSearchWord(ActionEvent event) {
        comboWordToText(comboSearchWords, selectSearchWords, lblSearchWord);
      
    }

    @FXML
    void pressAddAuthor(ActionEvent event) {
        comboWordToText(comboAuthors, selectAuthors, lblAuthor);
    }

    @FXML
    void pressRemoveAuthor(ActionEvent event) {
        comboRemoveWordFromText(comboAuthors, selectAuthors, lblAuthor);
    }

    @FXML
    void pressRemoveSearchWord(ActionEvent event) {

    }

        @FXML
    void pressCreate(ActionEvent event) {
        newBok();

    }

//    private String ListToString(ArrayList<String> list) {
//        String output = "";
//        for (int i = 0; i < list.size(); i++) {
//            if (output == "") {
//                output += list.get(i);
//            } else {
//                output += ", " + list.get(i);
//            }
//        }
//        return output;
//    }


private void comboWordToText(ComboBox selectedWord, ArrayList<String> list, Label text){
      String word = selectedWord.getValue().toString();
        if (word != null){
            if (list.contains(word)){
                System.out.println(word +" aldready in selectSearchWords");
            }
            else{
                list.add(word);
            }
        }
        text.setText(Util.listToString(list));

}

    private void comboRemoveWordFromText(ComboBox selectedWord, ArrayList<String> list, Label text) {
        String word = selectedWord.getValue().toString();
         if (word != null){
            if (list.contains(word)){
                list.remove(word);
            }
    }
         text.setText(Util.listToString(list));
    }
    
    private void newBok(){
        connection.newBok(txtTitle.getText(), Integer.parseInt(txtISBN.getText()));
    }
}