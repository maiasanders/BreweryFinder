BEGIN TRANSACTION;

-- Users
INSERT INTO users (username,password_hash,role, name, address, city, state_code, zip) VALUES ('user1','user1','ROLE_USER', 'User1 Name', 'User1 address', 'Cleveland', 'OH', '44123');
INSERT INTO users (username,password_hash,role, name, address, city, state_code, zip) VALUES ('user2','user2','ROLE_USER', 'User2 Name', null, 'Beverly Hills', 'CA', '90210');
INSERT INTO users (username,password_hash,role, name, address, city, state_code, zip) VALUES ('user3','user3','ROLE_USER', 'User3 Name', 'User3 address', 'Chicago', 'IL', '60609');
INSERT INTO users (username,password_hash,role, name, address, city, state_code, zip) VALUES ('user4','user4','ROLE_USER', 'User4 Name', 'User4 address', 'Richmond', 'VA', '22222');
INSERT INTO users (username,password_hash,role, name, address, city, state_code, zip) VALUES ('user5','user5','ROLE_USER', 'User5 Name', 'User5 address', 'Chicago', 'IL', '60609');
INSERT INTO users (username,password_hash,role, name, address, city, state_code, zip) VALUES ('user6','user6','ROLE_USER', 'User6 Name', 'User6 address', 'Chicago', 'IL', '60609');

-- Breweries
INSERT INTO brewery (brewery_name, brewer_id, address, city, state_code, zip, about_us, serves_food, has_foodtrucks, kid_friendly, dog_friendly, days_open, open_time, close_time, website) VALUES
    -- Brewery 1
    ('brewery1', 1, 'address1', 'city1', 'VA', '11111', 'about us 1', true, false, true, true, 'Su,M,Tu,W,Th,F,Sa', '10:00 AM', '11:59 PM', 'website1.com'),
    -- Brewery 2
    ('brewery2', 2, 'address2', 'city2', 'VA', '22222', 'about us 2', false, true, true, true, 'Su,M,W,Th,F,Sa', '12:00 PM', '02:00 AM', 'website2.com'),
    -- Brewery 3 - no beers
    ('brewery3', 3, 'address3', 'city1', 'IL', '33333', 'about us 3', false, true, false, false, 'M,Tu,W,Th,Fr', '12:00 PM', '11:30 PM', 'website3.com'),
    -- Brewery 4 - repeats for state and city but different combo
    ('brewery4', 4, 'address4', 'city1', 'MA', '44444', 'about us 4', true, false, true, false, 'Su,M,Tu,Th,F,Sa', '01:00 PM', '10:00 PM', 'website4.com'),
    -- Brewery 5 - repeats state city combo
    ('brewery5', 5, 'address5', 'city1', 'VA', '11111', 'about us 5', true, false, false, true, 'Su,M,Tu,Th,F,Sa', '12:00 PM', '11:59 PM', 'website5.com');

-- Styles
INSERT INTO style (style_name) VALUES
    ('style1'), ('style2'), ('style3');

-- Beers (non-seasonal)
INSERT INTO beer (beer_name, brewery_id, style_id, description, abv, seasonal) VALUES
    ('beer1', 1, 1, 'desc test 1', 5.0, false),
    ('beer2', 1, 2, 'desc2', 3.6, false);

-- Beers (seasonal)
INSERT INTO beer (beer_name, brewery_id, style_id, description, abv, seasonal, season_name) VALUES
    ('beer3', 1, 1, 'desc3', 3.5, true, 'spring test'),
    ('beer4', 2, 1, 'desc4', 4.0, true, 'summer'),
    ('beer5 test', 1, 2, 'desc5', 0.0, true, 'spring');

-- Reviews
INSERT INTO review (beer_id, user_id, review_text, rating, recommended) VALUES
    (1, 2, 'review 1 text', 1, false),
    (1, 3, 'review 2 text', 5, true),
    (3, 2, 'review 3 text', 4, true),
    (3, 4, 'review 4 text', 3, false);

COMMIT TRANSACTION;

