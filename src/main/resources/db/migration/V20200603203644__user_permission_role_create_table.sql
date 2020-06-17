CREATE TABLE role
(
    id   BIGINT GENERATED ALWAYS AS IDENTITY,
    name VARCHAR UNIQUE,
    PRIMARY KEY (id)
);

CREATE TABLE usr
(
    id       BIGINT GENERATED ALWAYS AS IDENTITY,
    login    VARCHAR UNIQUE,
    username VARCHAR UNIQUE,
    password VARCHAR,
    role_id BIGINT,
    FOREIGN KEY (role_id) references "role",
    PRIMARY KEY (id)
);

CREATE TABLE role_permission
(
    role_id       BIGINT NOT NULL,
    permission VARCHAR NOT NULL,
    FOREIGN KEY (role_id) REFERENCES "role"
);
