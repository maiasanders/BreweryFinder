package com.techelevator.dao;

import com.techelevator.model.Beer;

import java.util.List;

public interface BeerDao {
    List<Beer> getBeersByBreweryId(int id);
    Beer createBeer(Beer beer);

    Beer getBeerById(int id);

    int deleteBeerById(int id);

    List<Beer> getBeersByBreweryQuery(int id, String query);

    List<Beer> getBeersByBreweryStyle(int breweryId, int styleId);

    List<Beer> getBeersByBreweryQueryMinAbv(int id, String query, Double minAbv);

    List<Beer> getBeersByBreweryQueryStyle(int breweryId, String query, int styleId);

    List<Beer> getBeersByBreweryMinAbvStyle(int breweryId, Double minAbv, int styleId);

    List<Beer> getBeersByBreweryQueryMinAbvStyle(int breweryId, String query, Double minAbv, int styleId);

    List<Beer> getBeersByBreweryMinAbv(int id, Double minAbv);

    List<Beer> getBeersByBreweryMaxAbv(int id, Double maxAbv);

    List<Beer> getBeersByBreweryQueryMaxAbv(int id, String query, Double maxAbv);

    List<Beer> getBeersByBreweryAbvRange(int id, Double minAbv, Double maxAbv);

    List<Beer> getBeersByBreweryQueryAbvRange(int id, String query, Double minAbv, Double maxAbv);

    List<Beer> getBeersByBreweryQueryMaxAbvStyle(int breweryId, String query, Double maxAbv, int styleId);

    List<Beer> getBeersByBreweryAbvRangeStyle(int breweryId, Double minAbv, Double maxAbv, int styleId);

    List<Beer> getBeersByBreweryQueryAbvRangeStyle(int breweryId, String query, Double minAbv, Double maxAbv, int styleId);

    Beer updateBeer(Beer beer);

    List<Beer> getBeers();
}
