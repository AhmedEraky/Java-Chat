/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iti.ChatCommanServices.model.ServerInterfaces;

import com.iti.ChatCommanServices.model.ClientInterfaces.ClientServiceInterface;
import com.iti.ChatCommanServices.model.entity.message.EntityMessage;
import com.iti.ChatCommanServices.model.entity.message.MessageSettings;
import com.iti.ChatCommanServices.model.entity.user.User;
import com.iti.ChatCommanServices.model.exception.RejectFileException;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;


public interface ServerChatInterface extends Remote{
  
    ArrayList<User> USERS=null;

    public void registerClient(String clientID, ClientServiceInterface client) throws RemoteException;

    public void createChatGroup(ArrayList<User> mamber) throws RemoteException;
    /**
     * @author Eraky
     * upload MessageSettings to data base and send it to all users in chat
     * @param message
     */
    public void sendMessage(EntityMessage message,String reciverID) throws RemoteException;

    /**
     * @author Eraky
     * @param firstUser
     * @param secondUser
     * @throws RemoteException
     */
    public String startChating(User firstUser,User secondUser) throws RemoteException;
    
    public void updatSeenFriends(String string) throws RemoteException;

    public ArrayList<String> getChatHistory(User user)throws RemoteException;

    public ArrayList<EntityMessage> getMessages(String chatID,User user)throws RemoteException;

    public void sendFileTransfer(EntityMessage entityMessage, String reciverID,String path) throws RemoteException;

    public String friendAcceptanceFile(EntityMessage entityMessage, String string)throws RemoteException, RejectFileException;

    public ArrayList<String> getGroupHistory(User user) throws RemoteException;

    public void broadcastMessage(EntityMessage message,String groupID) throws RemoteException;

    public ArrayList<EntityMessage> getGroupMessages(String groupID,User user) throws RemoteException;

    public String validateStyle(MessageSettings settings,User user) throws RemoteException;

}
