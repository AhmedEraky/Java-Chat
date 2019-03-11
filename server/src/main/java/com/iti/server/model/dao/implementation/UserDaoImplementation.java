package com.iti.server.model.dao.implementation;


import com.iti.ChatCommanServices.model.entity.user.User;
import com.iti.ChatCommanServices.model.exception.RegistrationDuplicateException;
import com.iti.server.model.dal.cfg.DBConnection;
import com.iti.server.model.dao.UserDao;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoImplementation implements UserDao {
    DBConnection dbConnection;

    public UserDaoImplementation(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public void persistent(User user, Session session) throws RegistrationDuplicateException {

    }

    @Override
    public void delete(User user, Session session) {

    }

    @Override
    public void update(User user, Session session) {

    }

    @Override
    public User reterive(String userPhoneNumber, Session session) {
        Criteria criteria=session.createCriteria(User.class).add(Restrictions.eq("userPhoneNumber",userPhoneNumber));
        User user= (User) criteria.uniqueResult();
        return user;
    }

    @Override
    public boolean checkUserExist(String userPhoneNumber, Session session) {
        return false;
    }

    @Override
    public ArrayList<User> reteriveOnlineUsers() {
        return null;
    }

    @Override
    public int[] getNumbersOfFemalesAndMales(Session session) {
        return new int[0];
    }

    @Override
    public ResultSet getUsersByCountry(Session session) {
        return null;
    }

    @Override
    public ResultSet getAllOnlineAndOfflineUsers(Session session) {
        return null;
    }

    @Override
    public void updateStatus(String newStatus, String user, Session session) {
        Transaction transaction=session.beginTransaction();
        String query="update User u set u.status = :newStatus where u.userPhoneNumber = :id";
        session.createQuery(query).setParameter("newStatus",newStatus)
                .setParameter("id",user).executeUpdate();
        transaction.commit();
    }

   /* @Override
    public void persistent(User user) throws RegistrationDuplicateException {


        String query = " INSERT INTO user (phone_number, first_name, lastName, email, password,status, country, gender, date_birth, bio , image) VALUES (?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?)";
        try {
            PreparedStatement statement= dbConnection.getConnection().prepareStatement(query);
            statement.setString(1,user.getPhno());
            statement.setString(2,user.getFirstName());
            statement.setString(3,user.getLastName());
            statement.setString(4,user.getEmail());
            statement.setString(5,user.getPassword());
            statement.setString(6,user.getStatus());
            statement.setString(7,user.getCountry());
            statement.setString(8,user.getGender());
            statement.setDate(9,user.getDatBirth());
            statement.setString(10,user.getBio());
            //statement.setBinaryStream(2, (InputStream) inputStream, (int)(image.length()));
            statement.setObject(11, user.getPhoto());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            throw new RegistrationDuplicateException("Duplicate email Or Password");
        }

    }

    @Override
    public void delete(User user) {

    }

    @Override
    public void update(User user) {
        String updateQuery = "update user set first_name = ? , lastName = ? , "
                + "email = ? , password = ? , status=? , country=? , gender=?, "
                + "date_birth=?, bio=? , image =? "
                + "where phone_number = ?";
        PreparedStatement updateStatement=null;
        try {
            updateStatement = dbConnection.getConnection().prepareStatement(updateQuery);
            System.out.println(user.getPhno());
            updateStatement.setString(1,user.getFirstName());
            updateStatement.setString(2,user.getLastName());
            updateStatement.setString(3,user.getEmail());
            updateStatement.setString(4,user.getPassword());
            updateStatement.setString(5,user.getStatus());
            updateStatement.setString(6,user.getCountry());
            updateStatement.setString(7,user.getGender());
            updateStatement.setDate(8,user.getDatBirth());
            updateStatement.setString(9,user.getBio());
            updateStatement.setBytes(10, user.getPhoto());
            updateStatement.setString(11,user.getPhno());
            updateStatement.executeUpdate();
            updateStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(UserDaoImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            if(updateStatement!=null) {
                try {
                    updateStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public User reterive(String userPhoneNumber) {
        String query = "SELECT * FROM user where phone_number=?";
        User user=null;
        try {
            PreparedStatement preparedStatement=dbConnection.getConnection().prepareStatement(query);
            preparedStatement.setString(1, userPhoneNumber);
            ResultSet resultSet=preparedStatement.executeQuery();
            resultSet.first();
            user=new User();
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
            user.setPhoto(resultSet.getBytes("image"));
            preparedStatement.close();// closing
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return user;

    }

    @Override
    public boolean checkUserExist(String userPhoneNumber) {

        String query = "SELECT * FROM user where phone_number=?";
        boolean empty=true;
        try {
            PreparedStatement preparedStatement=dbConnection.getConnection().prepareStatement(query);
            preparedStatement.setString(1, userPhoneNumber);
            ResultSet resultSet=preparedStatement.executeQuery();
            // to get resultsetcount
            resultSet.beforeFirst();
            if(!(resultSet.next())){
                empty=false;
            }
            preparedStatement.close();// closing
        } catch (SQLException ex) {
            Logger.getLogger(UserDaoImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }

        return empty;
    }

    @Override
    public ArrayList<User> reteriveOnlineUsers() {
        String getOnlineUsersQuery="select * from user where status='onlineAvailable'";
        ArrayList<User> onlineUsers= new ArrayList<User>();
        User onlineUser= new User();
        try
        {
            PreparedStatement preparedStatment= dbConnection.getConnection().prepareStatement(getOnlineUsersQuery);
            ResultSet onlineUserRowSet= preparedStatment.executeQuery();
            while(onlineUserRowSet.next())
            {
                onlineUser.setPhno(onlineUserRowSet.getString(1));
                onlineUser.setFirstName(onlineUserRowSet.getString(2));
                onlineUser.setLastName( onlineUserRowSet.getString(3));
                onlineUser.setEmail(onlineUserRowSet.getString(4));
                onlineUser.setPhoto(onlineUserRowSet.getBytes(5));
                onlineUser.setPassword(onlineUserRowSet.getString(6));
                onlineUser.setStatus(onlineUserRowSet.getString(7));
                onlineUser.setCountry(onlineUserRowSet.getString(8));
                onlineUser.setGender(onlineUserRowSet.getString(9));
                onlineUser.setDatBirth(onlineUserRowSet.getDate(10));
                onlineUser.setBio(onlineUserRowSet.getString(11));
                onlineUsers.add(onlineUser);

            }
            preparedStatment.close();
            onlineUserRowSet.close();

        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return onlineUsers;

    }


    *//**
     * Bishoy
     * this method to retrieve an array of integers
     * first element is the number of females
     * second element is the number of males
     *//*

    @Override
    public int[] getNumbersOfFemalesAndMales() {
        ResultSet resultSet;
        int numberOfFemales=0;
        int numberOfMales=0;
        int count[] = new int [2];
        try {
            String query = "select count(phone_number) , gender from user group by gender order by gender";
            Statement statement= dbConnection.getConnection().createStatement();
            resultSet = statement.executeQuery(query);
            if(resultSet.next())
                numberOfFemales = resultSet.getInt(1);
            if(resultSet.next())
                numberOfMales = resultSet.getInt(1);
        } catch (SQLException ex) {
            Logger.getLogger(UserDaoImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        count[0] = numberOfFemales;
        count[1] = numberOfMales;
        return count;
    }

    *//**
     * Bishoy
     * this method to retrieve a resultset of all countries
     * and the number of users in this country
     *//*
    @Override
    public ResultSet getUsersByCountry() {

        ResultSet resultSet = null;
        String query = "select count(phone_number) , country from user group by country";
        Statement statement;
        try {
            statement = dbConnection.getConnection().createStatement();
            resultSet = statement.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(UserDaoImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultSet;
    }


    *//**
     * Bishoy
     * this method to retrieve a resultset of all users
     * grouped and ordered by their current status
     *//*
    @Override
    public ResultSet getAllOnlineAndOfflineUsers() {
        ResultSet resultSet = null;
        String query = "select count(phone_number) , status from user group by status order by status";
        Statement statement;
        try {
            statement = dbConnection.getConnection().createStatement();
            resultSet = statement.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(UserDaoImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultSet;
    }

    @Override
    public void updateStatus(String newStatus,String user) {
        String query="UPDATE user SET status = '"+newStatus+"' WHERE (phone_number = '"+user+"')";
        try {
            Statement statement=dbConnection.getConnection().createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/
}