package com.iti.server.model.dao.implementation;


import com.iti.ChatCommanServices.model.entity.chat.Chat;
import com.iti.ChatCommanServices.model.entity.chat.UserChat;
import com.iti.ChatCommanServices.model.entity.message.EntityMessage;
import com.iti.ChatCommanServices.model.entity.message.MessageSettings;
import com.iti.ChatCommanServices.model.entity.user.User;
import com.iti.server.model.dal.cfg.DBConnection;
import com.iti.server.model.dao.ChatDao;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import java.util.ArrayList;
import java.util.List;

public class ChatDaoImplementation implements ChatDao {
    DBConnection dbConnection=new DBConnection();

    public ChatDaoImplementation(DBConnection connection) {
        this.dbConnection = connection;

    }

    @Override
    public synchronized void persistent(EntityMessage message, Session session) {
        Chat chat=new Chat();
        chat.setChatUserPhoneNumber(message.getSenderUser().getPhno());
        chat.setMessageContent(message.getMessage());
        MessageSettings messageSettings=message.getMessageSettings();
        com.iti.ChatCommanServices.model.entity.dto.message.MessageSettings settings=new com.iti.ChatCommanServices.model.entity.dto.message.MessageSettings();
        settings.setTextBackground(messageSettings.getTextBackground());
        settings.setItalic(messageSettings.isItalic());
        settings.setBold(messageSettings.isBold());
        settings.setUnderline(messageSettings.isUnderline());
        settings.setColor(messageSettings.getFontColor());
        settings.setFontStyle(messageSettings.getFontStyle());
        settings.setFontSize(messageSettings.getFontSize());

        Transaction transaction=session.beginTransaction();
        session.save(settings);
        transaction.commit();


        chat.setMessageSettings(settings);
        Criteria criteria=session.createCriteria(UserChat.class).add(Restrictions.eq("chatId",Integer.parseInt(message.getId())));
        UserChat userChat= (UserChat) criteria.uniqueResult();
        chat.setUserChat(userChat);
        transaction=session.beginTransaction();
        session.persist(chat);
        transaction.commit();

    }

    @Override
    public ArrayList<EntityMessage> reterive(String chatID, Session session) {
        Criteria criteria=session.createCriteria(Chat.class).createCriteria("userChat").add(Restrictions.eq("chatId",Integer.parseInt(chatID)));
        List<Chat> chats=criteria.list();
        ArrayList<EntityMessage> messages=new ArrayList<>();
        for(Chat chat:chats){
            EntityMessage message1=new EntityMessage();
            MessageSettings messageSettings=new MessageSettings();
            messageSettings.setTextBackground(chat.getMessageSettings().getTextBackground());
            messageSettings.setItalic(chat.getMessageSettings().isItalic());
            messageSettings.setBold(chat.getMessageSettings().isBold());
            messageSettings.setUnderline(chat.getMessageSettings().isUnderline());
            messageSettings.setFontColor(chat.getMessageSettings().getColor());
            messageSettings.setFontStyle(chat.getMessageSettings().getFontStyle());
            messageSettings.setFontSize(chat.getMessageSettings().getFontSize());
            message1.setMessageSettings(messageSettings);
            message1.setMessage(chat.getMessageContent());
            message1.setChat_id(chatID);
            criteria=session.createCriteria(User.class).add(Restrictions.eq("userPhoneNumber",chat.getChatUserPhoneNumber()));
            User user= (User) criteria.uniqueResult();
            message1.setSenderUser(user);
            messages.add(message1);
        }
        return messages;
    }


}
