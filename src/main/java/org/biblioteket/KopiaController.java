/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.biblioteket;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.collections.FXCollections.observableList;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import org.biblioteket.Database.DBConnection;
import org.biblioteket.Objects.Kopia;
import org.biblioteket.Objects.Objekt;

/**
 *
 * @author Jenni
 */
public class KopiaController {

    @FXML
    private Text lblTitel;

    @FXML
    private Label lblTyp;

    @FXML
    private Label lblInfo2;

    @FXML
    private Label lblInfo3;

    @FXML
    private TableView tblKopia;

    @FXML
    private Button btnClose;

    private Objekt selectObjekt;
    private ArrayList<Kopia> result;
    private ObservableList<Kopia> observableResult;

    @FXML
    void pressBtnClose(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();

    }

    public void initialize() {

        try {
            DBConnection instance = DBConnection.getInstance();
            selectObjekt = App.getMainControll().getSearchController().getSelectedObjekt();
            int id = selectObjekt.getObjektID();
            System.out.println("ID = " + id);
            ArrayList<Kopia> Copies = instance.getObjectCopies(selectObjekt, selectObjekt.getType());
            updateTableView(Copies);
            updateDetailsView(selectObjekt);

//         updateTableView(getKopior());
        } catch (Exception ex) {
            System.out.println("Fel i initialize i KopiaController");
            Logger.getLogger(KopiaController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void updateTableView(List<Kopia> result) {

        tblKopia.getColumns().clear();
        observableResult = observableList(result);

        Field[] fields = result.get(0).getClass().getDeclaredFields();
        //System.out.println(fields);

        // För varje fält, skapa en kolumn och lägg till i TableView (fxTable)
        for (Field field : fields) {
            System.out.println(field);
            TableColumn<Map, String> column = new TableColumn<>(field.getName().toUpperCase());
            column.setCellValueFactory(new PropertyValueFactory<>(field.getName()));
            tblKopia.getColumns().add(column);
        }
        tblKopia.setItems(observableResult);

    }

    public Objekt getSelectObjekt() {
        return selectObjekt;
    }

    public void setSelectObjekt(Objekt selectObjekt) {
        this.selectObjekt = selectObjekt;
    }

    private void updateDetailsView(Objekt Objekt) {
        lblTitel.setText(Objekt.getTitel());
        lblTyp.setText(Objekt.getType().toString());
    }

}
