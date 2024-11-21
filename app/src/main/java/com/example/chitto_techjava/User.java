package com.example.chitto_techjava;

public class User {
    private String name;
    private String email;
    private String phone;
    private long timestamp;

    // Default constructor for Firebase
    public User() {
    }

    // Constructor with parameters
    public User(String name, String email, String phone, long timestamp) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.timestamp = timestamp;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
