package com.l.doggo;

public class UserAccount {

    private String userName;
    private double longitude;
    private double latitude;
    private String description;
    private int phoneNumber;
    private String mail;

    public UserAccount() {
        this("", 0, 0, "", 0, "");
    }

    public UserAccount(String userName, double longitude, double latitude, String description, int phoneNumber, String mail) {
        this.userName = userName;
        this.longitude = longitude;
        this.latitude = latitude;
        this.description = description;
        this.phoneNumber = phoneNumber;
        this.mail = mail;
    }

    public void displayPosition() {

    }



    public String getUserName() {
        return userName;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getDescription() {
        return description;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public String getMail() {
        return mail;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}