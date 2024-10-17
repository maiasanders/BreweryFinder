package com.techelevator.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;

public class ReviewRequestDto {
//    @Positive(message = "Beer Id must be positive and not blank")
//    private int beerId;
    private String reviewText;
    @Positive
    @Max(value = 5, message = "Rating must be between 1 and 5")
    private int rating;
    @JsonProperty
    private boolean recommended;

    public ReviewRequestDto() {}

    public ReviewRequestDto(/*int beerId,*/ String reviewText, int rating, boolean recommended) {
//        this.beerId = beerId;
        this.reviewText = reviewText;
        this.rating = rating;
        this.recommended = recommended;
    }

//    public int getBeerId() {
//        return beerId;
//    }
//
//    public void setBeerId(int beerId) {
//        this.beerId = beerId;
//    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public boolean isRecommended() {
        return this.recommended;
    }

    public void setRecommended(boolean recommended) {
        this.recommended = recommended;
    }
}
