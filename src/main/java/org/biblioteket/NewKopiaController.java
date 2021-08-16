package org.biblioteket;

import java.util.ArrayList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
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
public class NewKopiaController {

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

    int newObjektID;
    DBConnection connection = DBConnection.getInstance();
    String title;
    Type typ;
    NewObjektController newObjektController;
    private ArrayList<Kopia> listKopior = new ArrayList<>();
    ;
    
    ArrayList<Integer> allStreckkod;

    /**
     *
     * @param objekt
     */
    public NewKopiaController(Objekt objekt) {
        this.newObjektID = objekt.getObjektID();
        this.title = objekt.getTitel();
        this.typ = objekt.getType();
    }

    /**
     *
     */
    public void initialize() {
        //Set titel
        lblTitel.setText(newObjektID + " - " + title);

        //Get Categories and add them to comboBox. 
        setComboCategories();

        connection = DBConnection.getInstance();
        allStreckkod = connection.getAllSteckkod();
        System.out.println(allStreckkod);

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

//        ChangeListener<String> createButton = ((observable, oldValue, newValue) -> {
//            if (tblAddedCopies.getItems().isEmpty()){
//                btnCreateCopy.setDisable(true);
//            }
//                else{
//                btnCreateCopy.setDisable(false);
//            }
//        });
        txtStreckkod.textProperty().addListener(allFieldsListener);
        txtPlacement.textProperty().addListener(allFieldsListener);
        comboCategory.valueProperty().addListener(allFieldsListener);
    }

    @FXML
    void pressAddToDB(ActionEvent event) {
        Boolean newKopior = connection.newKopior(listKopior);

        Alert alert;
        if (newKopior) {
            alert = new Alert(Alert.AlertType.INFORMATION, "Kopiorna skapades\n fönstret stängs.");
            alert.showAndWait();
            ((Node) (event.getSource())).getScene().getWindow().hide();
            
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Något gick fel.\nKopiorna skapades inte");
            alert.show();
        }

    }

    @FXML
    void pressAddToList(ActionEvent event) {

        listKopior.add(new Kopia(Integer.parseInt(txtStreckkod.getText()), newObjektID,
                comboCategory.getValue().toString(), txtPlacement.getText()));
        allStreckkod.add(Integer.parseInt(txtStreckkod.getText()));
        btnCreateCopy.setDisable(false);
        System.out.println(allStreckkod);
        Util.updateTableView(tblAddedCopies, listKopior);

        lblWarning.setText("Streckkod finns redan");
        btnAdd.setDisable(true);

    }

    @FXML
    void pressBtnAvbryt(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    /**
     *
     * @param objektID
     * @param title
     */
    public void initData(int objektID, String title) {
        this.newObjektID = objektID;
        this.title = title;
    }

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

    //Table functions
//    private void updateTableView() {
//
//        tblAddedCopies.getColumns().clear();
//
//        Field[] fields = listKopior.get(0).getClass().getDeclaredFields();
//
//        ObservableList<Kopia> observableKopior = FXCollections.observableArrayList(listKopior);
//
//        // För varje fält, skapa en kolumn och lägg till i TableView (fxTable)
//        for (Field field : fields) {
//            System.out.println(field);
//            TableColumn<Map, String> column = new TableColumn<>(field.getName().toUpperCase());
//            column.setCellValueFactory(new PropertyValueFactory<>(field.getName()));
//            tblAddedCopies.getColumns().add(column);
//        }
//        tblAddedCopies.setItems(observableKopior);
//
//    }

}
