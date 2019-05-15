CREATE TABLE users(
id serial PRIMARY KEY NOT NULL,
login VARCHAR(100) UNIQUE,
admin BOOLEAN NOT NULL,
superadmin BOOLEAN NOT NULL,
password VARCHAR(100),
email VARCHAR(100) UNIQUE);

CREATE TABLE events(
id serial PRIMARY KEY NOT NULL,
userid INT NOT NULL,
description VARCHAR(500),
eventdate TIMESTAMP NOT NULL,
activeevent BOOLEAN NOT NULL,
reminderexpression VARCHAR(100));