package com.agribridge.backend.repository;

import com.agribridge.backend.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    // Get all messages for an order sorted by sent time
    List<Message> findByOrderIdOrderBySentAtAsc(Long orderId);

    // Count unread messages for a user in an order
    @Query("SELECT COUNT(m) FROM Message m WHERE m.orderId = :orderId " +
            "AND m.senderId != :userId AND m.isRead = false")
    Long countUnreadMessages(@Param("orderId") Long orderId,
                             @Param("userId") Long userId);

    // Mark all messages in an order as read for a user
    @Modifying
    @Transactional
    @Query("UPDATE Message m SET m.isRead = true WHERE m.orderId = :orderId " +
            "AND m.senderId != :userId")
    void markAllAsRead(@Param("orderId") Long orderId,
                       @Param("userId") Long userId);

    // Count total unread messages for a user across all orders
    @Query("SELECT COUNT(m) FROM Message m WHERE m.senderId != :userId " +
            "AND m.isRead = false AND m.orderId IN " +
            "(SELECT o.id FROM Order o WHERE o.buyerId = :userId OR o.listingId IN " +
            "(SELECT l.id FROM Listing l WHERE l.farmerId = :userId))")
    Long countTotalUnread(@Param("userId") Long userId);
}