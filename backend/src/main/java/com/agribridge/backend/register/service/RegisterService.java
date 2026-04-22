package com.agribridge.backend.register.service;

import com.agribridge.backend.EmailService;
import com.agribridge.backend.register.data.RegisterRequest;
import com.agribridge.backend.register.data.RegisterResponse;
import com.agribridge.backend.model.User;
import com.agribridge.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    public RegisterResponse register(RegisterRequest request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            return new RegisterResponse(false, "Passwords do not match", null, null);
        }

        // Password strength validation
        String pwd = request.getPassword();
        if (pwd.length() < 8) {
            return new RegisterResponse(false, "Password must be at least 8 characters", null, null);
        }
        if (!pwd.matches(".*[A-Z].*")) {
            return new RegisterResponse(false, "Password must contain at least one uppercase letter", null, null);
        }
        if (!pwd.matches(".*[0-9].*")) {
            return new RegisterResponse(false, "Password must contain at least one number", null, null);
        }
        if (!pwd.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*")) {
            return new RegisterResponse(false, "Password must contain at least one special character", null, null);
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            return new RegisterResponse(false, "Email already exists", null, null);
        }

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhone(request.getPhone());
        user.setLocation(request.getLocation());
        user.setRole(request.getRole());

        if ("BUYER".equalsIgnoreCase(request.getRole())) {
            user.setStatus("APPROVED");
        } else {
            user.setStatus("PENDING");
        }

        userRepository.save(user);

        emailService.sendRegistrationPending(user.getEmail(), user.getFullName());

        return new RegisterResponse(true, "Registration successful", user.getFullName(), user.getEmail());
    }
}