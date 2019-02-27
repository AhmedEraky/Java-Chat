package com.iti.server.model.utils;


import com.iti.ChatCommanServices.model.ClientInterfaces.ClientServiceInterface;
import com.iti.ChatCommanServices.model.entity.message.EntityMessage;
import com.iti.ChatCommanServices.model.entity.user.User;
import com.iti.ChatCommanServices.model.exception.RejectFileException;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public interface ServerUtils {

    /**
     * @author Eraky
     * send Message to all user in chat
     * @param message
     */
    public void broadCast(EntityMessage message,byte[] image, Connection connection, ArrayList<ClientServiceInterface> clients);
    public void SendToFriend(EntityMessage message, String reciverID, Map<String,ClientServiceInterface> clients);
    public void broadcastOnlieStatus(ArrayList<User> user,User currentUser, Map<String, ClientServiceInterface> clients);
    public void broadcastOfflineStatus(ArrayList<User> user,User currentUser, Map<String, ClientServiceInterface> clients);
    public void SendFileToFriend(EntityMessage message, String reciverID, Map<String,ClientServiceInterface> clients,String path);
    public String checkFriendAcceptance(EntityMessage entityMessage, String reciverID, HashMap<String, ClientServiceInterface> clients) throws RejectFileException;
    public void addFriendsResult(int invitationAccepted, int invitationReceived, int emailReceived, User senderPhoneNumber, Map<String, ClientServiceInterface> clients);

    public void broadCastToGroup(EntityMessage message,ArrayList<User> users, HashMap<String, ClientServiceInterface> clients,String groupID);
    public void  updateUserStatusOnFriendsWindow(User user,ArrayList<User>userContacts,Map<String, ClientServiceInterface> clients);
}
