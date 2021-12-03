ALTER TABLE article
    ADD COLUMN author_id BIGINT,
    ADD FOREIGN KEY ("author_id") REFERENCES "usr";