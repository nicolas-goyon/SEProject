CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    email VARCHAR(255),
    username VARCHAR(255),
    password VARCHAR(255),
    role VARCHAR(255),
    plan_id INT,
    payment_type_id INT,
    FOREIGN KEY (plan_id) REFERENCES plans(id),
    FOREIGN KEY (payment_type_id) REFERENCES payment_type(id)
);

CREATE TABLE payment_type (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    description VARCHAR(255)
);

CREATE TABLE managers (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255),
    username VARCHAR(255),
    password VARCHAR(255)
);

CREATE TABLE plans (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    description VARCHAR(255),
    price DECIMAL(10, 2)
);