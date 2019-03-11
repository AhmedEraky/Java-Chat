package com.iti.server.model.dao;

import com.iti.ChatCommanServices.model.entity.message.MessageSettings;
import org.hibernate.Session;

public interface MessageSetingDao {

    public String compareStyle(MessageSettings settings, Session session);
}
