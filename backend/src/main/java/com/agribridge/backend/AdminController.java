package com.agribridge.backend;

import com.agribridge.backend.model.Listing;
import com.agribridge.backend.model.User;
import com.agribridge.backend.repository.ListingRepository;
import com.agribridge.backend.repository.OrderRepository;
import com.agribridge.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ListingRepository listingRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private EmailService emailService;

    // GET all pending farmers
    @GetMapping("/pending-farmers")
    public ResponseEntity<?> getPendingFarmers() {
        List<User> pendingFarmers = userRepository.findByRoleAndStatus("FARMER", "PENDING");
        return ResponseEntity.ok(pendingFarmers);
    }

    // PUT approve a farmer
    @PutMapping("/approve/{id}")
    public ResponseEntity<?> approveFarmer(@PathVariable Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "User not found");
            return ResponseEntity.status(404).body(error);
        }
        user.setStatus("APPROVED");
        userRepository.save(user);
        String email = user.getEmail();
        String name = user.getFullName();
        new Thread(() -> emailService.sendRegistrationApproved(email, name)).start();
        Map<String, String> response = new HashMap<>();
        response.put("message", "Farmer approved successfully");
        return ResponseEntity.ok(response);
    }

    // PUT reject a farmer
    @PutMapping("/reject/{id}")
    public ResponseEntity<?> rejectFarmer(@PathVariable Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "User not found");
            return ResponseEntity.status(404).body(error);
        }
        user.setStatus("REJECTED");
        userRepository.save(user);
        String email = user.getEmail();
        String name = user.getFullName();
        new Thread(() -> emailService.sendRegistrationRejected(email, name)).start();
        Map<String, String> response = new HashMap<>();
        response.put("message", "Farmer rejected successfully");
        return ResponseEntity.ok(response);
    }

    // GET platform statistics
    @GetMapping("/statistics")
    public ResponseEntity<?> getStatistics() {
        long totalFarmers = userRepository.findByRoleAndStatus("FARMER", "APPROVED").size();
        long pendingFarmers = userRepository.findByRoleAndStatus("FARMER", "PENDING").size();
        long totalBuyers = userRepository.countByRole("BUYER");
        long totalListings = listingRepository.findAllActive().size();
        long totalOrders = orderRepository.count();

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalFarmers", totalFarmers);
        stats.put("pendingFarmers", pendingFarmers);
        stats.put("totalBuyers", totalBuyers);
        stats.put("totalListings", totalListings);
        stats.put("totalOrders", totalOrders);

        return ResponseEntity.ok(stats);
    }

    // GET all listings for moderation
    @GetMapping("/listings")
    public ResponseEntity<?> getAllListings() {
        List<Listing> listings = listingRepository.findAllForAdmin();
        return ResponseEntity.ok(listings);
    }

    // DELETE a listing (admin moderation)
    @DeleteMapping("/listings/{id}")
    public ResponseEntity<?> removeListing(@PathVariable Long id) {
        Listing listing = listingRepository.findById(id).orElse(null);
        if (listing == null) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Listing not found");
            return ResponseEntity.status(404).body(error);
        }
        listing.setDeletedAt(LocalDateTime.now());
        listingRepository.save(listing);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Listing removed successfully");
        return ResponseEntity.ok(response);
    }
}