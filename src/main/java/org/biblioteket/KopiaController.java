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
 * The class controlls the listing of copies of a certain Objekt.
 *
 * @author Jenni
 */
public class KopiaController extends Controllers{

    //FXML variables
    @FXML
    private Text lblTitel;
    @FXML
    private TableView tblKopia;
    @FXML
    private Button btnClose;
    @FXML
    private Label lblNoCopies;

    // The Objekt to get all the copies for. 
    private final Objekt selectObjekt;
    //Connection to DB. 
    private DBConnection connection;

    /**
     * Constructor
     * @param objekt 
     */
    public KopiaController(Objekt objekt){
        this.selectObjekt = objekt;
    }
    
    /**
     * The method is run automatically when the class is created.
     */
    public void initialize() {

        try {
            //Get connection to DB. 
            connection = DBConnection.getInstance();
            //Get list of copies for the Objekt. 
            ArrayList<Kopia> Copies = connection.getObjektCopies(selectObjekt, selectObjekt.getType());
            if (Copies == null || Copies.isEmpty()) {
                tblKopia.setVisible(false);
                lblNoCopies.setVisible(true);
            } else {
                updateTableView(tblKopia, Copies);
               lblTitel.setText(selectObjekt.getTitel());
            }
        } catch (Exception ex) {
            generalError(this.getClass().getName());
            Logger.getLogger(KopiaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Aborts and closes the pop-up. 
     * @param event 
     */
    @FXML
    void pressBtnClose(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

}
