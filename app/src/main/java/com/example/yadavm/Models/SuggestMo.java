package com.example.yadavm.Models;

public class SuggestMo {
    private String suggest;
    private String profile;
    private String name;
    private String timestamp;
    private String phone;

    public SuggestMo() {
    }

    public SuggestMo(String suggest, String profile, String name, String timestamp, String phone) {
        this.suggest = suggest;
        this.profile = profile;
        this.name = name;
        this.timestamp = timestamp;
        this.phone = phone;
    }

    public String getSuggest() {
        return suggest;
    }

    public void setSuggest(String suggest) {
        this.suggest = suggest;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
