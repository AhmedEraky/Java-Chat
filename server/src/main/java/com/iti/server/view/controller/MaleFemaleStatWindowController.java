/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.iti.server.view.controller;

import com.iti.server.controller.ServerController;
import com.iti.server.model.serverInterfaceImplementation.SereverImpelementation;
import com.iti.server.view.MainClass;
import com.iti.server.view.factory.ParentFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;

import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 * @author Esraa
 */
public class MaleFemaleStatWindowController implements Initializable {
    @FXML
    private PieChart maleFemalePie;
    @FXML
            Button backBtn;
    ServerController serverController;
    SereverImpelementation serverImplementation;
    
    MainClass mainClass;
    public MaleFemaleStatWindowController(MainClass mainClass) {
        this.mainClass=mainClass;
        serverController= new ServerController();
        serverImplementation= new SereverImpelementation(serverController.dbConnction);
        
        
        
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        int[] femaleMaleCount=serverImplementation.getNumbersOfFemalesAndMales();
        ObservableList<PieChart.Data> femaleMaleData= FXCollections.observableArrayList();
        femaleMaleData.addAll(new PieChart.Data("Female",femaleMaleCount[0]),new PieChart.Data("Male",femaleMaleCount[1]));
        
        maleFemalePie.setData(femaleMaleData);
        maleFemalePie.setTitle("Males And Female Stat");
        double total = 0;
        for (PieChart.Data d : maleFemalePie.getData()) {
            total += d.getPieValue();
        }
        for (final PieChart.Data data : maleFemalePie.getData()) {
            Node slice = data.getNode();
            double precentage=(data.getPieValue()/total)*100;
            String tip=data.getName()+"="+String.format("%.1f", precentage)+"%";
            Tooltip tipData = new Tooltip(tip);
            Tooltip.install(slice, tipData);
        }
        backBtn.setOnAction(event->
        {
            Parent root = ParentFactory.getServerHomeWindowRoot(mainClass);
            mainClass.changeScene(root);
        });
        
        
    }
    
}
