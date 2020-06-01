package com.example.yadavm.Models;

public class UserMo {
    private String name;
    private String password;
    private String phone;
    private String address;
    private String profilepic;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public UserMo() {
    }

    public UserMo(String name, String password, String phone, String address, String profilepic) {
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.profilepic = profilepic;
    }
}