DROP TABLE IF EXISTS roles CASCADE;

CREATE TABLE roles
(
    id   INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);

DROP TABLE IF EXISTS users CASCADE;

CREATE TABLE users
(
    id           INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    last_name    VARCHAR(255)        NOT NULL,
    first_name   VARCHAR(255)        NOT NULL,
    patronymic   VARCHAR(255)        NOT NULL,
    birth_date   DATE,
    email        VARCHAR(255) UNIQUE NOT NULL,
    password     VARCHAR(255)        NOT NULL,
    phone_number VARCHAR(255) UNIQUE,
    role_id      INT REFERENCES roles (id)
);
