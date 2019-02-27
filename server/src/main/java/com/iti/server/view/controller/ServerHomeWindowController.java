package com.iti.server.view.controller;

import com.iti.server.controller.ServerController;
import com.iti.server.controller.ServerSetup;
import com.iti.server.view.MainClass;
import com.iti.server.view.factory.ParentFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ServerHomeWindowController implements Initializable {

    @FXML
    Button createNewUser;
    @FXML
    Button startServerBtn;
    @FXML
    Button stopServerBtn;
    @FXML
    Button displayUsersBtn;
    @FXML
    ImageView displayUserIcon;
    @FXML
    ImageView ofOnIcon;
    @FXML
    ImageView maleFemaleIcon;

    @FXML
    ImageView createUserIcon;

    @FXML
    ImageView contriesicon;

    @FXML
    Button maleAndFemaleBtn;
    @FXML
    Button onlineOfflineBtn;
    @FXML
    Button coutriesBtn;
    @FXML
    VBox serverOptionsVbox;
    @FXML
    Label startingServerlabel;
    @FXML
    ImageView advirtisingIcon;

    @FXML
    Button advirtisingBtn;

    MainClass mainClass;


    public ServerHomeWindowController(MainClass mainClass) {
        this.mainClass = mainClass;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        createNewUser.setOnAction(this::createNewUser);
        startServerBtn.setOnAction(this::startServer);
        stopServerBtn.setOnAction(this::stopServer);
        displayUsersBtn.setOnAction(this::displayUsers);
        maleAndFemaleBtn.setOnAction(this::maleAndeFemaleStat);
        onlineOfflineBtn.setOnAction(this::onlineOfflineStat);
        coutriesBtn.setOnAction(this::countriesStat);
        advirtisingBtn.setOnAction(this::advirtisingUsers);
        displayUserIcon.setImage(new Image("display.png"));
        ofOnIcon.setImage(new Image("offon.png"));
        maleFemaleIcon.setImage(new Image("femalemale.png"));
        createUserIcon.setImage(new Image("new-user.png"));
        contriesicon.setImage(new Image("world.png"));
        advirtisingIcon.setImage(new Image("megaphone.png"));
        if (mainClass.serverOnline==true) {
            serverOptionsVbox.setVisible(true);
            startingServerlabel.setVisible(false);
        }
    }

    private void createNewUser(ActionEvent actionEvent) {
        Parent root = ParentFactory.getRegistrationWindowRoot(mainClass);
        mainClass.changeScene(root);
    }

    private void displayUsers(ActionEvent actionEvent) {
        Parent root = ParentFactory.getDisplayUsers(mainClass);
        mainClass.changeScene(root);
    }
    private void maleAndeFemaleStat(ActionEvent actionEvent)
    {
        Parent root = ParentFactory.getFemaleMaleStat(mainClass);
        mainClass.changeScene(root);
    }
    private void onlineOfflineStat(ActionEvent actionEvent)
    {
        Parent root = ParentFactory.getOfflineAndOnlineStat(mainClass);
        mainClass.changeScene(root);
    }
    private void advirtisingUsers(ActionEvent actionEvent)
    {
        Parent root = ParentFactory.getAdvirtisingWindow(mainClass);
        mainClass.changeScene(root);
    }
    private void countriesStat(ActionEvent actionEvent)
    {
        Parent root = ParentFactory.getCountriesStat(mainClass);
        mainClass.changeScene(root);
    }

    private void startServer(ActionEvent actionEvent) {
        if(mainClass.serverOnline==false) {
            serverOptionsVbox.setVisible(true);
            startingServerlabel.setVisible(true);
            startServerBtn.setVisible(false);
            mainClass.getServer().startServer(mainClass.getDbConnection());
            mainClass.serverOnline=true;
        }
    }

    private void stopServer(ActionEvent actionEvent) {
        if (mainClass.serverOnline==true) {
            mainClass.getServer().stopServer();
            mainClass.closeServer();
            serverOptionsVbox.setVisible(false);
            startingServerlabel.setVisible(false);
            startServerBtn.setVisible(true);
            mainClass.serverOnline=false;
        }

    }

}
