package com.agribridge.backend.register.data;

public class RegisterRequest {
    private String fullName;
    private String email;
    private String password;
    private String confirmPassword;
    private String phone;
    private String location;
    private String role;

    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getConfirmPassword() { return confirmPassword; }
    public String getPhone() { return phone; }
    public String getLocation() { return location; }
    public String getRole() { return role; }

    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setLocation(String location) { this.location = location; }
    public void setRole(String role) { this.role = role; }
}