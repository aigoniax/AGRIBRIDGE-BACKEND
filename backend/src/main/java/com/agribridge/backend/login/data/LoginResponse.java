package com.agribridge.backend.login.data;

public class LoginResponse {
    private boolean success;
    private String message;
    private String fullName;
    private String email;

    public LoginResponse(boolean success, String message, String fullName, String email) {
        this.success = success;
        this.message = message;
        this.fullName = fullName;
        this.email = email;
    }

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
}