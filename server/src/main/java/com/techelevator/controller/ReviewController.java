package com.techelevator.controller;

import com.techelevator.exception.UnauthorizedUserException;
import com.techelevator.model.Review;
import com.techelevator.model.dto.ReviewRequestDto;
import com.techelevator.model.dto.ReviewResponseDto;
import com.techelevator.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@CrossOrigin
@PreAuthorize("isAuthenticated()")
@RestController
//@RestController("/beers/{id}/reviews/")
public class ReviewController {

    private ReviewService service;

    public ReviewController(ReviewService service) {
        this.service = service;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/beers/{id}/reviews/")
    public ReviewResponseDto add(@PathVariable int id, @Valid @RequestBody ReviewRequestDto reviewRequestDto, Principal principal) {
        try {
            return service.add(id, reviewRequestDto, principal.getName());
        } catch (UnauthorizedUserException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping(path = "/beers/{id}/reviews")
    public List<ReviewResponseDto> list(@PathVariable int id,
                             @RequestParam(name = "rating_gte", required = false) Integer minRating,
                             @RequestParam(name = "rating_lte", required = false) Integer maxRating,
                             @RequestParam(required = false) Boolean recommended) {
        return service.list(id, minRating, maxRating, recommended);
    }
}
