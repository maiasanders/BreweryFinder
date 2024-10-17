package com.techelevator.model;

public class Review {
    private int reviewId;
    private int beerId;
    private int userId;
    private String username;
    private String reviewText;
    private int rating;
    private boolean isRecommended;

    public Review() {}

    public Review(int reviewId, int beerId, int userId, String username, String reviewText, int rating, boolean isRecommended) {
        this.reviewId = reviewId;
        this.beerId = beerId;
        this.userId = userId;
        this.username = username;
        this.reviewText = reviewText;
        this.rating = rating;
        this.isRecommended = isRecommended;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public int getBeerId() {
        return beerId;
    }

    public void setBeerId(int beerId) {
        this.beerId = beerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isRecommended() {
        return isRecommended;
    }

    public void setRecommended(boolean recommended) {
        isRecommended = recommended;
    }
}
