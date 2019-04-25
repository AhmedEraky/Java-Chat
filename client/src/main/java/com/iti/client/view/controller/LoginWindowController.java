/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package com.iti.client.view.controller;

import com.iti.ChatCommanServices.model.entity.user.EntityLogin;
import com.iti.ChatCommanServices.model.entity.user.User;
import com.iti.ChatCommanServices.model.exception.LoginFaieldException;
import com.iti.client.view.MainClass;
import com.iti.client.view.custom.dialog.PasswordDialog;
import com.iti.client.view.factory.ParentFactory;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import static com.iti.client.view.MainClass.alertMessageException;

public class LoginWindowController implements Initializable {

    @FXML
    Button loginButton;
    @FXML
    TextField userNameTextField;
    @FXML
    PasswordField passwordField;
    @FXML
    Button signupBtn;
    @FXML
    ImageView chatimg;
    @FXML
    VBox chatImgCbox;
    @FXML
    Pane chatimgPane;

    EntityLogin entityLogin;
    MainClass mainClass;
    Alert alert = new Alert(AlertType.INFORMATION);

    public LoginWindowController(MainClass mainClass) {
        this.mainClass = mainClass;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginButton.setOnAction(this::login);
        signupBtn.setOnAction(this::openSignupPage);
        chatimg.setImage(new Image("whatsapp.jpg"));
        chatimg.fitWidthProperty().bind(chatImgCbox.widthProperty());
        chatimg.fitHeightProperty().bind(chatImgCbox.heightProperty());
        chatimg.setPreserveRatio(true);
        mainClass.getStage().setTitle("Login Window");

        chatimg.setStyle("-fx-alignment: right;");

    }

    public void openSignupPage(ActionEvent actionEvent) {
        Parent root = ParentFactory.getRegistrationWindowRoot(mainClass);
        mainClass.changeScene(root);

    }

    public void login(ActionEvent actionEvent) {
        loginMethod();
    }

    public void loginMethod() {

        String phone, password;
        phone = userNameTextField.getText();
        password = passwordField.getText();
        if ((phone.equals("")) && (password.equals(""))) {
            emptyPhoneField("Please Enter Your Phone And Password");
        } else if (phone.equals("")) {
            emptyPhoneField("Please Enter Your Phone");
        } else if (password.equals("")) {
            emptyPhoneField("Please Enter Your Password");
        } else {
            entityLogin = new EntityLogin();
            entityLogin.setPhno(phone);
            entityLogin.setPassword(password);
            try {
                 mainClass.getServerServiceLocator().getEnteranceService().login(entityLogin);
                User user = mainClass.getServerServiceLocator().getUpdateService().getProfileData(phone);
                mainClass.setUser(user);
                OutputStream output = null;
                Properties properties = new Properties();
                InputStream inputStream = null;
                String fileName = "LoginFile";
                try {

                    output = new FileOutputStream("configFile");
                    properties.setProperty("UserName", mainClass.getUser().getPhno());
                    properties.setProperty("Password", mainClass.getUser().getPassword());
                    properties.store(output, null);

                } catch (FileNotFoundException ex) {
                    alertMessageException("Sorry File Not Found");
                } catch (IOException ex) {
                    alertMessageException("Sorry Error occurs");
                } finally {
                    if (output != null) {
                        try {
                            output.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
                Parent root = ParentFactory.getChatWindowRoot(mainClass);
                mainClass.changeScene(root);
                try {
                    mainClass.getServerServiceLocator().getUpdateService().notifyOnline(mainClass.getUser());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            } catch (RemoteException e) {
                alertMessageException("sorry error occurred in server");
            } catch (LoginFaieldException e) {
                emptyPhoneField("wrong password or phone");
            }
        }

    }

    public void emptyPhoneField(String message) {
        alert.setTitle("Information Dialog");
        alert.setHeaderText("warning");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void changePasswordByNewClient() {
        Platform.runLater(() -> {
            PasswordDialog passwordDialog = new PasswordDialog();
            String newPassword = passwordDialog.showAndWait().get();
            if (newPassword.length() >= 6) {
                try {
                    System.out.println(newPassword);
                    mainClass.getServerServiceLocator().getEnteranceService().changePasswordByNewClient(entityLogin, newPassword);
                } catch (RemoteException ex) {
                    alertMessageException("sorry error occurred in server");
                }
            } else {
                if (newPassword != "@#$#$") {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid");
                    alert.setHeaderText("Invalid Password");
                    alert.setContentText("Please your password must be mor than 6 characters");
                    alert.showAndWait();
                    changePasswordByNewClient();
                } else {
                    System.out.println("don't change");
                    try {
                        mainClass.getServerServiceLocator().getEnteranceService().samePasswordByNewClient(entityLogin);
                    } catch (RemoteException ex) {
                        alertMessageException("sorry error occurred in server");
                    }
                }

            }
        }
        );

    }

}
