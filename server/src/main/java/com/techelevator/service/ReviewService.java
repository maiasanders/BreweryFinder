package com.techelevator.service;

import com.techelevator.dao.BeerDao;
import com.techelevator.dao.BreweryDao;
import com.techelevator.dao.ReviewDao;
import com.techelevator.dao.UserDao;
import com.techelevator.exception.UnauthorizedUserException;
import com.techelevator.model.Beer;
import com.techelevator.model.Review;
import com.techelevator.model.User;
import com.techelevator.model.dto.ReviewRequestDto;
import com.techelevator.model.dto.ReviewResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReviewService {

    private ReviewDao reviewDao;
    private UserDao userDao;
    private BeerDao beerDao;
    private BreweryDao breweryDao;

    public ReviewService(ReviewDao reviewDao, UserDao userDao, BeerDao beerDao, BreweryDao breweryDao) {
        this.reviewDao = reviewDao;
        this.userDao = userDao;
        this.beerDao = beerDao;
        this.breweryDao = breweryDao;
    }

    public ReviewResponseDto add(int id, ReviewRequestDto dto, String name) {

        if (checkIsNotBrewer(id, name)) {
            Review review = new Review();
            review.setBeerId(id);
            review.setUserId(userDao.getUserByUsername(name).getId());
            review.setReviewText(dto.getReviewText());
            review.setRating(dto.getRating());
            review.setRecommended(dto.isRecommended());
            return convertToDto( reviewDao.createReview(review) );
        } else {
            throw new UnauthorizedUserException();
        }
    }

    public List<ReviewResponseDto> list(int id, Integer minRating, Integer maxRating, Boolean recommended) {
        if (minRating == null && maxRating == null && recommended == null) {
            return convertListToResponseDto(reviewDao.getReviewsByBeerId(id));
        } else if (minRating == null && maxRating == null) {
            return convertListToResponseDto( reviewDao.getReviewsByBeerIdRecommended(id, recommended) );
        } else if (maxRating == null && recommended == null) {
            return convertListToResponseDto( reviewDao.getReviewsByBeerIdMinRating(id, minRating) );
        } else if (minRating == null && recommended == null) {
            return convertListToResponseDto( reviewDao.getReviewsByBeerMaxRating(id, maxRating) );
        } else if (minRating == null) {
            return convertListToResponseDto( reviewDao.getReviewsByBeerMaxRatingRecommended(id, maxRating, recommended) );
        } else if (maxRating == null) {
            return convertListToResponseDto( reviewDao.getReviewsByBeerMinRatingRecommended(id, minRating, recommended) );
        } else if (recommended == null) {
            return convertListToResponseDto( reviewDao.getReviewsByBeerRatingRange(id, minRating, maxRating) );
        } else {
            return convertListToResponseDto( reviewDao.getReviewsByBeerRatingRangeRecommended(id, minRating, maxRating, recommended) );
        }
    }

    private boolean checkIsNotBrewer(int id, String name) {
        User user = userDao.getUserByUsername(name);
        boolean isBrewer = user.getAuthoritiesString().
                contains("ROLE_BREWER");
        if (isBrewer) {
            int userBreweryId = breweryDao.getBreweryByBrewerId(user.getId()).getBreweryId();
            int beerBreweryId = beerDao.getBeerById(id).getBreweryId();
            return userBreweryId != beerBreweryId;
        }
        return true;
    }

    private List<ReviewResponseDto> convertListToResponseDto(List<Review> reviews) {
        List<ReviewResponseDto> dtos = new ArrayList<>();
        for (Review review : reviews) {
            dtos.add(convertToDto(review));
        }
        return dtos;
    }

    private ReviewResponseDto convertToDto(Review review) {
        return new ReviewResponseDto(review.getReviewId(),
                review.getUsername(),
                review.getReviewText(),
                review.getRating(),
                review.isRecommended());
    }
}
