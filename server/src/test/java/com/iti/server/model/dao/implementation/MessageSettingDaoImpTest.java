package com.iti.server.model.dao.implementation;

import com.iti.server.model.dal.cfg.DBConnection;
import org.junit.Before;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.*;

public class MessageSettingDaoImpTest {
    DBConnection dbConnection;
    MessageSettingDaoImp messageSettingDaoImp;
    
    @Before
    public void setUp() throws ExceptionInInitializerError {
        dbConnection= new DBConnection();
        dbConnection.ConnectToDB();
        messageSettingDaoImp = new MessageSettingDaoImp(dbConnection);
    }

    @Test
    public void newSetting() {
        String query="select max(id) from message_settings";
        try {
            Statement statement=dbConnection.getConnection().createStatement();
            ResultSet resultSet=statement.executeQuery(query);
            if(resultSet.next()){
                int id=resultSet.getInt(1);
                assertEquals(id+1,Integer.parseInt(messageSettingDaoImp.newSetting()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
