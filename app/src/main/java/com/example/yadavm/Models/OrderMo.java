package com.example.yadavm.Models;

public class OrderMo {
    private String orderid;
    private String ordertime;
    private String orderdate;
    private String orderstatus;
    private String orderitems;

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
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

    public String getOrderitems() {
        return orderitems;
    }

    public void setOrderitems(String orderitems) {
        this.orderitems = orderitems;
    }

    public OrderMo(String orderid, String ordertime, String orderdate, String orderstatus, String orderitems) {
        this.orderid = orderid;
        this.ordertime = ordertime;
        this.orderdate = orderdate;
        this.orderstatus = orderstatus;
        this.orderitems = orderitems;
    }

    public OrderMo() {
    }
}
