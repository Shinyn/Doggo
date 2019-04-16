package com.l.doggo;

public class UserAccount {

    private String userName;
    private String description;
    private int phoneNumber;


    public UserAccount(String userName, String description, int phoneNumber) {
        this.userName = userName;
        this.description = description;
        this.phoneNumber = phoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}