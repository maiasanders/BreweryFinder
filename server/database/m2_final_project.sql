-- database Brewery
BEGIN TRANSACTION;

-- *************************************************************************************************
-- Drop all db objects in the proper order
-- *************************************************************************************************
DROP TABLE IF EXISTS event_category, category, event, review, beer, style, brewery, users;

-- *************************************************************************************************
-- Create the tables and constraints
-- *************************************************************************************************

--users (name is pluralized because 'user' is a SQL keyword)
CREATE TABLE users (
	user_id SERIAL,
	username varchar(50) NOT NULL UNIQUE,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	name varchar(50) NOT NULL,
	address varchar(100) NULL,
	city varchar(50) NULL,
	state_code char(2) NULL,
	zip varchar(5) NULL,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

CREATE TABLE brewery (
	brewery_id serial PRIMARY KEY,
	brewery_name varchar(100) NOT NULL,
	brewer_id int REFERENCES users(user_id),
	address varchar(200) NOT NULL,
	city varchar(100) NOT NULL,
	state_code char(2) NOT NULL,
	zip char(5) NOT NULL,
	about_us varchar(300),
	serves_food boolean,
	has_foodtrucks boolean,
	kid_friendly boolean,
	dog_friendly boolean,
	days_open varchar(20) NOT NULL,
	open_time char(8) NOT NULL,
	close_time char(8) NOT NULL,
	website varchar(200) NOT NULL);

CREATE TABLE style (
	style_id serial PRIMARY KEY,
	style_name varchar(100) NOT NULL UNIQUE
);

CREATE TABLE beer (
	beer_id serial PRIMARY KEY,
	beer_name varchar(50) NOT NULL,
	brewery_id int REFERENCES brewery(brewery_id),
	style_id int REFERENCES style(style_id),
	description varchar(200) NOT NULL,
	abv decimal(3, 1) NOT NULL,
	seasonal boolean,
	season_name varchar(20),
	picture_url varchar(1000)
);

CREATE TABLE review (
	review_id serial PRIMARY KEY,
	beer_id int REFERENCES beer(beer_id),
	user_id int REFERENCES users(user_id),
	review_text varchar(300),
	rating int NOT NULL,
	recommended boolean,
	CONSTRAINT CK_rating CHECK (rating BETWEEN 1 AND 5)
);

CREATE TABLE event (
    event_id serial PRIMARY KEY,
    event_name varchar(200) NOT NULL,
    brewery_id int REFERENCES brewery(brewery_id),
    event_date date NOT NULL,
    begins char(8) NOT NULL,
    ends char(8),
    description varchar(300) NOT NULL,
    over_21 boolean
);

CREATE TABLE category (
    category_id serial PRIMARY KEY,
    category_name varchar(100) NOT NULL
);

CREATE TABLE event_category (
    event_id int REFERENCES event(event_id),
    category_id int REFERENCES category(category_id),
    CONSTRAINT PK_event_category PRIMARY KEY(event_id, category_id)
);

-- *************************************************************************************************
-- Insert some sample starting data
-- *************************************************************************************************

-- Users
-- Password for all users is password
INSERT INTO users (username, password_hash, role, name, address, city, state_code, zip) VALUES
    ('user', '$2a$10$tmxuYYg1f5T0eXsTPlq/V.DJUKmRHyFbJ.o.liI1T35TFbjs2xiem','ROLE_USER',  'Jack O''Lantern', null, 'Cleveland', 'OH', '44123'),
    ('admin','$2a$10$tmxuYYg1f5T0eXsTPlq/V.DJUKmRHyFbJ.o.liI1T35TFbjs2xiem','ROLE_ADMIN', 'Jill O''Lantern', null, 'Beverly Hills', 'CA', '90210'),
    ('brewersam', '$2a$10$tmxuYYg1f5T0eXsTPlq/V.DJUKmRHyFbJ.o.liI1T35TFbjs2xiem', 'ROLE_BREWER', 'Samuel Adams', '1777 Brewer''s Lane', 'Boston', 'MA', '02109'),
    ('legendarybrewer', '$2a$10$tmxuYYg1f5T0eXsTPlq/V.DJUKmRHyFbJ.o.liI1T35TFbjs2xiem', 'ROLE_BREWER', 'William Smith', '123 Brook Rd', 'Richmond', 'VA', '23224');

-- Breweries
INSERT INTO brewery (brewery_name, brewer_id, address, city, state_code, zip, about_us, serves_food, has_foodtrucks, kid_friendly, dog_friendly, days_open, open_time, close_time, website) VALUES
    (
        'Sam''s Revolutionary Brewery',
        (SELECT user_id FROM users WHERE username = 'brewersam'),
        '1800 Fenwick Ave', 'Boston', 'MA', '02109',
        'A revolutionary brewery in central Boston',
        true, false, true, false,
        'Th,F,Sa,Su,M,Tu', '11:00 AM', '03:00 AM',
        'samsrevobrew.com'
    ),
    (
        'Legendary Brewery',
        (SELECT user_id FROM users WHERE username = 'legendarybrewer'),
        '234 Overbrook Rd', 'Richmond', 'VA', '23224',
        'Long-time brewery serving classic beers',
        false, true, true, true,
        'Su,M,Tu,W,Th,F,Sa', '02:00 PM', '02:00 PM', 'legendary.com'
    );

-- Styles for beers
INSERT INTO style (style_name) VALUES
    ('Pilsner'), ('Lager'), ('Stout'), ('IPA'), ('Brown Ale'), ('Specialty Ale'), ('Shandy'), ('White Ale');

-- Beers: non-seasonal
INSERT INTO beer (beer_name, brewery_id, style_id, description, abv, seasonal, picture_url) VALUES
    (
        'Lager Live the Revolution!',
        (SELECT brewery_id FROM brewery WHERE brewery_name = 'Sam''s Revolutionary Brewery'),
        (SELECT style_id FROM style WHERE style_name = 'Lager'),
        'Radical lager, with well-balanced hops and malt and citrus undertones',
        4.8, false, 'https://placehold.co/600x400'
    ), (
        'Tea Party Ale',
        (SELECT brewery_id FROM brewery WHERE brewery_name = 'Sam''s Revolutionary Brewery'),
        (SELECT style_id FROM style WHERE style_name = 'Specialty Ale'),
        'Experimental brew with black tea',
        3.0, false, 'https://placehold.co/600x400'
    ), (
        'Sam''s Original',
        (SELECT brewery_id FROM brewery WHERE brewery_name = 'Sam''s Revolutionary Brewery'),
        (SELECT style_id FROM style WHERE style_name = 'Pilsner'),
        'Classic pilsner, refreshing year round!',
        3.8, false, 'https://placehold.co/600x400'
    ), (
        'Best Ever Brown Ale',
        (SELECT brewery_id FROM brewery WHERE brewery_name = 'Legendary Brewery'),
        (SELECT style_id FROM style WHERE style_name = 'Brown Ale'),
        'Dark brown ale with nutty undertones',
        4.2, false, 'https://placehold.co/600x400'
    ), (
        'Bill the Pony',
        (SELECT brewery_id FROM brewery WHERE brewery_name = 'Legendary Brewery'),
        (SELECT style_id FROM style WHERE style_name = 'Stout'),
        'Dark stout, with a sweet, malty start and a dry hoppy finish',
        5.5, false, 'https://placehold.co/600x400'
    ), (
        'Pale Rider Ale',
        (SELECT brewery_id FROM brewery WHERE brewery_name = 'Legendary Brewery'),
        (SELECT style_id FROM style WHERE style_name = 'IPA'),
        'Stellar IPA with citrus undertones, and hoppiness that is strong without overwhelming',
        8.0, false, 'https://placehold.co/600x400'
    );

-- Beers - Seasonal
INSERT INTO beer (beer_name, brewery_id, style_id, description, abv, seasonal, season_name, picture_url) VALUES
    (
        '''Til You See the Whites of their Ales',
        (SELECT brewery_id FROM brewery WHERE brewery_name = 'Sam''s Revolutionary Brewery'),
        (SELECT style_id FROM style WHERE style_name = 'White Ale'),
		'Well balanced white ale that packs a punch while still refreshing',
        5.5, true, 'Spring - Summer', 'https://placehold.co/600x400'
    ), (
        'Sam''s Summer Shandy',
        (SELECT brewery_id FROM brewery WHERE brewery_name = 'Sam''s Revolutionary Brewery'),
        (SELECT style_id FROM style WHERE style_name = 'Shandy'),
		'Our classic lemon shandy, the perfect beer for hot summer nights!',
        2.8, true, 'Summer', 'https://placehold.co/600x400'
    ), (
        'Distant Shores Pils',
        (SELECT brewery_id FROM brewery WHERE brewery_name = 'Legendary Brewery'),
        (SELECT style_id FROM style WHERE style_name = 'Pilsner'),
		'Lighter and more refreshing than most of year-round brews, our Distant Shore Pils is malty and flavorful, never watery',
        3.9, true, 'Summer', 'https://placehold.co/600x400'
    );

-- REVIEWS with text
INSERT INTO review (beer_id, user_id, review_text, rating, recommended) VALUES
	((SELECT beer_id FROM beer WHERE beer_name = 'Distant Shores Pils'), (SELECT user_id FROM users WHERE username = 'user'), 'Solid pils coming from the legends!', 4, true),
	((SELECT beer_id FROM beer WHERE beer_name = 'Tea Party Ale'), (SELECT user_id FROM users WHERE username = 'user'), 'Kind of a weird combo, but not terrible', 3, false),
	((SELECT beer_id FROM beer WHERE beer_name = 'Bill the Pony'), (SELECT user_id FROM users WHERE username = 'user'), 'One of their best! It''s flavorful without being too heavy and has a really unique profile.', 5, true),
	((SELECT beer_id FROM beer WHERE beer_name = 'Lager Live the Revolution!'), (SELECT user_id FROM users WHERE username = 'legendarybrewer'), 'A really solid, no frills lager. Consistent with Sam''s brewing accumen!', 4, true),
	((SELECT beer_id FROM beer WHERE beer_name = 'Best Ever Brown Ale'), (SELECT user_id FROM users WHERE username = 'brewersam'), 'Although flavorful, this ale is a bit too heavy for my tastes', 2, false);

-- REVIEWS w/o text
INSERT INTO review (beer_id, user_id, rating, recommended) VALUES
	((SELECT beer_id FROM beer WHERE beer_name = 'Distant Shores Pils'), (SELECT user_id FROM users WHERE username = 'brewersam'), 4, true),
	((SELECT beer_id FROM beer WHERE beer_name = 'Bill the Pony'), (SELECT user_id FROM users WHERE username = 'brewersam'), 5, true),
	((SELECT beer_id FROM beer WHERE beer_name = 'Lager Live the Revolution!'), (SELECT user_id FROM users WHERE username = 'user'), 5, true),
	((SELECT beer_id FROM beer WHERE beer_name = 'Best Ever Brown Ale'), (SELECT user_id FROM users WHERE username = 'user'), 5, true);

INSERT INTO event (event_name, brewery_id, event_date, begins, ends, description, over_21) VALUES
    ('Halloween Hob Nob', (SELECT brewery_id FROM brewery WHERE brewery_name = 'Legendary Brewery'), '2024-10-31', '9:00 PM', '2:00 AM', 'DJ Ghoul will be spinning the spookiest tracks from genres spanning Goth to Pop', true),
    ('Best Pals Weekend', (SELECT brewery_id FROM brewery WHERE brewery_name = 'Legendary Brewery'), '2024-08-23', '11:00 AM', '07:00 PM', 'Continuing the local tradition with bands such as: Windhand, Inter Arma, Government Warning, Unmaker and Dying Breath. Cover is $15', false),
    ('Legendary Heroes: D&D&B Night', (SELECT brewery_id FROM brewery WHERE brewery_name = 'Legendary Brewery'), '2024-08-20', '07:30 PM', '10:00 PM', 'Join us for our weekly Dungeons and Dragons and Beers night. Features mini-campaigns, so there is no need to come every week', false),
    ('Legendary Heroes: D&D&B Night', (SELECT brewery_id FROM brewery WHERE brewery_name = 'Legendary Brewery'), '2024-09-20', '07:30 PM', '10:00 PM', 'Join us for our weekly Dungeons and Dragons and Beers night. Features mini-campaigns, so there is no need to come every week', false),
    ('Legendary Heroes: D&D&B Night', (SELECT brewery_id FROM brewery WHERE brewery_name = 'Legendary Brewery'), '2024-10-20', '07:30 PM', '10:00 PM', 'Join us for our weekly Dungeons and Dragons and Beers night. Features mini-campaigns, so there is no need to come every week', false),
    ('Legendary Brewery''s Semi-Annual Maker Mart', (SELECT brewery_id FROM brewery WHERE brewery_name = 'Legendary Brewery'), '2024-09-06', '10:30 AM', '05:00 PM', 'Come check out wares from makers both local and regional!', false);

INSERT INTO category (category_name) VALUES
    ('Holiday Party'), ('DJed Event'), ('Dance Party'), ('Concert'), ('Metal Concert'), ('Festival'), ('Hobby'), ('Recurring'), ('Expo'), ('Sale');

INSERT INTO event_category (event_id, category_id) VALUES
    ((SELECT event_id FROM event WHERE event_name = 'Halloween Hob Nob'), (SELECT category_id FROM category WHERE category_name = 'Holiday Party')),
    ((SELECT event_id FROM event WHERE event_name = 'Halloween Hob Nob'), (SELECT category_id FROM category WHERE category_name = 'DJed Event')),
    ((SELECT event_id FROM event WHERE event_name = 'Halloween Hob Nob'), (SELECT category_id FROM category WHERE category_name = 'Dance Party')),
    ((SELECT event_id FROM event WHERE event_name = 'Best Pals Weekend'), (SELECT category_id FROM category WHERE category_name = 'Concert')),
    ((SELECT event_id FROM event WHERE event_name = 'Best Pals Weekend'), (SELECT category_id FROM category WHERE category_name = 'Metal Concert')),
    ((SELECT event_id FROM event WHERE event_name = 'Best Pals Weekend'), (SELECT category_id FROM category WHERE category_name = 'Festival')),
    ((SELECT event_id FROM event WHERE event_name = 'Legendary Heroes: D&D&B Night'), (SELECT category_id FROM category WHERE category_name = 'Hobby')),
    ((SELECT event_id FROM event WHERE event_name = 'Legendary Heroes: D&D&B Night'), (SELECT category_id FROM category WHERE category_name = 'Recurring')),
    ((SELECT event_id FROM event WHERE event_name = 'Legendary Brewery''s Semi-Annual Maker Mart'), (SELECT category_id FROM category WHERE category_name = 'Expo')),
    ((SELECT event_id FROM event WHERE event_name = 'Legendary Brewery''s Semi-Annual Maker Mart'), (SELECT category_id FROM category WHERE category_name = 'Sale')),
    ((SELECT event_id FROM event WHERE event_name = 'Legendary Brewery''s Semi-Annual Maker Mart'), (SELECT category_id FROM category WHERE category_name = 'Recurring'));

COMMIT TRANSACTION;
