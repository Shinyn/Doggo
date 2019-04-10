package com.l.doggo;

public class UserAccount {

    private String userName;
    private double longitude;
    private double latitude;
    private String description;
    private int phoneNumber;

    public UserAccount() {
        this("", 0, 0, "", 0);
    }

    public UserAccount(String userName, double longitude, double latitude, String description, int phoneNumber) {
        this.userName = userName;
        this.longitude = longitude;
        this.latitude = latitude;
        this.description = description;
        this.phoneNumber = phoneNumber;
    }

    public void displayPosition() {

    }

}