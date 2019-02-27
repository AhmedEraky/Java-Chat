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


public interface ServerClientUpdationInterface extends Remote {
    ArrayList<User> USERS=null;
    public void registerClient(String clientID, ClientServiceInterface client) throws RemoteException;

    public User getProfileData(String userPhone)throws RemoteException;

    public void notifyOnline(User user) throws RemoteException;

    public void notifyOffline(User user) throws RemoteException;
    /**
     * @author Bishoy
     * @param phoneNumber
     * @return
     * @throws RemoteException
     */
    public ArrayList<User> getUserContacts(String phoneNumber) throws RemoteException;


    /**
     * change User Status
     * @param userPhoneNumber
     * @param status
     */
    public void changeStatus(User user) throws RemoteException;




    //todo: Move to Internal class
    /**
     * Notify all online User that a user status has been changed
     * @param userPhoneNumber
     */
    public void changeStatusNotification(String userPhoneNumber) throws RemoteException;

    public void updateUserInformation(User user) throws RemoteException;





}
