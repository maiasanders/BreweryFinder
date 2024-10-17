package com.techelevator.model;

public class Beer {
    private int beerId;
    private String beerName;
    private int breweryId;
    private String breweryName;
    private int styleId;
    private String styleName;
    private String desc;
    private double abv;
    private boolean isSeasonal;
    private String seasonName;
    private String imgUrl;

    public Beer() {}

    public Beer(int beerId, String beerName, int breweryId, String breweryName, int styleId, String styleName, String desc, double abv, String seasonName, String imgUrl) {
        this.beerId = beerId;
        this.beerName = beerName;
        this.breweryId = breweryId;
        this.breweryName = breweryName;
        this.styleId = styleId;
        this.styleName = styleName;
        this.desc = desc;
        this.abv = abv;
        this.imgUrl = imgUrl;
        this.isSeasonal = true;
        this.seasonName = seasonName;
    }

    public Beer(String beerName, int breweryId, String breweryName, int styleId, String styleName, String desc, double abv, boolean isSeasonal, String imgUrl) {
        this.beerName = beerName;
        this.breweryId = breweryId;
        this.breweryName = breweryName;
        this.styleId = styleId;
        this.styleName = styleName;
        this.desc = desc;
        this.abv = abv;
        this.isSeasonal = isSeasonal;
        this.imgUrl = imgUrl;
    }

    public Beer(int beerId, String beerName, int breweryId, String breweryName, int styleId, String styleName, String desc, double abv, boolean isSeasonal, String imgUrl) {
        this.beerId = beerId;
        this.beerName = beerName;
        this.breweryId = breweryId;
        this.breweryName = breweryName;
        this.styleId = styleId;
        this.styleName = styleName;
        this.desc = desc;
        this.abv = abv;
        this.isSeasonal = isSeasonal;
        this.imgUrl = imgUrl;
    }

    public Beer(int beerId, String beerName, int breweryId, String breweryName, int styleId, String styleName, String desc, double abv, boolean isSeasonal, String seasonName, String imgUrl) {
        this.beerId = beerId;
        this.beerName = beerName;
        this.breweryId = breweryId;
        this.breweryName = breweryName;
        this.styleId = styleId;
        this.styleName = styleName;
        this.desc = desc;
        this.abv = abv;
        this.isSeasonal = isSeasonal;
        this.seasonName = seasonName;
        this.imgUrl = imgUrl;
    }

    public String getBreweryName() {
        return breweryName;
    }

    public void setBreweryName(String breweryName) {
        this.breweryName = breweryName;
    }

    public String getStyleName() {
        return styleName;
    }

    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }

    public int getBeerId() {
        return beerId;
    }

    public void setBeerId(int beerId) {
        this.beerId = beerId;
    }

    public String getBeerName() {
        return beerName;
    }

    public void setBeerName(String beerName) {
        this.beerName = beerName;
    }

    public int getBreweryId() {
        return breweryId;
    }

    public void setBreweryId(int brewery) {
        this.breweryId = brewery;
    }

    public int getStyleId() {
        return styleId;
    }

    public void setStyleId(int styleId) {
        this.styleId = styleId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public double getAbv() {
        return abv;
    }

    public void setAbv(double abv) {
        this.abv = abv;
    }

    public boolean isSeasonal() {
        return isSeasonal;
    }

    public void setSeasonal(boolean seasonal) {
        isSeasonal = seasonal;
    }

    public String getSeasonName() {
        return seasonName;
    }

    public void setSeasonName(String seasonName) {
        this.seasonName = seasonName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
