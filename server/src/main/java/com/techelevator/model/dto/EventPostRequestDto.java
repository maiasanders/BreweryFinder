package com.techelevator.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.format.annotation.DateTimeFormat;

public class EventPostRequestDto {

    @NotBlank(message = "field eventName can not be blank")
    private String eventName;
    @Min(value = 0, message = "field breweryId can not be blank")
    private int breweryId;
    @NotBlank(message = "field eventDate can not be blank")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private String eventDate;
    @NotBlank(message = "field begins can not be blank")
    @Pattern(regexp = "[0-1][0-9]:[0-5][0-9] [a|A|p|P]M")
    private String begins;
    @Pattern(regexp = "[0-1][0-9]:[0-5][0-9] [a|A|p|P]M")
    private String ends;
    @NotBlank(message = "field desc can not be blank")
    private String desc;
    @JsonProperty("over21")
    private boolean is21Up;
    private String[] categories;
    private String imgUrl;

    public EventPostRequestDto() { }

    public EventPostRequestDto(String eventName, int breweryId, String eventDate, String begins, String ends, String desc, boolean is21Up, String[] categories, String imgUrl) {
        this.eventName = eventName;
        this.breweryId = breweryId;
        this.eventDate = eventDate;
        this.begins = begins;
        this.ends = ends;
        this.desc = desc;
        this.is21Up = is21Up;
        this.categories = categories;
        this.imgUrl = imgUrl;
    }


    public String getEventName() {
        return eventName;
    }

    public int getBreweryId() {
        return breweryId;
    }

    public void setBreweryId(int breweryId) {
        this.breweryId = breweryId;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getBegins() {
        return begins;
    }

    public void setBegins(String begins) {
        this.begins = begins;
    }

    public String getEnds() {
        return ends;
    }

    public void setEnds(String ends) {
        this.ends = ends;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isIs21Up() {
        return is21Up;
    }

    public void setIs21Up(boolean is21Up) {
        this.is21Up = is21Up;
    }

    public String[] getCategories() {
        return categories;
    }

    public void setCategories(String[] categories) {
        this.categories = categories;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
