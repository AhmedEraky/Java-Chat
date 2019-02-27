package com.iti.server.view;
import com.iti.server.controller.ServerController;
import com.iti.server.controller.ServerSetup;
import com.iti.server.model.*;
import com.iti.server.model.dal.cfg.DBConnection;
import com.iti.server.view.factory.ParentFactory;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.rmi.RemoteException;

public class MainClass extends Application {

    Stage stage;
    private Scene currentScene;
    ServerController serverController = new ServerController();
    ServerSetup server = new ServerSetup();
    public static boolean serverOnline;

    public DBConnection getDbConnection(){
        return serverController.dbConnction;
    }


    public ServerSetup getServer() {
        return server;
    }

    public static void main(String args[]) {
        launch(args);
    }

    public Stage getStage() {
        return stage;
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        serverOnline=true;
        stage = primaryStage;
        Parent window = ParentFactory.getServerHomeWindowRoot(this);
        Scene scene = new Scene(window);
        stage.setScene(scene);
        stage.show();
        server.startServer(getDbConnection());
        stage.setOnCloseRequest(this::close);
    }

    public void closeServer(){
        ServerClientEnteranceImplementation.clients.forEach((s, clientServiceInterface) -> {
            try {
                clientServiceInterface.notifyCloseServer();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
        Thread thread=new Thread(() -> {
            try {
                Thread.sleep(5000);
                System.exit(0);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
    public void close(WindowEvent windowEvent) {
        closeServer();
    }

    public void changeScene(Parent root){
        currentScene=new Scene(root);
        stage.setScene(currentScene);
    }


}

//}
