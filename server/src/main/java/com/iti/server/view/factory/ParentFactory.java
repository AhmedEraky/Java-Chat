package com.iti.server.view.factory;

import com.iti.server.view.MainClass;
import com.iti.server.view.controller.*;
//import com.iti.server.view.RegistrationWindowController;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URISyntaxException;

public class ParentFactory {
    
    static Initializable controller;
    
    public static Parent getRegistrationWindowRoot(MainClass mainClass){
        controller=new RegistrationWindowController(mainClass);
        return createParent(mainClass,controller,"RegistrationWindow");
        
    }
    public static Parent getServerHomeWindowRoot(MainClass mainClass){
        controller=new ServerHomeWindowController(mainClass);
        return createParent(mainClass,controller,"ServerHomeWindow");
        
    }
    public static Parent getAdvirtisingWindow(MainClass mainClass)
    {
        controller=new AdvirtisingWindowController(mainClass);
        return createParent(mainClass,controller,"AdvirtisingWindow");

    }
    public static Parent getDisplayUsers(MainClass mainClass){
        controller=new DisplayUsersController(mainClass);
        return createParent(mainClass,controller,"DisplayUsersWindow");
        
    }
    public static Parent getFemaleMaleStat(MainClass mainClass)
    {
        controller=new MaleFemaleStatWindowController(mainClass);
        return createParent(mainClass,controller,"MaleFemaleStatWindow");
        
    }
    public static Parent getOfflineAndOnlineStat(MainClass mainClass)
    {
        controller=new OnlineOfflineWindowController(mainClass);
        return createParent(mainClass,controller,"OnlineOfflineWindow");
        
    }
    public static Parent getCountriesStat(MainClass mainClass)
    {
       controller=new CountriesWindowController(mainClass);
        return createParent(mainClass,controller,"CountriesWindow");
        
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
}