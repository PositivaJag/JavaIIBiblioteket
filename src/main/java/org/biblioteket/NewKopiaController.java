package org.biblioteket;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import static javafx.collections.FXCollections.observableList;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import org.biblioteket.Database.DBConnection;
import org.biblioteket.Objects.Kopia;
import org.biblioteket.Objects.Objekt;

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
    
    int newObjektID;
    String title;
    NewObjektController newObjektController;
    private ObservableList<Kopia> observableKopior = FXCollections.observableArrayList();;
    
    ArrayList<Integer> allStreckkod;
     
    public void initialize(){
        
        newObjektController = App.getMainControll().getSearchController().
                getNewObjektController();
        newObjektID = newObjektController.getNewObjektID();
        title = newObjektController.getTitle();
        
        lblTitel.setText(newObjektID + " - " + title);
        
        System.out.println(newObjektID);
        
        setComboCategories();
        
        DBConnection connection = DBConnection.getInstance();
        allStreckkod = connection.getAllSteckkod();
        System.out.println(allStreckkod);
        
         txtStreckkod.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                if (!newValue.matches("\\d*")) {
                    txtStreckkod.setText(newValue.replaceAll("[^\\d]", ""));

                }
                if (allStreckkod.contains(Integer.parseInt(txtStreckkod.getText()))) {
                    lblWarning.setText("Streckkod finns redan");
                    btnAdd.setDisable(true);
                } else if (!(allStreckkod.contains(Integer.parseInt(txtStreckkod.getText())))) {
                    lblWarning.setText("");
                    btnAdd.setDisable(false);
                }
            }
        });
         
        ChangeListener<String> allFieldsListener = ((observable, oldValue, newValue) -> {
            if (txtStreckkod.getText() == null
                    || txtStreckkod.getText().equals("")
                    || txtPlacement.getText() == null
                    || txtPlacement.getText().equals("")
                    || comboCategory.getValue() == null)
            {
                btnAdd.setDisable(true);
            } else {
                btnAdd.setDisable(false);
            }
        });

        txtStreckkod.textProperty().addListener(allFieldsListener);
        txtPlacement.textProperty().addListener(allFieldsListener);
        comboCategory.valueProperty().addListener(allFieldsListener);
    }

    @FXML
    void pressAddToDB(ActionEvent event) {
        
        
    }

    @FXML
    void pressAddToList(ActionEvent event) {
        
       observableKopior.add(new Kopia(Integer.parseInt(txtStreckkod.getText()), newObjektID, 
                comboCategory.getValue().toString(), txtPlacement.getText()));
       allStreckkod.add(Integer.parseInt(txtStreckkod.getText()));
       System.out.println(allStreckkod);
       updateTableView();
        
    }
    private void setComboCategories(){
        List<String> list = DBConnection.getInstance().getAllKopiaCategories();
        Collections.sort(list);
        comboCategory.getItems().addAll(list);
    }
    
      //Table functions
    private void updateTableView() {

        tblAddedCopies.getColumns().clear();

        Field[] fields = observableKopior.get(0).getClass().getDeclaredFields();
        //System.out.println(fields);

        // För varje fält, skapa en kolumn och lägg till i TableView (fxTable)
        
        for (Field field : fields) {
            System.out.println(field);
            TableColumn<Map, String> column = new TableColumn<>(field.getName().toUpperCase());
            column.setCellValueFactory(new PropertyValueFactory<>(field.getName()));
            tblAddedCopies.getColumns().add(column);
        }
        tblAddedCopies.setItems(observableKopior);

    }


}
