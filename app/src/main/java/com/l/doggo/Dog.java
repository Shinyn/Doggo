package com.l.doggo;

import java.io.Serializable;

public class Dog implements Serializable {

    private String name;
    private String breed;
    private String owner;
    private String imageUrl; // denna ska va null tills Uri fungerar
    private String phoneNumber;
    private int age;
    private int height;
    private int weight;
    private boolean neutered;
    private boolean male;


    public Dog() {}

    public Dog(String name, String breed, String owner, String imageUrl,
               String phoneNumber, int age, int height, int weight, boolean neutered, boolean male) {
        this.name = name;
        this.breed = breed;
        this.owner = owner;
        this.imageUrl = imageUrl;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.neutered = neutered;
        this.male = male;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public boolean isNeutered() {
        return neutered;
    }

    public void setNeutered(boolean neutered) {
        this.neutered = neutered;
    }

    public boolean isMale() {
        return male;
    }

    public void setMale(boolean male) {
        this.male = male;
    }
}
