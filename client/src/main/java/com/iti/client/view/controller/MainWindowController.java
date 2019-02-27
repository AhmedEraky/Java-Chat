/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.iti.client.view.controller;

import com.iti.client.view.MainClass;

import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 * @author Esraa
 */
public class MainWindowController extends ParentMenuBarController {
    MainClass mainClass;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setButtonAction(mainClass);
    }
    
    public MainWindowController(MainClass mainClass)
    {
        this.mainClass = mainClass;
        
        
    }
    
}
