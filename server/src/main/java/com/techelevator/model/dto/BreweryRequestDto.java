package com.techelevator.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public class BreweryRequestDto {
    @NotBlank(message = "Brewery name can not be blank")
    private String breweryName;
    @Positive(message = "User ID of brewer must be specified as a positive number")
    private int brewerId;
    @NotBlank(message = "Address can not be blank")
    private String address;
    @NotBlank(message = "City can not be blank")
    private String city;
    @NotBlank
    @Pattern(regexp = "^(AL|AK|AR|AZ|CA|CO|CT|DC|DE|FL|GA|HI|IA|ID|IL|IN|KS|KY|LA|MA|MD|ME|MI|MN|MO|MS|MT|NC|ND|NE|NH|NJ|NM|NV|NY|OH|OK|OR|PA|RI|SC|SD|TN|TX|UT|VA|VT|WA|WI|WV|WY)$",
            message = "State code is invalid")
    private String state;
    @NotBlank
    @Pattern(regexp = "^[0-9][0-9][0-9][0-9][0-9]$", message = "Zip is not a valid 5-digit code")
    private String zip;
    private String aboutUs;
    private boolean servesFood;
    private boolean hasFoodTrucks;
    private boolean isKidFriendly;
    private boolean isDogFriendly;
    private String daysOpen;
    @NotBlank (message = "openTime can not be blank")
    @Pattern(regexp = "^[0-2][0-9]:[0-5][0-9] (AM|PM)$", message = "Please enter a valid time with format \"hh:mm am\\pm\"")
    private String openTime;
    @NotBlank(message = "closeTime can not be blank")
    @Pattern(regexp = "^[0-2][0-9]:[0-5][0-9] (AM|PM)$", message = "Please enter a valid time with format \"hh:mm am\\pm\"")
    private String closeTime;

//    @URL(message = "Website must be a valid URL")
    @NotBlank(message = "website can not be blank")
    private String website;
    private String picUrl;

    public BreweryRequestDto() {}

    public BreweryRequestDto(String breweryName, int brewerId, String address, String city, String state, String zip, String aboutUs, boolean servesFood, boolean hasFoodTrucks, boolean isKidFriendly, boolean isDogFriendly, String daysOpen, String openTime, String closeTime, String website, String picUrl) {
        this.breweryName = breweryName;
        this.brewerId = brewerId;
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

    public String getBreweryName() {
        return breweryName;
    }

    public void setBreweryName(String breweryName) {
        this.breweryName = breweryName;
    }

    public int getBrewerId() {
        return brewerId;
    }

    public void setBrewerId(int brewerId) {
        this.brewerId = brewerId;
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
