-- database BreweryTest
BEGIN TRANSACTION;

-- *************************************************************************************************
-- Drop all db objects in the proper order
-- *************************************************************************************************
DROP TABLE IF EXISTS review, beer, style, brewery, users;

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
	season_name varchar(20)
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

COMMIT TRANSACTION;
