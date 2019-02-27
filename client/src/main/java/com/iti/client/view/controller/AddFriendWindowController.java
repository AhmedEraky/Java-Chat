/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iti.client.view.controller;

import com.iti.ChatCommanServices.model.entity.user.User;
import com.iti.ChatCommanServices.model.entity.user.UserContact;
import com.iti.client.view.MainClass;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import static com.iti.client.view.MainClass.alertMessageException;

/**
 *
 * @author Esraa
 */
public class AddFriendWindowController extends ParentMenuBarController{

    MainClass mainClass;
    @FXML
    private BorderPane rootPane;

    @FXML
    private TextField textField;

    @FXML
    private Button Submit;

    @FXML
    private Button Add;

    @FXML
    private ListView listView;

    @FXML
    private Label addedFriends;

    @FXML
    private Label sendedInvitation;

    @FXML
    private Label unregistered;

    Stage stage;

    ObservableList<String> friendRequstList;
    public AddFriendWindowController(MainClass mainClass) {
        this.mainClass = mainClass;

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setButtonAction(mainClass);
        Add.setOnAction(this::addInList);
        Submit.setOnAction(this::addFriends);
        friendRequstList= FXCollections.observableList(new ArrayList<>());
        listView.setItems(friendRequstList);
        mainClass.getStage().setTitle("Add Friend");

    }
    public void addInList(ActionEvent actionEvent) {

        if (textField.getText() != null ) {
            if(textField.getText().length()==11){

                try {
                    String text = textField.getText();
                    //System.out.println(text);
                    //check first if they are already friends
                    ArrayList<User> userContacts=new ArrayList<>();
                    userContacts   = mainClass.getServerServiceLocator().getUpdateService().getUserContacts(mainClass.getUser().getPhno());
                    boolean friendsFlag=false;
                    for(int i=0;(i<userContacts.size())&&(!friendsFlag);i++){
                        if(userContacts.get(i).getPhno().equals(text)){
                            friendsFlag=true;
                        }

                    }
                    //if(yes)

                    System.out.println(friendsFlag);
                    if(friendsFlag){

                        //get user from phno
                        User user=mainClass.getServerServiceLocator().getUpdateService().getProfileData(text);

                        //alert
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText(user.getFirstName()+" "+user.getLastName()+" "+"is Friend");
                        alert.show();
                        //clear textfield
                        textField.clear();
                    }
                    System.out.println(mainClass.getUser().getPhno());
                    ArrayList<User> invitations=new ArrayList<>();
                    invitations= mainClass.getServerServiceLocator().getInvitationService().getInvitationsUsingSenderPhone(mainClass.getUser().getPhno());
                    boolean invitationFlag=false;
                    for(int i=0;(i<invitations.size())&&(!invitationFlag);i++){
                        if(invitations.get(i).getPhno().equals(text)){
                            invitationFlag=true;
                        }

                    }
                    System.out.println(invitationFlag);
                    if(invitationFlag){

                        //get user from phno
                        User user=mainClass.getServerServiceLocator().getUpdateService().getProfileData(text);
                        //alert
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText(user.getFirstName()+" "+user.getLastName()+" "+"has already received invitation");
                        alert.show();
                        //clear textfield
                        textField.clear();
                    }
                    //donot add it in listview and clear textfield and alert is appear
                    //also check if invitation is send
                    //if(yes)
                    //donot add it in listview and clear textfield and alert is appear

                    //else add on list
                    else if((!friendsFlag)&&(!invitationFlag)){

                        //listView.getItems().add(text);
                        if (friendRequstList.contains(text)){
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("this phone number is added in List");
                            alert.show();
                        }
                        else if(text.equals(mainClass.getUser().getPhno())){
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("You can't add your phone number");
                            alert.show();
                        }
                        else
                            friendRequstList.add(text);
                        textField.clear();
                    }
                } catch (RemoteException ex) {
                    alertMessageException("sorry error occured in server");
                }

            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You entered invalid phone number");
                alert.show();

            }
        }
    }
    public void addFriends(ActionEvent action){
        List<String> numbers = listView.getItems();
        ArrayList<UserContact> contact = new ArrayList<>();
        String userPhoneNumber=mainClass.getUser().getPhno();
        for (int i = 0; i < numbers.size(); i++) {
            UserContact userContact=new UserContact();
            userContact.setPhno(userPhoneNumber);
            userContact.setContactPhno(numbers.get(i));
            contact.add(userContact);
        }
        try {
            mainClass.getServerServiceLocator().getInvitationService().addContact(contact);
        } catch (RemoteException ex) {
            ex.printStackTrace();
            //alertMessageException("sorry error occured in server");
        }

        //clear listview

        listView.getItems().clear();



    }
    public void addFriendsResult(int invitationAccepted, int invitationReceived, int emailReceived){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                addedFriends.setText("Number of added Friends: "+String.valueOf(invitationAccepted));
                sendedInvitation.setText("Number of sended invitation: "+String.valueOf(invitationReceived));
                unregistered.setText("Number of unregistered: "+String.valueOf(emailReceived));
            }

        });
    }


}
