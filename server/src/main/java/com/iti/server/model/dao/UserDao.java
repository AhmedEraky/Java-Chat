package com.iti.server.model.dao;

import com.iti.ChatCommanServices.model.entity.user.User;
import com.iti.ChatCommanServices.model.exception.RegistrationDuplicateException;
import org.hibernate.Session;

import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.util.ArrayList;

public interface UserDao {
    public void persistent(User user, Session session) throws RegistrationDuplicateException;
    public void delete(User user, Session session);
    public void update(User user, Session session);
    public User reterive(String userPhoneNumber, Session session);
    public boolean checkUserExist(String userPhoneNumber, Session session);
    public ArrayList<User> reteriveOnlineUsers();
    public int[] getNumbersOfFemalesAndMales( Session session);
    public ResultSet getUsersByCountry( Session session);
    public ResultSet getAllOnlineAndOfflineUsers( Session session);
    public void updateStatus(String newStatus,String user, Session session);


}
