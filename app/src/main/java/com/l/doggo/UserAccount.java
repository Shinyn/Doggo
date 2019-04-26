package com.l.doggo;

public class UserAccount {

    private String userName;
    private String description;
    private String phoneNumber;
    private String imageUrl;

    public UserAccount() {}

    public UserAccount(String userName, String description, String phoneNumber, String imageUrl) {
        this.userName = userName;
        this.description = description;
        this.phoneNumber = phoneNumber;
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}