package com.agribridge.backend.order.service;

import com.agribridge.backend.JwtUtil;
import com.agribridge.backend.model.Listing;
import com.agribridge.backend.model.Order;
import com.agribridge.backend.model.User;
import com.agribridge.backend.order.data.OrderRequest;
import com.agribridge.backend.order.data.OrderResponse;
import com.agribridge.backend.repository.ListingRepository;
import com.agribridge.backend.repository.OrderRepository;
import com.agribridge.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ListingRepository listingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private String toBase64(byte[] photo) {
        if (photo == null) return null;
        return "data:image/png;base64," + Base64.getEncoder().encodeToString(photo);
    }

    private void enrichOrder(Order order) {
        // Attach listing info
        listingRepository.findById(order.getListingId()).ifPresent(listing -> {
            order.setProduceName(listing.getProduceName());
            order.setCategory(listing.getCategory());
            order.setUnit(listing.getUnit());
            if (listing.getPhoto() != null) {
                order.setPhotoBase64(toBase64(listing.getPhoto()));
            }
            // Attach farmer info
            userRepository.findById(listing.getFarmerId()).ifPresent(farmer -> {
                order.setFarmerName(farmer.getFullName());
                order.setFarmerPhone(farmer.getPhone());
            });
        });
        // Attach buyer info
        userRepository.findById(order.getBuyerId()).ifPresent(buyer -> {
            order.setBuyerName(buyer.getFullName());
            order.setBuyerPhone(buyer.getPhone());
        });
    }

    // Generate unique order number
    private String generateOrderNumber() {
        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        return "AGB-" + timestamp;
    }

    // Place a rescue order
    public OrderResponse placeOrder(String token, OrderRequest request) {
        String email = jwtUtil.extractEmail(token);
        User buyer = userRepository.findByEmail(email).orElse(null);

        if (buyer == null || !"BUYER".equalsIgnoreCase(buyer.getRole())) {
            return new OrderResponse(false, "Only buyers can place orders", null);
        }

        Listing listing = listingRepository.findById(request.getListingId()).orElse(null);
        if (listing == null || listing.getDeletedAt() != null) {
            return new OrderResponse(false, "Listing not found", null);
        }

        if (!"AVAILABLE".equalsIgnoreCase(listing.getStatus())) {
            return new OrderResponse(false, "This listing is no longer available", null);
        }

        if (request.getQuantity() > listing.getQuantity()) {
            return new OrderResponse(false,
                    "Requested quantity exceeds available stock (" +
                            listing.getQuantity() + " " + listing.getUnit() + ")", null);
        }

        boolean hasPending = orderRepository.existsPendingOrder(
                listing.getId(), buyer.getId());
        if (hasPending) {
            return new OrderResponse(false,
                    "You already have a pending order for this listing", null);
        }

        Order order = new Order();
        order.setOrderNumber(generateOrderNumber());
        order.setListingId(listing.getId());
        order.setBuyerId(buyer.getId());
        order.setQuantity(request.getQuantity());
        order.setPricePerUnit(listing.getPrice());
        order.setTotalPrice(request.getQuantity() * listing.getPrice());
        order.setStatus("PENDING");
        order.setPickupLocation(listing.getPickupLocation());
        order.setBuyerNotes(request.getBuyerNotes());

        orderRepository.save(order);
        enrichOrder(order);

        return new OrderResponse(true, "Rescue order placed successfully!", order);
    }

    // Get all orders for authenticated user
    public OrderResponse getMyOrders(String token) {
        String email = jwtUtil.extractEmail(token);
        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            return new OrderResponse(false, "User not found", null);
        }

        List<Order> orders;
        if ("BUYER".equalsIgnoreCase(user.getRole())) {
            orders = orderRepository.findByBuyerIdOrderByCreatedAtDesc(user.getId());
        } else {
            orders = orderRepository.findByFarmerId(user.getId());
        }

        orders.forEach(this::enrichOrder);
        return new OrderResponse(true, "Orders fetched successfully", orders);
    }

    // Update order status
    public OrderResponse updateOrderStatus(String token, Long orderId, String status) {
        String email = jwtUtil.extractEmail(token);
        User user = userRepository.findByEmail(email).orElse(null);
        Order order = orderRepository.findById(orderId).orElse(null);

        if (order == null) {
            return new OrderResponse(false, "Order not found", null);
        }

        if (user == null) {
            return new OrderResponse(false, "Unauthorized", null);
        }

        order.setStatus(status);

        if ("COMPLETED".equalsIgnoreCase(status)) {
            order.setCompletedAt(LocalDateTime.now());
        } else if ("CANCELLED".equalsIgnoreCase(status)) {
            order.setCancelledAt(LocalDateTime.now());
            order.setCancelledBy(user.getRole());
        }

        orderRepository.save(order);
        enrichOrder(order);
        return new OrderResponse(true, "Order status updated successfully", order);
    }
}