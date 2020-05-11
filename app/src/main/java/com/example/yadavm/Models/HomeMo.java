package com.example.yadavm.Models;

public class HomeMo {
    private String itemImage;
    private  String itemName;
    private  String itemPricekg;
    private  String itemPricepcs;

    public HomeMo() {
    }

    public HomeMo(String itemImage, String itemName, String itemPricekg, String itemPricepcs) {
        this.itemImage = itemImage;
        this.itemName = itemName;
        this.itemPricekg = itemPricekg;
        this.itemPricepcs = itemPricepcs;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPricekg() {
        return itemPricekg;
    }

    public void setItemPricekg(String itemPricekg) {
        this.itemPricekg = itemPricekg;
    }

    public String getItemPricepcs() {
        return itemPricepcs;
    }

    public void setItemPricepcs(String itemPricepcs) {
        this.itemPricepcs = itemPricepcs;
    }
}

