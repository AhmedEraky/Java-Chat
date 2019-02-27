package com.iti.server.model.dao;

import com.iti.ChatCommanServices.model.entity.user.User;
import com.iti.ChatCommanServices.model.entity.user.UserContact;

import java.util.ArrayList;

public interface UserContactDao {

    public void persistent(UserContact userContact);
    public void delete(UserContact userContact);
    public void update(UserContact userContact);
    public ArrayList<User> reterive(String userPhoneNumber);
    public ArrayList<User> reteriveOnlineFriends(String phno);
}
