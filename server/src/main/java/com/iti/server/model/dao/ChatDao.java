package com.iti.server.model.dao;

import com.iti.ChatCommanServices.model.entity.chat.Chat;
import com.iti.ChatCommanServices.model.entity.message.EntityMessage;
import com.iti.ChatCommanServices.model.entity.user.User;
import org.hibernate.Session;

import java.util.ArrayList;

public interface ChatDao {
    public void persistent(EntityMessage message,Session session);
    public ArrayList<EntityMessage> reterive(String chatID, Session session);

    
}
