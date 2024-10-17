package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.exception.NoRecordException;
import com.techelevator.model.Category;
import com.techelevator.model.Event;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class JdbcEventDao implements EventDao{

    private JdbcTemplate template;
    private CategoryDao categoryDao;
    private final String CANNOT_CONNECT_MSG = "Unable to connect to database";
    private final String SELECT_STATEMENT = "SELECT e.event_id, e.event_name, e.brewery_id, b.brewery_name, e.event_date, e.begins, e.ends, e.description, e.over_21, STRING_AGG(c.category_name, ',') AS categories, STRING_AGG(CAST(c.category_id AS varchar), ',') AS category_ids, e.img_url " +
            "FROM event AS e " +
            "JOIN brewery AS b ON e.brewery_id = b.brewery_id " +
            "JOIN event_category AS ec ON e.event_id = ec.event_id " +
            "JOIN category AS c ON ec.category_id = c.category_id ";
    private final String GROUP_BY = " GROUP BY e.event_id, b.brewery_id";
    private final String WHERE_WITH_QUERY = "WHERE e.brewery_id = ? AND (e.event_name ILIKE ? OR e.description ILIKE ?) ";

    public JdbcEventDao(JdbcTemplate template, CategoryDao categoryDao) {
        this.template = template;
        this.categoryDao = categoryDao;
    }

    @Override
    public Event getEventById(int id) {
        try {
            Event event = template.queryForObject(SELECT_STATEMENT + "WHERE e.event_id = ?" + GROUP_BY, this::mapRowToEvent, id);
            return event;
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        } catch (EmptyResultDataAccessException e) {
            throw new NoRecordException();
        }
    }

    @Override
    public Event createEvent(Event event) {
        String sql = "INSERT INTO event " +
                "(event_name, brewery_id, event_date, begins, ends, description, over_21, img_url) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?) " +
                "RETURNING event_id;";

        try {
            int eventId = template.queryForObject(sql, Integer.class, event.getEventName(), event.getBreweryId(), event.getEventDate(), event.getBegins(), event.getEnds(), event.getDesc(), event.isIs21Up(), event.getImgUrl());
            for (Category category : event.getCategories()) {
                addCategoryToEvent(eventId, category);
            }
            return getEventById(eventId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Unable to add Event due to data integrity violation", e);
        }
    }

    @Override
    public Category addCategoryToEvent(int eventId, Category category) {
        String sql = "INSERT INTO event_category " +
                "(event_id, category_id) VALUES (?, ?);";
        try {
            template.update(sql, eventId, category.getId());
            return category;
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG + ", failed to add category to event", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Unable to add category to event due to data integrity violation", e);
        }
    }

    @Override
    public List<Event> getEventsByBreweryId(int id) {
        try {
            return template.query(SELECT_STATEMENT + "WHERE e.brewery_id = ?" + GROUP_BY, this::mapRowToEvent, id);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }

    @Override
    public List<Event> getEventsByBreweryMinDate(int id, Date minDate) {
        try {
            return template.query(SELECT_STATEMENT +
                    "WHERE e.brewery_id = ? AND event_date >= ?" + GROUP_BY, this::mapRowToEvent, id, minDate);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }

    @Override
    public List<Event> getEventsByBreweryMaxDate(int id, Date maxDate) {
        try {
            return template.query(SELECT_STATEMENT +
                    "WHERE e.brewery_id = ? AND event_date <= ?" + GROUP_BY,
                    this::mapRowToEvent, id, maxDate);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }

    @Override
    public List<Event> getEventsByBreweryOver21(int id, Boolean over21) {
        try {
            return template.query(SELECT_STATEMENT +
                    "WHERE e.brewery_id = ? AND over_21 = ?" + GROUP_BY, this::mapRowToEvent, id, over21);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }

    @Override
    public List<Event> getEventsByBreweryQuery(int id, String query) {
        try {
            query = makeWildCard(query);
            return template.query(SELECT_STATEMENT  + WHERE_WITH_QUERY + GROUP_BY,
                    this::mapRowToEvent, id, query, query);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }

    @Override
    public List<Event> getEventsByBreweryDateRange(int id, Date minDate, Date maxDate) {
        try {
            return template.query(SELECT_STATEMENT +
                    "WHERE e.brewery_id = ? AND event_date BETWEEN ? AND ?" + GROUP_BY, this::mapRowToEvent, id, minDate, maxDate);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }

    @Override
    public List<Event> getEventsByBreweryMinDateOver21(int id, Date minDate, Boolean over21) {
        try {
            return template.query(SELECT_STATEMENT +
                    "WHERE e.brewery_id = ? AND event_date >= ? AND over_21 = ?" + GROUP_BY, this::mapRowToEvent, id, minDate, over21);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }

    @Override
    public List<Event> getEventsByBreweryMinDateQuery(int id, Date minDate, String query) {
        try {
            query = makeWildCard(query);
            return template.query(SELECT_STATEMENT +
                    WHERE_WITH_QUERY +
                    " AND event_date >= ?" + GROUP_BY, this::mapRowToEvent, id, query, query, minDate);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }

    @Override
    public List<Event> getEventsByBreweryMaxDateOver21(int id, Date maxDate, Boolean over21) {
        try {
            return template.query(SELECT_STATEMENT +
                    "WHERE e.brewery_id = ? AND event_date <= ? AND over_21 = ?" + GROUP_BY, this::mapRowToEvent, id, maxDate, over21);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }

    @Override
    public List<Event> getEventsByBreweryMaxDateQuery(int id, Date maxDate, String query) {
        try {
            query = makeWildCard(query);
            return template.query(SELECT_STATEMENT +
                    WHERE_WITH_QUERY +
                    " AND event_date <= ?" + GROUP_BY, this::mapRowToEvent, id, query, query, maxDate);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }

    @Override
    public List<Event> getEventsByBreweryOver21Query(int id, Boolean over21, String query) {
        try {
            query = makeWildCard(query);
            return template.query(SELECT_STATEMENT +
                    WHERE_WITH_QUERY +
                    " AND over_21 = ?" + GROUP_BY, this::mapRowToEvent, id, query, query, over21);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }

    @Override
    public List<Event> getEventsByBreweryDateRangeOver21(int id, Date minDate, Date maxDate, Boolean over21) {
        try {
            return template.query(SELECT_STATEMENT +
                    " AND event_date BETWEEN ? AND ? AND over_21 = ?" + GROUP_BY, this::mapRowToEvent, id, minDate, maxDate, over21);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }

    @Override
    public List<Event> getEventsByBreweryDatesQuery(int id, Date minDate, Date maxDate, String query) {
        try {
            query = makeWildCard(query);
            return template.query(SELECT_STATEMENT +
                    WHERE_WITH_QUERY +
                    " AND event_date BETWEEN ? AND ?" + GROUP_BY, this::mapRowToEvent, id, query, query, minDate, maxDate);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }

    @Override
    public List<Event> getEventsByBreweryMinDateOver21Query(int id, Date minDate, Boolean over21, String query) {
        try {
            query = makeWildCard(query);
            return template.query(SELECT_STATEMENT +
                    WHERE_WITH_QUERY +
                    " AND event_date >= ? AND over_21 = ?" + GROUP_BY, this::mapRowToEvent, id, query, query, minDate, over21);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }

    @Override
    public List<Event> getEventsByBreweryMaxDateOver21Query(int id, Date maxDate, Boolean over21, String query) {
        try {
            query = makeWildCard(query);
            return template.query(SELECT_STATEMENT +
                    WHERE_WITH_QUERY +
                    " AND event_date <= ? AND over_21 = ?" + GROUP_BY, this::mapRowToEvent, id, query, query, maxDate, over21);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }

    @Override
    public List<Event> getEventsByBreweryDatesOver21Query(int id, Date minDate, Date maxDate, Boolean over21, String query) {
        try {
            query = makeWildCard(query);
            return template.query(SELECT_STATEMENT +
                    WHERE_WITH_QUERY +
                    " AND event_date BETWEEN ? AND ? AND over_21 = ?" + GROUP_BY, this::mapRowToEvent, id, query, query, minDate, maxDate, over21);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }

    @Override
    public Event updateEvent(Event event) {
        String sql = "UPDATE event " +
                "SET event_name = ?, brewery_id = ?, event_date = ?, begins = ?, ends = ?, description = ?, over_21 = ?, img_url = ? " +
                "WHERE event_id = ?;";
        List<Category> existingCats = categoryDao.getCategoriesByEventId(event.getId());

        List<Category> newCats = new ArrayList<>();
        for (int i = 0; i < event.getCategories().size(); i++) {
            boolean isInDb = false;
            for (Category cat : existingCats) {
                if (cat.equals(event.getCategories().get(i))) {
                    isInDb = true;
                    break;
                }
            }
            if (!isInDb) {
                newCats.add(event.getCategories().get(i));
            }
        }

        List<Category> catsToDelete = new ArrayList<>();
        for (int i = 0; i < existingCats.size(); i++) {
            boolean needsDelete = true;
            for (Category category : event.getCategories()) {
                if (existingCats.get(i).equals(category)) {
                    needsDelete = false;
                }
            }
            if (needsDelete) catsToDelete.add(existingCats.get(i));
        }

        try {
            int rows = template.update(sql, event.getEventName(), event.getBreweryId(), event.getEventDate(), event.getBegins(), event.getEnds(), event.getDesc(), event.isIs21Up(), event.getImgUrl(), event.getId());

//            TODO add update for categories?
            for (Category category : newCats) {
                addCategoryToEvent(event.getId(), category);
            }

            for(Category category : catsToDelete) {
                removeCategoryFromEvent(event.getId(), category.getId());
            }

            return getEventById(event.getId());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Unable to update event id " + event.getId() + " due to data integrity violation", e);
        }
    }

    private void removeCategoryFromEvent(int eventId, int catId) {
        String sql = "DELETE FROM event_category WHERE event_id = ? AND category_id = ?;";
        try {
            template.update(sql, eventId, catId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Unable to remove category from event due to data integrity violation");
        }
    }

    @Override
    public void deleteEvent(int id) {
        String deleteCategories = "DELETE FROM event_category " +
                "WHERE event_id = ?";
        String deleteEvent = "DELETE FROM event WHERE event_id = ?";
        try {
            template.update(deleteCategories, id);
            template.update(deleteEvent, id);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Cannot delete event " + id + " due to data integrity violation");
        }
    }

    @Override
    public List<Event> get4Events() {
        String sql = " ORDER BY event_date " +
                "LIMIT 4";
        try {
            return template.query(SELECT_STATEMENT + "WHERE event_date >= CURRENT_DATE " + GROUP_BY + sql, this::mapRowToEvent);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }

    @Override
    public List<Event> getEvents() {
        try {
            return template.query(SELECT_STATEMENT + GROUP_BY, this::mapRowToEvent);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(CANNOT_CONNECT_MSG, e);
        }
    }

    private Event mapRowToEvent(ResultSet set, int rowNum) throws SQLException {
        Event event = new Event();
        event.setId(set.getInt("event_id"));
        event.setEventName(set.getString("event_name"));
        event.setBreweryId(set.getInt("brewery_id"));
        event.setBreweryName(set.getString("brewery_name"));
        event.setEventDate(LocalDate.parse(set.getString("event_date")));
        event.setBegins(set.getString("begins"));
        if (set.getString("ends") != null) {
            event.setEnds(set.getString("ends"));

        }
        event.setDesc(set.getString("description"));
        event.setIs21Up(set.getBoolean("over_21"));
        event.setImgUrl(set.getString("img_url"));

        event.setCategories(new ArrayList<>());
        String[] categoryNames = set.getString("categories").split(",");
        String[] categoryIds = set.getString("category_ids").split(",");
        for (int i = 0; i < categoryIds.length; i++) {
            event.addCategory(new Category(
                    Integer.parseInt(categoryIds[i]),
                    categoryNames[i]
            ));
        }

        return event;
    }

    private String makeWildCard(String str) {
        return "%" + str + "%";
    }


}
