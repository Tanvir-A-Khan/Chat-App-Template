package com.example.gfgwebsocket.chat.dto;

import lombok.Data;

@Data
public class ChatNotificationDTO {
    private String chatId;
    private String senderId;
    private String message;
}