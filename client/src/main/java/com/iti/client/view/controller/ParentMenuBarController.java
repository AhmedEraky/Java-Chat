/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package com.iti.client.view.controller;

import com.iti.client.view.MainClass;
import com.iti.client.view.factory.ParentFactory;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 *
 * @author Esraa
 */
public class ParentMenuBarController implements Initializable {

    @FXML
    private VBox chatRoomVbox;

    @FXML
    private Pane userProfilePane;

    @FXML
    private ImageView userProfileImage;

    @FXML
    private ImageView groupChatIcon;

    @FXML
    private ImageView addFriendIcon;

    @FXML
    private ImageView notificationIcon;

    @FXML
    private ImageView editProfileIcon;

    @FXML
    private ImageView signOutIcon;

    @FXML
    private ImageView onlinFriends;
    @FXML
    private HBox userImgHbox;

    MainClass mainClass;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setButtonAction(MainClass mainClass) {

        userProfileImage.setImage(new Image("/personal.png"));
        editProfileIcon.setImage(new Image("/gear.png"));
        notificationIcon.setImage(new Image("/notificationlast.png"));
        addFriendIcon.setImage(new Image("/add_friendImg.png"));
        signOutIcon.setImage(new Image("/sign-out.png"));
        groupChatIcon.setImage(new Image("/group.png"));
        onlinFriends.setImage(new Image("/facesbuble.png"));
        /*Circle imageCircle;
        userProfileImage.setClip(new Circle(25, 25, 30));
        imageCircle = new Circle(25, 25, 30);
        imageCircle.setFill(new ImagePattern(userProfileImage.getImage()));
        userImgHbox.getChildren().add(imageCircle);*/
        this.mainClass = mainClass;
        setUserImage(mainClass);
        signOutIcon.setOnMouseClicked(this::signOut);
        addFriendIcon.setOnMouseClicked(event->
        {
            Parent root= ParentFactory.getAddFriendWindowRoot(mainClass);
            mainClass.changeScene(root);
        });

        groupChatIcon.setOnMouseClicked(event
                -> {
            Parent root = ParentFactory.getGrouChatWindowRoot(mainClass);
            mainClass.changeScene(root);
        });
        notificationIcon.setOnMouseClicked(event
                -> {
            Parent root = ParentFactory.getInvitationWindowRoot(mainClass);
            mainClass.changeScene(root);
        });
        onlinFriends.setOnMouseClicked(event
                -> {
            Parent root = ParentFactory.getChatWindowRoot(mainClass);
            mainClass.changeScene(root);
        });
        editProfileIcon.setOnMousePressed(mouseEvent -> {
            Stage stage = new Stage();
            mainClass.getStage().hide();
            Parent root = ParentFactory.getUpdateWindowRoot(mainClass, stage);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            stage.setOnCloseRequest(windowEvent -> {
                stage.close();
            });

        });

    }

    private void signOut(MouseEvent event) {
        OutputStream output = null;
        Properties prop = new Properties();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Exit");
        String validate = "Confirm To SignOut";
        alert.setContentText(validate);
        Optional<ButtonType> result = alert.showAndWait();
        if ((result.get() == ButtonType.OK)) {
            try {
                output = new FileOutputStream("configFile");
                prop.setProperty("flag", "1");
                prop.setProperty("UserName", mainClass.getUser().getPhno());
                prop.setProperty("Password", mainClass.getUser().getPassword());
                prop.store(output, null);
                Parent root = ParentFactory.getLoginWindowRoot(mainClass);
                mainClass.getServerServiceLocator().getUpdateService().notifyOffline(mainClass.getUser());
                mainClass.getServerServiceLocator().getRegisterationService().unRegistration(mainClass.getUser().getPhno());
                mainClass.clearUser();
                mainClass.changeScene(root);
                
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                if (output != null) {
                    try {
                        output.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }

        }
    }

    private void setUserImage(MainClass mainClass) {
        byte[] imageByte = mainClass.getUser().getImage();
        userProfileImage.setImage(new Image(new ByteArrayInputStream(imageByte)));
    }
}
