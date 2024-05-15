package com.example.cinema.model;

import java.time.LocalDate;
import java.util.Date;

public class Register {
    private int id;
    private String name;
    private String phone;
    private String email;
    private String gender;
    private String password;
    private Date birthday;

    public Register(String name, String phone, String email, String gender, String password, Date birthday) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
        this.password = password;
        this.birthday = birthday;
    }

    public Register(int id, String name, String phone, String email, String gender, String password, Date birthday) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
        this.password = password;
        this.birthday = birthday;
    }

    public Register(String name, String phone, String email, String gender, Date birthday) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
        this.birthday = birthday;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
