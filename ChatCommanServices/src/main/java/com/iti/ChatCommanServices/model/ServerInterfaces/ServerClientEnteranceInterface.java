/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iti.ChatCommanServices.model.ServerInterfaces;

import com.iti.ChatCommanServices.model.ClientInterfaces.ClientServiceInterface;
import com.iti.ChatCommanServices.model.entity.user.EntityLogin;
import com.iti.ChatCommanServices.model.entity.user.User;
import com.iti.ChatCommanServices.model.exception.LoginFaieldException;
import com.iti.ChatCommanServices.model.exception.RegistrationDuplicateException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;


public interface ServerClientEnteranceInterface extends Remote {
    ArrayList<User> USERS=null;
     /**
     * Check DataBase to validate is Registration Success or not
     */

     public void registerClient(String clientID, ClientServiceInterface client) throws RemoteException;

    public void registration(User user) throws RemoteException, RegistrationDuplicateException;

    /**
     * Cherk DataBase to Validate is Login Success or not
     * @param entityLogin
     */
    public void login(EntityLogin entityLogin) throws RemoteException, LoginFaieldException;

    public void changePasswordByNewClient(EntityLogin entityLogin, String newPassword) throws RemoteException;

    public void samePasswordByNewClient(EntityLogin entityLogin) throws RemoteException;

}
