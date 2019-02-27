/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iti.client.view.controller;

import com.iti.ChatCommanServices.model.entity.message.EntityMessage;
import com.iti.client.view.MainClass;

import java.io.ByteArrayInputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import static com.iti.client.view.MainClass.alertMessageException;

/**
 *
 * @author Esraa
 */
public class GroupChatWindowController extends ParentMenuBarController{
    @FXML
    private Pane userProfilePane;

    @FXML
    private ImageView userProfileImage;

    @FXML
    private ListView groupListView;

    @FXML
    private ToggleButton botBtn;

    @FXML
    private ImageView addToGroup;

    @FXML
    private ImageView saveChat;

    @FXML
    private ComboBox fontsCB;

    @FXML
    private ComboBox sizeCB;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private ToggleButton underlineBtn;

    @FXML
    private ToggleButton italicBtn;

    @FXML
    private ToggleButton boldBtn;

    @FXML
    private ImageView attachFileImg;

    @FXML
    private TextField newMessageTF;

    @FXML
    private Button chatBtn;

    @FXML
    private ListView<EntityMessage> chatList;

    String chatID;
    ObservableList<String> userGroups;
    HashMap<String, ObservableList<EntityMessage>> chatSession;
    MainClass mainClass;


    public GroupChatWindowController(MainClass mainClass) {
        this.mainClass = mainClass;
        chatID="";
        chatSession=new HashMap<>();
        userGroups= FXCollections.observableArrayList();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setButtonAction(mainClass);
        addToGroup.setImage(new Image ("/plus.png"));
        saveChat.setImage(new Image ("/floppy-disk.png"));
        attachFileImg.setImage(new Image ("/attachfile.png"));
        getChatHistory();
        groupListView.setItems(userGroups);
        groupListView.setOnMouseClicked(this::startChat);
        chatBtn.setOnAction(this::sendMessage);
        groupListView.setCellFactory(param -> new ListCell<String>() {
            private ImageView imageView = new ImageView();

            @Override
            public void updateItem(String name, boolean empty) {
                super.updateItem(name, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    try {
                        HBox hbox = new HBox();
                        Image image = new Image(getClass().getClassLoader().getResource("personal.png").toURI().toString());
                        imageView.setImage(image);
                        imageView.setFitHeight(30);
                        imageView.setFitWidth(30);
                        hbox.getChildren().add(imageView);
                        hbox.getChildren().add(new Label("Group : " + name));
                        hbox.setSpacing(5);
                        hbox.setAlignment(Pos.CENTER_LEFT);
                        setGraphic(hbox);
                    } catch (URISyntaxException ex) {
                        Logger.getLogger(GroupChatWindowController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        mainClass.getStage().setTitle("Group Chat");
        chatList.setCellFactory(new Callback<ListView<EntityMessage>, ListCell<EntityMessage>>() {
            @Override
            public ListCell<EntityMessage> call(ListView<EntityMessage> entityMessageListView) {
                return new ListCell<EntityMessage>() {
                    @Override
                    protected void updateItem(EntityMessage message, boolean empty) {
                        super.updateItem(message, empty);
                        if (!empty) {

                            if (message.getSenderUser().getPhno().equals(mainClass.getUser().getPhno())) {
                                HBox hBox = new HBox();
                                hBox.setAlignment(Pos.BASELINE_RIGHT);
                                Label labelNewMessage = new Label();
                                labelNewMessage.setText(message.getMessage());
                                labelNewMessage.setAlignment(Pos.CENTER);
                                ImageView senderImage = new ImageView();
                                senderImage.setImage(new Image(new ByteArrayInputStream(mainClass.getUser().getPhoto())));
                                senderImage.setFitWidth(20);
                                senderImage.setFitHeight(20);
                                hBox.getChildren().add(labelNewMessage);
                                hBox.getChildren().add(senderImage);

                                setGraphic(hBox);
                            } else {
                                HBox hBox = new HBox();
                                hBox.setAlignment(Pos.BASELINE_LEFT);
                                Label labelNewMessage = new Label();
                                labelNewMessage.setText(message.getMessage());
                                labelNewMessage.setAlignment(Pos.CENTER);
                                ImageView senderImage = new ImageView();
                                senderImage.setImage(new Image(new ByteArrayInputStream(message.getSenderUser().getPhoto())));
                                senderImage.setFitWidth(20);
                                senderImage.setFitHeight(20);
                                hBox.getChildren().add(senderImage);
                                hBox.getChildren().add(labelNewMessage);
                                setGraphic(hBox);
                            }
                        }
                    }
                };
            }
        });

    }

    private void startChat(MouseEvent mouseEvent) {
        chatID= (String) groupListView.getSelectionModel().getSelectedItem();
        if(!chatSession.containsValue(chatID)){
            ObservableList<EntityMessage> messages=FXCollections.observableArrayList();
            try {
                messages.addAll(mainClass.getServerServiceLocator().getChatService().getGroupMessages(chatID));
            } catch (RemoteException e) {
                alertMessageException("sorry error occured in server");
            }
            chatSession.put(chatID,messages);

        }
        chatList.refresh();
        chatList.setItems(chatSession.get(chatID));
        Platform.runLater(() ->chatList.scrollTo(chatSession.get(chatID).size()-1));
    }

    private void sendMessage(ActionEvent actionEvent) {
        if (chatID.equals("")||newMessageTF.getText().equals("")){
            Platform.runLater(() -> {
                mainClass.showMessageWarning("please select a user to chat with");
            });
        }
        else
            sendNewMessage();
    }


    private void getChatHistory() {
        try {
            userGroups.addAll(mainClass.getServerServiceLocator().getChatService().getGroupHistory(mainClass.getUser()));
        } catch (RemoteException e) {
            alertMessageException("sorry error occured in server");
        }
    }

    public void sendNewMessage(){
        String newMessage=newMessageTF.getText().trim();
        EntityMessage entityMessage=new EntityMessage();
        entityMessage.setMessage(newMessage);
        entityMessage.setSenderUser(mainClass.getUser());
        entityMessage.setId(chatID);
        try {
            chatSession.get(chatID).add(entityMessage);
            mainClass.getServerServiceLocator().getChatService().broadcastMessage(entityMessage,chatID);
        } catch (RemoteException e) {
            alertMessageException("sorry error occurred in server");
        }
    }
    public void reciveGroupMessage(EntityMessage entityMessage, String groupID) {
        if(!chatID.equals("")&&chatID.equals(entityMessage.getId())) {
            chatSession.get(groupID).add(entityMessage);
        }
        else {
            Platform.runLater(() -> mainClass.showMessageWarning(entityMessage.getSenderUser().getFirstName()+" send a message in group Message"));
        }
    }

}
