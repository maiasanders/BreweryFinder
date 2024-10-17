package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.exception.NoRecordException;
import com.techelevator.model.Brewery;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class JdbcBreweryDaoTest extends BaseDaoTests {

    private JdbcBreweryDao dao;
    private final Brewery BREWERY_1 = new Brewery(1, "brewery1", 1, "user1", "address1", "city1", "VA", "11111", "about us 1", true, false, true, true, "Su,M,Tu,W,Th,F,Sa", "10:00 AM", "11:59 PM", "website1.com", "picUrl");
    private final Brewery BREWERY_2 = new Brewery(2, "brewery2", 2, "user2", "address2", "city2", "VA", "22222", "about us 2", false, true, true, true, "Su,M,W,Th,F,Sa", "12:00 PM", "02:00 AM", "website2.com", "picUrl");
    private final Brewery BREWERY_3 = new Brewery(3, "brewery3", 3, "user3", "address3", "city1", "IL", "33333", "about us 3", false, true, false, false, "M,Tu,W,Th,Fr", "12:00 PM", "11:30 PM", "website3.com", "picUrl");
    private final Brewery BREWERY_4 = new Brewery(4, "brewery4", 4, "user4", "address4", "city1", "MA", "44444", "about us 4", true, false, true, false, "Su,M,Tu,Th,F,Sa", "01:00 PM", "10:00 PM", "website4.com", "picUrl");
    private final Brewery BREWERY_5 = new Brewery(5, "brewery5", 5, "user5", "address5", "city1", "VA", "11111", "about us 5", true, false, false, true, "Su,M,Tu,Th,F,Sa","12:00 PM", "11:59 PM", "website5.com", "picUrl");
    private final String CITY_WITHOUT_BREWERIES = "dry city";
    private final String STATE_WITHOUT_BREWERIES = "OR";
    private final String WRONG_SIZE_MSG =  " returned a list of the wrong size";
    private final int NONEXISTENT_ID = 100;

    @Before
    public void setUp() {
        JdbcTemplate template = new JdbcTemplate(dataSource);
        dao = new JdbcBreweryDao(template);
    }

    @Test
    public void getAllBreweriesReturnsCorrectList() {
        // Act - get returned list
        List<Brewery> breweries = dao.getAllBreweries();
        // Assert - check size, first and last entry
        Assert.assertEquals("Size of List returned by getAllBreweries should be 6, but returned another number", 5, breweries.size());
        assertBreweriesMatch(BREWERY_1, breweries.get(0));
        assertBreweriesMatch(BREWERY_5, breweries.get(4));
    }

    @Test
    public void getBreweriesByStateReturnsAppropriateListForStateWithBreweries() {
        List<Brewery> breweries = dao.getBreweriesByState("VA");
        Assert.assertEquals("Size of List returned by getBreweriesByState(VA) should be 2 but returns a larger or smaller number", 3, breweries.size());
        assertBreweriesMatch(BREWERY_1, breweries.get(0));
        assertBreweriesMatch(BREWERY_5, breweries.get(2));
    }

    @Test
    public void getBreweriesByStateReturnsEmptyListForStateWithoutBreweries() {
        List<Brewery> breweries = dao.getBreweriesByState(STATE_WITHOUT_BREWERIES);
        Assert.assertEquals("getBreweriesForState() should return empty list if no breweries in state", 0, breweries.size());
    }

    @Test
    public void getBreweriesByCityHandlesCityWithMultipleBreweriesAndIsCaseInsensitive() {
        List<Brewery> breweries = dao.getBreweriesByCity("City1");
        Assert.assertEquals("getBreweriesByCity() " + WRONG_SIZE_MSG, 4, breweries.size());
        assertBreweriesMatch(BREWERY_1, breweries.get(0));
        assertBreweriesMatch(BREWERY_5, breweries.get(3));
    }
    @Test
    public void getBreweriesByCityReturnsEmptyListForCityWithoutBreweries() {
        List<Brewery> breweries = dao.getBreweriesByCity(CITY_WITHOUT_BREWERIES);
        Assert.assertEquals("getBreweriesByCity should return an empty list if no breweries found", 0, breweries.size());
    }

    @Test
    public void getBreweriesByCityHandlesPartialMatches() {
        List<Brewery> breweries = dao.getBreweriesByCity("y2");
        Assert.assertEquals("getBreweriesByCity()" + WRONG_SIZE_MSG, 1, breweries.size());
    }


    @Test
    public void getBreweriesServeFoodHandlesTrue() {
        List<Brewery> breweries = dao.getBreweriesServeFood(true);
        Assert.assertEquals("getBreweriesServeFood()" + WRONG_SIZE_MSG, 3, breweries.size());
        assertBreweriesMatch(BREWERY_1, breweries.get(0));
        assertBreweriesMatch(BREWERY_5, breweries.get(2));
    }

    @Test
    public void getBreweriesServeFoodHandlesFalse() {
        List<Brewery> breweries = dao.getBreweriesServeFood(false);
        Assert.assertEquals("getBreweriesServeFood()" + WRONG_SIZE_MSG, 2, breweries.size());
        assertBreweriesMatch(BREWERY_2, breweries.get(0));
        assertBreweriesMatch(BREWERY_3, breweries.get(1));
    }

    @Test
    public void getKidFriendlyBreweriesHandlesTrue() {
        List<Brewery> breweries = dao.getKidFriendlyBreweries(true);
        Assert.assertEquals("getKidFriendlyBreweries(true)" + WRONG_SIZE_MSG, 3, breweries.size());
        assertBreweriesMatch(BREWERY_1, breweries.get(0));
        assertBreweriesMatch(BREWERY_4, breweries.get(2));
    }

    @Test
    public void getKidFriendlyBreweriesHandlesFalse() {
        List<Brewery> breweries = dao.getKidFriendlyBreweries(false);
        Assert.assertEquals("getFriendlyBreweries(false)" + WRONG_SIZE_MSG, 2, breweries.size());
        assertBreweriesMatch(BREWERY_3, breweries.get(0));
        assertBreweriesMatch(BREWERY_5, breweries.get(1));
    }

    @Test
    public void getDogFriendlyBreweriesHandlesTrue() {
        List<Brewery> breweries = dao.getDogFriendlyBreweries(true);
        Assert.assertEquals("getDogFriendlyBreweries(true)" + WRONG_SIZE_MSG, 3, breweries.size());
        assertBreweriesMatch(BREWERY_1, breweries.get(0));
        assertBreweriesMatch(BREWERY_5, breweries.get(2));
    }

    @Test
    public void getDogFriendlyBreweriesHandlesFalse() {
        List<Brewery> breweries = dao.getDogFriendlyBreweries(false);
        Assert.assertEquals("getDogFriendlyBreweries(false)" + WRONG_SIZE_MSG, 2, breweries.size());
        assertBreweriesMatch(BREWERY_3, breweries.get(0));
        assertBreweriesMatch(BREWERY_4, breweries.get(1));
    }

    @Test
    public void getBreweriesByCityStateHandlesStateWithMultipleCities() {
        List<Brewery> breweries = dao.getBreweriesByCityState("city2", "VA");
        Assert.assertEquals("getBreweriesByCityState" + WRONG_SIZE_MSG, 1, breweries.size());
        assertBreweriesMatch(BREWERY_2, breweries.get(0));
    }

    @Test
    public void getBreweriesByCityStateHandlesCityInMultipleStates() {
        List<Brewery> breweries = dao.getBreweriesByCityState("city1", "VA");
        Assert.assertEquals("getBreweriesByCityState" + WRONG_SIZE_MSG, 2, breweries.size());
        assertBreweriesMatch(BREWERY_1, breweries.get(0));
        assertBreweriesMatch(BREWERY_5, breweries.get(1));
    }

    @Test
    public void getBreweriesByCityStateReturnsEmptyListForNoMatches() {
        List<Brewery> breweries = dao.getBreweriesByCityState("city", STATE_WITHOUT_BREWERIES);
        Assert.assertEquals("getBreweriesByCityState should return an empty list for no matches", 0, breweries.size());
    }

    @Test
    public void getBreweriesByCityHasFoodReturnsCorrectList() {
        List<Brewery> breweries = dao.getBreweriesByCityHasFood("city1", false);
        Assert.assertEquals("getBreweriesByCityHasFood" + WRONG_SIZE_MSG, 1, breweries.size());
        assertBreweriesMatch(BREWERY_3, breweries.get(0));
    }

    @Test
    public void getBreweriesByCityHasFoodReturnsEmptyList() {
        List<Brewery> breweries = dao.getBreweriesByCityHasFood("city2", true);
        Assert.assertEquals("getBreweriesByCityHasFood should return an empty list if no matches", 0, breweries.size());
    }

    @Test
    public void getBreweriesByCityKidsReturnsCorrectList() {
        List<Brewery> breweries = dao.getBreweriesByCityKids("city1", false);
        Assert.assertEquals("getBreweriesByCityKids" + WRONG_SIZE_MSG, 2, breweries.size());
        assertBreweriesMatch(BREWERY_3, breweries.get(0));
        assertBreweriesMatch(BREWERY_5, breweries.get(1));
    }

    @Test
    public void getBreweriesByCityKidsReturnsEmptyListIfNoMatch() {
        List<Brewery> breweries = dao.getBreweriesByCityKids("city2", false);
        Assert.assertEquals("getBreweriesByCityKids should return an empty list if no matches", 0, breweries.size());
    }

    @Test
    public void getBreweriesByCityDogsReturnsCorrectList() {
        List<Brewery> breweries = dao.getBreweriesByCityDogs("city1", true);
        Assert.assertEquals("getBreweriesByCityDogs" + WRONG_SIZE_MSG, 2, breweries.size());
        assertBreweriesMatch(BREWERY_1, breweries.get(0));
        assertBreweriesMatch(BREWERY_5, breweries.get(1));
    }

    @Test
    public void getBreweriesByCityDogsReturnsEmptyListIfNoMatch() {
        List<Brewery> breweries = dao.getBreweriesByCityDogs("city2", false);
        Assert.assertEquals("getBreweriesByCityDogs should return an empty list if no matches", 0, breweries.size());
    }

    @Test
    public void getBreweriesByStateFoodReturnsCorrectList() {
        List<Brewery> breweries = dao.getBreweriesByStateFood("VA", true);
        Assert.assertEquals("getBreweriesByStateFood" + WRONG_SIZE_MSG, 2, breweries.size());
        assertBreweriesMatch(BREWERY_1, breweries.get(0));
        assertBreweriesMatch(BREWERY_5, breweries.get(1));
    }

    @Test
    public void getBreweriesByStateFoodReturnsEmptyListNoMatch() {
        List<Brewery> breweries = dao.getBreweriesByStateFood("MA", false);
        Assert.assertEquals("getBreweriesByStateFood should return an empty list if no matches found", 0, breweries.size());
    }

    @Test
    public void getBreweriesByStateKidsReturnsCorrectList() {
        List<Brewery> breweries = dao.getBreweriesByStateKids("VA", true);
        Assert.assertEquals("getBreweriesByStateKids returned list of wrong size", 2, breweries.size());
        assertBreweriesMatch(BREWERY_1, breweries.get(0));
        assertBreweriesMatch(BREWERY_2, breweries.get(1));
    }

    @Test
    public void getBreweriesByStateKidsReturnsEmptyListForNoMatches() {
        List<Brewery> breweries = dao.getBreweriesByStateKids("MA", false);
        Assert.assertEquals("getBreweriesByStateKids should return an empty list if no matches", 0, breweries.size());
    }

    @Test
    public void getBreweriesByStateDogsReturnsCorrectList() {
        List<Brewery> breweries = dao.getBreweriesByStateDogs("VA", true);
        Assert.assertEquals("getBreweriesByStateDogs returns list of incorrect size", 3, breweries.size());
        assertBreweriesMatch(BREWERY_1, breweries.get(0));
        assertBreweriesMatch(BREWERY_5, breweries.get(2));
    }

    @Test
    public void getBreweriesByStateDogsReturnsEmptyListForNoMatches() {
        List<Brewery> breweries = dao.getBreweriesByStateDogs("MA", true);
        Assert.assertEquals("getBreweriesByStateDogs should return an empty list if no matches", 0, breweries.size());
    }
// TODO add more test paths for filtered searches?
    @Test
    public void getBreweriesByFoodKids() {
        List<Brewery> breweries = dao.getBreweriesByFoodKids(true, true);
        Assert.assertEquals("getBreweriesByFoodKids returned a list of the wrong size", 2, breweries.size());
        assertBreweriesMatch(BREWERY_1, breweries.get(0));
        assertBreweriesMatch(BREWERY_4, breweries.get(1));
    }

    @Test
    public void getBreweriesByFoodDogs() {
        List<Brewery> breweries = dao.getBreweriesByFoodDogs(true, true);
        Assert.assertEquals("getBreweriesByFoodDogs returned a list of hte wrong size", 2, breweries.size());
        assertBreweriesMatch(BREWERY_1, breweries.get(0));
        assertBreweriesMatch(BREWERY_5, breweries.get(1));
    }

    @Test
    public void getBreweriesByKidsDogs() {
        List<Brewery> breweries = dao.getBreweriesByKidsDogs(true, true);
        Assert.assertEquals("getBreweriesByKidsDogs returned a list of the wrong size", 2, breweries.size());
        assertBreweriesMatch(BREWERY_1, breweries.get(0));
        assertBreweriesMatch(BREWERY_2, breweries.get(1));
    }

    @Test
    public void getBreweriesByStateCityFood() {
        List<Brewery> breweries = dao.getBreweriesByStateCityFood("VA", "1", true);
        Assert.assertEquals("getBreweriesByStateCityFood returns list of wrong size", 2, breweries.size());
        assertBreweriesMatch(BREWERY_1, breweries.get(0));
        assertBreweriesMatch(BREWERY_5, breweries.get(1));
    }

    @Test
    public void getBreweriesByStateCityKids() {
        List<Brewery> breweries = dao.getBreweriesByStateCityKids("VA", "city1", true);
        Assert.assertEquals("getBreweriesByStateCityKids returned list of incorrect size", 1, breweries.size());
        assertBreweriesMatch(BREWERY_1, breweries.get(0));
    }

    @Test
    public void getBreweriesByStateCityDogs() {
        List<Brewery> breweries = dao.getBreweriesByStateCityDogs("VA", "city1", true);
        Assert.assertEquals("getBreweriesByStateCityDogs returned list of incorrect size", 2, breweries.size());
        assertBreweriesMatch(BREWERY_1, breweries.get(0));
        assertBreweriesMatch(BREWERY_5, breweries.get(1));
    }

    @Test
    public void getBreweriesByStateFoodKids() {
        List<Brewery> breweries = dao.getBreweriesByStateFoodKids("va", true, false);
        Assert.assertEquals("getBreweriesByStateFoodKids returned list of wrong size", 1, breweries.size());
        assertBreweriesMatch(BREWERY_5, breweries.get(0));
    }

    @Test
    public void getBreweriesByStateFoodDogs() {
        List<Brewery> breweries = dao.getBreweriesByStateFoodDogs("VA", true, true);
        Assert.assertEquals("getBreweriesByStateFoodDogs returned list of wrong size", 2, breweries.size());
        assertBreweriesMatch(BREWERY_1, breweries.get(0));
        assertBreweriesMatch(BREWERY_5, breweries.get(1));
    }

    @Test
    public void getBreweriesByCityFoodKids() {
        List<Brewery> breweries = dao.getBreweriesByCityFoodKids("city1", true, true);
        Assert.assertEquals("getBreweriesByCityFoodKids returned list of wrong size", 2, breweries.size());
        assertBreweriesMatch(BREWERY_1, breweries.get(0));
        assertBreweriesMatch(BREWERY_4, breweries.get(1));
    }

    @Test
    public void getBreweriesByCityFoodDogs() {
        List<Brewery> breweries = dao.getBreweriesByCityFoodDogs("city1", true, true);
        Assert.assertEquals("getBreweriesByCityFoodDogs returned a list of the wrong size", 2, breweries.size());
        assertBreweriesMatch(BREWERY_1, breweries.get(0));
        assertBreweriesMatch(BREWERY_5, breweries.get(1));
    }

    @Test
    public void getBreweriesByCityKidsDogs() {
        List<Brewery> breweries = dao.getBreweriesByCityKidsDogs("city1", true, true);
        Assert.assertEquals("getBreweriesByCityKidsDogs returned list of incorrect size", 1, breweries.size());
        assertBreweriesMatch(BREWERY_1, breweries.get(0));
    }

    @Test
    public void getBreweriesByFoodKidsDogs() {
        List<Brewery> breweries = dao.getBreweriesByFoodKidsDogs(false, true, true);
        Assert.assertEquals("getBreweriesByFoodKidsDogs returned a list of incorrect size", 1, breweries.size());
        assertBreweriesMatch(BREWERY_2, breweries.get(0));
    }


    @Test
    public void getBreweriesByStateCityFoodKids() {
        List<Brewery> breweries = dao.getBreweriesByStateCityFoodKids("VA", "city1", true, true);
        Assert.assertEquals("getBreweriesByStateCityFoodKids returned a list of incorrect size", 1, breweries.size());
        assertBreweriesMatch(BREWERY_1, breweries.get(0));
    }

    @Test
    public void getBreweriesByStateCityFoodDogs() {
        List<Brewery> breweries = dao.getBreweriesByStateCityFoodDogs("Va", "city1", true, true);
        Assert.assertEquals("getBreweriesByStateCityFoodDogs retuned list of incorrect size", 2, breweries.size());
        assertBreweriesMatch(BREWERY_1, breweries.get(0));
        assertBreweriesMatch(BREWERY_5, breweries.get(1));
    }

    @Test
    public void getBreweriesByStateFoodKidsDogs() {
        List<Brewery> breweries = dao.getBreweriesByStateFoodKidsDogs("va", true, true, true);
        Assert.assertEquals("getBreweriesByStateFoodKidsDogs returned list of incorrect size", 1, breweries.size());
        assertBreweriesMatch(BREWERY_1, breweries.get(0));
    }

    @Test
    public void getBreweriesByCityFoodKidsDogs() {
        List<Brewery> breweries = dao.getBreweriesByCityFoodKidsDogs("city1", true, true, false);
        Assert.assertEquals("getBreweriesByCityFoodKidsDogs returned list of incorrect size", 1, breweries.size());
        assertBreweriesMatch(BREWERY_4, breweries.get(0));
    }

    @Test
    public void getBreweriesByStateCityFoodKidsDogs() {
        List<Brewery> breweries = dao.getBreweriesByStateCityFoodKidsDogs("IL", "city1", false, false, false);
        Assert.assertEquals("getBreweriesByStateCityFoodKidsDogs returned list of incorrect size", 1, breweries.size());
        assertBreweriesMatch(BREWERY_3, breweries.get(0));
    }

    @Test
    public void getBreweriesByStateCityKidsDogsReturnsCorrectListAndHandlesPartialMatchesForCity() {
        List<Brewery> breweries = dao.getBreweriesByStateCityKidsDogs("VA", "city", true, true);
        Assert.assertEquals("getBreweriesByStateCityKidsDogs returned list of incorrect size", 2, breweries.size());
        assertBreweriesMatch(BREWERY_1, breweries.get(0));
        assertBreweriesMatch(BREWERY_2, breweries.get(1));
    }

    @Test
    public void getBreweryByIdReturnsExistingBrewery() {
        Brewery brewery = dao.getBreweryById(3);
        assertBreweriesMatch(BREWERY_3, brewery);
    }

    @Test
    public void getBreweryByIdThrowsCorrectExceptionForNonexistentId() {
        try {
            Brewery brewery = dao.getBreweryById(NONEXISTENT_ID);

            Assert.fail("getBreweryById should throw exception if no entry found");
        } catch (NoRecordException e) {
            return;
        } catch (Exception e) {
            Assert.fail("getBreweryById throws incorrect exception type");
        }
    }

    // TODO fix createBrewery method and add a test to check for
    @Test
    public void createBreweryAssignsIdAndValuesProperly() {
        // Arrange - create brewery & set properties
        Brewery brewery = getNewBrewery();
        // Act - add brewery to db and retrieve return value
        Brewery newBrewery = dao.createBrewery(brewery);
        Brewery retrievedBrewery = dao.getBreweryById(newBrewery.getBreweryId());
        // Assert - check that breweryId is assigned and all properties match
        Assert.assertNotEquals("createBrewery should assign a brewerId value", 0, retrievedBrewery.getBreweryId());
        Assert.assertEquals("breweryName not assigned properly", brewery.getBreweryName(), retrievedBrewery.getBreweryName());
        Assert.assertEquals("brewerId not assigned properly", brewery.getBrewerId(), retrievedBrewery.getBrewerId());
        Assert.assertEquals("brewerName not assigned properly", "user1", retrievedBrewery.getBrewerName());
        Assert.assertEquals("address not assigned properly", brewery.getAddress(), retrievedBrewery.getAddress());
        Assert.assertEquals("city not assigned properly", brewery.getCity(), retrievedBrewery.getCity());
        Assert.assertEquals("state not assigned properly", brewery.getState(), retrievedBrewery.getState());
        Assert.assertEquals("zip not assigned properly", brewery.getZip(), retrievedBrewery.getZip());
        Assert.assertEquals("aboutUs not assigned properly", brewery.getAboutUs(), retrievedBrewery.getAboutUs());
        Assert.assertEquals("servesFood not assigned properly", brewery.isServesFood(), retrievedBrewery.isServesFood());
        Assert.assertEquals("hasFoodTrucks not assigned properly", brewery.isHasFoodTrucks(), retrievedBrewery.isHasFoodTrucks());
        Assert.assertEquals("isKidFriendly not assigned properly", brewery.isKidFriendly(), retrievedBrewery.isKidFriendly());
        Assert.assertEquals("isDogFriendly not assigned properly", brewery.isDogFriendly(), retrievedBrewery.isDogFriendly());
        Assert.assertEquals("daysOpen not assigned properly", brewery.getDaysOpen(), retrievedBrewery.getDaysOpen());
        Assert.assertEquals("openTime not assigned properly", brewery.getOpenTime(), retrievedBrewery.getOpenTime());
        Assert.assertEquals("closeTime not assigned properly", brewery.getCloseTime(), retrievedBrewery.getCloseTime());
        Assert.assertEquals("website not assigned properly", brewery.getWebsite(), retrievedBrewery.getWebsite());
    }

    @Test
    public void createBreweryThrowsDaoExceptionForBadInputs() {
        Brewery brewery = getNewBrewery();
        brewery.setBrewerId(NONEXISTENT_ID);
        try {
            dao.createBrewery(brewery);

            Assert.fail("Passing bad values to createBrewery should throw an exception");
        } catch (DaoException e) {
            return;
        } catch (Exception e) {
            Assert.fail("Passing bad values to createBrewery should throw DaoException but threw another type");
        }
    }

    private static Brewery getNewBrewery() {
        Brewery brewery = new Brewery();
        brewery.setBreweryName("new brewery");
        brewery.setBrewerId(1);
        brewery.setAddress("new address");
        brewery.setCity("new city");
        brewery.setState("IA");
        brewery.setZip("99999");
        brewery.setAboutUs("we're new");
        brewery.setServesFood(true);
        brewery.setHasFoodTrucks(true);
        brewery.setKidFriendly(true);
        brewery.setDogFriendly(true);
        brewery.setDaysOpen("Su,M");
        brewery.setOpenTime("11:00 AM");
        brewery.setCloseTime("08:00 PM");
        brewery.setWebsite("newsite.com");
        return brewery;
    }

    @Test
    public void updateBreweryProperlyUpdatesValues() {
        Brewery updatedBrewery = new Brewery(1, "brewery11", 2, "user2", "address11", "city11", "PA", "11112", "about us 11", false, true, false, false, "Su,M,Tu,W,Th,F", "11:00:00", "23:00:00", "website11.com", "picUrl");
        Brewery retrievedBrewery = dao.updateBrewery(updatedBrewery);
        assertBreweriesMatch(updatedBrewery, retrievedBrewery);
    }

    @Test
    public void updateBreweryThrowsExceptionIfBreweryDoesntExist() {
        try {
            Brewery updatedBrewery = new Brewery(NONEXISTENT_ID, "brewery11", 2, "user2", "address11", "city11", "PA", "11112", "about us 11", false, true, false, false, "Su,M,Tu,W,Th,F", "11:00:00", "23:00:00", "website11.com", "picUrl");
            dao.updateBrewery(updatedBrewery);

            Assert.fail("updateBrewery should throw exception for nonexistent brewery");
        } catch (NoRecordException e) {
            return;
        } catch (Exception e) {
            Assert.fail("updateBrewery should throw DaoException for nonexistent brewery but threw another exception type");
        }
    }

    @Test
    public void updateBreweryThrowsCorrectExceptionForBadValues() {
        Brewery updatedBrewery = new Brewery(1, "brewery11", 2, "user2", "address11", "city11", "Pennsylvania", "11112", "about us 11", false, true, false, false, "Su,M,Tu,W,Th,F", "11:00:00", "23:00:00", "website11.com", "picUrl");
        try {
            dao.updateBrewery(updatedBrewery);

            Assert.fail("updateBrewery should throw exception when passing invalid values");
        } catch (DaoException e) {
            return;
        } catch (Exception e) {
            Assert.fail("updateBrewery should throw DaoException for bad values, but threw something else");
        }
    }

    @Test
    public void getBreweryByBrewerIdReturnsCorrectsBrewery() {
        Brewery brewery = dao.getBreweryByBrewerId(2);
        assertBreweriesMatch(BREWERY_2, brewery);
    }

    @Test
    public void getBreweryByBrewerIdThrowsExceptionIfUserDoesntExist() {
        try {
            dao.getBreweryByBrewerId(NONEXISTENT_ID);
            Assert.fail("getBreweryByBrewerId should throw an exception if brewerId doesn't exist");
        } catch (NoRecordException e) {
            return;
        } catch (Exception e) {
            Assert.fail("getBreweryByBrewerId should throw NoRecordException if brewerId doesn't exist, but threw something else");
        }
    }

    @Test
    public void getBreweryByBrewerIdThrowsExceptionIfUserNotABrewer() {
        try {
            dao.getBreweryByBrewerId(6);
            Assert.fail("getBreweryByBrewerId should throw an exception if brewerId doesn't match a brewer");
        } catch (NoRecordException e) {
            return;
        } catch (Exception e) {
            Assert.fail("getBreweryByBrewerId should throw NoRecordException if brewerId doesn't match a brewer, but threw something else");
        }
    }

    @Test
    public void deleteBreweryByIdRemovesBreweryAndDependencies() {
        int rows = dao.deleteBreweryById(1);
        Assert.assertEquals("deleteBreweryById should delete 1 row from brewery but deleted 0 or many", 1, rows);
        try {
            dao.getBreweryByBrewerId(1);
            Assert.fail("deleteBreweryById did not remove the selected brewery");
        } catch (NoRecordException e) {
            return;
        }
    }

    private void assertBreweriesMatch(Brewery expected, Brewery actual) {
        Assert.assertEquals(expected.getBreweryId(), actual.getBreweryId());
        Assert.assertEquals(expected.getBreweryName(), actual.getBreweryName());
        Assert.assertEquals(expected.getBrewerId(), actual.getBrewerId());
        Assert.assertEquals(expected.getBrewerName(), actual.getBrewerName());
        Assert.assertEquals(expected.getAddress(), actual.getAddress());
        Assert.assertEquals(expected.getCity(), actual.getCity());
        Assert.assertEquals(expected.getState(), actual.getState());
        Assert.assertEquals(expected.getZip(), actual.getZip());
        Assert.assertEquals(expected.getAboutUs(), actual.getAboutUs());
        Assert.assertEquals(expected.isServesFood(), actual.isServesFood());
        Assert.assertEquals(expected.isKidFriendly(), actual.isKidFriendly());
        Assert.assertEquals(expected.isDogFriendly(), actual.isDogFriendly());
        Assert.assertEquals(expected.getDaysOpen(), actual.getDaysOpen());
        Assert.assertEquals(expected.getOpenTime(), actual.getOpenTime());
        Assert.assertEquals(expected.getCloseTime(), actual.getCloseTime());
        Assert.assertEquals(expected.getWebsite(), actual.getWebsite());
    }
}