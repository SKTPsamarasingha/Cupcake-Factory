package com.example.cupcake.Model;

public class User {
    private final int userID;

    private final String username;
    private final int phone;

    private final String address;
    private final String password;

    public User(int userID, String username, int phone, String address, String password) {
        this.userID = userID;
        this.username = username;
        this.phone = phone;
        this.address = address;
        this.password = password;
    }

    public int getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public int getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getPassword() {
        return password;
    }
}
