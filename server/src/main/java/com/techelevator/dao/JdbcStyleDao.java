package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.exception.NoRecordException;
import com.techelevator.model.Style;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class JdbcStyleDao implements StyleDao {

    private JdbcTemplate template;
    private final String SQL_SELECT = "SELECT * FROM style ";
    private final String CANNOT_CONNECT_MSG = "Unable to connect to database";
    private final String NO_RECORD_MSG = "No style matches your search";

    public JdbcStyleDao(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public Style getStyleById(int id) {
        try {
            return template.queryForObject(SQL_SELECT + "WHERE style_id = ?;", this::mapRowToStyle, id);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        } catch (EmptyResultDataAccessException e) {
            throw new NoRecordException(NO_RECORD_MSG, e);
        }
    }

    @Override
    public Style getStyleByName(String name) {
        try {
            return template.queryForObject(SQL_SELECT + "WHERE style_name ILIKE ?", this::mapRowToStyle, name);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        } catch (EmptyResultDataAccessException e) {
            throw new NoRecordException(NO_RECORD_MSG, e);
        }
    }

    @Override
    public List<Style> getStyles() {
        try {
            return template.query(SQL_SELECT, this::mapRowToStyle);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }

    private Style mapRowToStyle(ResultSet set, int rowNum) throws SQLException {
        Style style = new Style();
        style.setStyleId(set.getInt("style_id"));
        style.setStyleName(set.getString("style_name"));
        return style;
    }
}
