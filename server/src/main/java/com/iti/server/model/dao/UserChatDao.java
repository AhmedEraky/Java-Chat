package com.iti.server.model.dao;

import com.iti.ChatCommanServices.model.entity.user.User;
import com.iti.server.model.dal.cfg.DBConnection;
import org.hibernate.Session;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Set;

public interface UserChatDao {
    public String retrieveChatID(User firstUser,User secondUser, Session session);

    public ArrayList<String> retrieveChatsID(User user, Session session);
}
