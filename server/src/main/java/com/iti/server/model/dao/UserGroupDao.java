package com.iti.server.model.dao;

import com.iti.ChatCommanServices.model.entity.user.User;
import com.iti.ChatCommanServices.model.exception.DublicateEntryException;
import com.iti.ChatCommanServices.model.exception.UserNotFoundException;

import java.util.ArrayList;

public interface UserGroupDao {
    public void persistent( ArrayList<User> users);
    public ArrayList<String> reteriveGroups(User user);
    public ArrayList<User> reteriveGroupMember(User user,String groupID);
    public void removeUser(User user,String groupID);
    public void addUser(User user,String groupID) throws DublicateEntryException, UserNotFoundException;
}
