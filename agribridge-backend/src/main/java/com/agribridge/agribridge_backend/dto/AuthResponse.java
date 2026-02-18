package com.agribridge.agribridge_backend.dto;

import com.agribridge.agribridge_backend.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private UserDTO user;
    private String token;
    private String message;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserDTO {
        private Long id;
        private String email;
        private String fullName;
        private User.Role role;
        private User.Status status;
        private String location;
        private String phone;
    }
}