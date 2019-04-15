package com.l.doggo;

public class UserAccount {

    private String userName;
    private String description;
    //private double longitude;
    //private double latitude;
    private int phoneNumber;

    public UserAccount() {
        this("", "", 0);
    }

    public UserAccount(String userName, String description, int phoneNumber) {
        this.userName = userName;
        this.description = description;
        this.phoneNumber = phoneNumber;
        //this.longitude = longitude;
        //this.latitude = latitude;
    }

    public void displayPosition() {

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