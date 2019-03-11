package com.iti.server.model.dao.implementation;


import com.iti.ChatCommanServices.model.entity.user.User;
import com.iti.ChatCommanServices.model.entity.user.UserContact;
import com.iti.ChatCommanServices.model.entity.user.UserInvitation;
import com.iti.server.model.dal.cfg.DBConnection;
import com.iti.server.model.dao.UserInvitationDao;
import org.hibernate.Session;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserInvitationDaoImplementation implements UserInvitationDao {

    DBConnection dbConnection;
    int userInvtationsCount;

    public UserInvitationDaoImplementation(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public void persistent(UserInvitation userInvitation, Session session) {

    }

    @Override
    public void deleteFromSender(UserInvitation userInvitation, Session session) {

    }

    @Override
    public void deleteFromReceiver(UserInvitation userInvitation, Session session) {

    }

    @Override
    public int getInvtationCount(String userPhone, Session session) {
        return 0;
    }

    @Override
    public void update(UserInvitation userInvitation, Session session) {

    }

    @Override
    public ArrayList<User> reterive(String userPhone, Session session) {
        return null;
    }

    @Override
    public boolean CheckInvitationExist(UserContact userContact, Session session) {
        return false;
    }

    @Override
    public void updatSeenFriends(String phoneNumber, Session session) {

    }

    @Override
    public void updateIgnoreFlag(UserInvitation userInvitation, Session session) {

    }

    @Override
    public ArrayList<User> reteriveInvitationUsingSenderPhone(String userPhoneNumber, Session session) {
        return null;
    }
  /*  @Override
    public void persistent(UserInvitation userInvitation) {

        String persistentQuery="insert into user_invitation values(?,?,?);";
        PreparedStatement preparedInsertStatement;
        try
        {
            preparedInsertStatement = dbConnection.getConnection().prepareStatement(persistentQuery);
            preparedInsertStatement.setString(1, userInvitation.getSenderPhno());
            preparedInsertStatement.setString(2, userInvitation.getReceiverPhno());
            preparedInsertStatement.setBoolean(3,false);
            preparedInsertStatement.execute();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void deleteFromSender(UserInvitation userInvitation) {
        String senderPhoneNumber=userInvitation.getSenderPhno();
        String receiverPhoneNumber=userInvitation.getReceiverPhno();
        String query = "DELETE FROM user_invitation where sender_phone_number=? and receiver_phone_number=?";
        try {
            PreparedStatement PreparedStatement = dbConnection.getConnection().prepareStatement(query);
            PreparedStatement.setString(1, senderPhoneNumber);
            PreparedStatement.setString(2, receiverPhoneNumber);
            PreparedStatement.executeUpdate();
            PreparedStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(UserInvitationDaoImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @Override
    public void deleteFromReceiver(UserInvitation userInvitation) {
        String senderPhoneNumber=userInvitation.getSenderPhno();
        String receiverPhoneNumber=userInvitation.getReceiverPhno();
        String query = "DELETE FROM user_invitation where sender_phone_number=? and receiver_phone_number=?";
        try {
            PreparedStatement PreparedStatement = dbConnection.getConnection().prepareStatement(query);
            PreparedStatement.setString(2, senderPhoneNumber);
            PreparedStatement.setString(1, receiverPhoneNumber);
            PreparedStatement.executeUpdate();
            PreparedStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(UserInvitationDaoImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(UserInvitation userInvitation) {

    }

    @Override
    public ArrayList<User> reterive(String userPhoneNumber) {
        ArrayList<User> senderUsersList=new ArrayList<>();
        Statement userStatement = null;
        ResultSet userResultSet = null;
        Statement statement=null;
        ResultSet resultSet=null;
        try {
            String senderUsersQuery = "select sender_phone_number from user_invitation where receiver_phone_number = '" + userPhoneNumber + "'";
            byte[] image;
            statement = dbConnection.getConnection().createStatement();
            resultSet = statement.executeQuery(senderUsersQuery);
            User user=null;
            while (resultSet.next()) {
                String senderUsersListQuery = "select * from user where phone_number ='" + resultSet.getString("sender_phone_number") + "'";
                userStatement = dbConnection.getConnection().createStatement();
                userResultSet = userStatement.executeQuery(senderUsersListQuery);
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
                    senderUsersList.add(user);

                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserInvitationDaoImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {

            try {
                if(userResultSet!=null)
                    userResultSet.close();
                if (userStatement!=null)
                    userStatement.close();
                if(resultSet!=null)
                    resultSet.close();
                if(statement!=null)
                    statement.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return senderUsersList;

    }

    @Override
    public boolean CheckInvitationExist(UserContact userContact) {
        String userPhoneNumber=userContact.getPhno();
        String contactPhoneNumber=userContact.getContactPhno();
        String query = "SELECT * FROM user_invitation where sender_phone_number=? and receiver_phone_number=?";
        boolean empty = true;
        try {
            PreparedStatement PreparedStatement = dbConnection.getConnection().prepareStatement(query);
            PreparedStatement.setString(2, userPhoneNumber);
            PreparedStatement.setString(1, contactPhoneNumber);

            ResultSet resultSet = PreparedStatement.executeQuery();
            // to get resultsetcount
            resultSet.beforeFirst();
            if (!(resultSet.next())) {
                empty = false;
            }
            PreparedStatement.close();// closing
        } catch (SQLException ex) {
            //handle exception
        }

        return empty;
    }

    @Override
    public int getInvtationCount(String userPhone) {

        try {
            String senderUsersQuery = "select * from user_invitation where receiver_phone_number = '" + userPhone + "' And ignore_flag=0";
            Statement statement = dbConnection.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(senderUsersQuery);
            while (resultSet.next()) {
                userInvtationsCount++;
            }
            resultSet.close();
            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return userInvtationsCount;
    }

    @Override
    public void updatSeenFriends(String phoneNumber) {
        try {
            String senderUsersQuery = "update user_invitation set seen_flag=1 where receiver_phone_number='"+phoneNumber+"'";
            Statement statement = dbConnection.getConnection().createStatement();
            statement.executeUpdate(senderUsersQuery);
            System.out.println("updateed");
            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void updateIgnoreFlag(UserInvitation userInvitation) {
        try {
            String senderUsersQuery = "update user_invitation set ignore_flag=1 where receiver_phone_number='"+userInvitation.getSenderPhno()+"'";
            Statement statement = dbConnection.getConnection().createStatement();
            statement.executeUpdate(senderUsersQuery);
            System.out.println("updateed");
            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public ArrayList<User> reteriveInvitationUsingSenderPhone (String userPhoneNumber) {
        ArrayList<User> senderUsersList=new ArrayList<>();
        Statement userStatement = null;
        ResultSet userResultSet = null;
        Statement statement=null;
        ResultSet resultSet=null;
        try {
            String senderUsersQuery = "select receiver_phone_number from user_invitation where sender_phone_number = '" + userPhoneNumber + "'";
            byte[] image;
            statement = dbConnection.getConnection().createStatement();
            resultSet = statement.executeQuery(senderUsersQuery);
            User user=null;
            while (resultSet.next()) {
                String senderUsersListQuery = "select * from user where phone_number ='" + resultSet.getString("receiver_phone_number") + "'";
                userStatement = dbConnection.getConnection().createStatement();
                userResultSet = userStatement.executeQuery(senderUsersListQuery);
                if (userResultSet.next()) {
                    user = new User();
                    user.setPhno(userResultSet.getString(1));
                    user.setFirstName(userResultSet.getString(2));
                    user.setLastName(userResultSet.getString(3));
                    user.setEmail(userResultSet.getString(4));
                    //user.setPhoto(userResultSet.getBytes(5));
                    user.setPassword(userResultSet.getString(6));
                    user.setStatus(userResultSet.getString(7));
                    user.setCountry(userResultSet.getString(8));
                    user.setGender(userResultSet.getString(9));
                    user.setDatBirth(userResultSet.getDate(10));
                    user.setBio(userResultSet.getString(11));
                    senderUsersList.add(user);

                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserInvitationDaoImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {

            try {
                if(userResultSet!=null)
                    userResultSet.close();
                if (userStatement!=null)
                    userStatement.close();
                if(resultSet!=null)
                    resultSet.close();
                if(statement!=null)
                    statement.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return senderUsersList;

    }


*/

}
