package com.techelevator.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReviewResponseDto {
    @JsonProperty("id")
    private int reviewId;
//    private int beerId;
    @JsonProperty("user")
    private String username;
    private String reviewText;
    private int rating;
    @JsonProperty("recommended")
    private boolean isRecommended;

    public ReviewResponseDto() {
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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
        return isRecommended;
    }

    public void setRecommended(boolean recommended) {
        isRecommended = recommended;
    }

    public ReviewResponseDto(int reviewId, String username, String reviewText, int rating, boolean isRecommended) {
        this.reviewId = reviewId;
        this.username = username;
        this.reviewText = reviewText;
        this.rating = rating;
        this.isRecommended = isRecommended;
    }
}
