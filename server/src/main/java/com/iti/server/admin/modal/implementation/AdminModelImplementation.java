/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iti.server.admin.modal.implementation;

import com.iti.ChatCommanServices.model.entity.user.User;
import com.iti.ChatCommanServices.model.exception.RegistrationDuplicateException;
import com.iti.server.admin.modal.AdminModel;
import com.iti.server.model.dal.cfg.DBConnection;
import com.iti.server.model.dao.implementation.UserDaoImplementation;
import org.hibernate.Session;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pc
 */
public class AdminModelImplementation implements AdminModel {

    DBConnection dBConnection = new DBConnection();
    public static Session session;

    public AdminModelImplementation() {
        dBConnection.ConnectToDB();
        session=dBConnection.getConnection().openSession();
    }

    @Override
    public void addClientByAdmin(User user) throws RegistrationDuplicateException {

    }

    @Override
    public ArrayList<User> displayUsers() {
        return null;
    }

    @Override
    public void updateUser(User user) throws RegistrationDuplicateException {

    }

    @Override
    public void deleteUser(String phoneNumber) {

    }

   /* @Override
    public void addClientByAdmin(User user) throws RegistrationDuplicateException {

        String query = " INSERT INTO user (phone_number, first_name, lastName, email,image, password,status, country, gender, date_birth, bio, admin_flag) VALUES (?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?,?)";
        try {
            PreparedStatement statement = dBConnection.getConnection().prepareStatement(query);
            statement.setString(1, user.getPhno());
            statement.setString(2, user.getFirstName());
            statement.setString(3, user.getLastName());
            statement.setString(4, user.getEmail());
            statement.setBytes(5, user.getPhoto());
            statement.setString(6, user.getPassword());
            statement.setString(7, user.getStatus());
            statement.setString(8, user.getCountry());
            statement.setString(9, user.getGender());
            statement.setDate(10, user.getDatBirth());
            statement.setString(11, user.getBio());
            statement.setInt(12, 1);
            statement.executeUpdate();
            System.out.println("user added");
            statement.close();
        } catch (SQLException e) {
            throw new RegistrationDuplicateException("Duplicate email Or Password");
        }

    }

    @Override
    public ArrayList<User> displayUsers() {
        String query = "SELECT * FROM user";
        ArrayList<User> userList = new ArrayList<>();
        User user;
        try {
            PreparedStatement preparedStatement = dBConnection.getConnection().prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = new User();
                user.setPhno(resultSet.getString("phone_number"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setStatus(resultSet.getString("status"));
                user.setCountry(resultSet.getString("country"));
                user.setGender(resultSet.getString("gender"));
                user.setDatBirth(resultSet.getDate("date_birth"));
                user.setBio(resultSet.getString("bio"));
                //user.setPhoto(resultSet.getBytes("image"));
                userList.add(user);
            }
            preparedStatement.close();// closing
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        for (int i = 0; i < userList.size(); i++) {
            System.out.println(userList.get(i).getFirstName());
        }
        return userList;

    }

    @Override
    public void updateUser(User user) throws RegistrationDuplicateException {
        String updateQuery = "update user set first_name = ? , lastName = ? , "
                + "email = ? , password = ? , status=? , country=? , gender=?, "
                + "date_birth=?, bio=? , image =? "
                + "where phone_number = ?";
        PreparedStatement updateStatement = null;
        try {
            updateStatement = dBConnection.getConnection().prepareStatement(updateQuery);
            System.out.println(user.getPhno());
            updateStatement.setString(1, user.getFirstName());
            updateStatement.setString(2, user.getLastName());
            updateStatement.setString(3, user.getEmail());
            updateStatement.setString(4, user.getPassword());
            updateStatement.setString(5, user.getStatus());
            updateStatement.setString(6, user.getCountry());
            updateStatement.setString(7, user.getGender());
            updateStatement.setDate(8, user.getDatBirth());
            updateStatement.setString(9, user.getBio());
            updateStatement.setBytes(10, user.getPhoto());
            updateStatement.setString(11, user.getPhno());
            updateStatement.executeUpdate();
            System.out.println("hhhh");
            //updateStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(UserDaoImplementation.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (updateStatement != null) {
                try {
                    updateStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void deleteUser(String phoneNumber) {
        String updateQuery = "delete from user where phone_number = ?";
        PreparedStatement updateStatement = null;
        try {
            updateStatement = dBConnection.getConnection().prepareStatement(updateQuery);
            updateStatement.setString(1, phoneNumber);
            updateStatement.executeUpdate();
            System.out.println("delete done");
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
