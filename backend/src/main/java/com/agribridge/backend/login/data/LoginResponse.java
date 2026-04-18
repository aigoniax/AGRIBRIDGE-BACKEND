package com.agribridge.backend.login.data;

public class LoginResponse {
    private boolean success;
    private String message;
    private Long id;
    private String fullName;
    private String email;
    private String token;
    private String role;

    public LoginResponse(boolean success, String message, Long id, String fullName, String email, String token, String role) {
        this.success = success;
        this.message = message;
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.token = token;
        this.role = role;
    }

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public Long getId() { return id; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getToken() { return token; }
    public String getRole() { return role; }
}