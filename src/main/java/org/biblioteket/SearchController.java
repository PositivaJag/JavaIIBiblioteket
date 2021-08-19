package org.biblioteket;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import static javafx.collections.FXCollections.observableList;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.biblioteket.Database.DBConnection;
import org.biblioteket.Objects.Objekt;
import org.biblioteket.Objects.Objekt.Type;

/**
 * Anyone can serach for Objekts and Kopia, ibrarians can handle Kopia and
 * Objekt.
 *
 * @author jenni
 */
public class SearchController extends Controllers {

    @FXML
    private TextField txtSearch;
    @FXML
    private Button btnSearch;
    @FXML
    private ComboBox comboType;
    @FXML
    private TableView tblSearch;
    @FXML
    private VBox vboxObjekt;
    @FXML
    private Button btnNyttObjekt;
    @FXML
    private Button btnUpdateObjekt;
    @FXML
    private VBox vboxKopia;
    @FXML
    private Button btnAddKopior;
    @FXML
    private Button btnUpdateKopia;
    @FXML
    private VBox vboxLoan;
    @FXML
    private Button btnLateLoans;
    @FXML
    private Button btnDetails;

    //Lists with types of objekts. 
    private ArrayList<Objekt> objekts;
    private ObservableList<Objekt> observableObjekts;
    private Objekt selectedObjekt;
    private DBConnection connection;

    public void initialize() {
        connection = DBConnection.getInstance();

        //Set start values
        setLibrarianButtons();
        setComboType();
        updateTableView(getObjekts());
        addTextFilter(observableObjekts, txtSearch, tblSearch);

//        Listener that updates the table when a new value has been chosen in 
//          the combo box. 
        comboType.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                updateTableView(getObjekts());
                addTextFilter(observableObjekts, txtSearch, tblSearch);
            }
        });
    }

    /**
     * Lets the user search in the Objekt table. 
     * @param event 
     */
    @FXML
    void pressBtnSearch(ActionEvent event) {
        addTextFilter(observableObjekts, txtSearch, tblSearch);
    }
    
