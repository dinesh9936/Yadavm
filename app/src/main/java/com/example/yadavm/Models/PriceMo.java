package com.example.yadavm.Models;

public class PriceMo {
    private String itemPricetotal;
    private String deliveryCharge;

    public PriceMo() {
    }

    public PriceMo(String itemPricetotal, String deliveryCharge) {
        this.itemPricetotal = itemPricetotal;
        this.deliveryCharge = deliveryCharge;
    }

    public String getItemPricetotal() {
        return itemPricetotal;
    }

    public void setItemPricetotal(String itemPricetotal) {
        this.itemPricetotal = itemPricetotal;
    }

    public String getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(String deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }
}
