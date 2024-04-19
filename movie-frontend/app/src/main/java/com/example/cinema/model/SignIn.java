package com.example.cinema.model;

public class SignIn {
    private String phone;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public SignIn(String phone, String password) {
        this.phone = phone;
        this.password = password;
    }
}
