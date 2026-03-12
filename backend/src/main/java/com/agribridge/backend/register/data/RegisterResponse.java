package com.agribridge.backend.register.data;

public class RegisterResponse {
    private boolean success;
    private String message;
    private String fullName;
    private String email;

    public RegisterResponse(boolean success, String message, String fullName, String email) {
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