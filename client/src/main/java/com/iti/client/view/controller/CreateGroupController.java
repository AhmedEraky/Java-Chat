package com.iti.client.view.controller;

import com.iti.ChatCommanServices.model.entity.user.User;
import com.iti.client.view.MainClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CreateGroupController implements Initializable {
    MainClass mainClass;
    Stage stage;
    @FXML
    private Pane userProfilePane;

    @FXML
    private ImageView userProfileImage;

    @FXML
    private ImageView onlinFriends;

    @FXML
    private ImageView addFriendIcon;

    @FXML
    private ImageView notificationIcon;

    @FXML
    private ImageView editProfileIcon;
    @FXML
    private ImageView groupChatIcon;
    @FXML
    private ImageView signOutIcon;
    @FXML
    private TextField textField;
    @FXML
    private Button Submit;
    @FXML
    private Button Add;
    @FXML
    private ListView listView;
    @FXML
    private Button cancelBtn;

    ObservableList<String> groupmember;
    ArrayList<User> userContacts=new ArrayList<>();


    public CreateGroupController(MainClass mainClass, Stage stage) {
        this.mainClass = mainClass;
        this.stage = stage;
        groupmember= FXCollections.observableList(new ArrayList<>());

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cancelBtn.setOnAction(this::returnToHome);
        Add.setOnMousePressed(this::addmember);
        Submit.setOnMousePressed(this::CreateGroup);

        listView.setItems(groupmember);
        mainClass.getStage().setTitle("Create Group");



    }

    private void CreateGroup(MouseEvent mouseEvent) {
        try {
            mainClass.getServerServiceLocator().getChatService().createChatGroup(userContacts);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void returnToHome(ActionEvent actionEvent) {
        stage.close();
        mainClass.getStage().show();
    }

    private void addmember(MouseEvent mouseEvent) {
        if (textField.getText() != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("OOps");
            alert.setContentText("Enter A text Field");
            alert.showAndWait();
            try {
                userContacts = mainClass.getServerServiceLocator().getUpdateService().getUserContacts(mainClass.getUser().getPhno());
                boolean friendsFlag=false;
                for(int i=0;(i<userContacts.size())&&(!friendsFlag);i++) {
                    if (userContacts.get(i).getPhno().equals(textField.getText())) {
                        friendsFlag = true;
                    }
                    if (friendsFlag==true){
                        User user=mainClass.getServerServiceLocator().getUpdateService().getProfileData(textField.getText());
                        groupmember.add(user.getFirstName());
                        userContacts.add(user);
                    }
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }



        }
    }

    private void returnToHome(MouseEvent mouseEvent) {
    }


}
