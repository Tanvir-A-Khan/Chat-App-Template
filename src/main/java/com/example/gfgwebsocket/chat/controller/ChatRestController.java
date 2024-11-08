package com.example.gfgwebsocket.chat.controller;

import com.example.gfgwebsocket.chat.model.Chat;
import com.example.gfgwebsocket.chat.model.Message;
import com.example.gfgwebsocket.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
@RequiredArgsConstructor
public class ChatRestController {
    private final ChatService chatService;

    @PostMapping("/direct")
    public ResponseEntity<Chat> createDirectChat(
            @RequestParam String user1Id,
            @RequestParam String user2Id) {
        return ResponseEntity.ok(chatService.createDirectChat(user1Id, user2Id));
    }

    @PostMapping("/group")
    public ResponseEntity<Chat> createGroupChat(
            @RequestParam String name,
            @RequestBody List<String> participantIds) {
        return ResponseEntity.ok(chatService.createGroupChat(name, participantIds));
    }

    @GetMapping("/{chatId}/messages")
    public ResponseEntity<List<Message>> getChatMessages(@PathVariable String chatId) {
        return ResponseEntity.ok(chatService.getChatMessages(chatId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Chat>> getUserChats(@PathVariable String userId) {
        return ResponseEntity.ok(chatService.getUserChats(userId));
    }
}