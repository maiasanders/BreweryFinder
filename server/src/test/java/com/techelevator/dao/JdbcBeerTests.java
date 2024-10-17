package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.exception.NoRecordException;
import com.techelevator.model.Beer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class JdbcBeerTests extends BaseDaoTests {
    private final Beer BEER_1 = new Beer(1, "beer1", 1, "brewery1", 1, "style1", "desc test 1", 5.0, false, imgUrl);
    private final Beer BEER_2 = new Beer(2, "beer2", 1, "brewery1", 2, "style1", "desc2", 3.6, false, imgUrl);
    private final Beer BEER_3 = new Beer(3, "beer3", 1, "brewery1", 1, "style1", "desc3", 3.5, true, "spring test");
    private final Beer BEER_4 = new Beer(4, "beer4", 2, "brewery2", 1, "style1", "desc4", 4.0, true, "summer");
    private final Beer BEER_5 = new Beer(5, "beer5 test", 1, "brewery1", 2, "style1", "desc5", 0.0, true, "spring");
    private final int NON_EXISTENT_ID = 100;
    private final int BEER_WO_REVIEWS_ID = 2;
    private JdbcBeerDao dao;

    @Before
    public void setup() {
        JdbcTemplate template = new JdbcTemplate(dataSource);
        dao = new JdbcBeerDao(template);
    }

    @Test
    public void getBeersByBreweryIdReturnsCorrectList() {
        List<Beer> beers = dao.getBeersByBreweryId(1);
        Assert.assertEquals("Size of list returned by getBeersByBrewery(1) was incorrect", 4, beers.size());
        assertBeersMatch(BEER_1, beers.get(0));
        assertBeersMatch(BEER_5, beers.get(3));
    }

    @Test
    public void getBeersByBreweryIdReturnsEmptyListForBreweryWithNoBeers() {
        List<Beer> beers = dao.getBeersByBreweryId(3);
        Assert.assertEquals("A brewery without beers should return an empty list when passed to getBeersByBreweryId", 0, beers.size());
    }

    @Test
    public void createBeerAssignsIdAndCorrectValuesForNonSeasonalBeer() {
        Beer beerToAdd = new Beer("new beer", 1, "brewery1", 1, "style1", "new desc", 5.5, false, imgUrl);
        Beer retrievedBeer = dao.createBeer(beerToAdd);

        Assert.assertNotEquals("beerId not assigned", 0, retrievedBeer.getBeerId());
        Assert.assertEquals("beerName improperly assigned", beerToAdd.getBeerName(), retrievedBeer.getBeerName());
        Assert.assertEquals("breweryId not properly assigned", beerToAdd.getBreweryId(), retrievedBeer.getBreweryId());
        Assert.assertEquals("styleId improperly assigned", beerToAdd.getStyleId(), retrievedBeer.getStyleId());
        Assert.assertEquals("desc improperly assigned", beerToAdd.getDesc(), retrievedBeer.getDesc());
        Assert.assertEquals("abv improperly assigned", beerToAdd.getAbv(), retrievedBeer.getAbv(), 0.0);
        Assert.assertEquals("isSeasonal improperly assigned", beerToAdd.isSeasonal(), retrievedBeer.isSeasonal());
        Assert.assertEquals("seasonName improperly assigned", beerToAdd.getSeasonName(), retrievedBeer.getSeasonName());
    }

    @Test
    public void createBeerHandlesSeasonalBeers() {
        Beer beerToAdd = new Beer("new beer", 1, "brewery1", 1, "style1", "new desc", 5.5, true, imgUrl);
        beerToAdd.setSeasonName("spring");
        Beer retrievedBeer = dao.createBeer(beerToAdd);

        Assert.assertNotEquals("beerId not assigned", 0, retrievedBeer.getBeerId());
        Assert.assertEquals("beerName improperly assigned", beerToAdd.getBeerName(), retrievedBeer.getBeerName());
        Assert.assertEquals("breweryId not properly assigned", beerToAdd.getBreweryId(), retrievedBeer.getBreweryId());
        Assert.assertEquals("styleId improperly assigned", beerToAdd.getStyleId(), retrievedBeer.getStyleId());
        Assert.assertEquals("desc improperly assigned", beerToAdd.getDesc(), retrievedBeer.getDesc());
        Assert.assertEquals("abv improperly assigned", beerToAdd.getAbv(), retrievedBeer.getAbv(), 0.0);
        Assert.assertEquals("isSeasonal improperly assigned", beerToAdd.isSeasonal(), retrievedBeer.isSeasonal());
        Assert.assertEquals("seasonName improperly assigned", beerToAdd.getSeasonName(), retrievedBeer.getSeasonName());
    }

    @Test
    public void createBeerThrowsDaoExceptionForBadValues() {
        try {
            Beer beerToAdd = new Beer("new beer", NON_EXISTENT_ID, "brewery100", 1, "style1", "new desc", 5.5, false, imgUrl);
            dao.createBeer(beerToAdd);

            Assert.fail("createBeer should throw exception when passing invalid values");
        } catch (DaoException e) {
            return;
        } catch (Exception e) {
            Assert.fail("createBeer should throw DaoException, but threw something else");
        }
    }

    @Test
    public void getBeersByBreweryIdReturnsEmptyListForNonexistentBrewery() {
        List<Beer> beers = dao.getBeersByBreweryId(NON_EXISTENT_ID);
        Assert.assertEquals("getBeersByBreweryId should return an empty list for a nonexistent brewery", 0, beers.size());
    }

    @Test
    public void getBeerByIdGetsExistingBeer() {
        Beer beer = dao.getBeerById(BEER_2.getBeerId());
        assertBeersMatch(BEER_2, beer);
    }

    @Test
    public void getBeerByIdThrowsNoRecordException() {
        try {
            dao.getBeerById(NON_EXISTENT_ID);
            Assert.fail("Passing a bad value to getBeerById should throw exception");
        } catch (NoRecordException e) {
            return;
        } catch (Exception e) {
            Assert.fail("Passing a bad value to getBeerById should throw NoRecordException but threw something else");
        }
    }

    @Test
    public void getBeersByBreweryQueryReturnsCorrectList() {
        List<Beer> beers = dao.getBeersByBreweryQuery(1, "test");
        Assert.assertEquals("getBeersByBreweryQueryReturnsCorrectList returned a list of incorrect size", 3, beers.size());
        assertBeersMatch(BEER_1, beers.get(0));
        assertBeersMatch(BEER_5, beers.get(2));
    }

    @Test
    public void getBeersByBreweryStyleReturnsCorrectList() {
        List<Beer> beers = dao.getBeersByBreweryStyle(1, 2);
        Assert.assertEquals("getBeersByBreweryStyle returned list of incorrect size", 2, beers.size());
        assertBeersMatch(BEER_2, beers.get(0));
        assertBeersMatch(BEER_5, beers.get(1));
    }

    @Test
    public void getBeersByBreweryQueryMinAbv() {
        List<Beer> beers = dao.getBeersByBreweryQueryMinAbv(1, "test", 3.5);
        Assert.assertEquals("getBeersByBreweryQueryMinAbv retuned list of incorrect size", 2, beers.size());
        assertBeersMatch(BEER_1, beers.get(0));
        assertBeersMatch(BEER_3, beers.get(1));
    }

    @Test
    public void getBeersByBreweryQueryStyle() {
        List<Beer> beers = dao.getBeersByBreweryQueryStyle(1, "test", 1);
        Assert.assertEquals("getBeersByBreweryQueryStyle returned a list of the incorrect size", 2, beers.size());
        assertBeersMatch(BEER_1, beers.get(0));
        assertBeersMatch(BEER_3, beers.get(1));
    }

    @Test
    public void getBeersByBreweryMinAbvStyle() {
        List<Beer> beers = dao.getBeersByBreweryMinAbvStyle(1, 4.0, 1);
        Assert.assertEquals("getBeersByBreweryMinAbvStyle returned list of incorrect size", 1, beers.size());
        assertBeersMatch(BEER_1, beers.get(0));
    }

    @Test
    public void getBeersByBreweryQueryMinAbvStyleReturnsCorrectList() {
        List<Beer> beers = dao.getBeersByBreweryQueryMinAbvStyle(1, "test", 4.0, 1);
        Assert.assertEquals("getBeersByBreweryQueryMinAbvStyle retuned list of incorrect size", 1, beers.size());
        assertBeersMatch(BEER_1, beers.get(0));
    }

    @Test
    public void getBeersByBreweryQueryMinAbvStyleReturnsEmptyListIfNoMatch() {
        List<Beer> beers = dao.getBeersByBreweryQueryMinAbvStyle(1, "test", 5.1, 1);
        Assert.assertEquals("getBeersByBreweryQueryMinAbvStyle should return an empty list if no matches found", 0, beers.size());
    }

    @Test
    public void getBeersByBreweryMinAbv() {
        List<Beer> beers = dao.getBeersByBreweryMinAbv(1, 3.6);
        Assert.assertEquals("getBeersByBreweryMinAbv returned list of wrong size", 2, beers.size());
        assertBeersMatch(BEER_1, beers.get(0));
        assertBeersMatch(BEER_2, beers.get(1));
    }

    @Test
    public void getBeersByBreweryMaxAbv() {
        List<Beer> beers = dao.getBeersByBreweryMaxAbv(1, 3.5);
        Assert.assertEquals("getBeersByBreweryMaxAbv returned list of wrong size", 2, beers.size());
        assertBeersMatch(BEER_3, beers.get(0));
        assertBeersMatch(BEER_5, beers.get(1));
    }

    @Test
    public void getBeersByBreweryQueryMaxAbv() {
        List<Beer> beers = dao.getBeersByBreweryQueryMaxAbv(1, "test", 3.5);
        Assert.assertEquals("getBeersByBreweryQueryMaxAbv returned list of wrong size", 2, beers.size());
        assertBeersMatch(BEER_3, beers.get(0));
        assertBeersMatch(BEER_5, beers.get(1));
    }

    @Test
    public void getBeersByBreweryAbvRange() {
        List<Beer> beers = dao.getBeersByBreweryAbvRange(1, 3.5, 6.0);
        Assert.assertEquals("getBeersByBreweryAbvRange returned a list of wrong size", 3, beers.size());
        assertBeersMatch(BEER_1, beers.get(0));
        assertBeersMatch(BEER_3, beers.get(2));
    }

    @Test
    public void getBeersByBreweryQueryAbvRange() {
        List<Beer> beers = dao.getBeersByBreweryQueryAbvRange(1, "test", 2.0, 4.5);
        Assert.assertEquals("getBeersByBreweryQueryAbvRange returned a list of the wrong size", 1, beers.size());
        assertBeersMatch(BEER_3, beers.get(0));
    }

    @Test
    public void getBeersByBreweryQueryMaxAbvStyle() {
        List<Beer> beers = dao.getBeersByBreweryQueryMaxAbvStyle(1, "test", 3.5, 1);
        Assert.assertEquals("getBeersByBreweryQueryMaxAbvStyle returned a list of wrong size", 1, beers.size());
        assertBeersMatch(BEER_3, beers.get(0));
    }

    @Test
    public void getBeersByBreweryAbvRangeStyle() {
        List<Beer> beers = dao.getBeersByBreweryAbvRangeStyle(1, 0.1, 3.6, 2);
        Assert.assertEquals("getBeersByBreweryAbvRangeStyle returned list of wrong size", 1, beers.size());
        assertBeersMatch(BEER_2, beers.get(0));
    }

    @Test
    public void getBeersByBreweryQueryAbvRangeStyle() {
        List<Beer> beers = dao.getBeersByBreweryQueryAbvRangeStyle(1, "test", 0.1, 4.9, 1);
        Assert.assertEquals("getBeersByBreweryQueryAbvRangeStyle returned list of wrong size", 1, beers.size());
        assertBeersMatch(BEER_3, beers.get(0));
    }

    @Test
    public void deleteBeerByIdDeletesBeerWithNoReviews() {
        int rows = dao.deleteBeerById(BEER_WO_REVIEWS_ID);
        Assert.assertEquals("Expected 1 row deleted by deleteBeerById but got 0 or many", 1, rows);
    }

    @Test
    public void deleteBeerByIdDeletesBeerWithReviews() {
        int rows = dao.deleteBeerById(BEER_1.getBeerId());
        Assert.assertEquals("Expected 1 row deleted by deleteBeerById but got 0 or many", 1, rows);
    }

    @Test
    public void deleteBeerByIdThrowsCorrectException() {
        try {
            dao.deleteBeerById(NON_EXISTENT_ID);
            Assert.fail("deleteBeerById should throw exception if beer doesn't exist");
        } catch (NoRecordException e) {
            return;
        } catch (Exception e) {
            Assert.fail("deleteBeerById should throw NoRecordException, but threw another type of exception");
        }
    }

    @Test
    public void updateBeerReturnsUpdatedBeerWithoutChangingOthers() {
        Beer updatedBeer = new Beer(5, "beer5 updated", 3, "brewery3", 1, "style1", "desc5 updated", 0.1, false, null);
        Beer retrievedBeer = dao.updateBeer(updatedBeer);
        assertBeersMatch(updatedBeer, retrievedBeer);
        assertBeersMatch(BEER_1, dao.getBeerById(1));
    }

    private void assertBeersMatch(Beer expected, Beer actual) {
        Assert.assertEquals(expected.getBeerId(), actual.getBeerId());
        Assert.assertEquals(expected.getBeerName(), actual.getBeerName());
        Assert.assertEquals(expected.getBreweryId(), actual.getBreweryId());
        Assert.assertEquals(expected.getStyleId(), actual.getStyleId());
        Assert.assertEquals(expected.getDesc(), actual.getDesc());
        Assert.assertEquals(expected.getAbv(), actual.getAbv(), 0.0);
        Assert.assertEquals(expected.isSeasonal(), actual.isSeasonal());
        Assert.assertEquals(expected.getSeasonName(), actual.getSeasonName());
    }
}
