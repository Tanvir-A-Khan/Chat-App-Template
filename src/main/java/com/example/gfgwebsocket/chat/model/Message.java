package com.example.gfgwebsocket.chat.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "messages")
public class Message {
    @Id
    private String id;
    private String chatId;
    private String senderId;
    private String content;
    private MessageType type;
    private String fileUrl;
    private LocalDateTime timestamp;
    private Status status;

    public enum MessageType {
        TEXT, FILE, IMAGE
    }

    public enum Status {
        SENT, DELIVERED, READ
    }
}