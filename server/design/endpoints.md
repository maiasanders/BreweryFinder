# API Endpoints
|   Endpoint   |   Method  |Query Params|   Description     |Success|Error | Authentication|
|:---------:|:---------:|:---------:|:-----------------:|:-----:|:----:|:-------------:|
|/api/login|POST| |Login user and receive JWT|200|400, 422|None|
|/api/register|POST| |Create a new user|201|400, 422|None|
|/api/breweries|POST| |Create a new brewery|201|401, 403|ADMIN|
|/api/breweries|GET|state, city, has_food, kid_friendly, dog_friendly|List of breweries|200|401|None|
|/api/breweries/{id}|GET| |View one brewery info|200|401|User|
|/api/breweries/{id}|PUT| |Update brewery info|200|401, 403|Brewer, ADMIN|
|/api/breweries/{id}/beers|GET|string_query(check for string in name, season, desc), abv_lte, abv_gte, style?|List beers from one brewery|200|401|User|
|/api/beers|POST| |Add a new beer|201|401, 403, 422|Brewer|
|/api/beers/{id}|GET| |View info for 1 beer|200|401|User|
|/api/beers/{id}|DELETE| |Delete a beer from list|204|401, 403|Brewer|
|/api/beers/{id}/reviews|POST| |Leave a review for a beer|201|401, 403|Drinker|
|/api/beers/{id}/reviews|GET|rating, recommended|View reviews and ratings for one beer|200|401|User|
## Flex Goal Endpoints
|   Endpoint   |   Method  |Query Params|   Description     |Success|Error | Authentication|
|:---------:|:---------:|:---------:|:-----------------:|:-----:|:----:|:-------------:|
|/api/beers/{id}|PUT| |Update a beer|200|401, 403|Brewer
|/api/events|POST| |Add a new event at the brewery|201|401, 403|Brewer|
|/api/breweries/{id}/events|GET|day, over_21|Get a list of events at a brewery|200|401, 404|User
|/api/events/{id}|GET| |View one event|200|401, 404|User
|/api/events/{id}|PUT| |Update event|200|401, 403|Brewer, ADMIN
|/api/events/{id}|DELETE| |Remove an event|204|401, 403|Brewer, ADMIN
|/api/breweries/{id}|DELETE| |Delete selected brewery|204|401, 403|ADMIN
|/api/photos|POST| |Add a photo|201|401, 403|Brewer
|/api/breweries/{id}/photos|POST| |Add a photo of a brewery (pending approval)|201|401, 403|Drinker
|/api/photos/{id}|PUT| |Approve a user-submitted photo|200|401, 403|Brewer
|/api/photos/{id}|DELETE| |Delete (or reject) a photo|204|401, 403|Brewer, ADMIN