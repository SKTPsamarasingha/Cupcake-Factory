package com.example.cupcake.Model;

public class Admin {
    private  final  int adminID;
    private  final String username;

    private  final  String password;

    public Admin(int adminID, String username, String password) {
        this.adminID = adminID;
        this.username = username;
        this.password = password;
    }

    public int getAdminID() {
        return adminID;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


}
