package com.techelevator.model.dto;

public class BeerResponseDto {
    private int beerId;
    private String beerName;
    private int breweryId;
    private String breweryName;
    private String styleName;
    private int style;
    private String desc;
    private double abv;
    private boolean isSeasonal;
    private String seasonName;
    private String imgUrl;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public BeerResponseDto() { }

    public BeerResponseDto(int beerId, String beerName, int breweryId, String breweryName, String styleName, int style, String desc, double abv, boolean isSeasonal, String seasonName, String imgUrl) {
        this.beerId = beerId;
        this.beerName = beerName;
        this.breweryId = breweryId;
        this.breweryName = breweryName;
        this.styleName = styleName;
        this.style = style;
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

    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }


    public int getBreweryId() {
        return breweryId;
    }

    public void setBreweryId(int breweryId) {
        this.breweryId = breweryId;
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
}
