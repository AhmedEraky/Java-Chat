package com.iti.server.model.dao;

import com.iti.ChatCommanServices.model.entity.user.User;
import com.iti.server.model.dal.cfg.DBConnection;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Set;

public interface UserChatDao {
    public String retrieveChatID(User firstUser,User secondUser, Connection connection);

    public ArrayList<String> retrieveChatsID(User user,Connection connection);
}
