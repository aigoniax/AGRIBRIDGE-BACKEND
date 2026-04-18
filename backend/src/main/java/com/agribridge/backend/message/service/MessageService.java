package com.agribridge.backend.message.service;

import com.agribridge.backend.JwtUtil;
import com.agribridge.backend.message.data.MessageRequest;
import com.agribridge.backend.message.data.MessageResponse;
import com.agribridge.backend.model.Message;
import com.agribridge.backend.model.Order;
import com.agribridge.backend.model.User;
import com.agribridge.backend.repository.ListingRepository;
import com.agribridge.backend.repository.MessageRepository;
import com.agribridge.backend.repository.OrderRepository;
import com.agribridge.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ListingRepository listingRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private User getUserFromToken(String token) {
        String email = jwtUtil.extractEmail(token);
        return userRepository.findByEmail(email).orElse(null);
    }

    private boolean isInvolvedInOrder(User user, Order order) {
        if (user == null || order == null) return false;
        // Check if buyer
        if (order.getBuyerId().equals(user.getId())) return true;
        // Check if farmer
        return listingRepository.findById(order.getListingId())
                .map(listing -> listing.getFarmerId().equals(user.getId()))
                .orElse(false);
    }

    private void enrichMessage(Message message) {
        userRepository.findById(message.getSenderId()).ifPresent(sender -> {
            message.setSenderName(sender.getFullName());
            message.setSenderRole(sender.getRole());
        });
    }

    // Get all messages for an order
    public MessageResponse getMessages(String token, Long orderId) {
        User user = getUserFromToken(token);
        Order order = orderRepository.findById(orderId).orElse(null);

        if (order == null) {
            return new MessageResponse(false, "Order not found", null);
        }
        if (!isInvolvedInOrder(user, order)) {
            return new MessageResponse(false, "Unauthorized", null);
        }

        // Mark messages as read
        messageRepository.markAllAsRead(orderId, user.getId());

        List<Message> messages = messageRepository.findByOrderIdOrderBySentAtAsc(orderId);
        messages.forEach(this::enrichMessage);

        return new MessageResponse(true, "Messages fetched successfully", messages);
    }

    // Send a message
    public MessageResponse sendMessage(String token, Long orderId, MessageRequest request) {
        User user = getUserFromToken(token);
        Order order = orderRepository.findById(orderId).orElse(null);

        if (order == null) {
            return new MessageResponse(false, "Order not found", null);
        }
        if (!isInvolvedInOrder(user, order)) {
            return new MessageResponse(false, "Unauthorized", null);
        }
        if (request.getMessageText() == null || request.getMessageText().trim().isEmpty()) {
            return new MessageResponse(false, "Message cannot be empty", null);
        }
        if (request.getMessageText().length() > 500) {
            return new MessageResponse(false, "Message cannot exceed 500 characters", null);
        }

        Message message = new Message();
        message.setOrderId(orderId);
        message.setSenderId(user.getId());
        message.setMessageText(request.getMessageText().trim());
        message.setIsRead(false);

        messageRepository.save(message);
        enrichMessage(message);

        return new MessageResponse(true, "Message sent successfully", message);
    }

    // Get unread message count for a user
    public MessageResponse getUnreadCount(String token) {
        User user = getUserFromToken(token);
        if (user == null) {
            return new MessageResponse(false, "Unauthorized", null);
        }
        Long count = messageRepository.countTotalUnread(user.getId());
        return new MessageResponse(true, "Unread count fetched", count);
    }

    public MessageResponse getUnreadCountForOrder(String token, Long orderId) {
        User user = getUserFromToken(token);
        if (user == null) {
            return new MessageResponse(false, "Unauthorized", null);
        }
        Long count = messageRepository.countUnreadMessages(orderId, user.getId());
        return new MessageResponse(true, "Unread count fetched", count);
    }
}