/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.iti.server.model.serverInterfaceImplementation;

import com.iti.server.model.dal.cfg.DBConnection;
import com.iti.server.model.dao.UserDao;
import com.iti.server.model.dao.implementation.UserDaoImplementation;
import com.iti.server.model.serverInterface.ServerInterface;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;

/**
 *
 * @author Esraa
 */
public class SereverImpelementation  implements ServerInterface {
    DBConnection dbConnection;
    
    public SereverImpelementation(DBConnection dbConnection) {
        this.dbConnection=dbConnection;
    }
    
    
    @Override
    public int[] getNumbersOfFemalesAndMales()  {
        UserDao userDao = new UserDaoImplementation(dbConnection);
        return userDao.getNumbersOfFemalesAndMales();
    }
    
    @Override
    public ResultSet getUsersByCountry() {
        UserDao userDao = new UserDaoImplementation(dbConnection);
        return userDao.getUsersByCountry();
    }
    
    @Override
    public ResultSet getAllOnlineAndOfflineUsers() {
        UserDao userDao = new UserDaoImplementation(dbConnection);
        return userDao.getAllOnlineAndOfflineUsers();
    }
    
}
