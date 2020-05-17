package com.example.yadavm.Models;

public class OrderDetail {
    private String orderid;
    private String orderaddress;
    private String orderitems;
    private String ordertime;
    private String orderdate;
    private String orderstatus;

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getOrderaddress() {
        return orderaddress;
    }

    public void setOrderaddress(String orderaddress) {
        this.orderaddress = orderaddress;
    }

    public String getOrderitems() {
        return orderitems;
    }

    public void setOrderitems(String orderitems) {
        this.orderitems = orderitems;
    }

    public String getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(String ordertime) {
        this.ordertime = ordertime;
    }

    public String getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(String orderdate) {
        this.orderdate = orderdate;
    }

    public String getOrderstatus() {
        return orderstatus;
    }

    public void setOrderstatus(String orderstatus) {
        this.orderstatus = orderstatus;
    }

    public OrderDetail() {
    }

    public OrderDetail(String orderid, String orderaddress, String orderitems, String ordertime, String orderdate, String orderstatus) {
        this.orderid = orderid;
        this.orderaddress = orderaddress;
        this.orderitems = orderitems;
        this.ordertime = ordertime;
        this.orderdate = orderdate;
        this.orderstatus = orderstatus;
    }
}
