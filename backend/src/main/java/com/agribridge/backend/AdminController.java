package com.agribridge.backend;

import com.agribridge.backend.repository.User;
import com.agribridge.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

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
        emailService.sendRegistrationApproved(user.getEmail(), user.getFullName());

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
        emailService.sendRegistrationRejected(user.getEmail(), user.getFullName());

        Map<String, String> response = new HashMap<>();
        response.put("message", "Farmer rejected successfully");
        return ResponseEntity.ok(response);
    }
}