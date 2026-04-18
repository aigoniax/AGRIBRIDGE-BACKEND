package com.agribridge.backend.message.controller;

import com.agribridge.backend.message.data.MessageRequest;
import com.agribridge.backend.message.data.MessageResponse;
import com.agribridge.backend.message.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages")
@CrossOrigin(origins = "http://localhost:3000")
public class MessageController {

    @Autowired
    private MessageService messageService;

    // GET all messages for an order
    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getMessages(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long orderId) {
        String token = authHeader.replace("Bearer ", "");
        return ResponseEntity.ok(messageService.getMessages(token, orderId));
    }

    // POST send a message
    @PostMapping("/order/{orderId}")
    public ResponseEntity<?> sendMessage(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long orderId,
            @RequestBody MessageRequest request) {
        String token = authHeader.replace("Bearer ", "");
        return ResponseEntity.ok(messageService.sendMessage(token, orderId, request));
    }

    // GET unread message count
    @GetMapping("/unread-count")
    public ResponseEntity<?> getUnreadCount(
            @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        return ResponseEntity.ok(messageService.getUnreadCount(token));
    }

    // GET unread count for a specific order
    @GetMapping("/order/{orderId}/unread-count")
    public ResponseEntity<?> getUnreadCountForOrder(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long orderId) {
        String token = authHeader.replace("Bearer ", "");
        return ResponseEntity.ok(messageService.getUnreadCountForOrder(token, orderId));
    }
}