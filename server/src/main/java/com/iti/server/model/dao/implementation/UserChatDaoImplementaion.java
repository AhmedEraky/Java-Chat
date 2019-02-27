package com.iti.server.model.dao.implementation;

import com.iti.ChatCommanServices.model.entity.user.User;
import com.iti.server.model.dal.cfg.DBConnection;
import com.iti.server.model.dao.UserChatDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class UserChatDaoImplementaion implements UserChatDao {

    @Override
    public String retrieveChatID(User firstUser, User secondUser, Connection connection) {
        String query="SELECT * FROM user_chat where (first_phone_number= '"+firstUser.getPhno()+"' and second_phone_number= '"+secondUser.getPhno()+"' ) ||(first_phone_number= '"+secondUser.getPhno()+"' and second_phone_number= '"+firstUser.getPhno()+"')";
        String chatId=null;
        try {
            Statement statement=connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet resultSet=statement.executeQuery(query);
            if(resultSet.next()){
                chatId=resultSet.getString(1);
            }
            else {
                resultSet.moveToInsertRow();
                resultSet.updateString("first_phone_number",firstUser.getPhno());
                resultSet.updateString("second_phone_number",secondUser.getPhno());
                resultSet.insertRow();
                resultSet.first();
                chatId=resultSet.getString(1);
                //return retrieveChatID(firstUser,secondUser,connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chatId;

    }

    @Override
    public ArrayList<String> retrieveChatsID(User user,Connection connection) {
        String query="select chat_id from user_chat where first_phone_number= '"+user.getPhno()+"' or second_phone_number='"+user.getPhno()+"'";
        ArrayList<String> chatList=null;
        try {
            chatList=new ArrayList<>();
            Statement statement=connection.createStatement();
            ResultSet resultSet=statement.executeQuery(query);
            while (resultSet.next()){
                chatList.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chatList;
    }
}
