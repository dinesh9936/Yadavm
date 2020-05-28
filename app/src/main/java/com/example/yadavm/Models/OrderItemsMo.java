package com.example.yadavm.Models;

public class OrderItemsMo {
    private String itemName;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public OrderItemsMo() {
    }

    public OrderItemsMo(String itemName) {
        this.itemName = itemName;
    }
}
