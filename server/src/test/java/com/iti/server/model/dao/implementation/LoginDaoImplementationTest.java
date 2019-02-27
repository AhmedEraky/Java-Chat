/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iti.server.model.dao.implementation;

import com.iti.ChatCommanServices.model.entity.user.EntityLogin;
import com.iti.server.model.dal.cfg.DBConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author pc
 */
public class LoginDaoImplementationTest {

    DBConnection dbConnection;
    LoginDaoImplementation loginDaoImplementation;

    @Before
    public void setUp() throws ExceptionInInitializerError {
        dbConnection = new DBConnection();
        dbConnection.ConnectToDB();
        loginDaoImplementation = new LoginDaoImplementation(dbConnection);
    }

    @Test
    public void reterive() {

        EntityLogin entityLogin = new EntityLogin();
        entityLogin.setPhno("01223586041");
        entityLogin.setPassword("111111");
        String query = "select * from user where phone_number = '" + entityLogin.getPhno() + "' AND password = '" + entityLogin.getPassword() + "'";
        try {
            Statement statement = dbConnection.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                assertEquals("01223586041", resultSet.getString(1));
            } else {
                System.out.println("Password or Phone incorrect");
            }
            statement.close();
        } catch (SQLException e) {

        }
    }
}
