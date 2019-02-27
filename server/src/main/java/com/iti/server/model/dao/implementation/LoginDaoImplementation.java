/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iti.server.model.dao.implementation;

import com.iti.ChatCommanServices.model.ClientInterfaces.ClientServiceInterface;
import com.iti.ChatCommanServices.model.entity.user.EntityLogin;
import com.iti.ChatCommanServices.model.entity.user.User;
import com.iti.ChatCommanServices.model.exception.LoginFaieldException;
import com.iti.server.model.dal.cfg.DBConnection;
import com.iti.server.model.dao.LoginDao;

import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pc
 */
public class LoginDaoImplementation implements LoginDao {

    DBConnection dbConnection;

    public LoginDaoImplementation(DBConnection dBConnection) {
        this.dbConnection = dBConnection;
    }


    @Override
    public void reterive(EntityLogin entityLogin, Map<String, ClientServiceInterface> clients) throws LoginFaieldException {
        //excute code here or call function do task

        //User user = new User();

        String query = "select * from user where phone_number = '" + entityLogin.getPhno() + "' AND password = '" + entityLogin.getPassword() + "'";
        try {
            Statement statement = dbConnection.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                if (resultSet.getInt("admin_flag") == 1) {
                    try {
                        clients.get(entityLogin.getPhno()).changePasswordByNewClient();
                    } catch (RemoteException ex) {
                        Logger.getLogger(LoginDaoImplementation.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                System.out.println("The Name Is " + resultSet.getString(2));
            } else {
                System.out.println("Password or Phone incorrect");
                throw new LoginFaieldException("login failed");
            }
            statement.close();
        } catch (SQLException e) {

        }
    }


    @Override
    public void changePasswordByNewClient(EntityLogin entityLogin, String newPassword) {
        try {
            System.out.println(newPassword);
            String query = "update user set password = '"+newPassword+"' , admin_flag = 0 where phone_number = '"+entityLogin.getPhno()+"'";
            Statement statement = dbConnection.getConnection().createStatement();
            statement.executeUpdate(query);
            System.out.println("user flag update");
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(LoginDaoImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void samePasswordByNewClient(EntityLogin entityLogin) {
        try {
            String query = "update user set admin_flag = 0 where phone_number = '"+entityLogin.getPhno()+"'";
            Statement statement = dbConnection.getConnection().createStatement();
            statement.executeUpdate(query);
            System.out.println("user flag update");
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(LoginDaoImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
