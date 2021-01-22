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

CREATE TABLE IF NOT EXISTS role
(
    id       SERIAL PRIMARY KEY,
    name     TEXT,
    UNIQUE (name)
);

-- INSERT INTO role (name) VALUES ('USER');
-- INSERT INTO role (name) VALUES ('ADMIN');

CREATE TABLE IF NOT EXISTS user_account
(
    id       SERIAL PRIMARY KEY,
    name     TEXT,
    email    TEXT,
    password TEXT,
    roleId INT,
    FOREIGN KEY (roleId) REFERENCES role (id),
    UNIQUE (email)
);

-- INSERT INTO user_account (name, email, password, roleId) VALUES ('admin', 'root@local', 'cm9vdEBsb2NhbERFRkFVTFRfU0FMVHBhc3N3b3Jk', 2);