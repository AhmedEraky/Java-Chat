package com.iti.server.model.dao.implementation;
import com.iti.ChatCommanServices.model.entity.chat.UserChat;
import com.iti.ChatCommanServices.model.entity.user.User;
import com.iti.server.model.dao.UserChatDao;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import java.util.ArrayList;


public class UserChatDaoImplementaion implements UserChatDao {
    @Override
    public String retrieveChatID(User firstUser, User secondUser, Session session) {
        Criteria criteria=session.createCriteria(UserChat.class);
        Criterion criterion1= Restrictions.and(Restrictions.eq("firstPhoneNumber",firstUser.getPhno()),Restrictions.eq("secondPhoneNumber",secondUser.getPhno()));
        Criterion criterion2= Restrictions.and(Restrictions.eq("firstPhoneNumber",secondUser.getPhno()),Restrictions.eq("secondPhoneNumber",firstUser.getPhno()));
        UserChat userChat= (UserChat)criteria.add(Restrictions.or(criterion1,criterion2)).uniqueResult();
        String chatID=Integer.toString(userChat.getChatId());
        return chatID;
    }

    @Override
    public ArrayList<String> retrieveChatsID(User user, Session session) {
        return null;
    }

   /*
    @Override
    public ArrayList<String> retrieveChatsID(User user,Connection connection) {
        String query="select chat_id from user_chat where first_phone_number= '"+user.getPhno()+"' or second_phone_number='"+user.getPhno()+"'";
        ArrayList<String> chatList=null;
        try {
            chatList=new ArrayList<>();
            Statement statement=connection.createStatement();
            ResultSet resultSet=statement.executeQuery(query);
            while (resultSet.next()){
                chatList.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chatList;
    }*/
}
