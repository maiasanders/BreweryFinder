package com.techelevator.controller;

import com.techelevator.dao.BeerDao;
import com.techelevator.exception.NoRecordException;
import com.techelevator.exception.UnauthorizedUserException;
import com.techelevator.model.dto.BeerRequestDto;
import com.techelevator.model.dto.BeerResponseDto;
import com.techelevator.service.BeerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class BeerController {

    private BeerDao dao;
    private BeerService service;

    public BeerController(BeerDao dao, BeerService service) {
        this.dao = dao;
        this.service = service;
    }

    @GetMapping(path = "/breweries/{id}/beers")
    public List<BeerResponseDto> listByBrewery(@PathVariable int id,
                                    @RequestParam (required = false, name = "query") String stringQuery,
                                    @RequestParam(required = false) Double minAbv,
                                    @RequestParam(required = false) Double maxAbv,
                                    @RequestParam(required = false) String style) {
        return service.listByBrewery(id, stringQuery, minAbv, maxAbv, style);
    }

    @GetMapping(path = "/beers")
    public List<BeerResponseDto> list() {
        return service.list();
    }

    @RequestMapping(path = "/beers", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public BeerResponseDto add(@Valid @RequestBody BeerRequestDto beerDto, Principal principal) {
        try {
            return service.add(beerDto, principal.getName());
        } catch (UnauthorizedUserException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping(path = "/beers/{id}")
    public BeerResponseDto get(@PathVariable int id) {
        try {
            return service.getBeer(id);
        } catch (NoRecordException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ROLE_BREWER')")
    @DeleteMapping(path = "/beers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id, Principal principal) {
        try {
            service.delete(id, principal.getName());
        } catch (UnauthorizedUserException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    @PreAuthorize("hasRole('ROLE_BREWER')")
    @PutMapping(path = "/beers/{id}")
    public BeerResponseDto update(@PathVariable int id, @RequestBody BeerRequestDto dto, Principal principal) {
        return service.update(id, dto, principal.getName());
    }

}
