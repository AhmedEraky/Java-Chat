/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iti.server.model.dao.implementation;

import com.iti.ChatCommanServices.model.entity.user.User;
import com.iti.ChatCommanServices.model.entity.user.UserInvitation;
import com.iti.ChatCommanServices.model.exception.RegistrationDuplicateException;
import com.iti.server.model.dal.cfg.DBConnection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author pc
 */
public class UserInvitationDaoImplementationTest {

    DBConnection dbConnection;
    UserInvitationDaoImplementation userInvitationDaoImplementation;

    @Before
    public void setUp() throws Exception {
        dbConnection = new DBConnection();
        dbConnection.ConnectToDB();
        userInvitationDaoImplementation = new UserInvitationDaoImplementation(dbConnection);

    }

    @Test
    public void deleteFromSender() {
        UserInvitation userInvitation = new UserInvitation();
        userInvitation.setSenderPhno("01116630078");
        userInvitation.setSenderPhno("01223586041");
        String senderPhoneNumber=userInvitation.getSenderPhno();
        String receiverPhoneNumber=userInvitation.getReceiverPhno();
        String query = "DELETE FROM user_invitation where sender_phone_number=? and receiver_phone_number=?";
        try {
            PreparedStatement PreparedStatement = dbConnection.getConnection().prepareStatement(query);
            PreparedStatement.setString(1, senderPhoneNumber);
            PreparedStatement.setString(2, receiverPhoneNumber);
            int rowset = PreparedStatement.executeUpdate();
            assertEquals(0,rowset);
            PreparedStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(UserInvitationDaoImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
