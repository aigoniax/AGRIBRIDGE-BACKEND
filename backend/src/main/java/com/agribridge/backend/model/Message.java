package com.agribridge.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @Column(name = "sender_id", nullable = false)
    private Long senderId;

    @Column(name = "message_text", nullable = false)
    private String messageText;

    @Column(name = "is_read")
    private Boolean isRead = false;

    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    // Transient fields
    @Transient private String senderName;
    @Transient private String senderRole;

    @PrePersist
    public void prePersist() {
        sentAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public Long getOrderId() { return orderId; }
    public Long getSenderId() { return senderId; }
    public String getMessageText() { return messageText; }
    public Boolean getIsRead() { return isRead; }
    public LocalDateTime getSentAt() { return sentAt; }
    public String getSenderName() { return senderName; }
    public String getSenderRole() { return senderRole; }

    public void setId(Long id) { this.id = id; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }
    public void setSenderId(Long senderId) { this.senderId = senderId; }
    public void setMessageText(String messageText) { this.messageText = messageText; }
    public void setIsRead(Boolean isRead) { this.isRead = isRead; }
    public void setSentAt(LocalDateTime sentAt) { this.sentAt = sentAt; }
    public void setSenderName(String senderName) { this.senderName = senderName; }
    public void setSenderRole(String senderRole) { this.senderRole = senderRole; }
}