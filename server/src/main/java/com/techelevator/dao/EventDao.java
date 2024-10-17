package com.techelevator.dao;

import com.techelevator.model.Category;
import com.techelevator.model.Event;
import com.techelevator.model.dto.EventGetResponseDto;

import java.util.Date;
import java.util.List;

public interface EventDao {
    Event getEventById(int id);
    Event createEvent(Event event);
    Category addCategoryToEvent(int eventId, Category category);

    List<Event> getEventsByBreweryId(int id);

    List<Event> getEventsByBreweryMinDate(int id, Date minDate);

    List<Event> getEventsByBreweryMaxDate(int id, Date maxDate);

    List<Event> getEventsByBreweryOver21(int id, Boolean over21);

    List<Event> getEventsByBreweryQuery(int id, String query);

    List<Event> getEventsByBreweryDateRange(int id, Date minDate, Date maxDate);

    List<Event> getEventsByBreweryMinDateOver21(int id, Date minDate, Boolean over21);

    List<Event> getEventsByBreweryMinDateQuery(int id, Date minDate, String query);

    List<Event> getEventsByBreweryMaxDateOver21(int id, Date maxDate, Boolean over21);

    List<Event> getEventsByBreweryMaxDateQuery(int id, Date maxDate, String query);

    List<Event> getEventsByBreweryOver21Query(int id, Boolean over21, String query);

    List<Event> getEventsByBreweryDateRangeOver21(int id, Date minDate, Date maxDate, Boolean over21);

    List<Event> getEventsByBreweryDatesQuery(int id, Date minDate, Date maxDate, String query);

    List<Event> getEventsByBreweryMinDateOver21Query(int id, Date minDate, Boolean over21, String query);

    List<Event> getEventsByBreweryMaxDateOver21Query(int id, Date maxDate, Boolean over21, String query);

    List<Event> getEventsByBreweryDatesOver21Query(int id, Date minDate, Date maxDate, Boolean over21, String query);

    Event updateEvent(Event event);

    void deleteEvent(int id);

    List<Event> get4Events();

    List<Event> getEvents();
}
