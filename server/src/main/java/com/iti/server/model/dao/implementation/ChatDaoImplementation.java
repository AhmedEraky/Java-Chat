package com.iti.server.model.dao.implementation;


import com.iti.ChatCommanServices.model.entity.chat.Chat;
import com.iti.ChatCommanServices.model.entity.message.EntityMessage;
import com.iti.ChatCommanServices.model.entity.message.MessageSettings;
import com.iti.ChatCommanServices.model.entity.user.User;
import com.iti.server.model.dal.cfg.DBConnection;
import com.iti.server.model.dao.ChatDao;
import com.iti.server.model.dao.UserDao;

import java.sql.*;
import java.util.ArrayList;

public class ChatDaoImplementation implements ChatDao {
    DBConnection dbConnection=new DBConnection();

    public ChatDaoImplementation(DBConnection connection) {
        this.dbConnection = connection;
    }

    @Override
    public synchronized void  persistent(EntityMessage message) {
        String insertQuery="INSERT INTO chat (chat_user_phone_number,file_time_stamp,file_path,message_content,message_time_stamp,chat_id_message,message_settings_message)VALUES(?,(Select NOW()),'',?,(Select NOW()),?,?)";
        try {
            PreparedStatement statement=dbConnection.getConnection().prepareStatement(insertQuery);
            statement.setString(1,message.getSenderUser().getPhno());
            statement.setString(2,message.getMessage());
            statement.setString(3,message.getId());
            MessageSettingDaoImp messageSettingDaoImp=new MessageSettingDaoImp(dbConnection);
            String id=messageSettingDaoImp.compareStyle(message.getMessageSettings());
            statement.setString(4,id);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<EntityMessage> reterive(String chatID, Connection connection) {
        String query="select * from chat,message_settings  where chat.message_settings_message = message_settings.id and chat_id_message='"+chatID+"'";
        ResultSet resultSet=null;
        Statement statement=null;
        ArrayList<EntityMessage> messages=null;
        try {
            messages=new ArrayList<>();
            statement=connection.createStatement();
            resultSet=statement.executeQuery(query);
            while (resultSet.next()){
                EntityMessage message=new EntityMessage();
                String user=resultSet.getString("chat_user_phone_number");
                message.setId(resultSet.getString("chat_id_message"));
                UserDao userDao=new UserDaoImplementation(dbConnection);
                User sender=userDao.reterive(user);
                message.setSenderUser(sender);

                message.setMessage(resultSet.getString("message_content"));
                MessageSettings settings=new MessageSettings();
                message.setChat_id(resultSet.getString("chat_id_message"));
                byte bold=resultSet.getByte("bold");
                byte italic=resultSet.getByte("italic");
                byte underline=resultSet.getByte("underline");
                settings.setFontColor(resultSet.getString("color"));
                settings.setFontSize(resultSet.getFloat("font_size"));
                settings.setFontStyle(resultSet.getString("font_style"));
                settings.setTimestamp(resultSet.getTimestamp("time_stamp"));
                settings.setTextBackground(resultSet.getString("text_background"));
                if (bold==1)
                    settings.setBold(true);
                else
                    settings.setBold(true);
                if (italic==1)
                    settings.setItalic(true);
                else
                    settings.setItalic(true);
                if (underline==1)
                    settings.setUnderline(true);
                else
                    settings.setUnderline(true);
                message.setMessageSettings(settings);
                messages.add(message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if(resultSet!=null)
                    resultSet.close();
                if (statement!=null)
                    statement.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return messages;
    }


}
