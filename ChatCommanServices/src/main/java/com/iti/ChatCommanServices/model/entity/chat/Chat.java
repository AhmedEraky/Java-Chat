package com.iti.ChatCommanServices.model.entity.chat;

import com.iti.ChatCommanServices.model.entity.chat.chating.behaviour.ChattingBehaviour;

import java.sql.Date;
import java.sql.Timestamp;

public abstract class Chat {
    int chatId;
    String phno,filePath,msgContent;
    Timestamp fileDate;


    ChattingBehaviour chattingBehaviour;

    public String communicate(){
        return chattingBehaviour.doChat();
    }








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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public Timestamp getFileDate() {
        return fileDate;
    }

    public void setFileDate(Timestamp fileDate) {
        this.fileDate = fileDate;
    }


}
