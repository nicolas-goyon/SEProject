CREATE DATABASE localAzureDB;
GO

USE localAzureDB;
GO

CREATE TABLE users (
    user_id INT IDENTITY(1,1) PRIMARY KEY,
    email VARCHAR(255),
    username VARCHAR(255),
    password VARCHAR(255),
    role VARCHAR(255)
);

GO