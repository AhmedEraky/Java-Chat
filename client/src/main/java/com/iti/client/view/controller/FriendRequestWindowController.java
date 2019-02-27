package com.iti.client.view.controller;


import com.iti.ChatCommanServices.model.entity.user.User;
import com.iti.client.view.MainClass;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.iti.client.view.MainClass.alertMessageException;

public class FriendRequestWindowController implements Initializable {
    @FXML
    VBox onlineUserVBox;
    @FXML
    Button invitationButton;
    ListView listView;

    MainClass mainClass;
    ArrayList<User> userInvtation;
    ScheduledExecutorService udateUserInvtations = Executors.newScheduledThreadPool(1);
    int userInvtationCount;
    ObservableList<User> invitationList;

    boolean friendRequstFlag = false;

    public FriendRequestWindowController(MainClass mainClass) {
        this.mainClass=mainClass;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        onlineUserVBox.setVisible(false);
        invitationButton.setOnAction((event) -> {
            displayFriendRequest(mainClass.getUser().getPhno());
        });
        getUserInvtations();
        mainClass.getStage().setTitle("Friend Request window");

    }

    private void getUserInvtations() {
        try {
            listView= new ListView();
            invitationList = FXCollections.observableArrayList();
            invitationList.addAll(mainClass.getServerServiceLocator().getInvitationService().getInvitations(mainClass.getUser().getPhno()));
            listView.setItems(invitationList);
            listView.setPrefSize(100, 900);
            onlineUserVBox.getChildren().add(listView);
        } catch (RemoteException ex) {
            alertMessageException("sorry error occured in server");

        }
    }

    public void reciveNewFriendRequst(User user){
        invitationList.add(user);
    }

    private void displayFriendRequest(String phoneNumber) {
        if (friendRequstFlag == false) {
            onlineUserVBox.setVisible(true);
            friendRequstFlag = true;
            if (userInvtationCount > 0) {
                updateSeenFriends(phoneNumber);
            }
        } else {

            if (userInvtationCount > 0) {
                updateSeenFriends(phoneNumber);
            }
            onlineUserVBox.setVisible(false);
            friendRequstFlag = false;
        }
    }

    private void updateSeenFriends(String phoneNumber) {
        try {
            mainClass.getServerServiceLocator().getChatService().updatSeenFriends(phoneNumber);
        } catch (RemoteException ex) {
            alertMessageException("sorry error occured in server");

        }
    }
}
