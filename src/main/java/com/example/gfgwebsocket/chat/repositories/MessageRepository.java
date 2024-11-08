package com.example.gfgwebsocket.chat.repositories;

import com.example.gfgwebsocket.chat.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageRepository extends MongoRepository<Message, String> {
    List<Message> findByChatIdOrderByTimestampDesc(String chatId);
}