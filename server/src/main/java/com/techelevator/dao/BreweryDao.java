package com.techelevator.dao;

import com.techelevator.model.Brewery;

import java.util.List;

public interface BreweryDao {

    List<Brewery> getAllBreweries();
    List<Brewery> getBreweriesByState(String state);
    List<Brewery> getBreweriesByCity(String city);
    List<Brewery> getBreweriesServeFood(boolean hasFood);
    List<Brewery> getKidFriendlyBreweries(boolean isKidFriendly);
    List<Brewery> getDogFriendlyBreweries(boolean isDogFriendly);
    List<Brewery> getBreweriesByCityState(String city, String state);
    List<Brewery> getBreweriesByCityHasFood(String city, boolean hasFood);
    List<Brewery> getBreweriesByCityKids(String city, boolean kidFriendly);
    List<Brewery> getBreweriesByCityDogs(String city, boolean dogFriendly);
    List<Brewery> getBreweriesByStateFood(String state, boolean hasFood);
    List<Brewery> getBreweriesByStateKids(String state, boolean kidFriendly);
    List<Brewery> getBreweriesByStateDogs(String state, boolean dogFriendly);
    List<Brewery> getBreweriesByFoodKids(boolean hasFood, boolean kidFriendly);
    List<Brewery> getBreweriesByFoodDogs(boolean hasFood, boolean dogFriendly);
    List<Brewery> getBreweriesByKidsDogs(boolean kidFriendly, boolean dogFriendly);
    List<Brewery> getBreweriesByStateCityFood(String state, String city, boolean hasFood);
    List<Brewery> getBreweriesByStateCityKids(String state, String city, boolean kidFriendly);
    List<Brewery> getBreweriesByStateCityDogs(String state, String city, boolean dogFriendly);
    List<Brewery> getBreweriesByStateFoodKids(String state, boolean hasFood, boolean kidFriendly);
    List<Brewery> getBreweriesByStateFoodDogs(String state, boolean hasFood, boolean dogFriendly);
    List<Brewery> getBreweriesByCityFoodKids(String city, boolean hasFood, boolean kidFriendly);
    List<Brewery> getBreweriesByCityFoodDogs(String city, boolean hasFood, boolean dogFriendly);
    List<Brewery> getBreweriesByCityKidsDogs(String city, boolean kidFriendly, boolean dogFriendly);
    List<Brewery> getBreweriesByFoodKidsDogs(boolean hasFood, boolean kidFriendly, boolean dogFriendly);
    List<Brewery> getBreweriesByStateCityFoodKids(String state, String city, boolean hasFood, boolean kidFriendly);
    List<Brewery> getBreweriesByStateCityFoodDogs(String state, String city, boolean hasFood, boolean dogFriendly);
    List<Brewery> getBreweriesByStateFoodKidsDogs(String state, boolean hasFood, boolean kidFriendly, boolean dogFriendly);
    List<Brewery> getBreweriesByCityFoodKidsDogs(String city, boolean hasFood, boolean kidFriendly, boolean dogFriendly);
    List<Brewery> getBreweriesByStateCityFoodKidsDogs(String state, String city, boolean hasFood, boolean kidFriendly, boolean dogFriendly);
    /**
     * Get 1 Brewery from database
     * @param id breweryId of selected brewery
     * @return brewery object
     */
    Brewery getBreweryById(int id);

    /**
     * Add a new brewery to database
     * @param brewery brewery to add
     * @return brewery with id assigned
     */
    Brewery createBrewery(Brewery brewery);

    /**
     * Updates an existing brewery in the database
     * @param brewery brewery with updated properties
     * @return brewery as pulled from database
     */
    Brewery updateBrewery(Brewery brewery);

    /**
     * Returns the brewery for a particular user
     * @param id user id of brewer
     * @return Brewery object associated with user
     */
    Brewery getBreweryByBrewerId(int id);

    List<Brewery> getBreweriesByStateCityKidsDogs(String state, String city, Boolean kidFriendly, Boolean dogFriendly);

    int deleteBreweryById(int id);

    List<Brewery> getBreweriesByBrewer(int id);
}
