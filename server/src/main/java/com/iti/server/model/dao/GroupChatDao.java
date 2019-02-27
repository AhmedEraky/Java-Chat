package com.iti.server.model.dao;

import com.iti.ChatCommanServices.model.entity.message.EntityMessage;

import java.sql.Connection;
import java.util.ArrayList;

public interface GroupChatDao {

    public void persistent(EntityMessage message);
    public ArrayList<EntityMessage> reterive(String chatID, Connection connection);

}
