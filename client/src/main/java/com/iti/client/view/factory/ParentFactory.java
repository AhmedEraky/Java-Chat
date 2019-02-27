package com.iti.client.view.factory;

import com.iti.client.view.MainClass;
import com.iti.client.view.controller.*;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;

public class ParentFactory {

    static Initializable controller;
    public static Parent getRegistrationWindowRoot(MainClass mainClass){
        controller=new RegistrationWindowController(mainClass);
        return createParent(mainClass,controller,"RegistrationWindow");

    }
    public static Parent getChatWindowRoot(MainClass mainClass){
        controller=new ChatWindowController(mainClass);
        return createParent(mainClass,controller,"ChatWindow");

    }
    public static  Parent getLoginWindowRoot(MainClass mainClass){
        controller=new LoginWindowController(mainClass);
        return createParent(mainClass,controller,"LoginWindow");
    }

    public static  Parent getFriendRequestWindowRoot(MainClass mainClass){
        controller=new FriendRequestWindowController(mainClass);
        return createParent(mainClass,controller,"FriendRequestWindow");
    }
    public static  Parent getAddFriendWindowRoot(MainClass mainClass){
        controller=new AddFriendWindowController(mainClass);
        return createParent(mainClass,controller,"AddfriendWindow");

    }
    public static  Parent getMainWindowRoot(MainClass mainClass){
        controller=new MainWindowController(mainClass);
        return createParent(mainClass,controller,"MainWindow");

    }
    public static  Parent getGrouChatWindowRoot(MainClass mainClass){
        controller=new GroupChatWindowController(mainClass);
        return createParent(mainClass,controller,"GroupChatFxml");

    }
    public static  Parent getInvitationWindowRoot(MainClass mainClass){
        controller=new InvitationWindowController(mainClass);
        return createParent(mainClass,controller,"InvitationWindow");

    }

    private static Parent createParent(MainClass mainClass,Initializable controller,String controllerName){
        FXMLLoader loader=new FXMLLoader();
        loader.setController(controller);
        Parent root=null;
        try {
            root= loader.load(mainClass.getClass().getResource(controllerName+".fxml").toURI().toURL().openStream());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return root;
    }
    public static Initializable getController() {
        return controller;
    }

    public static  Parent getUpdateWindowRoot(MainClass mainClass, Stage stage){
        controller=new UpdateWindowController(mainClass,stage);
        return createParent(mainClass,controller,"UpdateWindow");
    }

    public static Parent getAddGroupChatWindowRoot(MainClass mainClass,Stage stage){
        controller=new CreateGroupController(mainClass,stage);
        return createParent(mainClass,controller,"AddFriendsGroupChatWindow");
    }
}
