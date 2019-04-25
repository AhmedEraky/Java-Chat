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
import org.hibernate.Session;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

/**
 *
 * @author AG
 */
public class ServerClientEnteranceImplementation extends UnicastRemoteObject implements ServerClientEnteranceInterface {

    public static HashMap<String, ClientServiceInterface> clients;
    public static HashMap<String, Session> clientSession;

    LoginDao loginDao;
    DBConnection dbConnection;
    ServerUtils utils;

    public ServerClientEnteranceImplementation(DBConnection dbConnection) throws RemoteException {
        this.dbConnection=dbConnection;
        clients=new HashMap<>();
        clientSession=new HashMap<>();
        utils=new ServerUtilsImplementation();
    }
    @Override
    public void registration(User user) throws RemoteException, RegistrationDuplicateException {
        UserDao userDao=new UserDaoImplementation(dbConnection);
        if(clientSession.containsKey(user.getPhno()))
        {
            Session session=dbConnection.getConnection().openSession();
            userDao.persistent(user,clientSession.get(user.getPhno()));
        }
        else {
            Session session=dbConnection.getConnection().openSession();
            userDao.persistent(user,session);

        }

    }

    /**
     * get Methods implementation of ClientInterface
     * @param client
     */
    @Override
    public void registerClient(String clientID, ClientServiceInterface client) throws RemoteException {
        clients.put(clientID,client);
        Session session=dbConnection.getConnection().openSession();
        clientSession.put(clientID,session);
    }
    @Override
    public void login(EntityLogin entityLogin) throws LoginFaieldException  {

        LoginDao loginDao=new LoginDaoImplementation(dbConnection);
        try {
            if(clientSession.containsValue(entityLogin.getPhno())){
                loginDao.reterive(entityLogin,clients,clientSession.get(entityLogin.getPhno()));
                UserDao userDao=new UserDaoImplementation(dbConnection);
                userDao.updateStatus("onlineAvailable",entityLogin.getPhno(),clientSession.get(entityLogin.getPhno()));
            }else
            {
                Session session=dbConnection.getConnection().openSession();
                loginDao.reterive(entityLogin,clients,session);
                UserDao userDao=new UserDaoImplementation(dbConnection);
                userDao.updateStatus("onlineAvailable",entityLogin.getPhno(),session);
                session.close();
            }

        } catch (LoginFaieldException e) {
            throw new LoginFaieldException("Login Failed");
        }

    }

    @Override
    public void changePasswordByNewClient(EntityLogin entityLogin, String newPassword) throws RemoteException {
        LoginDao loginDao = new LoginDaoImplementation(dbConnection);
        loginDao.changePasswordByNewClient(entityLogin, newPassword,clientSession.get(entityLogin.getPhno()));
    }


    @Override
    public void samePasswordByNewClient(EntityLogin entityLogin) throws RemoteException {
        LoginDao loginDao = new LoginDaoImplementation(dbConnection);
        loginDao.samePasswordByNewClient(entityLogin,clientSession.get(entityLogin.getPhno()));
    }


}
