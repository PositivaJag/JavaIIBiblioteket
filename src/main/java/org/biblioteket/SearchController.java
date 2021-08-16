package org.biblioteket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.biblioteket.Database.DBConnection;
import org.biblioteket.Objects.Bok;
import org.biblioteket.Objects.Objekt;
import org.biblioteket.Objects.Objekt.Type;
import static org.biblioteket.Objects.Objekt.Type.Bok;

/**
 *
 * @author jenni
 */
public class SearchController {

    //FXML variables
    @FXML
    private TextField txtSearch;
    @FXML
//    private Button btnSearch;
//    @FXML
    private TableView tblSearch;
    @FXML
    private ComboBox comboType;
    @FXML
    private Button btnDetails;
    @FXML
    private Button btnUpdateObjekt;
    @FXML
    private Button btnNyttObjekt;
    @FXML
    private Button btnAddKopior;
    @FXML
    private Button btnLateLoans;
    @FXML
    private Button btnSearch;

    @FXML
    private VBox vboxKopia;

    @FXML
    private VBox vboxObjekt;
    @FXML
    private VBox vboxLoan;
    @FXML
    private Button btnUpdateKopia;

    //Other variables
    //List with types of objekts. 
    private ObservableList<String> objektTyp = FXCollections.observableArrayList("Alla");
    private ArrayList<Objekt> result;   //List of Objekts    
    private ObservableList<Objekt> observableResult;  //Observable list with Objekts. 

    private Objekt selectedObjekt;
    private KopiaController kopiaController;    //Controller object
    private SearchController searchController;
    private NewObjektController newObjektController;
    private Stage newObjektStage;
    private Parent kopiaRoot;
    private Parent newObjektRoot;

    /**
     *
     */
    public void initialize() {
        
        setLibrarianButtons();
      
        setComboType();
        updateTableView(getObjekts());
        addTextFilter(observableResult, txtSearch, tblSearch);

        comboType.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                updateTableView(getObjekts());
                addTextFilter(observableResult, txtSearch, tblSearch);
            }
        });
    }

    @FXML
    void pressUpdateObjekt(ActionEvent event) {
        setSelectedObjekt();
        if (selectedObjekt.getType() != Type.Bok){
            Alert alert = new Alert(AlertType.INFORMATION, "Programmet kan bara"
                    + " hantera böcker för tillfället.");
            alert.show();
        }
        else{
            
            loadPopupUpdateObjekt();
        }
        
       
    }

    @FXML
    void pressUpdateKopia(ActionEvent event) {

    }

    @FXML
    void pressNyttObjekt(ActionEvent event) {
        loadPopupNewObjekt();
    }

    @FXML
    void pressDetalis(ActionEvent event) {
        //Get selected item from list
        setSelectedObjekt();
        //Create popup and save as correct object. 
        loadPopupKopia();
    }

    @FXML
    void pressBtnSearch(ActionEvent event) {
        addTextFilter(observableResult, txtSearch, tblSearch);
    }

    @FXML
    void pressAddKopior(ActionEvent event) {
        //Get selected item from list
        setSelectedObjekt();
        loadPopupNewKopia(selectedObjekt);

        //Create popup and save as correct object. 
    }
    
        @FXML
    void pressLateLoans(ActionEvent event) {
        loadPopup("LateLoans.fxml");
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

    private void loadPopupNewKopia(Objekt objekt) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("NewKopia.fxml"));

            NewKopiaController controller = new NewKopiaController(objekt);
            loader.setController(controller);
            Parent root = loader.load();

            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(SearchController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadPopupNewObjekt() {
        try {
//            if (kopiaRoot == null){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("NewObjekt.fxml"));
            newObjektRoot = loader.load();
            App.getMainControll().getSearchController().setNewObjektController(loader.getController());
//            }
            Stage stage = new Stage();
            Scene scene = new Scene(newObjektRoot);
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            System.out.println("Fel i " + this.toString());
            Logger.getLogger(SearchController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     *
     * @param fxml
     */
    public void loadPopup(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();

            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(SearchController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
       public void loadPopupUpdateObjekt(){
        try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateObjekt.fxml"));
             
            UpdateObjektController controller = new UpdateObjektController(this.selectedObjekt);           
            loader.setController(controller);
            
            Parent root = loader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            System.out.println("Exception i klass NewObjektController.java, i "
                    + "metoden loadPage()");
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

    
        
    }
    
    


//    private Boolean loadPopup(String fxml) {
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

    //Set FXML values 

//    /**
//     *
//     * @param bool
//     */
//    public void setbtnUpdateObjektVisibility(Boolean bool) {
//        btnUpdateObjekt.setVisible(bool);
//    }

    /**
     *
     */
    public void setLibrarianButtons() {
        Boolean bool = App.getMainControll().checkIfLibrarianLoggedIn();
        vboxObjekt.setVisible(bool);
            vboxKopia.setVisible(bool);
            vboxLoan.setVisible(bool);
//        setbtnUpdateObjektVisibility(bool);
//        setbtnNyttObjektVisibility(bool);
    }

    /**
     *
     * @param bool
     */
    public void setbtnNyttObjektVisibility(Boolean bool) {
        btnNyttObjekt.setVisible(bool);
    }

    //Table 

    /**
     *
     * @param result
     */
    public void updateTableView(List<Objekt> result) {
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

    /**
     *
     * @return
     */
    public ArrayList<Objekt> getObjekts() {

        DBConnection connection = DBConnection.getInstance();
        try {

            //gets an arraylist with objects
            result = connection.getObjektsFromDB(comboType.getValue().toString());

        } catch (Exception ex) {
            Logger.getLogger(SearchController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    /**
     *
     * @param <Objekt>
     * @param observList
     * @param txtSearch
     * @param tblSearch
     */
    public static <Objekt> void addTextFilter(ObservableList<Objekt> observList,
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

    //Other functions
    private void setComboType() {
        comboType.getItems().add("Alla");
        comboType.getItems().addAll(DBConnection.getInstance().getObjektTypes());
        comboType.setValue("Alla");
    }

    //Getters

    /**
     *
     * @return
     */
    public Objekt getSelectedObjekt() {
        return selectedObjekt;
    }

    /**
     *
     */
    public void setSelectedObjekt() {
        this.selectedObjekt = (Objekt) tblSearch.getSelectionModel().getSelectedItem();
    }

    /**
     *
     * @return
     */
    public KopiaController getKopiaController() {
        return this.kopiaController;
    }

    //Setters   

    /**
     *
     * @param kopiaController
     */
    public void setKopiaController(KopiaController kopiaController) {
        this.kopiaController = kopiaController;
    }

    /**
     *
     * @return
     */
    public NewObjektController getNewObjektController() {
        return newObjektController;
    }

    /**
     *
     * @param newObjektController
     */
    public void setNewObjektController(NewObjektController newObjektController) {
        this.newObjektController = newObjektController;
    }

    /**
     *
     * @return
     */
    public Stage getNewObjektStage() {
        return newObjektStage;
    }

    /**
     *
     * @param newObjektStage
     */
    public void setNewObjektStage(Stage newObjektStage) {
        this.newObjektStage = newObjektStage;
    }

}
