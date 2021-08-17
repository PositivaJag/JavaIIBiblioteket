/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.biblioteket;

import java.util.ArrayList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import org.biblioteket.Database.DBConnection;
import org.biblioteket.Objects.Bok;

/**
 *
 * @author jenni
 */
public class update {
    
 public ArrayList<Integer> listAllISBN(Bok bok, DBConnection connection) {
       //get all ISBN numbers.
        ArrayList<Integer> allISBN = connection.getAllISBN();
        //then remove the ISBN of bok to make sure that that number is 
        //allowed to be added. 
        allISBN.remove(Integer.valueOf(bok.getISBN()));
        return allISBN;
 }
 
 
 
}
