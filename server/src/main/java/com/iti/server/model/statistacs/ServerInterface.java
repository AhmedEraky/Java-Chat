/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.iti.server.model.statistacs;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.ResultSet;

/**
 *
 * @author Esraa
 */
public interface ServerInterface {
    public int[] getNumbersOfFemalesAndMales();
    public ResultSet getUsersByCountry();
    public ResultSet getAllOnlineAndOfflineUsers();
}
