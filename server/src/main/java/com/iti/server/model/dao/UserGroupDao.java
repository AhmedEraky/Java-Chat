package com.iti.server.model.dao;

import com.iti.ChatCommanServices.model.entity.user.User;
import com.iti.ChatCommanServices.model.exception.DublicateEntryException;
import com.iti.ChatCommanServices.model.exception.UserNotFoundException;
import org.hibernate.Session;

import java.util.ArrayList;

public interface UserGroupDao {
    public void persistent( ArrayList<User> users, Session session);
    public ArrayList<String> reteriveGroups(User user, Session session);
    public ArrayList<User> reteriveGroupMember(User user,String groupID, Session session);
    public void removeUser(User user,String groupID, Session session);
    public void addUser(User user,String groupID, Session session) throws DublicateEntryException, UserNotFoundException;
}
