package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.exception.NoRecordException;
import com.techelevator.model.Beer;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class JdbcBeerDao implements BeerDao {

    private JdbcTemplate template;
    private final String SELECT_SQL = "SELECT be.beer_id, be.beer_name, be.brewery_id, br.brewery_name, be.style_id, s.style_name, be.description, be.abv, be.seasonal, be.season_name, be.picture_url  " +
            "FROM beer AS be " +
            "JOIN brewery AS br ON be.brewery_id = br.brewery_id " +
            "JOIN style AS s ON be.style_id = s.style_id ";
    private final String WHERE_STR_QUERY = "WHERE be.brewery_id = ? AND (beer_name ILIKE ? OR description ILIKE ? OR season_name ILIKE ? )";
    private final String CANNOT_CONNECT_MSG = "Unable to connect to database";

    public JdbcBeerDao(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public Beer getBeerById(int id) {
        try {
            return template.queryForObject(SELECT_SQL + " WHERE beer_id = ?;",
                    this::mapRowToBeer, id);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        } catch (EmptyResultDataAccessException e) {
            throw new NoRecordException("Oops! No beer matches your input!", e);
        }
    }

    @Override
    public List<Beer> getBeersByBreweryId(int id) {
        try {
            return template.query(SELECT_SQL + "WHERE be.brewery_id = ? ", this::mapRowToBeer, id);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }

    @Override
    public Beer createBeer(Beer beer) {
        String sql = "INSERT INTO beer (beer_name, brewery_id, style_id, description, abv, seasonal";
        int beerId = 0;
        Beer retrievedBeer = null;
        try {
            if (beer.isSeasonal()) {
                sql += ", season_name, picture_url) VALUES " +
                        "(?,?,?,?,?,?,?,?) " +
                        "RETURNING beer_id;";
                beerId = template.queryForObject(sql,
                        Integer.class,
                        beer.getBeerName(),
                        beer.getBreweryId(),
                        beer.getStyleId(),
                        beer.getDesc(),
                        beer.getAbv(),
                        beer.isSeasonal(),
                        beer.getSeasonName(),
                        beer.getImgUrl());
                return getBeerById(beerId);
            } else {
                sql += ", picture_url) VALUES " +
                        "(?,?,?,?,?,?,?) " +
                        "RETURNING beer_id;";
                beerId = template.queryForObject(sql,
                        Integer.class,
                        beer.getBeerName(),
                        beer.getBreweryId(),
                        beer.getStyleId(),
                        beer.getDesc(),
                        beer.getAbv(),
                        beer.isSeasonal(),
                        beer.getImgUrl());
                return getBeerById(beerId);
            }

        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Unable to add beer due to data integrity violation", e);
        }

    }

    @Override
    public int deleteBeerById(int id) {
        String sqlDeleteReviews = "DELETE FROM review WHERE beer_id = ?;";
        String sqlDeleteBeer = "DELETE FROM beer WHERE beer_id = ?;";
        try {
            template.update(sqlDeleteReviews, id);
            int rows = template.update(sqlDeleteBeer, id);
            if (rows == 0) {
                throw new NoRecordException("Unable to find beer you are trying to delete");
            }
            return rows;
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Unable to delete beer due to data integrity violation", e);
        }
    }

    @Override
    public List<Beer> getBeersByBreweryQuery(int id, String query) {
        query = makeWild(query);
        try {
            return template.query(SELECT_SQL + WHERE_STR_QUERY, this::mapRowToBeer, id, query, query, query);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }


    @Override
    public List<Beer> getBeersByBreweryStyle(int breweryId, int styleId) {
        try {
            return template.query(SELECT_SQL + "WHERE be.brewery_id = ? AND be.style_id = ?;", this::mapRowToBeer, breweryId, styleId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }

    @Override
    public List<Beer> getBeersByBreweryQueryMinAbv(int id, String query, Double minAbv) {
        query = makeWild(query);
        try {
            return template.query(SELECT_SQL + WHERE_STR_QUERY + "AND abv >= ?;", this::mapRowToBeer, id, query, query, query, minAbv);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }

    @Override
    public List<Beer> getBeersByBreweryQueryStyle(int breweryId, String query, int styleId) {
        query = makeWild(query);
        try {
            return template.query(SELECT_SQL + WHERE_STR_QUERY + "AND be.style_id = ?;", this::mapRowToBeer, breweryId, query, query, query, styleId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }

    @Override
    public List<Beer> getBeersByBreweryMinAbvStyle(int breweryId, Double minAbv, int styleId) {
        try {
            return template.query(SELECT_SQL + "WHERE be.brewery_id = ? AND abv >= ? AND be.style_id = ?;", this::mapRowToBeer, breweryId, minAbv, styleId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }

    @Override
    public List<Beer> getBeersByBreweryQueryMinAbvStyle(int breweryId, String query, Double minAbv, int styleId) {
        query = makeWild(query);
        try {
            return template.query(SELECT_SQL + WHERE_STR_QUERY + "AND abv >= ? AND be.style_id = ?;",
                    this::mapRowToBeer, breweryId, query, query, query, minAbv, styleId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }

    @Override
    public List<Beer> getBeersByBreweryMinAbv(int id, Double minAbv) {
        try {
            return template.query(SELECT_SQL + "WHERE be.brewery_id = ? AND abv >= ?;", this::mapRowToBeer, id, minAbv);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }

    @Override
    public List<Beer> getBeersByBreweryMaxAbv(int id, Double maxAbv) {
        try {
            return template.query(SELECT_SQL + "WHERE be.brewery_id = ? AND abv <= ?;", this::mapRowToBeer, id, maxAbv);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }

    @Override
    public List<Beer> getBeersByBreweryQueryMaxAbv(int id, String query, Double maxAbv) {
        query = makeWild(query);
        try {
            return template.query(SELECT_SQL + WHERE_STR_QUERY + "AND abv <= ?", this::mapRowToBeer, id, query, query, query, maxAbv);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }

    @Override
    public List<Beer> getBeersByBreweryAbvRange(int id, Double minAbv, Double maxAbv) {
        try {
            return template.query(SELECT_SQL + "WHERE be.brewery_id = ? AND abv BETWEEN ? AND ?;", this::mapRowToBeer, id, minAbv, maxAbv);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }

    @Override
    public List<Beer> getBeersByBreweryQueryAbvRange(int id, String query, Double minAbv, Double maxAbv) {
        query = makeWild(query);
        try {
            return template.query(SELECT_SQL + WHERE_STR_QUERY + "AND abv BETWEEN ? AND ?;", this::mapRowToBeer, id, query, query, query, minAbv, maxAbv);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }

    @Override
    public List<Beer> getBeersByBreweryQueryMaxAbvStyle(int breweryId, String query, Double maxAbv, int styleId) {
        query = makeWild(query);
        try {
            return template.query(SELECT_SQL + WHERE_STR_QUERY + "AND abv <= ? AND be.style_id = ?;",
                    this::mapRowToBeer, breweryId, query, query, query, maxAbv, styleId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }

    @Override
    public List<Beer> getBeersByBreweryAbvRangeStyle(int breweryId, Double minAbv, Double maxAbv, int styleId) {
        try {
            return template.query(SELECT_SQL + "WHERE be.brewery_id = ? AND abv BETWEEN ? AND ? AND be.style_id = ?;",
                    this::mapRowToBeer, breweryId, minAbv, maxAbv, styleId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }

    @Override
    public List<Beer> getBeersByBreweryQueryAbvRangeStyle(int breweryId, String query, Double minAbv, Double maxAbv, int styleId) {
        query = makeWild(query);
        try {
            return template.query(SELECT_SQL + WHERE_STR_QUERY + "AND abv BETWEEN ? AND ? AND be.style_id = ?;",
                    this::mapRowToBeer, breweryId, query, query, query, minAbv, maxAbv, styleId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }

    @Override
    public Beer updateBeer(Beer beer) {
        String sql = "UPDATE beer SET beer_name = ?, brewery_id = ?, " +
                "style_id = ?, " +
                "description = ?, abv = ?, seasonal = ?";
        try {
            int rows = 0;
            if (beer.isSeasonal()) {
                sql += ", season_name = ?, picture_url = ? WHERE beer_id = ?;";
                rows = template.update(sql, beer.getBeerName(), beer.getBreweryId(), beer.getStyleId(),
                        beer.getDesc(), beer.getAbv(), beer.isSeasonal(), beer.getImgUrl(), beer.getBeerId());
            } else {
                sql += ", picture_url = ? WHERE beer_id = ?;";
                rows = template.update(sql, beer.getBeerName(), beer.getBreweryId(), beer.getStyleId(),
                        beer.getDesc(), beer.getAbv(), beer.isSeasonal(), beer.getImgUrl(), beer.getBeerId());
            }
            if (rows > 0) {
                return getBeerById(beer.getBeerId());
            } else {
                throw new NoRecordException("The beer you're trying to update doesn't exist");
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Could not update beer due to data integrity violation", e);
        }
    }

    @Override
    public List<Beer> getBeers() {
        try {
            return template.query(SELECT_SQL, this::mapRowToBeer);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }

    private Beer mapRowToBeer(ResultSet set, int rowNum) throws SQLException {
        Beer beer = new Beer();
        beer.setBeerId(set.getInt("beer_id"));
        beer.setBeerName(set.getString("beer_name"));
        beer.setBreweryId(set.getInt("brewery_id"));
        beer.setBreweryName(set.getString("brewery_name"));
        beer.setStyleId(set.getInt("style_id"));
        beer.setStyleName(set.getString("style_name"));
        beer.setDesc(set.getString("description"));
        beer.setAbv(set.getDouble("abv"));
        beer.setSeasonal(set.getBoolean("seasonal"));
        if (beer.isSeasonal()) {
            beer.setSeasonName(set.getString("season_name"));
        }
        beer.setImgUrl(set.getString("picture_url"));
        return beer;
    }

    private String makeWild(String str) {
        return "%" + str + "%";
    }
}
