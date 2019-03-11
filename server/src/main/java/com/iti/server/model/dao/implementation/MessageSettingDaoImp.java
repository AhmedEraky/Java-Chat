package com.iti.server.model.dao.implementation;

import com.iti.ChatCommanServices.model.entity.message.MessageSettings;
import com.iti.server.model.dal.cfg.DBConnection;
import com.iti.server.model.dao.MessageSetingDao;
import org.hibernate.Session;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MessageSettingDaoImp  implements MessageSetingDao {
    DBConnection dbConnection;

    public MessageSettingDaoImp(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public String compareStyle(MessageSettings settings, Session session) {
        return null;
    }

/*    @Override
    public String compareStyle(MessageSettings settings) {
        String query="select * from message_settings where font_size=? and font_style=? and color = ? and\n" +
                "bold=? and italic=? and underline=? and text_background =?";
        String settingID="0";
        try {
            PreparedStatement statement=dbConnection.getConnection().prepareStatement(query);
            statement.setString(1, Float.toString(settings.getFontSize()));
            statement.setString(2,Float.toString(settings.getFontSize()));
            statement.setString(3,settings.getFontColor());
            if(settings.isBold()==true)
            statement.setByte(4, (byte) 1);
            else
                statement.setByte(4, (byte) 0);
            if(settings.isItalic()==true)
                statement.setByte(5, (byte) 1);
            else
                statement.setByte(5, (byte) 0);
            if(settings.isUnderline()==true)
                statement.setByte(6, (byte) 1);
            else
                statement.setByte(6, (byte) 0);

            statement.setString(7,settings.getTextBackground());

            ResultSet resultSet=statement.executeQuery();

            if(resultSet.next()){
                return resultSet.getString(1);
            }
            else {
                settingID=newSetting();
                insertnew(settingID,settings);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return settingID;

    }

    private void insertnew(String settingID, MessageSettings settings) {
        String query="INSERT INTO message_settings(id,font_size,font_style," +
                "color,bold,italic,underline," +
                "text_background,time_stamp,user_phone_number)VALUES (?,?,?,?,?,?,?,?,(select now()),'01116630078')";
        try {
            PreparedStatement statement=dbConnection.getConnection().prepareStatement(query);
            statement.setString(1,settingID);
            statement.setString(2,Float.toString(settings.getFontSize()));
            statement.setString(3,settings.getFontStyle());
            statement.setString(4,settings.getFontColor());
            if(settings.isBold())
                statement.setByte(5, (byte) 1);
            else
                statement.setByte(5, (byte) 0);

            if(settings.isItalic())
                statement.setByte(6, (byte) 1);
            else
                statement.setByte(6, (byte) 0);

            if(settings.isUnderline())
                statement.setByte(7, (byte) 1);
            else
                statement.setByte(7, (byte) 0);
            statement.setString(8,settings.getTextBackground());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String newSetting() {
        String query="select max(id) from message_settings";
        String id="0";
        try {
            Statement statement=dbConnection.getConnection().createStatement();
            ResultSet resultSet=statement.executeQuery(query);
            if(resultSet.next()){
                id=resultSet.getString(1);
            }
            id=Integer.toString(Integer.parseInt(id)+1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }*/
}
