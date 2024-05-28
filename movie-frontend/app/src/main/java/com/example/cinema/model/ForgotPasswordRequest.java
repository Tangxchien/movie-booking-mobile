package com.example.cinema.model;

public class ForgotPasswordRequest {
    private String phone;
    private String email;
    private String newPassword;

    public ForgotPasswordRequest() {
    }

    public ForgotPasswordRequest(String phone, String email, String newPassword) {
        this.phone = phone;
        this.email = email;
        this.newPassword = newPassword;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
