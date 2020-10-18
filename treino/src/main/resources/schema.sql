DROP TABLE IF EXISTS person;

CREATE TABLE person (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(250) NOT NULL,
  age VARCHAR(250) NOT NULL,
  birth_date date NOT NULL,
  trusty_birth_date boolean NOT NULL,
  valid boolean NOT NULL
);