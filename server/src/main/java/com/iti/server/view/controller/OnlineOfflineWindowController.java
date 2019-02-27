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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 *
 * @author Esraa
 */
public class OnlineOfflineWindowController implements Initializable {
    @FXML
    PieChart onlineOfflinePie;
    @FXML
    Button backBtn;
    MainClass mainClass;
    ServerController serverController;
    SereverImpelementation serverImplementation;
    public OnlineOfflineWindowController(MainClass mainClass) {
        this.mainClass=mainClass;
        serverController= new ServerController();
        serverImplementation= new SereverImpelementation(serverController.dbConnction);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ResultSet onlineofflineResultSet=serverImplementation.getAllOnlineAndOfflineUsers();
        ObservableList<PieChart.Data> onlineOfflineData= FXCollections.observableArrayList();
        try {
            while(onlineofflineResultSet.next())
            {

                onlineOfflineData.add(new PieChart.Data(onlineofflineResultSet.getString(2),onlineofflineResultSet.getInt(1)));

            }
        }
        catch (SQLException ex) {

        }
        onlineOfflinePie.setData(onlineOfflineData);
        onlineOfflinePie.setTitle("online offline Statstics");
        double total = 0;
        for (PieChart.Data d : onlineOfflinePie.getData()) {
            total += d.getPieValue();
        }
        for (final PieChart.Data data : onlineOfflinePie.getData()) {
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
