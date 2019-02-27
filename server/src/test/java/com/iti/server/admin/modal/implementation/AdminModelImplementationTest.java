
package com.iti.server.admin.modal.implementation;

import com.iti.ChatCommanServices.model.entity.user.User;
import com.iti.ChatCommanServices.model.exception.RegistrationDuplicateException;
import com.iti.server.model.dal.cfg.DBConnection;
import java.sql.Date;
import java.sql.PreparedStatement;
import org.junit.Before;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.*;

public class AdminModelImplementationTest {

    DBConnection dbConnection;
    AdminModelImplementation adminModelImplementation;

    @Before
    public void setUp() throws ExceptionInInitializerError {
        dbConnection = new DBConnection();
        dbConnection.ConnectToDB();
        adminModelImplementation = new AdminModelImplementation();
        dbConnection = new DBConnection();
        dbConnection.ConnectToDB();
    }

//    @Test
//    public void addClientByAdmin() throws RegistrationDuplicateException {
//        User user = new User();
//        user.setPhno("01214725836");
//        user.setFirstName("ahmed");
//        user.setLastName("mohamed");
//        user.setEmail("admh@gmail.com");
//        user.setPassword("123456789");
//        user.setStatus("offline");
//        user.setCountry("egypt");
//        user.setGender("male");
//        user.setDatBirth(new Date(1999, 5, 5));
//        user.setBio("good");
//        String query = " INSERT INTO user (phone_number, first_name, lastName, email, password,status, country, gender, date_birth, bio, admin_flag) VALUES (?, ?, ?, ?, ?, ?,?, ?, ?, ?,?)";
//        try {
//            PreparedStatement statement = dbConnection.getConnection().prepareStatement(query);
//            statement.setString(1, user.getPhno());
//            statement.setString(2, user.getFirstName());
//            statement.setString(3, user.getLastName());
//            statement.setString(4, user.getEmail());
//            statement.setString(5, user.getPassword());
//            statement.setString(6, user.getStatus());
//            statement.setString(7, user.getCountry());
//            statement.setString(8, user.getGender());
//            statement.setDate(9, user.getDatBirth());
//            statement.setString(10, user.getBio());
//            statement.setInt(11, 1);
//            int rowCount = statement.executeUpdate();
//            assertEquals(1, rowCount);
//            statement.close();
//        } catch (SQLException e) {
//            throw new RegistrationDuplicateException("Duplicate email Or Password");
//        }
//    }

   /* @Test
    public void deleteUser() {
        String phoneNumber = "01114725836";
        String updateQuery = "delete from user where phone_number = ?";
        PreparedStatement updateStatement = null;
        try {
            updateStatement = dbConnection.getConnection().prepareStatement(updateQuery);
            updateStatement.setString(1, phoneNumber);
            int rowCount = updateStatement.executeUpdate();
            assertEquals(1,rowCount);

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (updateStatement != null) {
                try {
                    updateStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }*/
}
