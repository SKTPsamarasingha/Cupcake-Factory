package com.example.cupcake.Model;

public class Order {

    private String username;
    private int orderID;
    private String orderDate;
    private String totalPrice;

    private String orderStatus;
    private String[][] orderData;

    public Order(String username, int orderID, String orderDate, String totalPrice, String orderStatus, String[][] orderData) {
        this.username = username;
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
        this.orderData = orderData;
    }

    public String getUsername() {
        return username;
    }

    public int getOrderID() {
        return orderID;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public String[][] getOrderData() {
        return orderData;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setOrderData(String[][] orderData) {
        this.orderData = orderData;
    }
}
