package com.iti.ChatCommanServices.model.ClientInterfaces;

import com.iti.ChatCommanServices.model.entity.message.EntityMessage;
import com.iti.ChatCommanServices.model.entity.user.User;
import com.iti.ChatCommanServices.model.exception.RejectFileException;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ClientServiceInterface extends Remote {
    /**
     * notify that you receive a new message
     */
    public void reciveMessage(EntityMessage message)throws RemoteException;
    public void addNewOnlineContact(User user) throws RemoteException;
    public void removeOnlineContact(User user) throws RemoteException;
    /**
     * notify that Some of online friend change his Status
     */
    public void showOnlineFriendChangeStatus(User user)throws RemoteException;

    public String getClientID() throws RemoteException;

    /**
     * notify that There is a new invitations
     */
    public void showNewInvitations(int count)throws RemoteException;

    /**
     * notify that is a new invitations
     */
    public void showAcceptedRequest(User user)throws RemoteException;

    /**
     * @param invitationAccepted
     */
    public void showAcceptedInvitation(ArrayList<String> invitationAccepted) throws RemoteException;

    /**
     *
     * @param invitationReceived
     */
    public void showReceivedInvitation(ArrayList<String> invitationReceived) throws RemoteException;

    public void reciveNewFriendRequst(User user) throws RemoteException;

    //new client recommend to change password
    public  void changePasswordByNewClient() throws RemoteException;

    public void changedPasswordByNewUser()throws RemoteException;

    //file
    public void reciveFile(EntityMessage message,String path)throws RemoteException;

    public String reciveDicisionFile(EntityMessage entityMessage)throws RemoteException, RejectFileException;


    /**
     *  add friends result
     * @param
     */
    public void addFriendsResult(int invitationAccepted,int invitationReceived,int emailReceived) throws RemoteException;

    /**
     *
     * @param
     */
    public void updateUserStatusOnFriendsWindow(User user,User userFriends) throws RemoteException;

    public void reciveGroupMessage(EntityMessage message,String groupid) throws RemoteException;

    public void reciveServerMessage(String message) throws RemoteException;

    public void notifyCloseServer() throws RemoteException;

}

