/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.biblioteket;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class KopiaController  {
    @FXML
    private Text lblTitel;

    @FXML
    private Label lblTyp;

    @FXML
    private Label lblInfo2;

    @FXML
    private Label lblInfo3;

    @FXML
    private TableView<?> tblKopia;

    @FXML
    private Button btnClose;
    private Objekt selectObjekt;
    private ArrayList<Kopia> result;

    @FXML
    void pressBtnClose(ActionEvent event) {
         ((Node)(event.getSource())).getScene().getWindow().hide();
    }
    
     public void initialize(){
         
        try {
            DBConnection instance = DBConnection.getInstance();
            System.out.println(instance.isConnectedToDB());
            int id = selectObjekt.getObjektID();
            System.out.println("ID = "+id);
            result = instance.getObjectCopies(id);
          
//         updateTableView(getKopior());
        } catch (Exception ex) {
            System.out.println("Fel i initialize i KopiaController");
            Logger.getLogger(KopiaController.class.getName()).log(Level.SEVERE, null, ex);
        }
         
     }
     
     private void updateTableView(){
         
     }


    public Objekt getSelectObjekt() {
        return selectObjekt;
    }

    public void setSelectObjekt(Objekt selectObjekt) {
        this.selectObjekt = selectObjekt;
    }

      
     
     

}
    

