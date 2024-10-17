# Test Plan

## Brewery Dao Tests
### getAllBreweries
* Returns all breweries
### getBreweriesByState
* Returns correct list for state w/ breweries
* Returns empty list for state w/ no breweries
### getBreweriesByCity
* Returns correct list for city w/ multiple breweries
* City is case insensitive
* Returns empty for city w/o breweries
* Handles partial matches
### getBreweriesServeFood
* Returns correct list for true
* Returns correct list for false
### getKidFriendlyBreweries
* Returns correctly for true
* Returns correctly for false
### getDogFriendlyBreweries
* Returns corectly for true
* returns correctly for false
### getBreweriesByCityState
* Handles state w/ multiple city
* Handles city with multiple states
* Returns empty list for city/state without breweries
### getBreweriesByCityHasFood
* Returns correct list
* returns empty if none in city fulfill hasFood
### getBreweriesByCityKids
* Returns correct list
* Returns empty list for no matches
### getBreweriesByCityDogs
* Returns correct list
* Returns empty list if no matches
### getBreweriesByStateFood
* Returns correct list
* Returns empty list if no matches
### getBreweriesByStateKids
* Returns correct list
* Returns empty list if no matches
### getBreweriesByStateDogs
* Returns correctly list
* Returns empty list if no matches
### getBreweriesByFoodKids
* Handles true, true
* handles false, false
* handles true, false
* handles false, true
* returns empty for no matches
### getBreweriesByFoodDogs
* Handles true, true
* handles false, false
* handles true, false
* handles false, true
* returns empty for no matches
### getBreweriesByKidsDogs
* Handles true, true
* handles false, false
* handles true, false
* handles false, true
* returns empty for no matches
### getBreweriesByStateCityFood
* handles true value
* handles false value
* returns empty list for no matches
### getBreweriesByStateCityKids
* handles true value
* handles false value
* returns empty list for no matches
### getBreweriesByStateCityDogs
* handles true value
* handles false value
* returns empty list for no matches
### getBreweriesByCityKidsDogs
* (Happy path) returns appropriate list for true, true
* returns empty list for no matches
* handles true, false
* handles false, true
* handles false, false
### getBreweriesByCityFoodDogs
* (Happy path) returns appropriate list for true, true
* returns empty list for no matches
* handles true, false
* handles false, true
* handles false, false
### getBreweriesByFoodKidsDogs
* handles true, true, true
* handles false, false, false
* handles true, true, false
* handles true, false, false
* handles false, true, true
* handles false, false, true
* handles false, true, false
### getBreweriesByStateCityFoodKids
* Handles true, true
* handles false, false
* handles true, false
* handles false, true
* returns empty for no matches
### getBreweriesByStateCityFoodDogs
* Handles true, true
* handles false, false
* handles true, false
* handles false, true
* returns empty for no matches
### getBreweriesByCityFoodKidsDogs
* handles true, true, true
* handles false, false, false
* handles true, true, false
* handles true, false, false
* handles false, true, true
* handles false, false, true
* handles false, true, false
### getBreweriesByStateFoodKidsDogs
* handles true, true, true
* handles false, false, false
* handles true, true, false
* handles true, false, false
* handles false, true, true
* handles false, false, true
* handles false, true, false
### getBreweriesByStateCityFoodKidsDogs
* handles true, true, true
* handles false, false, false
* handles true, true, false
* handles true, false, false
* handles false, true, true
* handles false, false, true
* handles false, true, false
### getBreweryById()
* Returns existing brewery
* Throws NoRecordException for nonexistent id
### createBrewery
* Creates brewery with valid inputs
* Throws DaoException if inputs invalid
### updateBrewery
* Returns updated brewery if brewery exists and inputs are valid
* Throws exception if brewery doesn't exist
* Throws exception for invalid inputs
### getBreweryByBrewerId
* Gets brewery if one is associated
* Throws exception if user doesn't exist
* Throws exception if user not associated with a brewery

## Beer Dao Tests
### getBeersByBreweryId
* Gets beers for existing brewery with beers
* Empty list if brewery doesn't have beers
* Empty list for non-existent brewery
### createBeer
* Adds and returns non seasonal beer
* Adds and returns seasonal beer
* Throws exception for invalid data
### getBeerById
* Gets existing beer
* Throws NoRecordException for nonexistent beer
### deleteBeerById
* Deleted beer with no reviews
* Deletes beer with reviews
* Throws exception if beer doesn't exist

## Style DAO tests
### getStyleById
* Gets existing style entry
* Throws correct exception if no match
### getStyleByName
* Returns existing style
* Throws correct exception if no match

## Review DAO tests
### getReviewById
* Returns existing review
* Returns null for non-existent style
### getReviewByBeerId
* Returns appropriate list for existing beer w/ reviews
* Returns empty list for existing beer w/o reviews
* Returns empty list for nonexistent beer
### createReview
* Returns review with id assigned
* Throws exception for review with bad values