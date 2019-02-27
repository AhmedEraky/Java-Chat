package com.iti.ChatCommanServices.model.entity.chat;

import com.iti.ChatCommanServices.model.entity.chat.chating.behaviour.VoiceChattingBehaviour;

public class VoiceChat extends Chat {

    public VoiceChat() {
        chattingBehaviour=new VoiceChattingBehaviour();
    }
}

