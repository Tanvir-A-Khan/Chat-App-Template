package com.example.gfgwebsocket.chat.repositories;

import com.example.gfgwebsocket.chat.model.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatRepository extends MongoRepository<Chat, String> {
    List<Chat> findByParticipantsContaining(String userId);
}