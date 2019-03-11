package com.iti.server.model.dao;

import com.iti.ChatCommanServices.model.entity.user.User;
import com.iti.ChatCommanServices.model.entity.user.UserContact;
import org.hibernate.Session;

import java.util.ArrayList;

public interface UserContactDao {

    public void persistent(UserContact userContact, Session session);
    public void delete(UserContact userContact, Session session);
    public void update(UserContact userContact, Session session);
    public ArrayList<User> reterive(String userPhoneNumber, Session session);
    public ArrayList<User> reteriveOnlineFriends(String phno, Session session);
}
