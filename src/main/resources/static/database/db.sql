CREATE DATABASE sddb;
USE sddb;

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

-- --------------------------------------------------------

-- Roles
CREATE TABLE Roles (
    roleId INT AUTO_INCREMENT PRIMARY KEY,
    nome   VARCHAR(255) NOT NULL
);

INSERT INTO Roles (roleId, nome) VALUES
(0, 'admin'),
(1, 'default_user');

-- --------------------------------------------------------

-- Users
CREATE TABLE Users (
    userId        INT AUTO_INCREMENT PRIMARY KEY,
    roleId        INT,
    nome          VARCHAR(255) NOT NULL,
    mail          VARCHAR(255) NOT NULL UNIQUE,
    infoAdicional TEXT,
    FOREIGN KEY (roleId) REFERENCES Roles(roleId)
);

INSERT INTO Users (roleId, nome, mail, infoAdicional) VALUES
    (1, 'admin', 'admin@admin.pt', NULL);


-- --------------------------------------------------------

-- UserSessions
CREATE TABLE UserSessions (
    userId INT PRIMARY KEY,
    FOREIGN KEY (userId) REFERENCES Users(userId)
);

-- --------------------------------------------------------

-- Purchases
CREATE TABLE Purchases (
    purchaseId INT AUTO_INCREMENT PRIMARY KEY,
    userId     INT,
    FOREIGN KEY (userId) REFERENCES Users(userId)
);

-- --------------------------------------------------------

-- Types
CREATE TABLE Types (
    typeId   INT AUTO_INCREMENT PRIMARY KEY,
    typeName VARCHAR(255) NOT NULL
);

-- --------------------------------------------------------

-- Colors
CREATE TABLE Colors (
    colorId   INT AUTO_INCREMENT PRIMARY KEY,
    colorName VARCHAR(255) NOT NULL
);

-- --------------------------------------------------------

-- Brands
CREATE TABLE Brands (
    brandId   INT AUTO_INCREMENT PRIMARY KEY,
    brandName VARCHAR(255) NOT NULL
);

-- --------------------------------------------------------

-- Products
CREATE TABLE Products (
    prodId      INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    description TEXT,
    imageURL    TEXT,
    doorCount   INT,
    price       DECIMAL(10, 2),
    typeId      INT,
    colorId     INT,
    brandId     INT,
    purchaseId  INT,
    FOREIGN KEY (typeId)     REFERENCES Types(typeId),
    FOREIGN KEY (colorId)    REFERENCES Colors(colorId),
    FOREIGN KEY (brandId)    REFERENCES Brands(brandId),
    FOREIGN KEY (purchaseId) REFERENCES Purchases(purchaseId)
);

-- --------------------------------------------------------

COMMIT;
