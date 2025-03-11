package com.example.chatapp.repository;

import com.example.chatapp.model.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class ChatRepository {

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    public ChatRepository(RedisTemplate<String, String> redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    // Create a new chat room
    public String createChatRoom(String roomName) {
        String roomKey = "chatroom:" + roomName;
        if (Boolean.TRUE.equals(redisTemplate.hasKey(roomKey))) {
            throw new RuntimeException("Chat room already exists!");
        }
        redisTemplate.opsForHash().put(roomKey, "name", roomName);
        return roomName;
    }

    // Add a participant to a chat room
    public String addParticipant(String roomId, String participant) {
        String roomKey = "chatroom:" + roomId;
        if (Boolean.FALSE.equals(redisTemplate.hasKey(roomKey))) {
            throw new RuntimeException("Chat room does not exist!");
        }
        redisTemplate.opsForSet().add(roomKey + ":participants", participant);
        return participant;
    }

    // Save a message in Redis List
    public void saveMessage(String roomId, Message message) {
        try {
            String messageJson = objectMapper.writeValueAsString(message);
            redisTemplate.opsForList().rightPush("chatroom:" + roomId + ":messages", messageJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error saving message: " + e.getMessage());
        }
    }

    // Retrieve the last N messages from Redis List
    public List<Message> getMessages(String roomId, int limit) {
        String key = "chatroom:" + roomId + ":messages";
        List<String> messages = redisTemplate.opsForList().range(key, -limit, -1);
        if (messages == null || messages.isEmpty()) {
            throw new RuntimeException("No messages found for room: " + roomId);
        }
        return messages.stream().map(msg -> {
            try {
                return objectMapper.readValue(msg, Message.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Error parsing message: " + e.getMessage());
            }
        }).collect(Collectors.toList());
    }
}

