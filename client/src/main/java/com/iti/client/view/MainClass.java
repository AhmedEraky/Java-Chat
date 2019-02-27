package com.iti.client.view;

import com.iti.ChatCommanServices.model.ClientInterfaces.ClientServiceInterface;
import com.iti.ChatCommanServices.model.entity.user.EntityLogin;
import com.iti.ChatCommanServices.model.entity.user.User;
import com.iti.client.controller.ServerServiceLocator;
import com.iti.client.model.ClientServiceImplementation;
import com.iti.client.view.controller.ChatWindowController;
import com.iti.client.view.controller.LoginWindowController;
import com.iti.client.view.factory.ParentFactory;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.rmi.RemoteException;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class MainClass extends Application {

    private Scene currentScene;
    private ServerServiceLocator serverServiceLocator;
    private ClientServiceInterface clientServiceInterface;
    private Stage stage;
    private User user;
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    Properties properties = new Properties();
    InputStream propertyFileInputStream = null;
    String fileName = "configFile";

    public Stage getStage() {
        return stage;
    }

    public ServerServiceLocator getServerServiceLocator() {
        return serverServiceLocator;
    }

    public void setUser(User user) {
        this.user = user;
        try {
            serverServiceLocator.getRegisterationService().registerClient(user.getPhno(), clientServiceInterface);
            serverServiceLocator.getInvitationService().registerClient(user.getPhno(), clientServiceInterface);
            serverServiceLocator.getChatService().registerClient(user.getPhno(), clientServiceInterface);
            serverServiceLocator.getEnteranceService().registerClient(user.getPhno(), clientServiceInterface);
            serverServiceLocator.getUpdateService().registerClient(user.getPhno(), clientServiceInterface);

        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    public User getUser() {
        return user;
    }

    public static void main(String args[]) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        super.init();
        serverServiceLocator = new ServerServiceLocator();
        clientServiceInterface = new ClientServiceImplementation(this);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        InputStream inputStream = null;
        Properties properties = new Properties();
        inputStream = new FileInputStream("configFile");
        properties.load(inputStream);
        String flag = properties.getProperty("flag");
        stage = primaryStage;

        Parent homeRoot = null;
        if (flag != null && flag.equals("0")) {
            EntityLogin entityLogin = new EntityLogin();
            entityLogin.setPhno(properties.getProperty("UserName"));
            entityLogin.setPassword(properties.getProperty("Password"));
            try {
                User user = serverServiceLocator.getUpdateService().getProfileData(entityLogin.getPhno());
                setUser(user);
                serverServiceLocator.getEnteranceService().login(entityLogin);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            homeRoot = ParentFactory.getChatWindowRoot(this);
            try {
                serverServiceLocator.getUpdateService().notifyOnline(user);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        } else {
            homeRoot = ParentFactory.getLoginWindowRoot(this);
        }

        changeScene(homeRoot);
        stage.show();

        stage.setOnCloseRequest(windowEvent -> {

            if (user != null) {
                try {
                    serverServiceLocator.getUpdateService().notifyOffline(user);
                    serverServiceLocator.getRegisterationService().unRegistration(user.getPhno());
                    if (user != null) {
                        OutputStream output = null;
                        output = new FileOutputStream("configFile");
                        properties.setProperty("flag", "0");
                        properties.setProperty("UserName", user.getPhno());
                        properties.setProperty("Password", user.getPassword());
                        properties.store(output, null);
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            Platform.exit();
            System.exit(0);
        });
    }

    public void changeScene(Parent root) {
        currentScene = new Scene(root);
        stage.setScene(currentScene);
    }

    public void clearUser(){
        user=null;
    }
    public Initializable getGui() {
        return ParentFactory.getController();
    }

    public ClientServiceInterface getClientServiceInterface() {
        return clientServiceInterface;
    }
//

    public void showMessageWarning(String warning) {
        alert.setTitle("Note");
        alert.setHeaderText("warning");
        alert.setContentText(warning);
        alert.showAndWait();
    }


    public static void alertMessageException(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText("OOps");
        alert.setContentText(message);
        alert.showAndWait();
    }


}
