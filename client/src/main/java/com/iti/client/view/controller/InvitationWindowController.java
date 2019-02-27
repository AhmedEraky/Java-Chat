/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iti.client.view.controller;

import com.iti.ChatCommanServices.model.entity.user.User;
import com.iti.ChatCommanServices.model.entity.user.UserInvitation;
import com.iti.client.view.MainClass;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Callback;

import static com.iti.client.view.MainClass.alertMessageException;

/**
 *
 * @author Esraa
 */
public class InvitationWindowController extends ParentMenuBarController {
    @FXML
    private Label userInvitationCount;
    @FXML
    private Label invitationCount;
    @FXML
    private ListView userInvitationviewList;

    ObservableList<User> userInvitationObseravleList;
    MainClass mainClass;
    public InvitationWindowController(MainClass mainClass) {
        this.mainClass = mainClass;
        userInvitationObseravleList= FXCollections.observableArrayList();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setButtonAction(mainClass);

        mainClass.getStage().setTitle("Invitations");

        userInvitationviewList.setCellFactory(new Callback<ListView<User>, ListCell<User>>() {
            @Override
            public ListCell<User> call(ListView<User> param) {
                return new ListCell<User>() {
                    @Override
                    protected void updateItem(User user, boolean empty) {
                        super.updateItem(user, empty);
                        if (!empty) {
                            HBox hbox = new HBox();
                            Label userNameLabel= new Label();
                            userNameLabel.setText(user.getFirstName() +" "+user.getLastName()+" ");
                            userNameLabel.setStyle("-fx-font-size:18px;"
                                    + "-fx-text-fill: #0fc56d;"
                                    + "-fx-font-weight: bold;");
                            userNameLabel.setTranslateX(20.0);
                            userNameLabel.setTranslateY(15.0);
                            userNameLabel.setPrefWidth(200);

                            ImageView friendImage=new ImageView();
                            byte [] imageByte = user.getPhoto();
                            friendImage.setFitHeight(40);
                            friendImage.setFitWidth(40);
                            friendImage.setImage(new Image(new ByteArrayInputStream(imageByte)));
                            Circle imageCircle;
                            friendImage.setClip(new Circle(25, 25, 30));
                            imageCircle = new Circle(25, 25, 30);
                            imageCircle.setFill(new ImagePattern(friendImage.getImage()));

                            Button acceptInvitiaionBtn= new Button("Accept");
                            acceptInvitiaionBtn.setTranslateY(40);
                            acceptInvitiaionBtn.setTranslateX(220);
                            acceptInvitiaionBtn.setPrefWidth(80.0);
                            acceptInvitiaionBtn.setPrefHeight(10.0);
                            acceptInvitiaionBtn.setStyle("-fx-background-color:#0fc56d;"
                                    + "-fx-text-fill:white;"
                                    + "-fx-font-weight: bold;");
                            acceptInvitiaionBtn.setOnAction(event->
                            {
                                acceptInvitation(user);

                            });

                            Button rejectInvitiaionBtn= new Button("Reject");
                            rejectInvitiaionBtn.setTranslateY(40);
                            rejectInvitiaionBtn.setTranslateX(260);

                            rejectInvitiaionBtn.setPrefWidth(80.0);
                            rejectInvitiaionBtn.setPrefHeight(10.0);
                            rejectInvitiaionBtn.setStyle("-fx-background-color:#c4130f;"
                                    + "-fx-text-fill:white;"
                                    + "-fx-font-weight: bold;");
                            rejectInvitiaionBtn.setOnAction(event->
                            {
                                rejectInvitation(user);
                            });

                            hbox.setPrefWidth(300);
                            hbox.setPrefHeight(65);
                            hbox.getChildren().add(imageCircle);
                            hbox.getChildren().add(userNameLabel);
                            hbox.getChildren().add(acceptInvitiaionBtn);
                            hbox.getChildren().add(rejectInvitiaionBtn);
                            setGraphic(hbox);
                        }
                    }


                };
            }
        });
        getUserInvtations();

    }

    private void rejectInvitation(User user) {
        UserInvitation userInvitation=new UserInvitation();
        userInvitation.setReceiverPhno(user.getPhno());
        userInvitation.setSenderPhno(mainClass.getUser().getPhno());
        try {
            mainClass.getServerServiceLocator().getInvitationService().rejectInvitation(userInvitation);
            userInvitationObseravleList.remove(user);
        } catch (RemoteException e) {
            alertMessageException("sorry error occured in server");
        }
        invitationCount.setText("Count : "+userInvitationObseravleList.size());

    }

    private void getUserInvtations() {
        try {
            userInvitationObseravleList=FXCollections.observableArrayList();
            userInvitationObseravleList.addAll(mainClass.getServerServiceLocator().getInvitationService().getInvitations(mainClass.getUser().getPhno()));
            userInvitationviewList.setItems(userInvitationObseravleList);
//            userInvitationCount.setText("Count : "+ userInvitationObseravleList.size());
        } catch (RemoteException ex) {
            alertMessageException("sorry error occured in server");
        }

        invitationCount.setText("Count : "+userInvitationObseravleList.size());
    }

    public void reciveNewFriendRequst(User user){
        userInvitationObseravleList.add(user);
        userInvitationCount.setText("Count : "+ userInvitationObseravleList.size());
        invitationCount.setText("Count : "+userInvitationObseravleList.size());
    }

    private void acceptInvitation(User user) {

        UserInvitation userInvitation=new UserInvitation();
        userInvitation.setSenderPhno(user.getPhno());
        userInvitation.setReceiverPhno(mainClass.getUser().getPhno());
        try {
            mainClass.getServerServiceLocator().getInvitationService().acceptInvitation(userInvitation);
            userInvitationObseravleList.remove(user);
        } catch (RemoteException e) {
            alertMessageException("sorry error occured in server");
        }
        invitationCount.setText("Count : "+userInvitationObseravleList.size());

    }
    private void igonreInvitation(User user) {
        UserInvitation userInvitation=new UserInvitation();
        userInvitation.setSenderPhno(mainClass.getUser().getPhno());
        userInvitation.setReceiverPhno(user.getPhno());

        try {
            mainClass.getServerServiceLocator().getInvitationService().ignoreInvitation(userInvitation);

            userInvitationObseravleList.remove(user);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        invitationCount.setText("Count : "+userInvitationObseravleList.size());
    }


}
