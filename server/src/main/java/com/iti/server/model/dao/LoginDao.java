package com.iti.server.model.dao;

import com.iti.ChatCommanServices.model.ClientInterfaces.ClientServiceInterface;
import com.iti.ChatCommanServices.model.entity.user.EntityLogin;
import com.iti.ChatCommanServices.model.entity.user.User;
import com.iti.ChatCommanServices.model.exception.LoginFaieldException;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.Map;

public interface LoginDao {
    public void reterive(EntityLogin entityLogin, Map<String, ClientServiceInterface> clients, Session session) throws LoginFaieldException;
    public void changePasswordByNewClient(EntityLogin entityLogin, String newPassword, Session session);
    public void samePasswordByNewClient(EntityLogin entityLogin, Session session);

}
