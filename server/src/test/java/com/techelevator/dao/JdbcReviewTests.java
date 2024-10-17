package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.exception.NoRecordException;
import com.techelevator.model.Review;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class JdbcReviewTests extends BaseDaoTests{

    private final Review REVIEW_1 = new Review(1, 1, 2, "user2", "review 1 text", 1, false);
    private final Review REVIEW_2 = new Review(2, 1, 3, "user3", "review 2 text", 5, true);
    private final Review REVIEW_3 = new Review(3, 3, 2, "user2", "review 3 text", 4, true);
    private final Review REVIEW_4 = new Review(4, 3, 4, "user4", "review 4 text", 3, false);
    private JdbcReviewDao dao;
    private final int NONEXISTENT_ID = 100;

    @Before
    public void setup() {
        JdbcTemplate template = new JdbcTemplate(dataSource);
        this.dao = new JdbcReviewDao(template);
    }

    @Test
    public void getReviewByIdReturnsCorrectReview() {
        Review review = dao.getReviewById(1);
        assertReviewsMatch(REVIEW_1, review);
        review = dao.getReviewById(3);
        assertReviewsMatch(REVIEW_3, review);
    }

    @Test
    public void getReviewsByIdReturnsNoContentExceptionForNonexistentId() {
        try {
            Review review = dao.getReviewById(NONEXISTENT_ID);

            Assert.fail("Passing an invalid id to getReviewById should throw an exception");
        } catch (NoRecordException e) {
            return;
        } catch (Exception e) {
            Assert.fail("Passing an invalid id to getReviewById should throw NoRecordException, but threw something else");
        }
    }
    @Test
    public void getReviewByBeerIdReturnsCorrectList() {
        List<Review> reviews = dao.getReviewsByBeerId(1);
        Assert.assertEquals("getReviewsByBeerId returned a list of the wrong size", 2, reviews.size());
        assertReviewsMatch(REVIEW_1, reviews.get(0));
        assertReviewsMatch(REVIEW_2, reviews.get(1));
    }

    @Test
    public void getReviewsByBeerIdReturnEmptyListForBeerWithoutReviews() {
        List<Review> reviews = dao.getReviewsByBeerId(2);
        Assert.assertEquals("getReviewsByBeerId should return an empty list if beer has not been reviewed", 0, reviews.size());
    }

    @Test
    public void getReviewsByBeerIdReturnsEmptyListForNonexistentBeer() {
        List<Review> reviews = dao.getReviewsByBeerId(NONEXISTENT_ID);
        Assert.assertEquals("getReviewsByBeerId should return an empty list for nonexistent beer", 0, reviews.size());
    }

    @Test
    public void createReviewReturnsWithIdAssignedAndCorrectValues() {
        Review reviewToAdd = new Review();
        reviewToAdd.setBeerId(1);
        reviewToAdd.setUserId(1);
        reviewToAdd.setReviewText("new review text");
        reviewToAdd.setRating(2);
        reviewToAdd.setRecommended(true);

        Review retrievedReview = dao.createReview(reviewToAdd);

        Assert.assertNotEquals("createReview did not assign a reviewId", 0, retrievedReview.getReviewId());
        Assert.assertEquals("createReview did not assign beerId correctly", reviewToAdd.getBeerId(), retrievedReview.getBeerId());
        Assert.assertEquals("createReview did not assign userId correctly", reviewToAdd.getUserId(), retrievedReview.getUserId());
        Assert.assertEquals("createReview did not assign reviewText correctly", reviewToAdd.getReviewText(), retrievedReview.getReviewText());
        Assert.assertEquals("createReview did not assign rating correctly", reviewToAdd.getRating(), retrievedReview.getRating());
        Assert.assertEquals("createReview did not assign isRecommended correctly", reviewToAdd.isRecommended(), retrievedReview.isRecommended());
    }

    @Test
    public void createReviewThrowsDaoExceptionForBadInputs() {
        Review reviewToAdd = new Review();
        reviewToAdd.setBeerId(NONEXISTENT_ID);
        reviewToAdd.setUserId(1);
        reviewToAdd.setReviewText("new review text");
        reviewToAdd.setRating(2);
        reviewToAdd.setRecommended(true);

        try {
            Review retrievedReview = dao.createReview(reviewToAdd);

            Assert.fail("No exception was thrown when passing an invalid foreign key to createReview");
        } catch (DaoException e) {
            return;
        } catch (Exception e) {
            Assert.fail("Passing an invalid key should throw a DaoException, but threw something else");
        }
    }

    @Test
    public void getReviewsByBeerIdRecommendedReturnsCorrectList() {
        List<Review> reviews = dao.getReviewsByBeerIdRecommended(1, true);
        Assert.assertEquals("getReviewsByBeerIdRecommended returned list of incorrect size", 1, reviews.size());
        assertReviewsMatch(REVIEW_2, reviews.get(0));
    }

    @Test
    public void getReviewsByBeerIdMinRatingReturnsCorrectList() {
        List<Review> reviews = dao.getReviewsByBeerIdMinRating(3, 4);
        Assert.assertEquals("getReviewsByBeerIdMinRating returned list of incorrect size", 1, reviews.size());
        assertReviewsMatch(REVIEW_3, reviews.get(0));
    }

    @Test
    public void getReviewsByBeerMaxRating() {
        List<Review> reviews = dao.getReviewsByBeerMaxRating(1, 2);
        Assert.assertEquals("getReviewsByBeerMaxRating returned list of wrong size", 1, reviews.size());
        assertReviewsMatch(REVIEW_1, reviews.get(0));
    }

    @Test
    public void getReviewsByBeerMaxRatingRecommended() {
        List<Review> reviews = dao.getReviewsByBeerMaxRatingRecommended(3, 4, false);
        Assert.assertEquals("getReviewsByBeerMaxRatingRecommended returned list of incorrect size", 1, reviews.size());
        assertReviewsMatch(REVIEW_4, reviews.get(0));
    }

    @Test
    public void getReviewsByBeerMinRatingRecommended() {
        List<Review> reviews = dao.getReviewsByBeerMinRatingRecommended(3, 4, true);
        Assert.assertEquals("getReviewsByBeerMinRatingRecommended returned list of wrong size", 1, reviews.size());
        assertReviewsMatch(REVIEW_3, reviews.get(0));
    }

    @Test
    public void getReviewsByBeerRatingRange() {
        List<Review> reviews = dao.getReviewsByBeerRatingRange(3, 4, 5);
        Assert.assertEquals("getReviewsByBeerRatingRange returned list of wrong size", 1, reviews.size());
        assertReviewsMatch(REVIEW_3, reviews.get(0));
    }

    @Test
    public void getReviewsByBeerRatingRangeRecommended() {
        List<Review> reviews = dao.getReviewsByBeerRatingRangeRecommended(3, 3, 5, false);
        Assert.assertEquals("getReviewsByBeerRatingRangeRecommended returned list of wrong size", 1, reviews.size());
        assertReviewsMatch(REVIEW_4, reviews.get(0));
    }

    public void assertReviewsMatch(Review expected, Review actual) {
        Assert.assertEquals(expected.getReviewId(), actual.getReviewId());
        Assert.assertEquals(expected.getBeerId(), actual.getBeerId());
        Assert.assertEquals(expected.getUserId(), actual.getUserId());
        Assert.assertEquals(expected.getUsername(), actual.getUsername());
        Assert.assertEquals(expected.getReviewText(), actual.getReviewText());
        Assert.assertEquals(expected.getRating(), actual.getRating());
        Assert.assertEquals(expected.isRecommended(), actual.isRecommended());
    }
}
