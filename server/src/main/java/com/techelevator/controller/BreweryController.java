package com.techelevator.controller;

import com.techelevator.dao.BreweryDao;
import com.techelevator.exception.NoRecordException;
import com.techelevator.exception.UnauthorizedUserException;
import com.techelevator.model.Brewery;
import com.techelevator.model.dto.BreweryRequestDto;
import com.techelevator.service.BreweryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/breweries")
@PreAuthorize("isAuthenticated()")
public class BreweryController {

    private BreweryDao dao;
    private BreweryService service;

    public BreweryController(BreweryDao dao, BreweryService service) {
        this.dao = dao;
        this.service = service;
    }

    @GetMapping("")
    @PreAuthorize("permitAll()")
    public List<Brewery> list(
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String city,
            @RequestParam(name = "has_food", required = false) Boolean hasFood,
            @RequestParam(name = "kid_friendly", required = false) Boolean kidFriendly,
            @RequestParam(name = "dog_friendly", required = false) Boolean dogFriendly) {
        return service.listBreweries(state, city, hasFood, kidFriendly, dogFriendly);
    }
    @GetMapping(path = "{id}")
    @PreAuthorize("permitAll()")
    public Brewery get(@PathVariable int id) {
        try {
            return dao.getBreweryById(id);
        } catch (NoRecordException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Brewery add(@Valid @RequestBody BreweryRequestDto brewery, Principal principal) {
        return service.add(brewery, principal.getName());
    }

    @PutMapping(path = "{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_BREWER')")
    public Brewery update(@PathVariable int id, @Valid @RequestBody BreweryRequestDto brewery, Principal principal) {
        try {
            return service.updateBrewery(id, brewery, principal.getName());
        } catch (UnauthorizedUserException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping(path = "{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        dao.deleteBreweryById(id);
    }

    @GetMapping(path = "/byuser")
    @PreAuthorize("hasRole('ROLE_BREWER')")
    public List<Brewery> getByBrewer(Principal principal) {
        return service.getByBrewer(principal.getName());
    }
}
