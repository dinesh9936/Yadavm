package com.example.yadavm.Models;

public class CartMo {
    private String itemName;
    private String itemImage;
    private String itemId;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public CartMo() {
    }

    public CartMo(String itemName, String itemImage, String itemId) {
        this.itemName = itemName;
        this.itemImage = itemImage;
        this.itemId = itemId;
    }
}
