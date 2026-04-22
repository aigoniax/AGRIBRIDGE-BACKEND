package com.agribridge.backend;

import com.agribridge.backend.model.User;
import com.agribridge.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserProfileController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    private String getEmailFromHeader(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) return null;
        return jwtUtil.extractEmail(authHeader.substring(7));
    }

    // GET PROFILE
    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(
            @RequestHeader("Authorization") String authHeader) {

        String email = getEmailFromHeader(authHeader);
        if (email == null)
            return ResponseEntity.status(401).body(Map.of("message", "Unauthorized"));

        Optional<User> optUser = userRepository.findByEmail(email);
        if (optUser.isEmpty())
            return ResponseEntity.notFound().build();

        User u = optUser.get();

        String photoBase64 = null;
        if (u.getPhoto() != null) {
            photoBase64 = "data:image/png;base64," +
                    Base64.getEncoder().encodeToString(u.getPhoto());
        }

        Map<String, Object> response = new HashMap<>();
        response.put("fullName", u.getFullName() != null ? u.getFullName() : "");
        response.put("firstName", u.getFirstName() != null ? u.getFirstName() : "");
        response.put("lastName", u.getLastName() != null ? u.getLastName() : "");
        response.put("email", u.getEmail() != null ? u.getEmail() : "");
        response.put("phone", u.getPhone() != null ? u.getPhone() : "");
        response.put("location", u.getLocation() != null ? u.getLocation() : "");
        response.put("role", u.getRole() != null ? u.getRole() : "");
        response.put("photo", photoBase64);

        return ResponseEntity.ok(response);
    }

    // EDIT PROFILE
    @PutMapping("/profile")
    public ResponseEntity<?> editProfile(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody Map<String, String> body) {

        String email = getEmailFromHeader(authHeader);
        if (email == null)
            return ResponseEntity.status(401).body(Map.of("message", "Unauthorized"));

        Optional<User> optUser = userRepository.findByEmail(email);
        if (optUser.isEmpty())
            return ResponseEntity.notFound().build();

        userRepository.updateProfile(
                email,
                body.getOrDefault("firstName", optUser.get().getFirstName()),
                body.getOrDefault("lastName", optUser.get().getLastName()),
                body.getOrDefault("phone", optUser.get().getPhone()),
                body.getOrDefault("location", optUser.get().getLocation())
        );

        return ResponseEntity.ok(Map.of("message", "Profile updated successfully"));
    }

    // EDIT PASSWORD
    @PutMapping("/password")
    public ResponseEntity<?> editPassword(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody Map<String, String> body) {

        String email = getEmailFromHeader(authHeader);
        if (email == null)
            return ResponseEntity.status(401).body(Map.of("message", "Unauthorized"));

        Optional<User> optUser = userRepository.findByEmail(email);
        if (optUser.isEmpty())
            return ResponseEntity.notFound().build();

        User user = optUser.get();
        String currentPassword = body.get("currentPassword");
        String newPassword = body.get("newPassword");

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Current password is incorrect"));
        }

        String encodedPassword = passwordEncoder.encode(newPassword);
        userRepository.updatePassword(email, encodedPassword);
        return ResponseEntity.ok(Map.of("message", "Password updated successfully"));
    }

    // UPLOAD PHOTO
    @PostMapping("/photo")
    public ResponseEntity<?> uploadPhoto(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam("file") MultipartFile file) throws IOException {

        String email = getEmailFromHeader(authHeader);
        if (email == null)
            return ResponseEntity.status(401).body(Map.of("message", "Unauthorized"));

        String contentType = file.getContentType();
        if (contentType == null ||
                (!contentType.equals("image/jpeg") && !contentType.equals("image/png"))) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Only .jpg and .png files are allowed"));
        }

        Optional<User> optUser = userRepository.findByEmail(email);
        if (optUser.isEmpty())
            return ResponseEntity.notFound().build();

        userRepository.updatePhoto(email, file.getBytes());

        return ResponseEntity.ok(Map.of("message", "Photo uploaded successfully"));
    }
}