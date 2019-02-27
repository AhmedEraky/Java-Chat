/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iti.server.model;

import com.iti.ChatCommanServices.model.ClientInterfaces.ClientServiceInterface;
import com.iti.ChatCommanServices.model.ServerInterfaces.ServerClientUpdationInterface;
import com.iti.ChatCommanServices.model.entity.user.User;
import com.iti.server.model.dal.cfg.DBConnection;
import com.iti.server.model.dao.LoginDao;
import com.iti.server.model.dao.UserContactDao;
import com.iti.server.model.dao.UserDao;
import com.iti.server.model.dao.implementation.UserContactDaoImplementation;
import com.iti.server.model.dao.implementation.UserDaoImplementation;
import com.iti.server.model.utils.ServerUtils;
import com.iti.server.model.utils.implementation.ServerUtilsImplementation;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;


public class ServerClientUpdationImplementation  extends UnicastRemoteObject implements ServerClientUpdationInterface {

    LoginDao loginDao;
    DBConnection dbConnection;
    public static HashMap<String, ClientServiceInterface> clients;
    ServerUtils utils;

    public ServerClientUpdationImplementation(DBConnection dbConnection) throws RemoteException {
        this.dbConnection=dbConnection;
        clients=new HashMap<>();
        utils=new ServerUtilsImplementation();
    }

    @Override
    public User getProfileData(String userPhoneNumber) throws RemoteException {
        UserDao userDao=new UserDaoImplementation(dbConnection);
        return userDao.reterive(userPhoneNumber);

    }

    @Override
    public void notifyOffline(User user){
        UserContactDao userContactDao=new UserContactDaoImplementation(dbConnection);
        ArrayList<User> friends=userContactDao.reteriveOnlineFriends(user.getPhno());
        ServerUtils utils=new ServerUtilsImplementation();
        utils.broadcastOfflineStatus(friends,user,clients);

    }

    @Override
    public void notifyOnline(User user){
        UserContactDao userContactDao=new UserContactDaoImplementation(dbConnection);
        ArrayList<User> friends=userContactDao.reteriveOnlineFriends(user.getPhno());
        ServerUtils utils=new ServerUtilsImplementation();
        utils.broadcastOnlieStatus(friends,user,clients);
    }

    @Override
    public ArrayList<User> getUserContacts(String phoneNumber) throws RemoteException {
        UserContactDao userContactDao =new UserContactDaoImplementation(dbConnection);
        return userContactDao.reterive(phoneNumber);
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
    public void changeStatus(User user) throws RemoteException {
        UserDao userDao=new UserDaoImplementation(dbConnection);
        userDao.updateStatus(user.getStatus(), user.getPhno());
        //get usercontacts
        UserContactDao userContactDao =new UserContactDaoImplementation(dbConnection);

        ArrayList<User> userContacts=userContactDao.reteriveOnlineFriends(user.getPhno());
        ServerUtils utils=new ServerUtilsImplementation();
        utils.updateUserStatusOnFriendsWindow(user, userContacts, clients);
    }

    @Override
    public void changeStatusNotification(String userPhoneNumber) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateUserInformation(User user) throws RemoteException {
        UserDao userDao=new UserDaoImplementation(dbConnection);
        userDao.update(user);
    }



}
