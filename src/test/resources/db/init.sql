drop table if exists events;
drop table if exists users;

CREATE TABLE users
(
    id         serial PRIMARY KEY NOT NULL,
    email      VARCHAR(200) UNIQUE,
    name       VARCHAR(200),
    admin      BOOLEAN            NOT NULL,
    superadmin BOOLEAN            NOT NULL,
    password   VARCHAR(100)
);

CREATE TABLE events
(
    id                 serial PRIMARY KEY                          NOT NULL,
    userid             INT NOT NULL,
    description        VARCHAR(500),
    eventdate          TIMESTAMP                                   NOT NULL,
    activeevent        BOOLEAN                                     NOT NULL,
    reminderexpression VARCHAR(100),
    FOREIGN KEY (userid) REFERENCES users (id) ON DELETE CASCADE
);
