package com.iti.ChatCommanServices.model.entity.chat;
// Generated Mar 7, 2019 7:51:46 PM by Hibernate Tools 4.3.1


import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * UserGroupChatId generated by hbm2java
 */
@Embeddable
public class UserGroupChatId  implements java.io.Serializable {


     private int userGroupchatId;
     private String phoneNumber;

    public UserGroupChatId() {
    }

    public UserGroupChatId(int userGroupchatId, String phoneNumber) {
       this.userGroupchatId = userGroupchatId;
       this.phoneNumber = phoneNumber;
    }
   


    @Column(name="user_groupchat_id", nullable=false)
    public int getUserGroupchatId() {
        return this.userGroupchatId;
    }
    
    public void setUserGroupchatId(int userGroupchatId) {
        this.userGroupchatId = userGroupchatId;
    }


    @Column(name="phone_Number", nullable=false, length=20)
    public String getPhoneNumber() {
        return this.phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof UserGroupChatId) ) return false;
		 UserGroupChatId castOther = ( UserGroupChatId ) other; 
         
		 return (this.getUserGroupchatId()==castOther.getUserGroupchatId())
 && ( (this.getPhoneNumber()==castOther.getPhoneNumber()) || ( this.getPhoneNumber()!=null && castOther.getPhoneNumber()!=null && this.getPhoneNumber().equals(castOther.getPhoneNumber()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + this.getUserGroupchatId();
         result = 37 * result + ( getPhoneNumber() == null ? 0 : this.getPhoneNumber().hashCode() );
         return result;
   }   


}

