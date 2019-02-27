/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iti.ChatCommanServices.model.entity.user;

import java.io.Serializable;

/**
 *
 * @author Esraa
 */
public class UserChat implements Serializable {
    int chatId;
    String phno;
    boolean leftFlag,chatBotFlag;

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public String getPhno() {
        return phno;
    }

    public void setPhno(String phno) {
        this.phno = phno;
    }

    public boolean isLeftFlag() {
        return leftFlag;
    }

    public void setLeftFlag(boolean leftFlag) {
        this.leftFlag = leftFlag;
    }

    public boolean isChatBotFlag() {
        return chatBotFlag;
    }

    public void setChatBotFlag(boolean chatBotFlag) {
        this.chatBotFlag = chatBotFlag;
    }
    
    
}
