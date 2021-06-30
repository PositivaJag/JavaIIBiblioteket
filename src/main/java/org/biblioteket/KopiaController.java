/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.biblioteket;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
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
    private TableView<?> tblKopia;

    @FXML
    private Button btnClose;
    
    int ObjektID; 

    @FXML
    void pressBtnClose(ActionEvent event) {

    }
    
     public void initialize(){
         
         updateTableView(getKopior());
         
     }
     
     private void updateTableView(){
         
     }
     
     private void getKopior(int ObjektID){
         
     }

             
    public void setObjektID(int ObjektID) {
        this.ObjektID = ObjektID;
    }
     
     

}
    

