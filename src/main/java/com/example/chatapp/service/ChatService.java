package com.example.chatapp.service;

import com.example.chatapp.model.Message;
import com.example.chatapp.repository.ChatRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class ChatService {

    private final ChatRepository chatRepository;
    private final RedisMessagePublisher redisMessagePublisher;

    public ChatService(ChatRepository chatRepository, RedisMessagePublisher redisMessagePublisher) {
        this.chatRepository = chatRepository;
        this.redisMessagePublisher = redisMessagePublisher;
    }

    public String createChatRoom(String roomName) {
        if (roomName == null || roomName.trim().isEmpty()) {
            throw new IllegalArgumentException("Chat room name cannot be empty!");
        }
        return chatRepository.createChatRoom(roomName);
    }

    public String joinChatRoom(String roomId, String participant) {
        if (roomId == null || roomId.trim().isEmpty() || participant == null || participant.trim().isEmpty()) {
            throw new IllegalArgumentException("Room ID and participant name cannot be empty!");
        }
        return chatRepository.addParticipant(roomId, participant);
    }

    public String sendMessage(String roomId, String participant, String message) {
        if (roomId == null || roomId.trim().isEmpty() || participant == null || participant.trim().isEmpty() || message == null || message.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid input: Room ID, participant, and message cannot be empty!");
        }

        Message msg = new Message(participant, message, Instant.now().toString());
        chatRepository.saveMessage(roomId, msg);

        String topic = "chatroom:" + roomId;
        redisMessagePublisher.publish(topic, message);

        return "Message sent successfully.";
    }

    public List<Message> getChatHistory(String roomId, int limit) {
        if (roomId == null || roomId.trim().isEmpty()) {
            throw new IllegalArgumentException("Room ID cannot be empty!");
        }
        return chatRepository.getMessages(roomId, limit);
    }
}


