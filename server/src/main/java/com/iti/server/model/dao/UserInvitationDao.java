package com.iti.server.model.dao;

import com.iti.ChatCommanServices.model.entity.user.User;
import com.iti.ChatCommanServices.model.entity.user.UserContact;
import com.iti.ChatCommanServices.model.entity.user.UserContactId;
import com.iti.ChatCommanServices.model.entity.user.UserInvitation;
import org.hibernate.Session;

import java.util.ArrayList;

public interface UserInvitationDao {

    public void persistent(UserInvitation userInvitation, Session session);
    public void deleteFromSender(UserInvitation userInvitation, Session session);
    public void deleteFromReceiver(UserInvitation userInvitation, Session session);
    public int getInvtationCount(String userPhone, Session session);
    public void update(UserInvitation userInvitation, Session session);
    public ArrayList<User> reterive(String userPhone, Session session);
    public boolean CheckInvitationExist(UserContactId userContact, Session session);
    public void updatSeenFriends(String phoneNumber, Session session);
    public void updateIgnoreFlag(UserInvitation userInvitation, Session session);
    public ArrayList<User> reteriveInvitationUsingSenderPhone (String userPhoneNumber, Session session);


}
