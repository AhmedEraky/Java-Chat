package com.iti.server.model.dao.implementation;

import com.iti.ChatCommanServices.model.entity.message.EntityMessage;
import com.iti.ChatCommanServices.model.entity.message.MessageSettings;
import com.iti.ChatCommanServices.model.entity.user.User;
import com.iti.server.model.dal.cfg.DBConnection;
import com.iti.server.model.dao.UserDao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import org.junit.Before;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class ChatDaoImplementationTest {

    DBConnection dbConnection;
    ChatDaoImplementation chatDaoImplementation;

    @Before
    public void setUp() throws ExceptionInInitializerError {
        dbConnection = new DBConnection();
        dbConnection.ConnectToDB();
        chatDaoImplementation = new ChatDaoImplementation(dbConnection);
    }

    @Test
    public void reterive() {
        ResultSet resultSet = null;
        Statement statement = null;
        String chatID = "2";
        String query = "select * from chat,message_settings  where chat.message_settings_message = message_settings.id and chat_id_message='" + chatID + "'";
        ArrayList<EntityMessage> messages = null;
        try {
            messages = new ArrayList<>();
            statement = dbConnection.getConnection().createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                EntityMessage message = new EntityMessage();
                String user = resultSet.getString("chat_user_phone_number");
                message.setId(resultSet.getString("chat_id_message"));
                UserDao userDao = new UserDaoImplementation(dbConnection);
                User sender = userDao.reterive(user);
                message.setSenderUser(sender);
                message.setMessage(resultSet.getString("message_content"));
                MessageSettings settings = new MessageSettings();
                message.setChat_id(resultSet.getString("chat_id_message"));
                byte bold = resultSet.getByte("bold");
                byte italic = resultSet.getByte("italic");
                byte underline = resultSet.getByte("underline");
                settings.setFontColor(resultSet.getString("text_background"));
                settings.setFontSize(resultSet.getFloat("font_size"));
                settings.setFontStyle(resultSet.getString("font_style"));
                settings.setTimestamp(resultSet.getTimestamp("time_stamp"));
                if (bold == 1) {
                    settings.setBold(true);
                } else {
                    settings.setBold(true);
                }
                if (italic == 1) {
                    settings.setItalic(true);
                } else {
                    settings.setItalic(true);
                }
                if (underline == 1) {
                    settings.setUnderline(true);
                } else {
                    settings.setUnderline(true);
                }
                message.setMessageSettings(settings);
                messages.add(message); 
               
                assertEquals("2",resultSet.getString("chat_id_message"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
