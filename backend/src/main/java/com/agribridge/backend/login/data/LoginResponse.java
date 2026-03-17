package com.agribridge.backend.login.data;

public class LoginResponse {
    private boolean success;
    private String message;
    private String fullName;
    private String email;
    private String token;

    public LoginResponse(boolean success, String message, String fullName, String email, String token) {
        this.success = success;
        this.message = message;
        this.fullName = fullName;
        this.email = email;
        this.token = token;
    }

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getToken() { return token; }
}