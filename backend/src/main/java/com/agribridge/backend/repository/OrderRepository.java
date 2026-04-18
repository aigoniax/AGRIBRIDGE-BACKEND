package com.agribridge.backend.repository;

import com.agribridge.backend.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // Get all orders for a buyer
    List<Order> findByBuyerIdOrderByCreatedAtDesc(Long buyerId);

    // Get all orders for a farmer (via listing)
    @Query("SELECT o FROM Order o WHERE o.listingId IN " +
            "(SELECT l.id FROM Listing l WHERE l.farmerId = :farmerId)")
    List<Order> findByFarmerId(@Param("farmerId") Long farmerId);

    // Check if buyer already has pending order for listing
    @Query("SELECT COUNT(o) > 0 FROM Order o WHERE o.listingId = :listingId " +
            "AND o.buyerId = :buyerId AND o.status = 'PENDING'")
    boolean existsPendingOrder(@Param("listingId") Long listingId,
                               @Param("buyerId") Long buyerId);
}