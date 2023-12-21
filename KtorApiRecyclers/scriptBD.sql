-- Creación de la base de datos Android
CREATE DATABASE Android;
-- Cambiamos a usar la base de datos
USE Android;
-- Creación de la tabla de Users
CREATE TABLE Users (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    Username VARCHAR(50) UNIQUE NOT NULL,
    Email VARCHAR(100) UNIQUE NOT NULL,
    Password VARCHAR(255) NOT NULL,
    IsAdmin BOOLEAN
);
-- Creación de la tabla de Games
CREATE TABLE Games (
    Id INT PRIMARY KEY AUTO_INCREMENT,
    UserId INT NOT NULL,
    Result VARCHAR(20) NOT NULL,
    FOREIGN KEY (UserId) REFERENCES Users(Id)
);
-- Creamos un usuario admin y un usuario normal
INSERT INTO Users (Username, Email, Password, IsAdmin) VALUES ('admin', 'admin@admin.com', 'admin', true);
INSERT INTO Users (Username, Email, Password, IsAdmin) VALUES ('default', 'default@default.com', 'default', false);
-- Insertar una nueva partida para el usuario "default"
INSERT INTO Games (UserId, Result) VALUES (2, '3-1');