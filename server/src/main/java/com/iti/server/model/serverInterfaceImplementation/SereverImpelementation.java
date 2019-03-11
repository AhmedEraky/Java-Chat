/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.iti.server.model.serverInterfaceImplementation;

import com.iti.server.model.dal.cfg.DBConnection;
import com.iti.server.model.dao.UserDao;
import com.iti.server.model.dao.implementation.UserDaoImplementation;
import com.iti.server.model.statistacs.ServerInterface;
import org.hibernate.Session;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.util.HashMap;

/**
 *
 * @author Esraa
 */
public class SereverImpelementation  implements ServerInterface {
    DBConnection dbConnection;
    public static Session session;



    public SereverImpelementation(DBConnection dbConnection) {
        this.dbConnection=dbConnection;
        session=dbConnection.getConnection().openSession();
    }
    
    
    @Override
    public int[] getNumbersOfFemalesAndMales()  {
        UserDao userDao = new UserDaoImplementation(dbConnection);
        return userDao.getNumbersOfFemalesAndMales(session);
    }
    
    @Override
    public ResultSet getUsersByCountry() {
        UserDao userDao = new UserDaoImplementation(dbConnection);
        return userDao.getUsersByCountry(session);
    }
    
    @Override
    public ResultSet getAllOnlineAndOfflineUsers() {
        UserDao userDao = new UserDaoImplementation(dbConnection);
        return userDao.getAllOnlineAndOfflineUsers(session);
    }
    
}
