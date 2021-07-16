package org.biblioteket;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.biblioteket.Persons.Loantagare;
import org.biblioteket.Persons.Person;
import org.biblioteket.Persons.Person.PersonTyp;

public class LoanController {

    @FXML
    private Text lblTitel;
    @FXML
    private TextField txtStreckkod;
    @FXML
    private Button btnAddKopia;
    @FXML
    private Label lblInformation;
    @FXML
    private Label lblMaxLoan;
    @FXML
    private Label lblExisitngLoans;
    @FXML
    private Label lblPossibleLoans;
    @FXML
    private TableView<?> tblTitles;
    @FXML
    private Button btnLoanTitles;
    
    private PersonTyp personTyp = Person.PersonTyp.NONE;
    private Person activeLibrarian = null;
    private Loantagare activeUser = null;
    
    public LoanController(Loantagare loantagare){
        this.activeUser = loantagare;
        PersonTyp personTyp = PersonTyp.LOANTAGARE;
    }
        
    public LoanController(Person person){
        this.activeLibrarian = person;
        PersonTyp personTyp = PersonTyp.BIBLIOTEKARIE;
    }
        
   
    
    @FXML
    void pressAddKopia(ActionEvent event) {

    }

    @FXML
    void pressLoanTitles(ActionEvent event) {

    }

}
