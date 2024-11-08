package com.example.gfgwebsocket.WebSocketConfigurations;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SocketConnectionHandler extends TextWebSocketHandler {
    List<WebSocketSession> webSocketSessions = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        printWebSocketSessionData(session);
        System.out.println(session.getId() + " CONNECTED ");
        webSocketSessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        System.out.println(session.getId() + " DISCONNECTED ");
        System.out.println(status.getCode());
        webSocketSessions.remove(session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        super.handleMessage(session, message);
        printWebSocketSessionData(session);

        for(WebSocketSession webSocketSession: webSocketSessions){
            if(webSocketSession == session) continue;
            webSocketSession.sendMessage(message);
        }
    }
    public static void printWebSocketSessionData(WebSocketSession session) {
        if (session == null) {
            System.out.println("Session is null.");
            return;
        }

        System.out.println("WebSocketSession Data:");
        try {
            Field[] fields = WebSocketSession.class.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true); // Allow access to private fields
                Object value = field.get(session);
                System.out.println(field.getName() + ": " + value);
            }

            // Print additional dynamic values from methods
            System.out.println("ID: " + session.getId());
            System.out.println("URI: " + session.getUri());
            System.out.println("Handshake Headers: " + session.getHandshakeHeaders());
            System.out.println("Attributes: " + session.getAttributes());
            System.out.println("Principal: " + session.getPrincipal());
            System.out.println("Local Address: " + session.getLocalAddress());
            System.out.println("Remote Address: " + session.getRemoteAddress());
            System.out.println("Accepted Protocol: " + session.getAcceptedProtocol());
            System.out.println("Text Message Size Limit: " + session.getTextMessageSizeLimit());
            System.out.println("Binary Message Size Limit: " + session.getBinaryMessageSizeLimit());
            System.out.println("Extensions: " + session.getExtensions());
            System.out.println("Is Open: " + session.isOpen());
        } catch (IllegalAccessException e) {
            System.out.println("Error accessing fields: " + e.getMessage());
        }
    }
}
