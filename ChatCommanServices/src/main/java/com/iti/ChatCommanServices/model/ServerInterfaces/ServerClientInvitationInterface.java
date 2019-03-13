/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iti.ChatCommanServices.model.ServerInterfaces;

import com.iti.ChatCommanServices.model.ClientInterfaces.ClientServiceInterface;
import com.iti.ChatCommanServices.model.entity.user.User;
import com.iti.ChatCommanServices.model.entity.user.UserContact;
import com.iti.ChatCommanServices.model.entity.user.UserContactId;
import com.iti.ChatCommanServices.model.entity.user.UserInvitation;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;


public interface ServerClientInvitationInterface extends Remote{

    ArrayList<User> USERS=null;
    /**
     * Accept User Invitation
     * @param userInvitation
     */
    public void acceptInvitation(UserInvitation userInvitation)throws RemoteException;

    public void registerClient(String clientID, ClientServiceInterface client) throws RemoteException;

    /**
     * Reject User Invitation
     * @param userInvitation
     */
    public void rejectInvitation(UserInvitation userInvitation) throws RemoteException;
/**
     *add user contact
     *  add forloop on arraylist of phone that user need to add
     *  (in view control to handle add button),each loop will call this method
     * @param userContact
     */
    public void addContact(ArrayList<UserContactId> userContact) throws RemoteException;
    
    public ArrayList<User> getInvitations(String UserphoneNumber) throws RemoteException;

    /**
     * @author Abdelkarim
     * @param UserphoneNumber
     * @return
     * @throws RemoteException
     */
    public int getInvitationsCount(String UserphoneNumber) throws RemoteException;

    public void ignoreInvitation(UserInvitation userInvitation) throws RemoteException;

    public ArrayList<User> getInvitationsUsingSenderPhone(String UserphoneNumber) throws RemoteException;

}
