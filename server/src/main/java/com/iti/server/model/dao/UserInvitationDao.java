package com.iti.server.model.dao;

import com.iti.ChatCommanServices.model.entity.user.User;
import com.iti.ChatCommanServices.model.entity.user.UserContact;
import com.iti.ChatCommanServices.model.entity.user.UserInvitation;

import java.util.ArrayList;

public interface UserInvitationDao {

    public void persistent(UserInvitation userInvitation);
    public void deleteFromSender(UserInvitation userInvitation);
    public void deleteFromReceiver(UserInvitation userInvitation);
    public int getInvtationCount(String userPhone);
    public void update(UserInvitation userInvitation);
    public ArrayList<User> reterive(String userPhone);
    public boolean CheckInvitationExist(UserContact userContact);
    public void updatSeenFriends(String phoneNumber);
    public void updateIgnoreFlag(UserInvitation userInvitation);
    public ArrayList<User> reteriveInvitationUsingSenderPhone (String userPhoneNumber);


}
