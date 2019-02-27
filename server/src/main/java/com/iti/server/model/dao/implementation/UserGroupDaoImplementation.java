package com.iti.server.model.dao.implementation;

import com.iti.ChatCommanServices.model.entity.user.User;
import com.iti.ChatCommanServices.model.exception.DublicateEntryException;
import com.iti.ChatCommanServices.model.exception.UserNotFoundException;
import com.iti.server.model.dal.cfg.DBConnection;
import com.iti.server.model.dao.UserDao;
import com.iti.server.model.dao.UserGroupDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UserGroupDaoImplementation implements UserGroupDao {
    DBConnection dbConnection;

    public UserGroupDaoImplementation(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public void persistent(ArrayList<User> users) {
        int id=createID();
        String query="insert into user_group_chat values("+id+",?)";
        PreparedStatement statement=null;
        try {
            statement=dbConnection.getConnection().prepareStatement(query);
            for(User user:users){
                statement.setString(1,user.getPhno());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if(statement!=null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private int createID() {
        String query="select max(user_groupchat_id) from user_group_chat";
        int id=0;
        try {
            Statement statement=dbConnection.getConnection().createStatement();
            ResultSet resultSet=statement.executeQuery(query);
            if (resultSet.next())
                id=resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  id+1;
    }

    @Override
    public ArrayList<String> reteriveGroups(User user) {
        ArrayList<String> groups=new ArrayList<>();
        String query="select * from user_group_chat where phone_Number='"+user.getPhno()+"'";
        try {
            Statement statement=dbConnection.getConnection().createStatement();
            ResultSet resultSet=statement.executeQuery(query);
            while (resultSet.next()){
                groups.add(resultSet.getString("user_groupchat_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groups;
    }

    @Override
    public ArrayList<User> reteriveGroupMember(User user, String groupID) {
        String query="SELECT * FROM user_group_chat where user_groupchat_id=?";
        ArrayList<User> groups=new ArrayList<>();
        PreparedStatement statement=null;
        ResultSet resultSet=null;
        try {
            statement=dbConnection.getConnection().prepareStatement(query);
            statement.setString(1,groupID);
            resultSet=statement.executeQuery();
            UserDao userDao=new UserDaoImplementation(dbConnection);
            while (resultSet.next()){
                if (!resultSet.getString(2).equals(user.getPhno())){
                    User groupUser=userDao.reterive(resultSet.getString(2));
                    groups.add(groupUser);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if(statement!=null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if(resultSet!=null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return groups;
    }

    @Override
    public void removeUser(User user, String groupID) {
        String query="DELETE FROM user_group_chat WHERE user_groupchat_id = ? and phone_Number = ?";
        PreparedStatement statement=null;
        try {
            statement=dbConnection.getConnection().prepareStatement(query);
            statement.setString(1,groupID);
            statement.setString(2,user.getPhno());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (statement!=null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    @Override
    public void addUser(User user, String groupID) throws DublicateEntryException, UserNotFoundException {
        String query="INSERT INTO user_group_chat VALUES (?,?)";
        PreparedStatement statement=null;
        try {
            statement=dbConnection.getConnection().prepareStatement(query);
            statement.setString(1,groupID);
            statement.setString(2,user.getPhno());
            statement.executeUpdate();
        } catch (SQLException e) {
            if(e.getErrorCode()==1062)
                throw new DublicateEntryException("this Id Already exists ");
            else if (e.getErrorCode()==1452){
                throw new UserNotFoundException("there is no User With Phone number "+ user.getPhno() );
            }
        }finally {
            if (statement!=null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}
