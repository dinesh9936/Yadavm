package com.example.yadavm.Models;

public class CartMo {
    private String itemType;
    private String itemName;
    private String itemImage;
    private String itemId;
    private String itemPricekg;
    private String itemPriceprkg;
    private String itemPricegm;
    private String itemPricepcs;
    private String itemPriceprpcs;
    private String itemPricetotal;
    private String itemQuantitykg;
    private String itemQuantitygm;
    private String itemQuantitypcs ;
    private String deliveryCharge;
    private String timestamp;

    public CartMo() {
    }

    public CartMo(String itemType, String itemName, String itemImage, String itemId, String itemPricekg, String itemPriceprkg, String itemPricegm, String itemPricepcs, String itemPriceprpcs, String itemPricetotal, String itemQuantitykg, String itemQuantitygm, String itemQuantitypcs, String deliveryCharge, String timestamp) {
        this.itemType = itemType;
        this.itemName = itemName;
        this.itemImage = itemImage;
        this.itemId = itemId;
        this.itemPricekg = itemPricekg;
        this.itemPriceprkg = itemPriceprkg;
        this.itemPricegm = itemPricegm;
        this.itemPricepcs = itemPricepcs;
        this.itemPriceprpcs = itemPriceprpcs;
        this.itemPricetotal = itemPricetotal;
        this.itemQuantitykg = itemQuantitykg;
        this.itemQuantitygm = itemQuantitygm;
        this.itemQuantitypcs = itemQuantitypcs;
        this.deliveryCharge = deliveryCharge;
        this.timestamp = timestamp;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

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

    public String getItemPricekg() {
        return itemPricekg;
    }

    public void setItemPricekg(String itemPricekg) {
        this.itemPricekg = itemPricekg;
    }

    public String getItemPriceprkg() {
        return itemPriceprkg;
    }

    public void setItemPriceprkg(String itemPriceprkg) {
        this.itemPriceprkg = itemPriceprkg;
    }

    public String getItemPricegm() {
        return itemPricegm;
    }

    public void setItemPricegm(String itemPricegm) {
        this.itemPricegm = itemPricegm;
    }

    public String getItemPricepcs() {
        return itemPricepcs;
    }

    public void setItemPricepcs(String itemPricepcs) {
        this.itemPricepcs = itemPricepcs;
    }

    public String getItemPriceprpcs() {
        return itemPriceprpcs;
    }

    public void setItemPriceprpcs(String itemPriceprpcs) {
        this.itemPriceprpcs = itemPriceprpcs;
    }

    public String getItemPricetotal() {
        return itemPricetotal;
    }

    public void setItemPricetotal(String itemPricetotal) {
        this.itemPricetotal = itemPricetotal;
    }

    public String getItemQuantitykg() {
        return itemQuantitykg;
    }

    public void setItemQuantitykg(String itemQuantitykg) {
        this.itemQuantitykg = itemQuantitykg;
    }

    public String getItemQuantitygm() {
        return itemQuantitygm;
    }

    public void setItemQuantitygm(String itemQuantitygm) {
        this.itemQuantitygm = itemQuantitygm;
    }

    public String getItemQuantitypcs() {
        return itemQuantitypcs;
    }

    public void setItemQuantitypcs(String itemQuantitypcs) {
        this.itemQuantitypcs = itemQuantitypcs;
    }

    public String getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(String deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
