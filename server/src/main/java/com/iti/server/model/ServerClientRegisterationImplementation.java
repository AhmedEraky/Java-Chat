/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iti.server.model;

import com.iti.ChatCommanServices.model.ClientInterfaces.ClientServiceInterface;
import com.iti.ChatCommanServices.model.ServerInterfaces.ServerClientRegisterationInterface;
import com.iti.server.model.dal.cfg.DBConnection;
import com.iti.server.model.dao.LoginDao;
import com.iti.server.model.dao.UserDao;
import com.iti.server.model.dao.implementation.UserDaoImplementation;
import com.iti.server.model.utils.ServerUtils;
import com.iti.server.model.utils.implementation.ServerUtilsImplementation;
import org.hibernate.Session;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class ServerClientRegisterationImplementation extends UnicastRemoteObject implements ServerClientRegisterationInterface {

    LoginDao loginDao;
    DBConnection dbConnection;
    public static HashMap<String, ClientServiceInterface> clients;

    ServerUtils utils;

    public ServerClientRegisterationImplementation(DBConnection dbConnection) throws RemoteException {
        this.dbConnection=dbConnection;
        clients=new HashMap<>();
        utils=new ServerUtilsImplementation();
        if(ServerClientEnteranceImplementation.clientSession==null)
            ServerClientEnteranceImplementation.clientSession=new HashMap<>();

    }

    /**
     * get Methods implementation of ClientInterface
     * @param client
     */
    @Override
    public void registerClient(String clientID, ClientServiceInterface client) throws RemoteException {
        clients.put(clientID,client);
        Session session=dbConnection.getConnection().openSession();
        if(!ServerClientEnteranceImplementation.clientSession.containsKey(clientID))
            ServerClientEnteranceImplementation.clientSession.put(clientID,session);

    }

    @Override
    public void unRegistration(String userID) {
        UserDao userDao=new UserDaoImplementation(dbConnection);
        userDao.updateStatus("offline",userID,ServerClientEnteranceImplementation.clientSession.get(userID));

        ServerClientEnteranceImplementation.clients.remove(userID);
        ServerClientInvitationImplementation.clients.remove(userID);
        ServerChatImplementation.clients.remove(userID);
        ServerClientUpdationImplementation.clients.remove(userID);
        ServerClientRegisterationImplementation.clients.remove(userID);

        ServerClientEnteranceImplementation.clientSession.get(userID).close();
        ServerClientEnteranceImplementation.clientSession.remove(userID);



    }



}
