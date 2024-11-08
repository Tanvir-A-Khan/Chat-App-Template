package com.example.gfgwebsocket.chat.controller;


import com.example.gfgwebsocket.chat.dto.ChatMessageDTO;
import com.example.gfgwebsocket.chat.dto.ChatNotificationDTO;
import com.example.gfgwebsocket.chat.model.Chat;
import com.example.gfgwebsocket.chat.model.Message;
import com.example.gfgwebsocket.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatService chatService;

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload ChatMessageDTO chatMessageDTO) {
        Message savedMessage = chatService.saveMessage(chatMessageDTO);

        // Send to chat topic
        messagingTemplate.convertAndSend("/chat/" + chatMessageDTO.getChatId(), savedMessage);

        // Send notifications to all participants
        Chat chat = chatService.getChatById(chatMessageDTO.getChatId());
        ChatNotificationDTO notification = new ChatNotificationDTO();
        notification.setChatId(chatMessageDTO.getChatId());
        notification.setSenderId(chatMessageDTO.getSenderId());
        notification.setMessage("New message from " + chatMessageDTO.getSenderId());

        for (String participantId : chat.getParticipants()) {
            if (!participantId.equals(chatMessageDTO.getSenderId())) {
                messagingTemplate.convertAndSendToUser(
                        participantId,
                        "/queue/notifications",
                        notification
                );
            }
        }
    }
}