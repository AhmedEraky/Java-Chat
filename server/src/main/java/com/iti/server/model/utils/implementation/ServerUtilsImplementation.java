package com.iti.server.model.utils.implementation;

import com.iti.ChatCommanServices.model.ClientInterfaces.ClientServiceInterface;
import com.iti.ChatCommanServices.model.entity.message.EntityMessage;
import com.iti.ChatCommanServices.model.entity.user.User;
import com.iti.ChatCommanServices.model.exception.RejectFileException;
import com.iti.server.model.dao.UserDao;
import com.iti.server.model.dao.implementation.UserDaoImplementation;
import com.iti.server.model.utils.ServerUtils;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerUtilsImplementation implements ServerUtils {

    @Override
    public void broadCast(EntityMessage message,byte[] image, Connection connection, ArrayList<ClientServiceInterface> clients) {
        /*//retrieve All users in current Chat
        UserChatDao userChatDao = new UserChatDaoImplementaion();
        Set<String> users = userChatDao.retiveUser(message.getId(), connection);

        clients.stream().filter(client ->
        {
            try {
                String currentUser=client.getClientID();
                return ( !currentUser.equals(message.getChatUserPhoneNumber())  )&& users.contains(currentUser);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            return false;
        }).forEach(client -> {
            try {
                client.reciveMessage(message,image);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
*/
    }

    @Override
    public void SendToFriend(EntityMessage message, String reciverID, Map<String, ClientServiceInterface> clients) {

        try {
            clients.get(reciverID).reciveMessage(message);
        } catch (RemoteException e) {
            //todo handel Close Friend
            e.printStackTrace();
        }

    }

    @Override
    public void broadcastOnlieStatus(ArrayList<User> user,User currentUser, Map<String, ClientServiceInterface> clients) {
        for (User contact:user){
            try {
                clients.get(contact.getPhno()).addNewOnlineContact(currentUser);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void broadcastOfflineStatus(ArrayList<User> user, User currentUser, Map<String, ClientServiceInterface> clients) {
        for (User contact:user){
            try {
                clients.get(contact.getPhno()).removeOnlineContact(currentUser);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }


    //file
    @Override
    public void SendFileToFriend(EntityMessage message, String reciverID, Map<String, ClientServiceInterface> clients,String path) {

        try {
            String home = System.getProperty("user.home");
            clients.get(reciverID).reciveFile(message,home+"/Downloads/");
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String checkFriendAcceptance(EntityMessage entityMessage, String reciverID, HashMap<String, ClientServiceInterface> clients) throws RejectFileException {
        try {
            try {
                clients.get(reciverID).reciveDicisionFile(entityMessage);
            }
            catch (RejectFileException e){
                if (e.getMessage().charAt(0)=='D'){
                    return e.getMessage().substring(1,e.getMessage().length());
                }
                else
                    throw e;
            }
        } catch (RemoteException ex) {
            Logger.getLogger(ServerUtilsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            return "";
        }
    }

    @Override
    public void addFriendsResult(int invitationAccepted, int invitationReceived, int emailReceived, User senderPhoneNumber, Map<String, ClientServiceInterface> clients) {
        try {
            if (senderPhoneNumber.getStatus().equals("onlineAvailable"))
                clients.get(senderPhoneNumber.getPhno()).addFriendsResult(invitationAccepted, invitationReceived, emailReceived);

        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void broadCastToGroup(EntityMessage message, ArrayList<User> users, HashMap<String, ClientServiceInterface> clients,String groupID) {
        for (User user:users){
            try {
                if (user.getStatus().equals("onlineAvailable"))
                    clients.get(user.getPhno()).reciveGroupMessage(message,groupID);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateUserStatusOnFriendsWindow(User user, ArrayList<User> userContacts, Map<String, ClientServiceInterface> clients) {

        try {
            for(int i=0;i<userContacts.size();i++)
                clients.get(userContacts.get(i).getPhno()).updateUserStatusOnFriendsWindow( user,userContacts.get(i));
        } catch (RemoteException ex) {
            Logger.getLogger(ServerUtilsImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}


