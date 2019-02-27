/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iti.server.model;

import com.iti.ChatCommanServices.model.ClientInterfaces.ClientServiceInterface;
import com.iti.ChatCommanServices.model.ServerInterfaces.ServerClientEnteranceInterface;
import com.iti.ChatCommanServices.model.entity.user.EntityLogin;
import com.iti.ChatCommanServices.model.entity.user.User;
import com.iti.ChatCommanServices.model.exception.LoginFaieldException;
import com.iti.ChatCommanServices.model.exception.RegistrationDuplicateException;
import com.iti.server.model.dal.cfg.DBConnection;
import com.iti.server.model.dao.LoginDao;
import com.iti.server.model.dao.UserDao;
import com.iti.server.model.dao.implementation.LoginDaoImplementation;
import com.iti.server.model.dao.implementation.UserDaoImplementation;
import com.iti.server.model.utils.ServerUtils;
import com.iti.server.model.utils.implementation.ServerUtilsImplementation;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

/**
 *
 * @author AG
 */
public class ServerClientEnteranceImplementation extends UnicastRemoteObject implements ServerClientEnteranceInterface {

    public static HashMap<String, ClientServiceInterface> clients;

    LoginDao loginDao;
    DBConnection dbConnection;
    ServerUtils utils;

    public ServerClientEnteranceImplementation(DBConnection dbConnection) throws RemoteException {
        this.dbConnection=dbConnection;
        clients=new HashMap<>();

        utils=new ServerUtilsImplementation();
    }
 @Override
    public void registration(User user) throws RemoteException, RegistrationDuplicateException {
        UserDao userDao=new UserDaoImplementation(dbConnection);
        userDao.persistent(user);

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
    public void login(EntityLogin entityLogin) throws LoginFaieldException  {

        LoginDao loginDao=new LoginDaoImplementation(dbConnection);
        try {
            loginDao.reterive(entityLogin,clients);
            UserDao userDao=new UserDaoImplementation(dbConnection);
            userDao.updateStatus("onlineAvailable",entityLogin.getPhno());


        } catch (LoginFaieldException e) {
            throw new LoginFaieldException("Login Faild");
        }

    }

    @Override
    public void changePasswordByNewClient(EntityLogin entityLogin, String newPassword) throws RemoteException {
        LoginDao loginDao = new LoginDaoImplementation(dbConnection);
        loginDao.changePasswordByNewClient(entityLogin, newPassword);
    }


    @Override
    public void samePasswordByNewClient(EntityLogin entityLogin) throws RemoteException {
        LoginDao loginDao = new LoginDaoImplementation(dbConnection);
        loginDao.samePasswordByNewClient(entityLogin);
    }


}
