-- Creación de la base de datos Android
CREATE DATABASE Android;
-- Cambiamos a usar la base de datos
USE Android;
-- Creación de la tabla de Users
CREATE TABLE Users (
    Id INT PRIMARY KEY,
    Username VARCHAR(50) NOT NULL,
    Email VARCHAR(100) UNIQUE NOT NULL,
    Password VARCHAR(255) NOT NULL,
    IsAdmin BOOLEAN
);
-- Creación de la tabla de Games
CREATE TABLE Games (
    Id INT PRIMARY KEY,
    UserId INT NOT NULL,
    Result VARCHAR(20) NOT NULL,
    FOREIGN KEY (UserId) REFERENCES Users(Id)
);
-- Creamos un usuario admin y un usuario normal
INSERT INTO Users (Id, Username, Email, Password, IsAdmin) VALUES (0, 'admin', 'admin@admin.com', 'admin', true);
INSERT INTO Users (Id, Username, Email, Password, IsAdmin) VALUES (1, 'default', 'default@default.com', 'default', false);