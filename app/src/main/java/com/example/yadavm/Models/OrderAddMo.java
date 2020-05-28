package com.example.yadavm.Models;

public class OrderAddMo {
    private String orderAddress;

    public String getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress;
    }

    public OrderAddMo() {
    }

    public OrderAddMo(String orderAddress) {
        this.orderAddress = orderAddress;
    }
}
