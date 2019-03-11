package com.iti.server.model.dao.implementation;


import com.iti.ChatCommanServices.model.entity.user.User;
import com.iti.ChatCommanServices.model.entity.user.UserContact;

import com.iti.server.model.dal.cfg.DBConnection;
import com.iti.server.model.dao.UserContactDao;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserContactDaoImplementation implements UserContactDao {
     DBConnection dbConnection;

    public UserContactDaoImplementation(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public void persistent(UserContact userContact, Session session) {

    }

    @Override
    public void delete(UserContact userContact, Session session) {

    }

    @Override
    public void update(UserContact userContact, Session session) {

    }

    @Override
    public ArrayList<User> reterive(String userPhoneNumber, Session session) {
        Criteria criteria=session.createCriteria(User.class).add(Restrictions.eq("userPhoneNumber",userPhoneNumber));
        User user= (User) criteria.uniqueResult();
        ArrayList<User> users=new ArrayList();
        for(UserContact userContact:user.getUserContacts()){
            Criteria criteria1=session.createCriteria(User.class).add(Restrictions.eq("userPhoneNumber",userContact.getId().getContact()));
            users.add((User) criteria1.uniqueResult());
        }
        return users;
    }

    @Override
    public ArrayList<User> reteriveOnlineFriends(String phno, Session session) {
        return null;
    }
   /* @Override
    public void persistent(UserContact userContact) {
         String query = " INSERT INTO user_contact (phone_number,contact) VALUES (?, ?)";
       
         try {
             PreparedStatement statement= dbConnection.getConnection().prepareStatement(query);
             statement.setString(1, userContact.getPhno());
             statement.setString(2, userContact.getContactPhno());
             statement.execute();
             statement.close();
         } catch (SQLException ex) {
             Logger.getLogger(UserContactDaoImplementation.class.getName()).log(Level.SEVERE, null, ex);
         }
           
    }

    @Override
    public void delete(UserContact userContact) {

    }

    @Override
    public void update(UserContact userContact) {

    }

    @Override
    public ArrayList<User> reterive(String phoneNumber) {

        String query = "select contact from user_contact where phone_number = '" + phoneNumber + "' ";
        ArrayList<User> userContacts = new ArrayList<>();
        try {
            Statement statement = dbConnection.getConnection().createStatement();
            ResultSet userFriendsResultSet = statement.executeQuery(query);
            User user = null;
            while (userFriendsResultSet.next()) {
                String friendPhoneNumber = userFriendsResultSet.getString(1);
                String friendsQuery = "select * from user where phone_number ='" + friendPhoneNumber + "'";
                Statement friendStatement = dbConnection.getConnection().createStatement();
                ResultSet userResultSet = friendStatement.executeQuery(friendsQuery);
                if (userResultSet.next()) {
                    user = new User();
                    user.setPhno(userResultSet.getString(1));
                    user.setFirstName(userResultSet.getString(2));
                    user.setLastName(userResultSet.getString(3));
                    user.setEmail(userResultSet.getString(4));
                    user.setPhoto(userResultSet.getBytes(5));
                    user.setPassword(userResultSet.getString(6));
                    user.setStatus(userResultSet.getString(7));
                    user.setCountry(userResultSet.getString(8));
                    user.setGender(userResultSet.getString(9));
                    user.setDatBirth(userResultSet.getDate(10));
                    user.setBio(userResultSet.getString(11));
                    userContacts.add(user);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return userContacts;
    }

    @Override
    public ArrayList<User> reteriveOnlineFriends(String phoneNumber) {
        String query = "select contact from user_contact where phone_number = '" + phoneNumber + "' ";
        ArrayList<User> userContacts = new ArrayList<>();
        try {
            Statement statement = dbConnection.getConnection().createStatement();
            ResultSet userFriendsResultSet = statement.executeQuery(query);
            User user = null;
            while (userFriendsResultSet.next()) {
                String friendPhoneNumber = userFriendsResultSet.getString(1);
                String friendsQuery = "select * from user where phone_number ='" + friendPhoneNumber + "' and status= 'onlineAvailable'";
                Statement friendStatement = dbConnection.getConnection().createStatement();
                ResultSet userResultSet = friendStatement.executeQuery(friendsQuery);
                if (userResultSet.next()) {
                    user = new User();
                    user.setPhno(userResultSet.getString(1));
                    user.setFirstName(userResultSet.getString(2));
                    user.setLastName(userResultSet.getString(3));
                    user.setEmail(userResultSet.getString(4));
                    user.setPhoto(userResultSet.getBytes(5));
                    user.setPassword(userResultSet.getString(6));
                    user.setStatus(userResultSet.getString(7));
                    user.setCountry(userResultSet.getString(8));
                    user.setGender(userResultSet.getString(9));
                    user.setDatBirth(userResultSet.getDate(10));
                    user.setBio(userResultSet.getString(11));
                    userContacts.add(user);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return userContacts;
    }*/
}


