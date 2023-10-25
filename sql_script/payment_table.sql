DROP TABLE IF EXISTS payment;

CREATE TABLE payment (
    id SERIAL PRIMARY KEY,
    user_email varchar(45) DEFAULT NULL,
    amount money DEFAULT NULL
);

