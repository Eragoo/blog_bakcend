CREATE TABLE article
(
    id     BIGINT GENERATED ALWAYS AS IDENTITY,
    title  VARCHAR NOT NULL,
    text   TEXT,
    rating INT     NOT NULL DEFAULT 0,
    PRIMARY KEY (id)
);

CREATE TABLE genre
(
    id   BIGINT GENERATED ALWAYS AS IDENTITY,
    name VARCHAR NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

CREATE TABLE article_genre
(
    article_id BIGINT NOT NULL,
    genre_id   BIGINT NOT NULL,
    FOREIGN KEY (article_id) REFERENCES "article",
    FOREIGN KEY (genre_id) REFERENCES "genre"
);