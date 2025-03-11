package com.example.chatapp.controller;

import com.example.chatapp.model.Message;
import com.example.chatapp.service.ChatService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chatapp")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/chatrooms")
    public String createChatRoom(@RequestParam String roomName) {
        return chatService.createChatRoom(roomName);
    }

    @PostMapping("/chatrooms/{roomId}/join")
    public String joinChatRoom(@PathVariable String roomId, @RequestParam String participant) {
        return chatService.joinChatRoom(roomId, participant);
    }

    @PostMapping("/chatrooms/{roomId}/messages")
    public String sendMessage(@PathVariable String roomId, @RequestParam String participant, @RequestParam String message) {
        return chatService.sendMessage(roomId, participant, message);
    }

    @GetMapping("/chatrooms/{roomId}/messages")
    public List<Message> getChatHistory(@PathVariable String roomId, @RequestParam(defaultValue = "10") int limit) {
        return chatService.getChatHistory(roomId, limit);
    }
}
