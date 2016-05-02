CREATE TABLE content (
    id CHAR(3) NOT NULL,
    value VARCHAR(64),
    PRIMARY KEY (id)
);

INSERT INTO content (id, value) VALUES('001', 'Content');