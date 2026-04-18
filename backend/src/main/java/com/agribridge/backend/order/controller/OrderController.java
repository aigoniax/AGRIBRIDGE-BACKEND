package com.agribridge.backend.order.controller;

import com.agribridge.backend.order.data.OrderRequest;
import com.agribridge.backend.order.data.OrderResponse;
import com.agribridge.backend.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // POST place a rescue order
    @PostMapping
    public ResponseEntity<?> placeOrder(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody OrderRequest request) {
        String token = authHeader.replace("Bearer ", "");
        return ResponseEntity.ok(orderService.placeOrder(token, request));
    }

    // GET all orders for authenticated user
    @GetMapping("/my-orders")
    public ResponseEntity<?> getMyOrders(
            @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        return ResponseEntity.ok(orderService.getMyOrders(token));
    }

    // PUT update order status
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateOrderStatus(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        String token = authHeader.replace("Bearer ", "");
        String status = body.get("status");
        return ResponseEntity.ok(orderService.updateOrderStatus(token, id, status));
    }
}