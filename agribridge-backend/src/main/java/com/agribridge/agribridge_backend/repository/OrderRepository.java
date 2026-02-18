package com.agribridge.agribridge_backend.repository;

import com.agribridge.agribridge_backend.model.Order;
import com.agribridge.agribridge_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // Find all orders by buyer
    List<Order> findByBuyer(User buyer);

    // Find all orders by farmer (through listing)
    List<Order> findByListingFarmer(User farmer);

    // Find order by order number
    Optional<Order> findByOrderNumber(String orderNumber);

    // Check if buyer already has pending order for listing
    boolean existsByBuyerAndListingIdAndStatus(
            User buyer,
            Long listingId,
            Order.OrderStatus status
    );

    // Find orders by status
    List<Order> findByStatus(Order.OrderStatus status);

    // Count orders by status (for admin stats)
    long countByStatus(Order.OrderStatus status);
}