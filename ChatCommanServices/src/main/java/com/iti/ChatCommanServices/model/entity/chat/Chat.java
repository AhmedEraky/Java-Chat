package com.iti.ChatCommanServices.model.entity.chat;
// Generated Mar 7, 2019 7:51:46 PM by Hibernate Tools 4.3.1


import com.iti.ChatCommanServices.model.entity.message.MessageSettings;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Chat generated by hbm2java
 */
@Entity
@Table(name="chat"
        ,catalog="javachatdatabase"
)
public class Chat  implements java.io.Serializable {


    private Integer id;
    private MessageSettings messageSettings;
    private UserChat userChat;
    private String chatUserPhoneNumber;
    private Date fileTimeStamp;
    private String filePath;
    private String messageContent;
    private Date messageTimeStamp;

    public Chat() {
    }


    public Chat(UserChat userChat, String chatUserPhoneNumber) {
        this.userChat = userChat;
        this.chatUserPhoneNumber = chatUserPhoneNumber;
    }
    public Chat(MessageSettings messageSettings, UserChat userChat, String chatUserPhoneNumber, Date fileTimeStamp, String filePath, String messageContent, Date messageTimeStamp) {
        this.messageSettings = messageSettings;
        this.userChat = userChat;
        this.chatUserPhoneNumber = chatUserPhoneNumber;
        this.fileTimeStamp = fileTimeStamp;
        this.filePath = filePath;
        this.messageContent = messageContent;
        this.messageTimeStamp = messageTimeStamp;
    }

    @Id @GeneratedValue(strategy=IDENTITY)
    @Column(name="id", unique=true, nullable=false)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="message_settings_message")
    public MessageSettings getMessageSettings() {
        return this.messageSettings;
    }

    public void setMessageSettings(MessageSettings messageSettings) {
        this.messageSettings = messageSettings;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="chat_id_message", nullable=false)
    public UserChat getUserChat() {
        return this.userChat;
    }

    public void setUserChat(UserChat userChat) {
        this.userChat = userChat;
    }


    @Column(name="chat_user_phone_number", nullable=false, length=20)
    public String getChatUserPhoneNumber() {
        return this.chatUserPhoneNumber;
    }

    public void setChatUserPhoneNumber(String chatUserPhoneNumber) {
        this.chatUserPhoneNumber = chatUserPhoneNumber;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="file_time_stamp", length=19)
    public Date getFileTimeStamp() {
        return this.fileTimeStamp;
    }

    public void setFileTimeStamp(Date fileTimeStamp) {
        this.fileTimeStamp = fileTimeStamp;
    }


    @Column(name="file_path", length=20)
    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }


    @Column(name="message_content")
    public String getMessageContent() {
        return this.messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="message_time_stamp", length=19)
    public Date getMessageTimeStamp() {
        return this.messageTimeStamp;
    }

    public void setMessageTimeStamp(Date messageTimeStamp) {
        this.messageTimeStamp = messageTimeStamp;
    }




}


