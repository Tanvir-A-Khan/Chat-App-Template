package com.example.gfgwebsocket.chat.repositories;

import com.example.gfgwebsocket.chat.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findByUsername(String username);
    User findByEmail(String email);
}