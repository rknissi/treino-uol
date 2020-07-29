DROP TABLE IF EXISTS person;

CREATE TABLE person (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(250) NOT NULL,
  age VARCHAR(250) NOT NULL,
  locationid INT NOT NULL
);

insert into person values (1, 'Teste', 15, 1);