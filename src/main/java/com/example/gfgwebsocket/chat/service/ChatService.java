package com.example.gfgwebsocket.chat.service;// ChatService.java

import com.example.gfgwebsocket.chat.dto.ChatMessageDTO;
import com.example.gfgwebsocket.chat.model.Chat;
import com.example.gfgwebsocket.chat.model.Message;
import com.example.gfgwebsocket.chat.repositories.ChatRepository;
import com.example.gfgwebsocket.chat.repositories.MessageRepository;
import com.example.gfgwebsocket.chat.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public Chat createDirectChat(String user1Id, String user2Id) {
        List<String> participants = Arrays.asList(user1Id, user2Id);

        Chat chat = new Chat();
        chat.setType(Chat.ChatType.DIRECT);
        chat.setParticipants(participants);
        chat.setCreatedAt(LocalDateTime.now());

        return chatRepository.save(chat);
    }

    public Chat createGroupChat(String name, List<String> participantIds) {
        Chat chat = new Chat();
        chat.setName(name);
        chat.setType(Chat.ChatType.GROUP);
        chat.setParticipants(participantIds);
        chat.setCreatedAt(LocalDateTime.now());

        return chatRepository.save(chat);
    }

    public Message saveMessage(ChatMessageDTO chatMessageDTO) {
        Message message = new Message();
        message.setChatId(chatMessageDTO.getChatId());
        message.setSenderId(chatMessageDTO.getSenderId());
        message.setContent(chatMessageDTO.getContent());
        message.setType(chatMessageDTO.getType());
        message.setFileUrl(chatMessageDTO.getFileUrl());
        message.setTimestamp(LocalDateTime.now());
        message.setStatus(Message.Status.SENT);

        return messageRepository.save(message);
    }

    public List<Message> getChatMessages(String chatId) {
        return messageRepository.findByChatIdOrderByTimestampDesc(chatId);
    }

    public List<Chat> getUserChats(String userId) {
        return chatRepository.findByParticipantsContaining(userId);
    }

    public Chat getChatById(String chatId) {
        Optional<Chat> chat = chatRepository.findById(chatId);
        if(chat.isPresent()){
            return chat.get();
        }else{
            throw new RuntimeException();
        }

    }
}