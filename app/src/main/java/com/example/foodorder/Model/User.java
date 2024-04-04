package com.example.foodorder.Model;

public class User {
    private String email;
    private String orders;
    private String password;
    private String username;

    public User() {
    }

    public User(String email, String orders, String password, String username) {
        this.email = email;
        this.orders = orders;
        this.password = password;
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOrders() {
        return orders;
    }

    public void setOrders(String orders) {
        this.orders = orders;
    }


}
