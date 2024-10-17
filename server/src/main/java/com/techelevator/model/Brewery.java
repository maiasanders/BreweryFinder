package com.techelevator.model;

public class Brewery {
    private int breweryId;
    private String breweryName;
    private int brewerId;
    private String brewerName;
    private String address;
    private String city;
    private String state;
    private String zip;
    private String aboutUs;
    private boolean servesFood;
    private boolean hasFoodTrucks;
    private boolean isKidFriendly;
    private boolean isDogFriendly;
    private String daysOpen;
    private String openTime;
    private String closeTime;
    private String website;
    private String picUrl;

    public Brewery() {}

    public Brewery(int breweryId, String breweryName, int brewerId, String brewerName, String address, String city, String state, String zip, String aboutUs, boolean servesFood, boolean hasFoodTrucks, boolean isKidFriendly, boolean isDogFriendly, String daysOpen, String openTime, String closeTime, String website, String picUrl) {
        this.breweryId = breweryId;
        this.breweryName = breweryName;
        this.brewerId = brewerId;
        this.brewerName = brewerName;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.aboutUs = aboutUs;
        this.servesFood = servesFood;
        this.hasFoodTrucks = hasFoodTrucks;
        this.isKidFriendly = isKidFriendly;
        this.isDogFriendly = isDogFriendly;
        this.daysOpen = daysOpen;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.website = website;
        this.picUrl = picUrl;
    }

    public int getBreweryId() {
        return breweryId;
    }

    public void setBreweryId(int breweryId) {
        this.breweryId = breweryId;
    }

    public int getBrewerId() {
        return brewerId;
    }

    public void setBrewerId(int brewerId) {
        this.brewerId = brewerId;
    }

    public String getBrewerName() {
        return brewerName;
    }

    public void setBrewerName(String brewerName) {
        this.brewerName = brewerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getAboutUs() {
        return aboutUs;
    }

    public void setAboutUs(String aboutUs) {
        this.aboutUs = aboutUs;
    }

    public boolean isServesFood() {
        return servesFood;
    }

    public void setServesFood(boolean servesFood) {
        this.servesFood = servesFood;
    }

    public boolean isHasFoodTrucks() {
        return hasFoodTrucks;
    }

    public void setHasFoodTrucks(boolean hasFoodTrucks) {
        this.hasFoodTrucks = hasFoodTrucks;
    }

    public boolean isKidFriendly() {
        return isKidFriendly;
    }

    public void setKidFriendly(boolean kidFriendly) {
        isKidFriendly = kidFriendly;
    }

    public boolean isDogFriendly() {
        return isDogFriendly;
    }

    public void setDogFriendly(boolean dogFriendly) {
        isDogFriendly = dogFriendly;
    }

    public String getDaysOpen() {
        return daysOpen;
    }

    public void setDaysOpen(String daysOpen) {
        this.daysOpen = daysOpen;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public String getWebsite() {
        return website;
    }

    public String getBreweryName() {
        return breweryName;
    }

    public void setBreweryName(String breweryName) {
        this.breweryName = breweryName;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}
