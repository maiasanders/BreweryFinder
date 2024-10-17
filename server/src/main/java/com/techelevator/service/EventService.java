package com.techelevator.service;

import com.techelevator.dao.BreweryDao;
import com.techelevator.dao.CategoryDao;
import com.techelevator.dao.EventDao;
import com.techelevator.dao.UserDao;
import com.techelevator.exception.UnauthorizedUserException;
import com.techelevator.model.Brewery;
import com.techelevator.model.Event;
import com.techelevator.model.User;
import com.techelevator.model.dto.EventGetResponseDto;
import com.techelevator.model.dto.EventPostRequestDto;
import com.techelevator.model.dto.EventPostResponseDto;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class EventService {
    private EventDao eventDao;
    private UserDao userDao;
    private BreweryDao breweryDao;
    private CategoryDao categoryDao;

    public EventService(EventDao eventDao, UserDao userDao, BreweryDao breweryDao, CategoryDao categoryDao) {
        this.eventDao = eventDao;
        this.userDao = userDao;
        this.breweryDao = breweryDao;
        this.categoryDao = categoryDao;
    }

    @Transactional
    public EventPostResponseDto add(EventPostRequestDto dto, String name) {
        User user = userDao.getUserByUsername(name);
        Brewery brewery = breweryDao.getBreweryByBrewerId(user.getId());
        if (isAuthBrewer(user, brewery)) {
            Event event = dtoToEvent(dto);
            return convertEventToPostResponseDto(eventDao.createEvent(event));
        }
        throw new UnauthorizedUserException();
    }

    public List<EventGetResponseDto> listEventsByBrewery(int id, Date minDate, Date maxDate, Boolean over21, String query) {
        if (minDate == null && maxDate == null && over21 == null && query == null) {
            return convertListToDto(eventDao.getEventsByBreweryId(id));
        } else if (maxDate == null && over21 == null && query == null) {
            return convertListToDto(eventDao.getEventsByBreweryMinDate(id, minDate));
        } else if (minDate == null && over21 == null && query == null) {
            return convertListToDto(eventDao.getEventsByBreweryMaxDate(id, maxDate));
        } else if (minDate == null && maxDate == null && query == null) {
            return convertListToDto(eventDao.getEventsByBreweryOver21(id, over21));
        } else if (minDate == null && maxDate == null && over21 == null) {
            return convertListToDto(eventDao.getEventsByBreweryQuery(id, query));
        } else if (over21 == null && query == null) {
            return convertListToDto(eventDao.getEventsByBreweryDateRange(id, minDate, maxDate));
        } else if (maxDate == null && query == null) {
            return convertListToDto(eventDao.getEventsByBreweryMinDateOver21(id, minDate, over21));
        } else if (maxDate == null && over21 == null) {
            return convertListToDto(eventDao.getEventsByBreweryMinDateQuery(id, minDate, query));
        } else if (minDate == null && query == null) {
            return convertListToDto(eventDao.getEventsByBreweryMaxDateOver21(id, maxDate, over21));
        } else if (minDate == null && over21 == null) {
            return convertListToDto(eventDao.getEventsByBreweryMaxDateQuery(id, maxDate, query));
        } else if (minDate == null && maxDate == null) {
            return convertListToDto(eventDao.getEventsByBreweryOver21Query(id, over21, query));
        } else if (query == null) {
            return convertListToDto(eventDao.getEventsByBreweryDateRangeOver21(id, minDate, maxDate, over21));
        } else if (over21 == null) {
            return convertListToDto(eventDao.getEventsByBreweryDatesQuery(id, minDate, maxDate, query));
        } else if (maxDate == null) {
            return convertListToDto(eventDao.getEventsByBreweryMinDateOver21Query(id, minDate, over21, query));
        } else if (minDate == null) {
            return convertListToDto(eventDao.getEventsByBreweryMaxDateOver21Query(id, maxDate, over21, query));
        } else {
            return convertListToDto(eventDao.getEventsByBreweryDatesOver21Query(id, minDate, maxDate, over21, query));
        }
    }

    private EventPostResponseDto convertEventToPostResponseDto(Event event) {
        return new EventPostResponseDto(
                event.getId(),
                event.getEventName(),
                event.getEventDate(),
                event.getBegins(),
                event.getEnds(),
                event.getDesc(),
                event.isIs21Up(),
                event.getCategories(),
                event.getImgUrl());
    }

    private boolean isAuthBrewer(User user, Brewery brewery) {
        return user.getId() == brewery.getBrewerId();
    }
    private Event dtoToEvent(EventPostRequestDto dto) {
        Event event = new Event();
        event.setEventName(dto.getEventName());
        event.setBreweryId(dto.getBreweryId());
        event.setEventDate(LocalDate.parse(dto.getEventDate()));
        event.setBegins(dto.getBegins());
        event.setEnds(dto.getEnds());
        event.setDesc(dto.getDesc());
        event.setIs21Up(dto.isIs21Up());
        event.setCategories(new ArrayList<>());
        for (String category : dto.getCategories()) {
            event.addCategory(categoryDao.getCategoryByName(category, false) );
        };
        event.setImgUrl(dto.getImgUrl());
        return event;
    }
    private EventGetResponseDto eventToGetDto(Event event) {
        EventGetResponseDto dto = new EventGetResponseDto();
        dto.setId(event.getId());
        dto.setEventName(event.getEventName());
        dto.setBreweryName(event.getBreweryName());
        dto.setBreweryId(event.getBreweryId());
        dto.setEventDate(event.getEventDate());
        dto.setBegins(event.getBegins());
        dto.setEnds(event.getEnds());
        dto.setDesc(event.getDesc());
        dto.setIs21Up(event.isIs21Up());
        dto.setCategories(event.getCategories());
        dto.setImgUrl(event.getImgUrl());
        return dto;
    }
    private List<EventGetResponseDto> convertListToDto(List<Event> events) {
        List<EventGetResponseDto> dtos = new ArrayList<>();
        for (Event event : events) {
            dtos.add(eventToGetDto(event));
        }
        return dtos;
    }

    public EventGetResponseDto getEvent(int id) {
        return eventToGetDto(eventDao.getEventById(id));
    }

    @Transactional
    public EventPostResponseDto put(int id, EventPostRequestDto body, String username) {
        isAdminOrAuthBrewer(body.getBreweryId(), username);
        Event event = dtoToEvent(body);
        event.setId(id);
        return convertEventToPostResponseDto(eventDao.updateEvent(event));
    }

    private void isAdminOrAuthBrewer(int breweryId, String username) {
        User user = userDao.getUserByUsername(username);
        Brewery brewery = breweryDao.getBreweryById(breweryId);
        if (!user.getAuthoritiesString().contains("ADMIN") && !isAuthBrewer(user, brewery)) {
            throw new UnauthorizedUserException();
        }
    }


    public void delete(int id, Principal principal) {
        int breweryID = eventDao.getEventById(id).getBreweryId();
        isAdminOrAuthBrewer(breweryID, principal.getName());
        eventDao.deleteEvent(id);
    }

    public List<EventGetResponseDto> get4Events() {
        return convertListToDto(eventDao.get4Events());
    }

    public List<EventGetResponseDto> getEvents() {
        return convertListToDto(eventDao.getEvents());
    }
}
