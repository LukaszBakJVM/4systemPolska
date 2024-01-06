CREATE SCHEMA systemPolska;







CREATE TABLE users
(
    id
            BIGINT
        PRIMARY
            KEY
        AUTO_INCREMENT,
    name
            VARCHAR
                (
                255
                ),
    surname VARCHAR
                (
                255
                ),
    login VARCHAR
                (
                255
                )
);