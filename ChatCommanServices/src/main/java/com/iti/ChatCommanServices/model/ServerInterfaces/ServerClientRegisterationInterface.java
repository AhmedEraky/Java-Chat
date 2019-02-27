/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iti.ChatCommanServices.model.ServerInterfaces;

import com.iti.ChatCommanServices.model.ClientInterfaces.ClientServiceInterface;
import com.iti.ChatCommanServices.model.entity.user.User;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;


public interface ServerClientRegisterationInterface extends Remote{
    
      ArrayList<User> USERS=null;
    /**
     * first method to call
     * Save Client implementation to server
     * @param client
     */
    public void registerClient(String clientID, ClientServiceInterface client) throws RemoteException;


    /**
     * remove client from list of onine user
     * @param userID
     */
    public void unRegistration(String userID) throws RemoteException;


}
