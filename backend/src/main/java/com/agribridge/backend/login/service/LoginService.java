package com.agribridge.backend.login.service;

import com.agribridge.backend.JwtUtil;
import com.agribridge.backend.login.data.LoginRequest;
import com.agribridge.backend.login.data.LoginResponse;
import com.agribridge.backend.repository.User;
import com.agribridge.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElse(null);

        if (user == null) {
            return new LoginResponse(false, "Invalid email or password", null, null, null);
        }
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return new LoginResponse(false, "Invalid email or password", null, null, null);
        }

        String token = jwtUtil.generateToken(user.getEmail());
        return new LoginResponse(true, "Login successful", user.getFullName(), user.getEmail(), token);
    }
}