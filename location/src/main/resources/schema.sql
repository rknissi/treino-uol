DROP TABLE IF EXISTS location;
DROP TABLE IF EXISTS weather;

create sequence weatherId;
create sequence locationId;

CREATE TABLE weather (
  id INT AUTO_INCREMENT PRIMARY KEY,
  mintemp NUMBER ,
  maxTemp NUMBER
);

CREATE TABLE location (
  id INT AUTO_INCREMENT PRIMARY KEY,
  continent VARCHAR(250) ,
  country VARCHAR(250) ,
  subdivision1 VARCHAR(250) ,
  subdivision2 VARCHAR(250) ,
  city VARCHAR(250) ,
  latitude VARCHAR(250) ,
  longitude VARCHAR(250) ,
  weather_id int ,
  foreign key (weather_id) REFERENCES weather(id)
);

