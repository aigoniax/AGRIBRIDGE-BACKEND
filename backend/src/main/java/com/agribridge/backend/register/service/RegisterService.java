package com.agribridge.backend.register.service;

import com.agribridge.backend.register.data.RegisterRequest;
import com.agribridge.backend.register.data.RegisterResponse;
import com.agribridge.backend.repository.User;
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

    public RegisterResponse register(RegisterRequest request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            return new RegisterResponse(false, "Passwords do not match", null, null);
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            return new RegisterResponse(false, "Email already exists", null, null);
        }

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhone(request.getPhone());
        user.setLocation(request.getLocation());
        user.setRole(request.getRole());
        userRepository.save(user);

        return new RegisterResponse(true, "Registration successful", user.getFullName(), user.getEmail());
    }
}