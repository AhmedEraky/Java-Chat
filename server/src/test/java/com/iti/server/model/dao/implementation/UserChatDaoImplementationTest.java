/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iti.server.model.dao.implementation;

/**
 *
 * @author pc
 */
import com.iti.ChatCommanServices.model.entity.user.User;
import com.iti.ChatCommanServices.model.exception.RegistrationDuplicateException;
import com.iti.server.model.dal.cfg.DBConnection;
import java.sql.Date;
import java.sql.PreparedStatement;
import org.junit.Before;
import org.junit.Test;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class UserChatDaoImplementationTest {

    DBConnection dbConnection;
    UserDaoImplementation userDaoImplementation;

    @Before
    public void setUp() throws Exception {
        dbConnection = new DBConnection();
        dbConnection.ConnectToDB();
        userDaoImplementation = new UserDaoImplementation(dbConnection);

    }

    @Test
    public void persistent() throws RegistrationDuplicateException {
        String query = " INSERT INTO user (phone_number, first_name, lastName, email, password,status, country, gender, date_birth, bio, image) VALUES (?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?)";
        User user = new User();
        int rowcount;
        user.setPhno("01055456328");
        user.setFirstName("ahmed");
        user.setLastName("mohamed");
        user.setEmail("ahmed182@dgmail.com");
        user.setPassword("12589634");
        user.setStatus("offline");
        user.setBio("good");
        user.setCountry("Egypt");
        user.setDatBirth(new Date(1999, 5, 5));
        user.setGender("male");
        user.setPhoto(null);
        try {
            PreparedStatement statement = dbConnection.getConnection().prepareStatement(query);
            statement.setString(1, user.getPhno());
            statement.setString(2, user.getFirstName());
            statement.setString(3, user.getLastName());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getPassword());
            statement.setString(6, user.getStatus());
            statement.setString(7, user.getCountry());
            statement.setString(8, user.getGender());
            statement.setDate(9, user.getDatBirth());
            statement.setString(10, user.getBio());
            statement.setBytes(11, user.getPhoto());
            //rowcount = statement.executeUpdate();
            //assertEquals(1, rowcount);
            statement.close();
        } catch (SQLException e) {
            throw new RegistrationDuplicateException("Duplicate email Or Password");
        }
    }
}
