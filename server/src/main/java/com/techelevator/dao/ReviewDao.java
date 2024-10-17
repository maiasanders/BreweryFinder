package com.techelevator.dao;

import com.techelevator.model.Review;

import java.util.List;

public interface ReviewDao {
    Review getReviewById(int id);
    List<Review> getReviewsByBeerId(int id);
    Review createReview(Review review);

    List<Review> getReviewsByBeerIdRecommended(int id, boolean recommended);

    List<Review> getReviewsByBeerIdMinRating(int id, int minRating);

    List<Review> getReviewsByBeerMaxRating(int id, int maxRating);

    List<Review> getReviewsByBeerMaxRatingRecommended(int id, int maxRating, boolean recommended);

    List<Review> getReviewsByBeerMinRatingRecommended(int id, int minRating, boolean recommended);

    List<Review> getReviewsByBeerRatingRange(int id, int minRating, int maxRating);

    List<Review> getReviewsByBeerRatingRangeRecommended(int id, int minRating, int maxRating, boolean recommended);
}
