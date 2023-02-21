-- Для @GeneratedValue(strategy = GenerationType.IDENTITY)

CREATE TABLE addresses
(
    id   BIGSERIAL NOT NULL PRIMARY KEY,
    address VARCHAR(50),
    client_id BIGINT

);
CREATE TABLE phone
(
    id   BIGSERIAL NOT NULL PRIMARY KEY,
    phone VARCHAR(50),
    client VARCHAR(250),
    client_id BIGINT

);
CREATE TABLE client
(
    id   BIGSERIAL NOT NULL PRIMARY KEY,
    name VARCHAR(50),
    password VARCHAR(50),
    address VARCHAR(250),
    phone VARCHAR(250),
    address_id BIGINT REFERENCES ADDRESSES,
    phone_id BIGINT REFERENCES PHONE
);

-- Для @GeneratedValue(strategy = GenerationType.SEQUENCE)
-- create sequence hibernate_sequence start with 1 increment by 1;
--
-- create table client
-- (
--     id   bigint not null primary key,
--     name varchar(50)
-- );
