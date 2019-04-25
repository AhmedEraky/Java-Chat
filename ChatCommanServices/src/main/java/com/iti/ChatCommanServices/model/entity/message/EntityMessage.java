package com.iti.ChatCommanServices.model.entity.message;

import com.iti.ChatCommanServices.model.entity.user.User;
import java.io.Serializable;
import java.sql.Timestamp;

public class EntityMessage implements Serializable {
    String id,chat_id;
    User senderUser;
    String message,fileTimeStamp,filePath;
    Timestamp messageTimeStamp;
    MessageSettings messageSettings;
    int fileLenght;
    byte[] fileData;
    String fileName;

    public String getChat_id() {
        return chat_id;
    }

    public void setChat_id(String chat_id) {
        this.chat_id = chat_id;
    }

    public MessageSettings getMessageSettings() {
        return messageSettings;
    }

    public void setMessageSettings(MessageSettings messageSettings) {
        this.messageSettings = messageSettings;
    }

    public EntityMessage() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getSenderUser() {
        return senderUser;
    }

    public void setSenderUser(User senderUser) {
        this.senderUser = senderUser;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFileTimeStamp() {
        return fileTimeStamp;
    }

    public void setFileTimeStamp(String fileTimeStamp) {
        this.fileTimeStamp = fileTimeStamp;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Timestamp getMessageTimeStamp() {
        return messageTimeStamp;
    }

    public void setMessageTimeStamp(Timestamp messageTimeStamp) {
        this.messageTimeStamp = messageTimeStamp;
    }

    public int getFileLenght() {
        return fileLenght;
    }

    public void setFileLenght(int fileLenght) {
        this.fileLenght = fileLenght;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}