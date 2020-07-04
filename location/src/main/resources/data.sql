DROP TABLE IF EXISTS location;
DROP TABLE IF EXISTS weather;

create sequence weatherId;
create sequence locationId;

CREATE TABLE weather (
  id INT AUTO_INCREMENT PRIMARY KEY,
  mintemp NUMBER NOT NULL,
  maxTemp NUMBER NOT NULL
);

CREATE TABLE location (
  id INT AUTO_INCREMENT PRIMARY KEY,
  continent VARCHAR(250) NOT NULL,
  country VARCHAR(250) NOT NULL,
  subdivision1 VARCHAR(250) NOT NULL,
  subdivision2 VARCHAR(250) NOT NULL,
  city VARCHAR(250) NOT NULL,
  latitude VARCHAR(250) NOT NULL,
  longitude VARCHAR(250) NOT NULL,
  weather_id int NOT NULL,
  foreign key (weather_id) REFERENCES weather(id)
);

