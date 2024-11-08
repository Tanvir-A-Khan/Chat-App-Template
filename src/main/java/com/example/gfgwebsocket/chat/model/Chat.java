package com.example.gfgwebsocket.chat.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(collection = "chats")
public class Chat {
    @Id
    private String id;
    private String name; // for group chats
    private ChatType type;
    private List<String> participants;
    private LocalDateTime createdAt;

    public enum ChatType {
        DIRECT, GROUP
    }
}