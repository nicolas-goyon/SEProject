-- Table creation script

CREATE TABLE accesses
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(255) UNIQUE,
    description VARCHAR(255)
);

CREATE TABLE payment_type
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(255),
    description VARCHAR(255) UNIQUE
);

CREATE TABLE managers
(
    id       SERIAL PRIMARY KEY,
    email    VARCHAR(255) UNIQUE,
    username VARCHAR(255) UNIQUE,
    password VARCHAR(255)
);



CREATE TABLE plans
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(255) UNIQUE,
    description VARCHAR(255),
    price       DECIMAL(10, 2)
);

CREATE TABLE plan_accesses
(
    plan_id    INT,
    access_id  INT,
    FOREIGN KEY (plan_id) REFERENCES plans (id),
    FOREIGN KEY (access_id) REFERENCES accesses (id),
    PRIMARY KEY (plan_id, access_id)
);

CREATE TABLE members
(
    id                SERIAL PRIMARY KEY,
    email             VARCHAR(255) UNIQUE,
    username          VARCHAR(255) UNIQUE,
    password          VARCHAR(255),
    plan_id           INT,
    payment_type_id   INT,
    last_payment_date VARCHAR(255),
    FOREIGN KEY (plan_id) REFERENCES plans (id),
    FOREIGN KEY (payment_type_id) REFERENCES payment_type (id)
);


-- Data insertion script

INSERT INTO payment_type (name, description)
VALUES ('Credit Card', 'Credit Card');
INSERT INTO payment_type (name, description)
VALUES ('Paypal', 'Paypal');
INSERT INTO payment_type (name, description)
VALUES ('Debit Card', 'Debit Card');


INSERT INTO managers (email, username, password)
VALUES ('jhondoe@gmai.com', 'jhondoe', '123456');
INSERT INTO managers (email, username, password)
VALUES ('aligator@gmail.com', 'ali gator', '123456');

INSERT INTO plans (name, description, price)
VALUES ('Free', 'Free plan', 0);
INSERT INTO plans (name, description, price)
VALUES ('Basic', 'Basic plan', 10);
INSERT INTO plans (name, description, price)
VALUES ('Premium', 'Premium plan', 20);


INSERT INTO members (email, username, password, plan_id, payment_type_id)
VALUES ('jhon.attend@gmal.com', 'jonaten', '123456', 3, 1);
INSERT INTO members (email, username, password, plan_id, payment_type_id)
VALUES ('jhon2.attend@gmal.com', 'jonate2n', '123456', 1, 1);
INSERT INTO members (email, username, password, plan_id, payment_type_id)
VALUES ('jhon3.attend@gmal.com', 'jon3aten', '123456', 1, 1);
INSERT INTO members (email, username, password, plan_id, payment_type_id)
VALUES ('jhon4.attend@gmal.com', 'jon4aten', '123456', 1, 1);
INSERT INTO members (email, username, password, plan_id, payment_type_id)
VALUES ('jhon5.attend@gmal.com', 'jona5ten', '123456', 1, 1);

INSERT INTO accesses (name, description)
VALUES ('gym', 'Gym access');

INSERT INTO accesses (name, description)
VALUES ('pool', 'Pool access');

INSERT INTO accesses (name, description)
VALUES ('sauna', 'Sauna access');

INSERT INTO accesses (name, description)
VALUES ('jacuzzi', 'Jacuzzi access');



INSERT INTO plan_accesses (plan_id, access_id)
VALUES (2, 1);

INSERT INTO plan_accesses (plan_id, access_id)
VALUES (3, 1);

INSERT INTO plan_accesses (plan_id, access_id)
VALUES (3, 2);

INSERT INTO plan_accesses (plan_id, access_id)
VALUES (3, 3);
