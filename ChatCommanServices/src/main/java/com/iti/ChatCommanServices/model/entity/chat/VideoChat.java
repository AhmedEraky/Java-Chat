package com.iti.ChatCommanServices.model.entity.chat;

import com.iti.ChatCommanServices.model.entity.chat.chating.behaviour.VideoChattingBehaviour;

public class VideoChat extends Chat {
    public VideoChat() {
        chattingBehaviour=new VideoChattingBehaviour();
    }
}
