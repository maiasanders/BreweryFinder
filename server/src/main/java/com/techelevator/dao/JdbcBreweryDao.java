package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.exception.NoRecordException;
import com.techelevator.model.Brewery;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.relational.core.sql.SQL;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.print.DocFlavor;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class JdbcBreweryDao implements BreweryDao {

    private JdbcTemplate template;
    private final String SQL_SELECT = "SELECT b.brewery_id, b.brewery_name, b.brewer_id, u.username, b.address, b.city, b.state_code, b.zip, b.about_us, b.serves_food, b.has_foodtrucks, b.kid_friendly, b.dog_friendly, b.days_open, b.open_time, b.close_time, b.website, b.pic_url " +
            "FROM brewery AS b " +
            "JOIN users AS u ON b.brewer_id = u.user_id ";
    private final String CANNOT_CONNECT_MSG = "Unable to connect to database";

    public JdbcBreweryDao(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public List<Brewery> getAllBreweries() {
        try {
            return template.query(SQL_SELECT, this::mapRowToBrewery);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }

    @Override
    public List<Brewery> getBreweriesByState(String state) {
        try {
            return template.query(SQL_SELECT + "WHERE b.state_code ILIKE ?;", this::mapRowToBrewery, state);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }

    @Override
    public List<Brewery> getBreweriesByCity(String city) {
        try {
            return template.query(SQL_SELECT + "WHERE b.city ILIKE ?;", this::mapRowToBrewery, makeWild(city));
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }

    @Override
    public List<Brewery> getBreweriesServeFood(boolean hasFood) {
        try {
            return template.query(SQL_SELECT + "WHERE serves_food = ?;", this::mapRowToBrewery, hasFood);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }

    @Override
    public List<Brewery> getKidFriendlyBreweries(boolean isKidFriendly) {
        try {
            return template.query(SQL_SELECT + "WHERE kid_friendly = ?;", this::mapRowToBrewery, isKidFriendly);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }

    @Override
    public List<Brewery> getDogFriendlyBreweries(boolean isDogFriendly) {
        try {
            return template.query(SQL_SELECT + "WHERE dog_friendly = ?;", this::mapRowToBrewery, isDogFriendly);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }

    @Override
    public List<Brewery> getBreweriesByCityState(String city, String state) {
        try {
            return template.query(SQL_SELECT + "WHERE b.city ILIKE ? AND b.state_code ILIKE ?;",
                    this::mapRowToBrewery, makeWild(city), state);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }

    @Override
    public List<Brewery> getBreweriesByCityHasFood(String city, boolean hasFood) {
        try {
            return template.query(SQL_SELECT + "WHERE b.city ILIKE ? AND serves_food = ?;", this::mapRowToBrewery, makeWild(city), hasFood);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }

    @Override
    public List<Brewery> getBreweriesByCityKids(String city, boolean kidFriendly) {
        try {
            return template.query(SQL_SELECT + "WHERE b.city ILIKE ? AND kid_friendly = ?;", this::mapRowToBrewery, makeWild(city), kidFriendly);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }

    @Override
    public List<Brewery> getBreweriesByCityDogs(String city, boolean dogFriendly) {
        try {
            return template.query(SQL_SELECT + "WHERE b.city ILIKE ? AND dog_friendly = ?;", this::mapRowToBrewery, makeWild(city), dogFriendly);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }

    @Override
    public List<Brewery> getBreweriesByStateFood(String state, boolean hasFood) {
        try {
            return template.query(SQL_SELECT + "WHERE b.state_code ILIKE ? AND serves_food = ?;", this::mapRowToBrewery, state, hasFood);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }

    @Override
    public List<Brewery> getBreweriesByStateKids(String state, boolean kidFriendly) {
        try {
            return template.query(SQL_SELECT + "WHERE b.state_code ILIKE ? AND kid_friendly = ?;", this::mapRowToBrewery, state, kidFriendly);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }

    @Override
    public List<Brewery> getBreweriesByStateDogs(String state, boolean dogFriendly) {
        try {
            return template.query(SQL_SELECT + "WHERE b.state_code ILIKE ? AND dog_friendly = ?;", this::mapRowToBrewery, state, dogFriendly);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }

    @Override
    public List<Brewery> getBreweriesByFoodKids(boolean hasFood, boolean kidFriendly) {
        try {
            return template.query(SQL_SELECT + "WHERE serves_food = ? AND kid_friendly = ?;", this::mapRowToBrewery, hasFood, kidFriendly);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }

    @Override
    public List<Brewery> getBreweriesByFoodDogs(boolean hasFood, boolean dogFriendly) {
        try {
            return template.query(SQL_SELECT + "WHERE serves_food = ? AND dog_friendly = ?;", this::mapRowToBrewery, hasFood, dogFriendly);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }

    @Override
    public List<Brewery> getBreweriesByKidsDogs(boolean kidFriendly, boolean dogFriendly) {
        try {
            return template.query(SQL_SELECT + "WHERE kid_friendly = ? AND dog_friendly = ?;", this::mapRowToBrewery, kidFriendly, dogFriendly);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }

    @Override
    public List<Brewery> getBreweriesByStateCityFood(String state, String city, boolean hasFood) {
        try {
            return template.query(SQL_SELECT + "WHERE b.state_code ILIKE ? AND b.city ILIKE ? AND serves_food = ?;",
                    this::mapRowToBrewery,
                    state,
                    makeWild(city),
                    hasFood);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }

    @Override
    public List<Brewery> getBreweriesByStateCityKids(String state, String city, boolean kidFriendly) {
        try {
            return template.query(SQL_SELECT + "WHERE b.state_code ILIKE ? AND b.city ILIKE ? AND kid_friendly = ?;",
                    this::mapRowToBrewery,
                    state, makeWild(city), kidFriendly);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }

    @Override
    public List<Brewery> getBreweriesByStateCityDogs(String state, String city, boolean dogFriendly) {
        try {
            return template.query(SQL_SELECT + "WHERE b.state_code ILIKE ? AND b.city ILIKE ? AND dog_friendly = ?;",
                    this::mapRowToBrewery, state, makeWild(city), dogFriendly);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }

    @Override
    public List<Brewery> getBreweriesByStateFoodKids(String state, boolean hasFood, boolean kidFriendly) {
        try {
            return template.query(SQL_SELECT + "WHERE b.state_code ILIKE ? AND serves_food = ? AND kid_friendly = ?;",
                    this::mapRowToBrewery, state, hasFood, kidFriendly);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }

    @Override
    public List<Brewery> getBreweriesByStateFoodDogs(String state, boolean hasFood, boolean dogFriendly) {
        try {
            return template.query(SQL_SELECT +
                    "WHERE b.state_code ILIKE ? AND serves_food = ? AND dog_friendly = ?;",
                    this::mapRowToBrewery, state, hasFood, dogFriendly);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }

    @Override
    public List<Brewery> getBreweriesByCityFoodKids(String city, boolean hasFood, boolean kidFriendly) {
        try {
            return template.query(SQL_SELECT +
                    "WHERE b.city ILIKE ? AND serves_food = ? AND kid_friendly = ?;",
                    this::mapRowToBrewery, makeWild(city), hasFood, kidFriendly);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }

    @Override
    public List<Brewery> getBreweriesByCityFoodDogs(String city, boolean hasFood, boolean dogFriendly) {
        try {
            return template.query(SQL_SELECT + "WHERE b.city ILIKE ? AND serves_food = ? AND dog_friendly = ?;",
                    this::mapRowToBrewery, makeWild(city), hasFood, dogFriendly);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }

    @Override
    public List<Brewery> getBreweriesByCityKidsDogs(String city, boolean kidFriendly, boolean dogFriendly) {
        try {
            return template.query(SQL_SELECT + "WHERE b.city ILIKE ? AND kid_friendly = ? AND dog_friendly = ?;",
                    this::mapRowToBrewery, makeWild(city), kidFriendly, dogFriendly);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }

    @Override
    public List<Brewery> getBreweriesByFoodKidsDogs(boolean hasFood, boolean kidFriendly, boolean dogFriendly) {
        try {
            return template.query(SQL_SELECT + "WHERE serves_food = ? AND kid_friendly = ? AND dog_friendly = ?;",
                    this::mapRowToBrewery, hasFood, kidFriendly, dogFriendly);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }

    @Override
    public List<Brewery> getBreweriesByStateCityFoodKids(String state, String city, boolean hasFood, boolean kidFriendly) {
        try {
            return template.query(SQL_SELECT +
                    "WHERE b.state_code ILIKE ? AND b.city ILIKE ? AND serves_food = ? AND kid_friendly = ?;",
                    this::mapRowToBrewery, state, makeWild(city), hasFood, kidFriendly);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }
    @Override
    public List<Brewery> getBreweriesByStateCityFoodDogs(String state, String city, boolean hasFood, boolean dogFriendly) {
        try {
            return template.query(SQL_SELECT + "WHERE b.state_code ILIKE ? AND b.city ILIKE ? AND serves_food = ? AND dog_friendly = ?;",
                    this::mapRowToBrewery, state, makeWild(city), hasFood, dogFriendly);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }

    @Override
    public List<Brewery> getBreweriesByStateFoodKidsDogs(String state, boolean hasFood, boolean kidFriendly, boolean dogFriendly) {
        try {
            return template.query(SQL_SELECT + "WHERE b.state_code ILIKE ? AND serves_food = ? AND kid_friendly = ? AND dog_friendly = ?;",
                    this::mapRowToBrewery, state, hasFood, kidFriendly, dogFriendly);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }

    @Override
    public List<Brewery> getBreweriesByCityFoodKidsDogs(String city, boolean hasFood, boolean kidFriendly, boolean dogFriendly) {
        try {
            return template.query(SQL_SELECT + "WHERE b.city ILIKE ? AND serves_food = ? AND kid_friendly = ? AND dog_friendly = ?;",
                    this::mapRowToBrewery, makeWild(city), hasFood, kidFriendly, dogFriendly);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }

    @Override
    public List<Brewery> getBreweriesByStateCityFoodKidsDogs(String state, String city, boolean hasFood, boolean kidFriendly, boolean dogFriendly) {
        try {
            return template.query(SQL_SELECT + "WHERE b.state_code ILIKE ? AND b.city ILIKE ? AND serves_food = ? AND kid_friendly = ? AND dog_friendly = ?;",
                    this::mapRowToBrewery, state, makeWild(city), hasFood, kidFriendly, dogFriendly);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }

    @Override
    public Brewery getBreweryById(int id) {

        Brewery brewery = null;
        String sql = SQL_SELECT + "WHERE brewery_id = ?;";

        try {
            brewery = template.queryForObject(sql, this:: mapRowToBrewery, id);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG);
        } catch (EmptyResultDataAccessException e) {
            throw new NoRecordException("Oops! This brewery doesn't exist", e);
        }

        return brewery;
    }

    @Override
    public List<Brewery> getBreweriesByStateCityKidsDogs(String state, String city, Boolean kidFriendly, Boolean dogFriendly) {
        try {
            return template.query(SQL_SELECT + "WHERE b.state_code ILIKE ? AND b.city ILIKE ? AND kid_friendly = ? AND dog_friendly = ?;",
                    this::mapRowToBrewery, state, makeWild(city), kidFriendly, dogFriendly);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }

    @Override
    public int deleteBreweryById(int id) {
        String deleteReviewsSql = "DELETE FROM review WHERE beer_id IN (SELECT beer_id FROM beer WHERE brewery_id = ?);";
        String deleteBeersSql = "DELETE FROM beer WHERE brewery_id = ?;";
        String deleteBrewerySql = "DELETE FROM brewery WHERE brewery_id = ?;";
        try {
            template.update(deleteReviewsSql, id);
            template.update(deleteBeersSql, id);
            int rows = template.update(deleteBrewerySql, id);
            if (rows > 0) {
                return rows;
            } else {
                throw new NoRecordException("Brewery with id " + id + " is not in database");
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Failed to delete brewery with id " + id + " due to data integrity violation", e);
        }
    }

    @Override
    public List<Brewery> getBreweriesByBrewer(int id) {
        try {
            return template.query(SQL_SELECT + "WHERE brewer_id = ?;", this::mapRowToBrewery, id);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG);
        }
    }

    @Override
    public Brewery createBrewery(Brewery newBrewery) {

        Brewery brewery = null;
        String sql = "INSERT INTO brewery " +
                "(brewery_name, brewer_id, address, city, state_code, zip, about_us, serves_food, has_foodtrucks, kid_friendly, dog_friendly, days_open, open_time, close_time, website, pic_url) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                "RETURNING brewery_id;";

        try {
            int id = template.queryForObject(sql,
                    Integer.class,
                    newBrewery.getBreweryName(),
                    newBrewery.getBrewerId(),
                    newBrewery.getAddress(),
                    newBrewery.getCity(),
                    newBrewery.getState(),
                    newBrewery.getZip(),
                    newBrewery.getAboutUs(),
                    newBrewery.isServesFood(),
                    newBrewery.isHasFoodTrucks(),
                    newBrewery.isKidFriendly(),
                    newBrewery.isDogFriendly(),
                    newBrewery.getDaysOpen(),
                    newBrewery.getOpenTime(),
                    newBrewery.getCloseTime(),
                    newBrewery.getWebsite(),
                    newBrewery.getPicUrl());
            return getBreweryById(id);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Cannot add brewery due to data integrity violation");
        }
    }

    @Override
    public Brewery updateBrewery(Brewery brewery) {
        String sql = "UPDATE brewery SET brewery_name = ?, brewer_id = ?, address = ?, city = ?, state_code = ?, zip = ?, about_us = ?, serves_food = ?, has_foodtrucks = ?, kid_friendly = ?, dog_friendly = ?, days_open = ?, open_time = ?, close_time = ?, website = ?, pic_url = ? " +
                "WHERE brewery_id = ?;";

        try {
            int rows = template.update(sql,
                    brewery.getBreweryName(),
                    brewery.getBrewerId(),
                    brewery.getAddress(),
                    brewery.getCity(),
                    brewery.getState(),
                    brewery.getZip(),
                    brewery.getAboutUs(),
                    brewery.isServesFood(),
                    brewery.isHasFoodTrucks(),
                    brewery.isKidFriendly(),
                    brewery.isDogFriendly(),
                    brewery.getDaysOpen(),
                    brewery.getOpenTime(),
                    brewery.getCloseTime(),
                    brewery.getWebsite(),
                    brewery.getPicUrl(),
                    brewery.getBreweryId()
            );
            if (rows > 0) {
                return getBreweryById(brewery.getBreweryId());
            } else {
                throw new NoRecordException("Oops! Could not find the brewery to update!");
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Unable to update brewery due to data integrity violation");
        }
    }

    @Override
    public Brewery getBreweryByBrewerId(int id) {
        try {
            return template.queryForObject(SQL_SELECT + "WHERE brewer_id = ?;",
                    this:: mapRowToBrewery,
                    id);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG);
        } catch (EmptyResultDataAccessException e) {
            throw new NoRecordException("No brewery matches the record");
        }
    }

    private Brewery mapRowToBrewery(ResultSet set, int rowNum) throws SQLException {
        Brewery brewery = new Brewery();
        brewery.setBreweryId(set.getInt("brewery_id"));
        brewery.setBreweryName(set.getString("brewery_name"));
        brewery.setBrewerId(set.getInt("brewer_id"));
        brewery.setBrewerName(set.getString("username"));
        brewery.setAddress(set.getString("address"));
        brewery.setCity(set.getString("city"));
        brewery.setState(set.getString("state_code"));
        brewery.setZip(set.getString("zip"));
        brewery.setAboutUs(set.getString("about_us"));
        brewery.setServesFood(set.getBoolean("serves_food"));
        brewery.setHasFoodTrucks(set.getBoolean("has_foodtrucks"));
        brewery.setKidFriendly(set.getBoolean("kid_friendly"));
        brewery.setDogFriendly(set.getBoolean("dog_friendly"));
        brewery.setDaysOpen(set.getString("days_open"));
        brewery.setOpenTime(set.getString("open_time").toString());
        brewery.setCloseTime(set.getString("close_time").toString());
        brewery.setWebsite(set.getString("website"));
        brewery.setPicUrl(set.getString("pic_url"));
        return brewery;
    }

    private String makeWild(String str) {
        return "%" + str + "%";
    }
}
