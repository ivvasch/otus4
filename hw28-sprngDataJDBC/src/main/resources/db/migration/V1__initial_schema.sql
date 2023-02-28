-- Для @GeneratedValue(strategy = GenerationType.IDENTITY)

CREATE TABLE client
(
    id   VARCHAR(50) NOT NULL PRIMARY KEY,
    name VARCHAR(50)
);
CREATE TABLE phone
(
    client_id VARCHAR NOT NULL REFERENCES client (id),
    phone VARCHAR(50)

);
CREATE TABLE addresses
(
    client_id VARCHAR NOT NULL REFERENCES client (id),
    address VARCHAR(50)

);
