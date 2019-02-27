package com.iti.ChatCommanServices.model.entity.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserInvitation implements Serializable {
    String senderPhno,receiverPhno;
    boolean ignoreFlag;
    //to save each user and his invitiaions ID is the phone number and List<phonenumbers of senders invitations >
    Map<String,List<String>> userInvitations= new HashMap<String,List<String>>();
    //to save the senders each element is phonenumber of the sender
    List<String> invitaionsSenders= new ArrayList<String>();

    public String getSenderPhno() {
        return senderPhno;
    }

    public void setSenderPhno(String senderPhno) {
        this.senderPhno = senderPhno;
    }

    public String getReceiverPhno() {
        return receiverPhno;
    }

    public void setReceiverPhno(String receiverPhno) {
        this.receiverPhno = receiverPhno;
    }

    public boolean isIgnoreFlag() {
        return ignoreFlag;
    }

    public void setIgnoreFlag(boolean ignoreFlag) {
        this.ignoreFlag = ignoreFlag;
    }

    public Map<String, List<String>> getUserInvitations() {
        return userInvitations;
    }

    public void setUserInvitations(Map<String, List<String>> userInvitations) {
        this.userInvitations = userInvitations;
    }

    public List<String> getInvitaionsSenders() {
        return invitaionsSenders;
    }

    public void setInvitaionsSenders(List<String> invitaionsSenders) {
        this.invitaionsSenders = invitaionsSenders;
    }


}
