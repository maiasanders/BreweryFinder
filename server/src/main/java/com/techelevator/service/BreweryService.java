package com.techelevator.service;

import com.techelevator.dao.BreweryDao;
import com.techelevator.dao.UserDao;
import com.techelevator.exception.UnauthorizedUserException;
import com.techelevator.model.Brewery;
import com.techelevator.model.User;
import com.techelevator.model.dto.BreweryRequestDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BreweryService {

    private UserDao userDao;
    private BreweryDao breweryDao;

    public BreweryService(UserDao userDao, BreweryDao breweryDao) {
        this.userDao = userDao;
        this.breweryDao = breweryDao;
    }

    public Brewery updateBrewery(int id, BreweryRequestDto brewery, String name) {

        Brewery updatedBrewery = mapRequestToBrewery(brewery);
        updatedBrewery.setBreweryId(id);
        User user = userDao.getUserByUsername(name);
        if (user.getAuthoritiesString().contains("ROLE_ADMIN")
                || name.equals(updatedBrewery.getBrewerName())) {
            return breweryDao.updateBrewery(updatedBrewery);
        } else {
            throw new UnauthorizedUserException();
        }
    }

    // TODO finish implementing list method
    public List<Brewery> listBreweries(String state, String city, Boolean hasFood, Boolean kidFriendly, Boolean dogFriendly) {
        if (state == null && city == null && hasFood == null
                && kidFriendly == null && dogFriendly == null) {
            return breweryDao.getAllBreweries();
        } else if (city == null && hasFood == null
                && kidFriendly == null && dogFriendly == null) {
            return breweryDao.getBreweriesByState(state);
        } else if (state == null && hasFood == null
                && kidFriendly == null && dogFriendly == null) {
            return breweryDao.getBreweriesByCity(city);
        } else if (state == null && city == null && kidFriendly == null && dogFriendly == null) {
            return breweryDao.getBreweriesServeFood(hasFood);
        } else if (state == null && city == null && hasFood == null
                && dogFriendly == null) {
            return breweryDao.getKidFriendlyBreweries(kidFriendly);
        } else if (state == null && city == null && hasFood == null
                && kidFriendly == null) {
            return breweryDao.getDogFriendlyBreweries(dogFriendly);
        } else if (hasFood == null && kidFriendly == null && dogFriendly == null) {
            return breweryDao.getBreweriesByCityState(city, state);
        } else if (city == null && kidFriendly == null && dogFriendly == null) {
            return breweryDao.getBreweriesByStateFood(state, hasFood);
        } else if (city == null && hasFood == null && dogFriendly == null) {
            return breweryDao.getBreweriesByStateKids(state, kidFriendly);
        } else if (city == null && hasFood == null && kidFriendly == null) {
            return breweryDao.getBreweriesByStateDogs(state, dogFriendly);
        } else if (state == null && kidFriendly == null && dogFriendly == null) {
            return breweryDao.getBreweriesByCityHasFood(city, hasFood);
        } else if (state == null && hasFood == null && dogFriendly == null) {
            return breweryDao.getBreweriesByCityKids(city, kidFriendly);
        } else if (state == null && hasFood == null && kidFriendly == null) {
            return breweryDao.getBreweriesByCityDogs(city, dogFriendly);
        } else if (state == null && city == null && dogFriendly == null) {
            return breweryDao.getBreweriesByFoodKids(hasFood, kidFriendly);
        } else if (state == null && city == null && kidFriendly == null) {
            return breweryDao.getBreweriesByFoodDogs(hasFood, dogFriendly);
        } else if (state == null && city == null && hasFood == null) {
            return breweryDao.getBreweriesByKidsDogs(kidFriendly, dogFriendly);
        } else if (kidFriendly == null && dogFriendly == null) {
            return breweryDao.getBreweriesByStateCityFood(state, city, hasFood);
        } else if (hasFood == null && dogFriendly == null) {
            return breweryDao.getBreweriesByStateCityKids(state, city, kidFriendly);
        } else if (hasFood == null && kidFriendly == null) {
            return breweryDao.getBreweriesByStateCityDogs(state, city, dogFriendly);
        } else if (state == null && dogFriendly == null) {
            return breweryDao.getBreweriesByCityFoodKids(city, hasFood, kidFriendly);
        } else if (state == null && kidFriendly == null) {
            return breweryDao.getBreweriesByCityFoodDogs(city, hasFood, dogFriendly);
        } else if (state == null && city == null) {
            return breweryDao.getBreweriesByFoodKidsDogs(hasFood, kidFriendly, dogFriendly);
        } else if (city == null && dogFriendly == null) {
            return breweryDao.getBreweriesByStateFoodKids(state, hasFood, kidFriendly);
        } else if (dogFriendly == null) {
            return breweryDao.getBreweriesByStateCityFoodKids(state, city, hasFood, kidFriendly);
        } else if (kidFriendly == null) {
            return breweryDao.getBreweriesByStateCityFoodDogs(state, city, hasFood, dogFriendly);
        } else if (hasFood == null) {
            return breweryDao.getBreweriesByStateCityKidsDogs(state, city, kidFriendly, dogFriendly);
        } else if (city == null) {
            return breweryDao.getBreweriesByStateFoodKidsDogs(state, hasFood, kidFriendly, dogFriendly);
        } else if (state == null) {
            return breweryDao.getBreweriesByCityFoodKidsDogs(city, hasFood, kidFriendly, dogFriendly);
        } else {
            return breweryDao.getBreweriesByStateCityFoodKidsDogs(state, city, hasFood, kidFriendly, dogFriendly);
        }
    }

    public Brewery add(BreweryRequestDto dto, String username) {
        Brewery brewery = mapRequestToBrewery(dto);
        brewery.setBrewerId(userDao.getUserByUsername(username).getId());
        return breweryDao.createBrewery(brewery);
    }

    private Brewery mapRequestToBrewery(BreweryRequestDto dto) {
        Brewery brewery = new Brewery();
        brewery.setBreweryName(dto.getBreweryName());
        brewery.setBrewerId(dto.getBrewerId());
        brewery.setBrewerName(userDao.getUserById(dto.getBrewerId()).getUsername());
        brewery.setAddress(dto.getAddress());
        brewery.setCity(dto.getCity());
        brewery.setState(dto.getState());
        brewery.setZip(dto.getZip());
        brewery.setAboutUs(dto.getAboutUs());
        brewery.setServesFood(dto.isServesFood());
        brewery.setHasFoodTrucks(dto.isHasFoodTrucks());
        brewery.setKidFriendly(dto.isKidFriendly());
        brewery.setDogFriendly(dto.isDogFriendly());
        brewery.setDaysOpen(dto.getDaysOpen());
        brewery.setOpenTime(dto.getOpenTime());
        brewery.setCloseTime(dto.getCloseTime());
        brewery.setWebsite(dto.getWebsite());
        brewery.setPicUrl(dto.getPicUrl());
        return brewery;
    }

    public List<Brewery> getByBrewer(String username) {
        User user = userDao.getUserByUsername(username);
        return breweryDao.getBreweriesByBrewer(user.getId());
    }
}