/**
 * Opens a popup with the Kopias that are associated to the selected Objekt. 
 * @param event 
 */
    @FXML
    void pressDetalis(ActionEvent event) {
        //Get selected item from list
        setSelectedObjekt();
        //Create popup and save as correct object. 
        loadPopup("Kopia.fxml", (Object) new KopiaController(this.selectedObjekt));
    }
    
        @FXML
    void pressNyttObjekt(ActionEvent event) {
        loadPopup("NewObjekt.fxml");
    }
    
     @FXML
    void pressAddKopior(ActionEvent event) {
        //Get selected item from list
        setSelectedObjekt();
        loadPopup("NewKopia.fxml", new NewKopiaController(selectedObjekt));
    }
    
    /**
     * Sends the Objekt selected in the table to the update Objekt page. Can
     * only handle Bok objekt at the moment, not Film or Tidskrift.
     * @param event
     */
    @FXML
    void pressUpdateObjekt(ActionEvent event) {
        setSelectedObjekt();

        if (selectedObjekt.getType() != Type.Bok) {
            simpleInfoAlert("Programmet kan bara hantera böcker för tillfället.");
        } else {
            loadPopup("UpdateObjekt.fxml", (Object) new UpdateObjektController(this.selectedObjekt));
        }
    }

    /**
     * Sends the Objekt selected in the table to the update Kopia page. Can only
     * handle Bok objekt at the moment, not Film or Tidskrift.
     * @param event
     */
    @FXML
    void pressUpdateKopia(ActionEvent event) {
        setSelectedObjekt();
        //If selectedObjekt not Bok, show message
        if (selectedObjekt.getType() != Type.Bok) {
            simpleInfoAlert("Programmet kan bara hantera böcker för tillfället.");
        } //If the Objekt has no Kopia to update, show message. 
        else if (connection.getObjektCopies(selectedObjekt, 
                selectedObjekt.getType()) == null) {
            simpleInfoAlert("Titeln " + selectedObjekt.getTitel() 
                    + "har inga kopior att uppdatera.");
        } else {
            loadPopup("UpdateKopia.fxml", (Object) new UpdateKopiaController(this.selectedObjekt));
        }
    }
    
    /**
     * Opens a popup where the user can add new Kopias to the Objekt selected
     * in the search table. 
     * @param event 
     */
    @FXML
    void pressLateLoans(ActionEvent event) {
        loadPopup("LateLoans.fxml");
    }

    /**
     * If a librarian is logged in, show the buttons for creating and updating
     * Objekt and kopia and to see late loans. 
     */
    private void setLibrarianButtons() {
        Boolean bool = App.getMainControll().checkIfLibrarianLoggedIn();
        vboxObjekt.setVisible(bool);
        vboxKopia.setVisible(bool);
        vboxLoan.setVisible(bool);
    }

    //Table 
    /**
     * Fills the table with Objekt from DB. 
     * @param result
     */
    private void updateTableView(List<Objekt> result) {
        //Clean table
        tblSearch.getColumns().clear();

        //Create ObservableList from list of objects. 
        observableObjekts = observableList(result);
        
        TableColumn<Objekt, String> colObjektID = new TableColumn<>("ObjektID");
        TableColumn<Objekt, String> colTitel = new TableColumn<>("Titel");
        TableColumn<Objekt, String> colTyp = new TableColumn<>("Typ");
        TableColumn<Objekt, String> colCreator = new TableColumn<>("Författare/artister");
        TableColumn<Objekt, String> colSearchWord = new TableColumn<>("Sökord");

        colObjektID.setCellValueFactory(new PropertyValueFactory<>("objektID"));
        colTitel.setCellValueFactory(new PropertyValueFactory<>("titel"));
        colTyp.setCellValueFactory(new PropertyValueFactory<>("type"));
        colCreator.setCellValueFactory(new PropertyValueFactory<>("creators"));
        colSearchWord.setCellValueFactory(new PropertyValueFactory<>("searchWords"));

        tblSearch.getColumns().addAll(colObjektID, colTitel, colTyp, colCreator, colSearchWord);
        tblSearch.setItems(observableObjekts);

        selectFirstEntry();
    }

    /**
     * Retrieves all Objekts from DB and adds them to an ArrayList. 
     * @return
     */
    private ArrayList<Objekt> getObjekts() {
        try {
            //gets an arraylist with objects
            objekts = connection.getObjektsFromDB(comboType.getValue().toString());

        } catch (Exception ex) {
            Logger.getLogger(SearchController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return objekts;
    }

    /**
     * Handles searching in the table. 
     * This function could use some more work. 
     * 
     * @param <Objekt>
     * @param observList
     * @param txtSearch
     * @param tblSearch
     */
    private static <Objekt> void addTextFilter(ObservableList<Objekt> observList,
            TextField txtSearch, TableView tblSearch) {

        // skapa en lista med tabellens kolumner    
        List<TableColumn<Objekt, ?>> columns = tblSearch.getColumns();

        FilteredList<Objekt> filteredData = new FilteredList<>(observList);
        filteredData.predicateProperty().bind(Bindings.createObjectBinding(() -> {
            // läser av textfältet (och kontrollerarr om det är tomt)
            String text = txtSearch.getText();

            if (text == null || text.isEmpty()) {
                return null;
            }
            // gör söksträngen till gemener
            final String filterText = text.toLowerCase();

            return o -> {
                // går igenom alla kolumner
                for (TableColumn<Objekt, ?> col : columns) {
                    ObservableValue<?> observable = col.getCellObservableValue(o);
                    if (observable != null) {
                        // läser in värde ...
                        Object value = observable.getValue();
                        // och jämför med textfältets värde (lowercase)
                        if (value != null && value.toString().toLowerCase().contains(filterText)) {
                            return true;
                        }
                    }
                }
                return false;
            };
        }, txtSearch.textProperty()));

        SortedList<Objekt> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblSearch.comparatorProperty());
        tblSearch.setItems(sortedData);
    }

    private void selectFirstEntry() {
        tblSearch.getSelectionModel().selectFirst();
    }

    private void setComboType() {
        comboType.getItems().add("Alla");
        comboType.getItems().addAll(connection.getObjektTypes());
        comboType.setValue("Alla");
    }

    private void setSelectedObjekt() {
        this.selectedObjekt = (Objekt) tblSearch.getSelectionModel().getSelectedItem();
    }
}
