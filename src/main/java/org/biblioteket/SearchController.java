package org.biblioteket;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import static javafx.collections.FXCollections.observableList;
import javafx.collections.ObservableList;
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

public class SearchController {

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

    //List with types of objekts. 
    private ObservableList<String> objektTyp = FXCollections.observableArrayList("Alla");
    private ArrayList<Objekt> result;   //List of Objekts    
    private ObservableList<Objekt> observableResult;  //Observable list with Objekts. 
    private Objekt selectedObjekt;
    private KopiaController kopiaController;
    private Parent kopiaRoot;
    
    

    public void initialize() throws SQLException {
//        MainController mainControll = App.getMainControll();
//        searchController = mainControll.getSearchController();
//        System.out.println("Search controller: " +searchController.toString());
        setComboType();
        updateTableView(getObjekts());
    }

    @FXML
    void pressDetalis(ActionEvent event) {
        //Get selected item from list
        selectedObjekt = (Objekt) tblSearch.getSelectionModel().getSelectedItem();
        //Create popup and save as correct object. 
        loadPopupKopia();
    }

    @FXML
    void pressSearchBtn(ActionEvent event) throws SQLException {
        getObjekts();
        updateTableView(result);

    }

    //Gets all the alternative types from the DB and makes it possible to choose 
    //them. 
    private void setComboType() throws SQLException {
        comboType.getItems().add("Alla");
        comboType.getItems().addAll(DBConnection.getInstance().getObjektTypes());
        comboType.setValue("Alla");
    }

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

    private ArrayList<Objekt> getObjekts() throws SQLException {

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
            result = connection.getObjectsData(SQL);

//            System.out.println(result.size());
//            for (Objekt i : result){
//                System.out.println(i.getTitel()+" "+i.getType());
//            }
        } catch (Exception ex) {
            Logger.getLogger(SearchController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    // select first item in TableView
    private void selectFirstEntry() {
        tblSearch.getSelectionModel().selectFirst();
    }

    public Objekt getSelectedObjekt() {
        return selectedObjekt;
    }

//    public boolean loadPopupKopia() {

//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("Kopia.fxml"));
//            Parent root = loader.load();
//            
//            //Now we have access to getController() through the instance... don't forget the type cast
//            KopiaController kopiaControll = (KopiaController) loader.getController();
//            kopiaControll.setSelectObjekt(selectedObjekt);
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

    public KopiaController getKopiaController() {
        return this.kopiaController;
    }

   
    private void loadPopupKopia() {
        try {
            if (kopiaRoot == null){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Kopia.fxml"));
            kopiaRoot = loader.load();
            App.getMainControll().getSearchController().setKopiaController(loader.getController());
            }
            Stage stage = new Stage();
            Scene scene = new Scene(kopiaRoot);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println("Fel i "+this.toString());
            Logger.getLogger(SearchController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setKopiaController(KopiaController kopiaController) {
        this.kopiaController = kopiaController;
    }


    
}
