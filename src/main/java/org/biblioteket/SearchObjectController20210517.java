package org.biblioteket;

import java.sql.SQLException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.biblioteket.Objects.Objekt;

public class SearchObjectController20210517 {

    @FXML
    private TextField searchField;

    @FXML
    private Button buttonSearch;

    @FXML
    private Button buttonUpdate;

    @FXML
    private Button buttonExit;

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab tabAlla;

    @FXML
    private TableView tableAlla;

    @FXML
    private Tab tabBok;

    @FXML
    private TableView tableBok;

    @FXML
    private Tab tabFilm;

    @FXML
    private TableView tableFilm;

    @FXML
    private Tab tabTidning;

    @FXML
    private TableView tableTidning;
    
    private List<Objekt> listAlla, listBok, listFilm, listTidning;
    private ObservableList<Objekt> observableAll, observableBok, observableFilm, observableTidning;
    FilteredList<Objekt> filterObjekt;
    private UseCase useCase;
    
     public void initialize() throws Exception {

        useCase = UseCase.getInstance();
        getAllObjekts();

    }
     
      private void getAllObjekts() throws SQLException, Exception {
        //clear table
        tableAlla.getColumns().clear();
        tableBok.getColumns().clear();
        tableFilm.getColumns().clear();
        tableTidning.getColumns().clear();

        //Create lists with objects
        this.listAlla = useCase.getAllObjekts();
        splitAllObjektList();
        
        //To observable lists. 
        System.out.println(listAlla.size());
        observableAll = FXCollections.observableList(listAlla);
        observableBok = FXCollections.observableList(listBok);
        observableFilm =FXCollections.observableList(listFilm);
        observableTidning = FXCollections.observableList(listTidning);
//        observableObjekt.forEach(O -> {
//            System.out.println(O.getObjektID());
//        });
        //Create tables. 
        createTableColumns(tableAlla);
        createTableColumns(tableBok);
        createTableColumns(tableFilm);
        createTableColumns(tableTidning);

//        filterList = new FilteredList<>(observableAll);
//        tableAlla.setItems(filterObjekt);
////        tableAlla.setItems(observableObjekt);
//        selectFirstEntry();
//
//        searchField.textProperty().addListener((observable, oldValue, newValue)
//                -> filterObjekt.setPredicate(createPredicate(newValue)));

        //FilteredList<Objekt> filteredData = new FilteredList<>(observableObjekt, v -> true);
    }
    
    private void splitAllObjektList(){
        String temp;
        for (int L = 0; L < listAlla.size(); L++) {
            temp = listAlla.get(L).getType();
            if (temp.equalsIgnoreCase("Bok")) {
                listBok.add(listAlla.get(L));
            } else if (temp.equalsIgnoreCase("Film")) {
                listFilm.add(listAlla.get(L));
            } else if (temp.equalsIgnoreCase("Tidskrift")) {
                listTidning.add(listAlla.get(L));
            }
        }
    }
    
    private void createTableColumns(TableView table){
        //Skapa tableColumns
        TableColumn<Objekt, String> column1 = new TableColumn<>("ObjektID");
        TableColumn<Objekt, String> column2 = new TableColumn<>("Titel");
        TableColumn<Objekt, String> column3 = new TableColumn<>("Typ");
        TableColumn<Objekt, String> column4 = new TableColumn<>("Artist");

        column1.setCellValueFactory(new PropertyValueFactory<>("objektID"));
        column2.setCellValueFactory(new PropertyValueFactory<>("titel"));
        column3.setCellValueFactory(new PropertyValueFactory<>("type"));
        column4.setCellValueFactory(new PropertyValueFactory<>("authors"));

        table.getColumns().addAll(column1, column2, column3, column4);
    }
    
     private void selectFirstEntry() {
        tableAlla.getSelectionModel().selectFirst();
    }
    

    @FXML
    void clickButtonExit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void clickButtonSearch(ActionEvent event) {

    }

    @FXML
    void clickButtonUpdate(ActionEvent event) throws Exception {
         useCase.getAllObjekts();
    }

    @FXML
    void writeSearchField(ActionEvent event) {

    }

}
