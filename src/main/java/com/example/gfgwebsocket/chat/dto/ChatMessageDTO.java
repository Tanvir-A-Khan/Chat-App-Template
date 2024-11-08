package com.example.gfgwebsocket.chat.dto;// ChatMessageDTO.java


import com.example.gfgwebsocket.chat.model.Message;
import lombok.Data;

@Data
public class ChatMessageDTO {
    private String chatId;
    private String senderId;
    private String content;
    private Message.MessageType type;
    private String fileUrl;
}
