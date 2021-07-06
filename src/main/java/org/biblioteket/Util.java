/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.biblioteket;

import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Jenni
 */
public class Util {
    
    public static String listToString(ArrayList<String> list){
        String string = "";
        for (int i = 0; i < list.size(); i++){
            string += list.get(i)+"; ";
        }
        return string;
    }
    

    
}
