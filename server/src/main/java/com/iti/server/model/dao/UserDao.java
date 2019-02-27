package com.iti.server.model.dao;

import com.iti.ChatCommanServices.model.entity.user.User;
import com.iti.ChatCommanServices.model.exception.RegistrationDuplicateException;

import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.util.ArrayList;

public interface UserDao {
    public void persistent(User user) throws RegistrationDuplicateException;
    public void delete(User user);
    public void update(User user);
    public User reterive(String userPhoneNumber);
    public boolean checkUserExist(String userPhoneNumber);
    public ArrayList<User> reteriveOnlineUsers();
    public int[] getNumbersOfFemalesAndMales();
    public ResultSet getUsersByCountry();
    public ResultSet getAllOnlineAndOfflineUsers();
    public void updateStatus(String newStatus,String user);


}
