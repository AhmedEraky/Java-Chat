/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iti.server.view.controller;

import com.iti.ChatCommanServices.model.ClientInterfaces.ClientServiceInterface;
import com.iti.ChatCommanServices.model.ServerInterfaces.ServerClientEnteranceInterface;
import com.iti.server.model.ServerClientEnteranceImplementation;
import com.iti.server.view.MainClass;
import com.iti.server.view.factory.ParentFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.ResourceBundle;

/**
 *
 * @author Esraa
 */
public class AdvirtisingWindowController implements Initializable {
    @FXML
    TextField broadCastTf;
    @FXML
    private Button sendMsgBtn;
    @FXML
    Button backBtn;

    MainClass mainClass;



    public AdvirtisingWindowController(MainClass mainClass) {
        this.mainClass=mainClass;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sendMsgBtn.setOnMousePressed(this::broadcastMessage);
        backBtn.setOnAction(event->
        {
            Parent root = ParentFactory.getServerHomeWindowRoot(mainClass);
            mainClass.changeScene(root);
        });
    }

    private void broadcastMessage(MouseEvent mouseEvent) {
        String message=broadCastTf.getText().trim();
        if(!message.equals(""))
        {
            ServerClientEnteranceImplementation.clients.forEach((s, clientServiceInterface) -> {
                try {
                    clientServiceInterface.reciveServerMessage(broadCastTf.getText());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            });
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("OOps");
            alert.setContentText("Please Enter a Message");
            alert.showAndWait();
        }
    }


}
