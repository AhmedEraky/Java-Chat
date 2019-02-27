package com.iti.ChatCommanServices.model.entity.chat;

import com.iti.ChatCommanServices.model.entity.chat.chating.behaviour.TextChattingBehaviour;

public class TextChat extends Chat {

    public TextChat() {
        chattingBehaviour=new TextChattingBehaviour();
    }
}
