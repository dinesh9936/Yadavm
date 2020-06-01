package com.example.yadavm.Models;

public class SuggestMo {
    private String suggest;
    private String phone;
    private String name;

    public SuggestMo() {

    }

    public SuggestMo(String suggest, String phone, String name) {
        this.suggest = suggest;
        this.phone = phone;
        this.name = name;
    }

    public String getSuggest() {
        return suggest;
    }

    public void setSuggest(String suggest) {
        this.suggest = suggest;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
