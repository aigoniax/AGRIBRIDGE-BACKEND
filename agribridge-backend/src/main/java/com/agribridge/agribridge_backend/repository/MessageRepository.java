package com.agribridge.agribridge_backend.repository;

import com.agribridge.agribridge_backend.model.Message;
import com.agribridge.agribridge_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    // Get all messages for an order
    List<Message> findByOrderIdOrderBySentAtAsc(Long orderId);

    // Count unread messages for a user
    long countByOrderBuyerAndIsReadFalse(User buyer);
    long countByOrderListingFarmerAndIsReadFalse(User farmer);
}