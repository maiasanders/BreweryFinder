package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.exception.NoRecordException;
import com.techelevator.model.Review;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class JdbcReviewDao implements ReviewDao{
    private JdbcTemplate template;
    private final String SELECT_SQL = "SELECT r.review_id, r.beer_id, r.user_id, u.username, r.review_text, r.rating, r.recommended " +
            "FROM review AS r " +
            "JOIN users AS u ON r.user_id = u.user_id ";
    private final String CANNOT_CONNECT_MSG = "Unable to connect to database";


    public JdbcReviewDao(JdbcTemplate template) {
        this.template = template;
    }

    /*
        review_id serial PRIMARY KEY,
        beer_id int REFERENCES beer(beer_id),
        user_id int REFERENCES users(user_id),
        review_text varchar(300),
        rating int NOT NULL,
        recommended boolean,
         */
    @Override
    public Review getReviewById(int id) {
        try {
            return template.queryForObject(SELECT_SQL + "WHERE review_id = ?;", this::mapRowToReview, id);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        } catch (EmptyResultDataAccessException e) {
            throw new NoRecordException("Oops! Can't find the review you're looking for!", e);
        }
    }

    @Override
    public List<Review> getReviewsByBeerId(int id) {
        try {
            return template.query(SELECT_SQL + "WHERE beer_id = ?;",
                    this::mapRowToReview, id);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }

    @Override
    public Review createReview(Review review) {
        String sql = "INSERT INTO review " +
                "(beer_id, user_id, review_text, rating, recommended) " +
                "VALUES (?, ?, ?, ?, ?) " +
                "RETURNING review_id;";
        try {
            int reviewId = template.queryForObject(sql, Integer.class,
                    review.getBeerId(),
                    review.getUserId(),
                    review.getReviewText(),
                    review.getRating(),
                    review.isRecommended());
            return getReviewById(reviewId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Unable to save review due to data integrity violation", e);
        }
    }

    @Override
    public List<Review> getReviewsByBeerIdRecommended(int id, boolean recommended) {
        try {
            return template.query(SELECT_SQL + "WHERE beer_id = ? AND recommended = ?;", this::mapRowToReview, id, recommended);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }

    @Override
    public List<Review> getReviewsByBeerIdMinRating(int id, int minRating) {
        try {
            return template.query(SELECT_SQL + "WHERE beer_id = ? AND rating >= ?;", this::mapRowToReview, id, minRating);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }

    @Override
    public List<Review> getReviewsByBeerMaxRating(int id, int maxRating) {
        try {
            return template.query(SELECT_SQL + "WHERE beer_id = ? AND rating <= ?;", this::mapRowToReview, id, maxRating);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }

    @Override
    public List<Review> getReviewsByBeerMaxRatingRecommended(int id, int maxRating, boolean recommended) {
        try {
            return template.query(SELECT_SQL + "WHERE beer_id = ? AND rating <= ? AND recommended = ?;",
                    this::mapRowToReview, id, maxRating, recommended);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }

    @Override
    public List<Review> getReviewsByBeerMinRatingRecommended(int id, int minRating, boolean recommended) {
        try {
            return template.query(SELECT_SQL + "WHERE beer_id = ? AND rating >= ? AND recommended = ?;",
                    this::mapRowToReview, id, minRating, recommended);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }

    @Override
    public List<Review> getReviewsByBeerRatingRange(int id, int minRating, int maxRating) {
        try {
            return template.query(SELECT_SQL + "WHERE beer_id = ? AND rating BETWEEN ? AND ?;", this::mapRowToReview, id, minRating, maxRating);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }

    @Override
    public List<Review> getReviewsByBeerRatingRangeRecommended(int id, int minRating, int maxRating, boolean recommended) {
        try {
            return template.query(SELECT_SQL + "WHERE beer_id = ? AND recommended = ? AND rating BETWEEN ? AND ?;",
                    this::mapRowToReview, id, recommended, minRating, maxRating);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }

    private Review mapRowToReview(ResultSet result, int rowNum) throws SQLException {
        Review review = new Review();
        review.setReviewId(result.getInt("review_id"));
        review.setBeerId(result.getInt("beer_id"));
        review.setUserId(result.getInt("user_id"));
        review.setUsername(result.getString("username"));
        review.setReviewText(result.getString("review_text"));
        review.setRating(result.getInt("rating"));
        review.setRecommended(result.getBoolean("recommended"));
        return review;
    }
}
