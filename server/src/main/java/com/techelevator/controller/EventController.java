package com.techelevator.controller;

import com.techelevator.exception.NoRecordException;
import com.techelevator.exception.UnauthorizedUserException;
import com.techelevator.model.dto.EventGetResponseDto;
import com.techelevator.model.dto.EventPostRequestDto;
import com.techelevator.model.dto.EventPostResponseDto;
import com.techelevator.service.EventService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class EventController {
    private EventService service;

    public EventController(EventService service) {
        this.service = service;
    }

    @PostMapping(path = "/events")
    @ResponseStatus(code = HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_BREWER')")
    public EventPostResponseDto add(@Valid @RequestBody EventPostRequestDto dto, Principal principal) {
        try {
            return service.add(dto, principal.getName());
        } catch (UnauthorizedUserException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    };

    @GetMapping(path = "/events/{id}")
    public EventGetResponseDto get(@PathVariable int id) {
        try {
            return service.getEvent(id);
        } catch (NoRecordException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_BREWER', 'ROLE_ADMIN')")
    @PutMapping(path = "/events/{id}")
    public EventPostResponseDto update(
            @PathVariable int id,
            @Valid @RequestBody EventPostRequestDto body,
            Principal principal
    ) {
        EventPostResponseDto event = null;
        try {
            event = service.put(id, body, principal.getName());
            return event;
        } catch (NoRecordException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping(path = "/events")
    public List<EventGetResponseDto> list() {
        return service.getEvents();
    }

    @GetMapping(path = "/breweries/{id}/events")
    public List<EventGetResponseDto> listByBrewery(@PathVariable int id,
                                                   @RequestParam(name = "from-date" ,required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date minDate,
                                                   @RequestParam(name = "to-date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date maxDate,
                                                   @RequestParam(required = false) Boolean over21,
                                                   @RequestParam(required = false) String query) {
        return service.listEventsByBrewery(id, minDate, maxDate, over21, query);
    }

    @DeleteMapping(path = "/events/{id}")
    @PreAuthorize("hasAnyRole('ROLE_BREWER', 'ROLE_ADMIN')")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id, Principal principal) {
        try {
            service.delete(id, principal);
        } catch (UnauthorizedUserException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping(path = "/events/featured")
    @PreAuthorize("permitAll()")
    public List<EventGetResponseDto> get4Events() {
        return service.get4Events();
    }
}
