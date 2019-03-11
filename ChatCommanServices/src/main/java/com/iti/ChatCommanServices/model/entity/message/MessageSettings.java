package com.iti.ChatCommanServices.model.entity.message;

import com.iti.ChatCommanServices.model.entity.chat.Chat;
import com.iti.ChatCommanServices.model.entity.user.User;
import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="message_settings"
        ,catalog="javachatdatabase"
)
public class MessageSettings  implements java.io.Serializable {


    private Integer id;
    private User user;
    private float fontSize;
    private String fontStyle;
    private String color;
    private boolean bold;
    private boolean italic;
    private boolean underline;
    private String textBackground;
    private Date timeStamp;
    private Set<Chat> chats = new HashSet<Chat>(0);

    public MessageSettings() {
    }


    public MessageSettings(User user, float fontSize, String fontStyle, String color, boolean bold, boolean italic, boolean underline, String textBackground, Date timeStamp) {
        this.user = user;
        this.fontSize = fontSize;
        this.fontStyle = fontStyle;
        this.color = color;
        this.bold = bold;
        this.italic = italic;
        this.underline = underline;
        this.textBackground = textBackground;
        this.timeStamp = timeStamp;
    }
    public MessageSettings(User user, float fontSize, String fontStyle, String color, boolean bold, boolean italic, boolean underline, String textBackground, Date timeStamp, Set<Chat> chats) {
        this.user = user;
        this.fontSize = fontSize;
        this.fontStyle = fontStyle;
        this.color = color;
        this.bold = bold;
        this.italic = italic;
        this.underline = underline;
        this.textBackground = textBackground;
        this.timeStamp = timeStamp;
        this.chats = chats;
    }

    @Id
    @GeneratedValue(strategy=IDENTITY)


    @Column(name="id", unique=true, nullable=false)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_phone_number", nullable=false)
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    @Column(name="font_size", nullable=false, precision=12, scale=0)
    public float getFontSize() {
        return this.fontSize;
    }

    public void setFontSize(float fontSize) {
        this.fontSize = fontSize;
    }


    @Column(name="font_style", nullable=false, length=20)
    public String getFontStyle() {
        return this.fontStyle;
    }

    public void setFontStyle(String fontStyle) {
        this.fontStyle = fontStyle;
    }


    @Column(name="color", nullable=false, length=10)
    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }


    @Column(name="bold", nullable=false)
    public boolean isBold() {
        return this.bold;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
    }


    @Column(name="italic", nullable=false)
    public boolean isItalic() {
        return this.italic;
    }

    public void setItalic(boolean italic) {
        this.italic = italic;
    }


    @Column(name="underline", nullable=false)
    public boolean isUnderline() {
        return this.underline;
    }

    public void setUnderline(boolean underline) {
        this.underline = underline;
    }


    @Column(name="text_background", nullable=false, length=20)
    public String getTextBackground() {
        return this.textBackground;
    }

    public void setTextBackground(String textBackground) {
        this.textBackground = textBackground;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="time_stamp", nullable=false, length=19)
    public Date getTimeStamp() {
        return this.timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    @OneToMany(fetch=FetchType.LAZY, mappedBy="messageSettings")
    public Set<Chat> getChats() {
        return this.chats;
    }

    public void setChats(Set<Chat> chats) {
        this.chats = chats;
    }




}


