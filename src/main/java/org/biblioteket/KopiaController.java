
package org.biblioteket;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import org.biblioteket.Database.DBConnection;
import org.biblioteket.Objects.Kopia;
import org.biblioteket.Objects.Objekt;

/**
 *
 * @author Jenni
 */
public class KopiaController {

    //FXML variables
    @FXML
    private Text lblTitel;
    @FXML
    private TableView tblKopia;
    @FXML
    private Button btnClose;
    @FXML
    private Label lblNoCopies;

    //Other variables
    private Objekt selectObjekt;
    private ObservableList<Kopia> observableResult;

    public void initialize() {

        try {
            DBConnection instance = DBConnection.getInstance();
            selectObjekt = App.getMainControll().getSearchController().getSelectedObjekt();
            int id = selectObjekt.getObjektID();
            System.out.println("ID = " + id);
            ArrayList<Kopia> Copies = instance.getObjectCopies(selectObjekt, selectObjekt.getType());
            if (Copies == null || Copies.isEmpty()){
                tblKopia.setVisible(false);
                lblNoCopies.setVisible(true);
            }
            else{
            Util.updateTableView(tblKopia, Copies);
            updateDetailsView(selectObjekt);
            }

//         updateTableView(getKopior());
        } catch (Exception ex) {
            System.out.println("Fel i initialize i KopiaController");
            Logger.getLogger(KopiaController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
     //FXML functions
    @FXML
    void pressBtnClose(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
    
    //Table functions
//    private void updateTableView(List<Kopia> result) {
//
//        tblKopia.getColumns().clear();
//        observableResult = observableList(result);
//        
//        Field[] fields = result.get(0).getClass().getDeclaredFields();
//        //System.out.println(fields);
//
//        // För varje fält, skapa en kolumn och lägg till i TableView (fxTable)
//        
//        for (Field field : fields) {
//            System.out.println(field);
//            TableColumn<Map, String> column = new TableColumn<>(field.getName().toUpperCase());
//            column.setCellValueFactory(new PropertyValueFactory<>(field.getName()));
//            tblKopia.getColumns().add(column);
//        }
//        tblKopia.setItems(observableResult);
//
//    }

    public Objekt getSelectObjekt() {
        return selectObjekt;
    }

    //Other functions
      private void updateDetailsView(Objekt Objekt) {
        lblTitel.setText(Objekt.getTitel());
    }
      
    //Setters
    public void setSelectObjekt(Objekt selectObjekt) {
        this.selectObjekt = selectObjekt;
    }
}
