/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iti.ChatCommanServices.model.entity.message;

import java.io.Serializable;

/**
 *
 * @author Esraa
 */
public class ReceivedMessage  implements Serializable {
    String phno;
    int chatId,msgId;
    boolean notificationFlag;

    public String getPhno() {
        return phno;
    }

    public boolean isNotificationFlag() {
        return notificationFlag;
    }

    public void setNotificationFlag(boolean notificationFlag) {
        this.notificationFlag = notificationFlag;
    }

    public void setPhno(String phno) {
        this.phno = phno;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public int getMsgId() {
        return msgId;
    }

    public void setMsgId(int msgId) {
        this.msgId = msgId;
    }


    
    
}
