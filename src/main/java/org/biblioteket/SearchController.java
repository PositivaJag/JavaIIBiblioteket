package org.biblioteket;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import static javafx.collections.FXCollections.observableList;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.biblioteket.Database.DBConnection;
import org.biblioteket.Objects.Objekt;
import org.biblioteket.Persons.Person.PersonTyp;

public class SearchController {

    //FXML variables
    @FXML
    private TextField txtSearch;
    @FXML
    private Button btnSearch;
    @FXML
    private TableView tblSearch;
    @FXML
    private ComboBox comboType;
    @FXML
    private Button btnDetails;
    @FXML
    private Button btnUpdateObjekt;
    @FXML
    private Button btnNyttObjekt;

    //Other variables
    //List with types of objekts. 
    private ObservableList<String> objektTyp = FXCollections.observableArrayList("Alla");
    private ArrayList<Objekt> result;   //List of Objekts    
    private ObservableList<Objekt> observableResult;  //Observable list with Objekts. 

    private Objekt selectedObjekt;
    private KopiaController kopiaController;    //Controller object
    private SearchController searchController;
    private Parent kopiaRoot;

    public void initialize() {
        if (App.getMainControll().getPersonTyp() == PersonTyp.BIBLIOTEKARIE) {
            setbtnNyttObjektVisibility(true);
            setbtnUpdateObjektVisibility(true);
        } else {
            setbtnNyttObjektVisibility(false);
            setbtnUpdateObjektVisibility(false);
        }
        setComboType();
        updateTableView(getObjekts());
    }

    //FXML functions
    @FXML
    void pressSearchBtn(ActionEvent event) {
        getObjekts();
        updateTableView(result);
        addTextFilter(observableResult, txtSearch, tblSearch);
    }

    @FXML
    void pressUpdateObjekt(ActionEvent event) {
    }

    @FXML
    void pressNyttObjekt(ActionEvent event) {
        loadPopup("NewObjekt.fxml");
    }

    @FXML
    void pressDetalis(ActionEvent event) {
        //Get selected item from list
        selectedObjekt = (Objekt) tblSearch.getSelectionModel().getSelectedItem();
        //Create popup and save as correct object. 
        loadPopupKopia();
    }

    //Load pages
    private void loadPopupKopia() {
        try {
//            if (kopiaRoot == null){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Kopia.fxml"));
            kopiaRoot = loader.load();
            App.getMainControll().getSearchController().setKopiaController(loader.getController());
//            }
            Stage stage = new Stage();
            Scene scene = new Scene(kopiaRoot);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println("Fel i " + this.toString());
            Logger.getLogger(SearchController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Boolean loadPopup(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();

            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            return true;

        } catch (IOException ex) {
            Logger.getLogger(SearchController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    //Set FXML values 
    public void setbtnUpdateObjektVisibility(Boolean bool) {
        btnUpdateObjekt.setVisible(bool);
    }

    public void setLibrarianButtonsVisibility() {
        Boolean bool = App.getMainControll().checkIfLibrarianLoggedIn();
        setbtnUpdateObjektVisibility(bool);
        setbtnNyttObjektVisibility(bool);
    }

    public void setbtnNyttObjektVisibility(Boolean bool) {
        btnNyttObjekt.setVisible(bool);
    }

    //Table 
    private void updateTableView(List<Objekt> result) {
        //Clean table
        tblSearch.getColumns().clear();

        //Create ObservableList from list of objects. 
        observableResult = observableList(result);
//        for (int i = 0; i <observableResult.size(); i++){
//            System.out.println(observableResult.get(i));
//        }
        //Get felds from first Object. 
        //Skapa tableColumns
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
        tblSearch.setItems(observableResult);

        selectFirstEntry();
    }

    private ArrayList<Objekt> getObjekts() {

        DBConnection connection = DBConnection.getInstance();
        try {
            String SQL;
            if (comboType.getValue() == "Alla") {
                SQL = "Select ObjektID, Titel, Typ from Objekt";
            } else {
                SQL = "Select ObjektID, Titel, Typ from Objekt "
                        + "Where Typ = '" + comboType.getValue() + "';";
            }
            System.out.println(SQL);

            //gets an arraylist with objects
            result = connection.getObjektsFromDB(SQL);

        } catch (Exception ex) {
            Logger.getLogger(SearchController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public static <Objekt> void addTextFilter(ObservableList<Objekt> observList,
            TextField txtSearch, TableView tblSearch) {

        // skapa en lista med tabellens kolumner    
        List<TableColumn<Objekt, ?>> columns = tblSearch.getColumns();

        // Skapa en filtrerad lista baserat på ObservableList (allData)
        FilteredList<Objekt> filteredData = new FilteredList<>(observList);
        // Utan att gå in på alltför mycket detaljer som vi inte pratat om ...
        // ... så skapar vi här en koppling mellan filterField och data i kolumnerna
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

    //Other functions
    private void setComboType() {
        comboType.getItems().add("Alla");
        comboType.getItems().addAll(DBConnection.getInstance().getObjektTypes());
        comboType.setValue("Alla");
    }

    //Getters
    public Objekt getSelectedObjekt() {
        return selectedObjekt;
    }

    public KopiaController getKopiaController() {
        return this.kopiaController;
    }

    //Setters   
    public void setKopiaController(KopiaController kopiaController) {
        this.kopiaController = kopiaController;
    }
}
