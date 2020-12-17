CREATE TABLE post (
   id SERIAL PRIMARY KEY,
   name TEXT
);

CREATE TABLE candidate (
    id SERIAL PRIMARY KEY,
    name TEXT
);

CREATE TABLE IF NOT EXISTS user_account (
    id SERIAL PRIMARY KEY,
    name TEXT,
    email TEXT,
    password TEXT
);
