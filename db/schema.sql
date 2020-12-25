CREATE TABLE IF NOT EXISTS post
(
    id   SERIAL PRIMARY KEY,
    name TEXT
);

CREATE TABLE IF NOT EXISTS city
(
    id   SERIAL PRIMARY KEY,
    name TEXT,
    UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS candidate
(
    id     SERIAL PRIMARY KEY,
    name   TEXT,
    cityId INT,
    FOREIGN KEY (cityId) REFERENCES city (id)
);

CREATE TABLE IF NOT EXISTS user_account
(
    id       SERIAL PRIMARY KEY,
    name     TEXT,
    email    TEXT,
    password TEXT,
    UNIQUE (email)
);