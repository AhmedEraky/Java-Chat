/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iti.server.model;
import com.iti.ChatCommanServices.model.ClientInterfaces.ClientServiceInterface;
import com.iti.ChatCommanServices.model.ServerInterfaces.ServerChatInterface;
import com.iti.ChatCommanServices.model.entity.message.EntityMessage;
import com.iti.ChatCommanServices.model.entity.message.MessageSettings;
import com.iti.ChatCommanServices.model.entity.user.User;
import com.iti.ChatCommanServices.model.exception.RejectFileException;
import com.iti.server.model.dal.cfg.DBConnection;
import com.iti.server.model.dao.*;
import com.iti.server.model.dao.implementation.*;
import com.iti.server.model.utils.ServerUtils;
import com.iti.server.model.utils.implementation.ServerUtilsImplementation;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author AG
 */
public class ServerChatImplementation extends UnicastRemoteObject implements ServerChatInterface {

    public static HashMap<String, ClientServiceInterface> clients;
    LoginDao loginDao;
    DBConnection dbConnection;

    ServerUtils utils;

    public ServerChatImplementation(DBConnection dbConnection) throws RemoteException {
        this.dbConnection=dbConnection;
        clients=new HashMap<>();
        utils=new ServerUtilsImplementation();
    }

    /**
     * get Methods implementation of ClientInterface
     * @param client
     */
    @Override
    public void registerClient(String clientID, ClientServiceInterface client) throws RemoteException {
        clients.put(clientID,client);
    }



    @Override
    public void createChatGroup(ArrayList<User> mamber) throws RemoteException {
        UserGroupDao userGroupDao=new UserGroupDaoImplementation(dbConnection);
        userGroupDao.persistent(mamber);
    }

     @Override
    public void sendMessage(EntityMessage message,String reciverID) throws RemoteException {
        ChatDao chatDao=new ChatDaoImplementation(dbConnection);
        chatDao.persistent(message);
        utils.SendToFriend(message,reciverID,clients);

    }

     @Override
    public String startChating(User firstUser, User secondUser) throws RemoteException {
        UserChatDao userChatDao=new UserChatDaoImplementaion();
        String chatID = userChatDao.retrieveChatID(firstUser,secondUser,dbConnection.getConnection());
        return chatID;
    }


    @Override
    public void updatSeenFriends(String string) {
        UserInvitationDao userInvtationCountDao = new UserInvitationDaoImplementation(dbConnection);
        userInvtationCountDao.updatSeenFriends(string);
    }

    @Override
    public ArrayList<String> getChatHistory(User user) {
        UserChatDao userChatDao =new UserChatDaoImplementaion();
        return userChatDao.retrieveChatsID(user,dbConnection.getConnection());
    }

    @Override
    public ArrayList<EntityMessage> getMessages(String chatID) {
        ChatDao chatDao=new ChatDaoImplementation(dbConnection);
        return chatDao.reterive(chatID,dbConnection.getConnection());
    }

    @Override
    public void sendFileTransfer(EntityMessage entityMessage, String reciverID,String path) throws RemoteException {
        utils.SendFileToFriend(entityMessage,reciverID,clients,path);

    }

    @Override
    public String friendAcceptanceFile(EntityMessage entityMessage, String reciverID) throws RemoteException, RejectFileException {
        try {
            return utils.checkFriendAcceptance(entityMessage,reciverID,clients);
        }catch (RejectFileException e)
        {
            throw e;
        }finally {
            return "";
        }
    }
    @Override
    public ArrayList<String> getGroupHistory(User user) throws RemoteException {
        UserGroupDao userGroupDao=new UserGroupDaoImplementation(dbConnection);
        return userGroupDao.reteriveGroups(user);
    }

    @Override
    public void broadcastMessage(EntityMessage entityMessage,String groupID) throws RemoteException {
        UserGroupDao userGroupDao=new UserGroupDaoImplementation(dbConnection);
        GroupChatDao groupChatDao=new GroupChatDaoImplementation(dbConnection);
        ServerUtils utils=new ServerUtilsImplementation();
        ArrayList<User> users= userGroupDao.reteriveGroupMember(entityMessage.getSenderUser(),groupID);
        groupChatDao.persistent(entityMessage);
        utils.broadCastToGroup(entityMessage,users,clients,groupID);

    }

    @Override
    public ArrayList<EntityMessage> getGroupMessages(String s) throws RemoteException {
        GroupChatDao groupChatDao=new GroupChatDaoImplementation(dbConnection);
        return groupChatDao.reterive(s,dbConnection.getConnection());
    }

    @Override
    public String validateStyle(MessageSettings messageSettings) throws RemoteException {
        MessageSetingDao messageSetingDao=new MessageSettingDaoImp(dbConnection);
        return messageSetingDao.compareStyle(messageSettings);
    }

}
