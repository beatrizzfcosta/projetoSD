CREATE DATABASE sddb;
USE sddb;

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

-- --------------------------------------------------------

-- Roles
CREATE TABLE roles (
    roleId INT AUTO_INCREMENT PRIMARY KEY,
    nome   VARCHAR(255) NOT NULL
);

INSERT INTO roles (roleId, nome) VALUES
(0, 'admin'),
(1, 'default_user');

-- --------------------------------------------------------

-- Users
CREATE TABLE users (
    userId        INT AUTO_INCREMENT PRIMARY KEY,
    roleId        INT,
    nome          VARCHAR(255) NOT NULL,
    mail          VARCHAR(255) NOT NULL UNIQUE,
    nif           VARCHAR(255) UNIQUE,
    morada        VARCHAR(255),
    codigoPostal  VARCHAR(255),
    pais          VARCHAR(255),
    telefone      VARCHAR(255),
    infoAdicional TEXT,
    FOREIGN KEY (roleId) REFERENCES roles(roleId)
);

INSERT INTO users (roleId, nome, mail, infoAdicional) VALUES
    (1, 'admin', 'admin@admin.pt', NULL);


-- --------------------------------------------------------

-- UserSessions
CREATE TABLE userSessions (
    userId INT PRIMARY KEY,
    FOREIGN KEY (userId) REFERENCES users(userId)
);

-- --------------------------------------------------------

-- Purchases
CREATE TABLE purchases (
    purchaseId INT AUTO_INCREMENT PRIMARY KEY,
    userId     INT,
    date       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (userId) REFERENCES users(userId)
);



-- --------------------------------------------------------

-- Types
CREATE TABLE types (
    typeId   INT AUTO_INCREMENT PRIMARY KEY,
    typeName VARCHAR(255) NOT NULL,
    imageURL TEXT
);

INSERT INTO types (typeName, imageURL) VALUES
    ('suv', '/images/suv.png'),
    ('hatchback', '/images/hatchback.png'),
    ('coupé', '/images/coupe.png'),
    ('cabrio', '/images/cabrio.png'),
    ('carrinha', '/images/carrinha.png');

-- --------------------------------------------------------

-- Colors
CREATE TABLE colors (
    colorId   INT AUTO_INCREMENT PRIMARY KEY,
    colorName VARCHAR(255) NOT NULL
);

INSERT INTO colors (colorName) VALUES
    ('branco'),
    ('preto'),
    ('amarelo'),
    ('verde');

-- --------------------------------------------------------

-- Brands
CREATE TABLE brands (
    brandId   INT AUTO_INCREMENT PRIMARY KEY,
    imageURL  TEXT,
    brandName VARCHAR(255) NOT NULL
);

INSERT INTO brands (brandName, imageURL) VALUES
    ('audi', '/images/audi.png'),
    ('bmw', '/images/bmw.png'),
    ('mercedes', '/images/mercedes.png'),
    ('toyota', '/images/Toyota.png'),
    ('volkswagen', '/images/Volkswagen.png'),
    ('porsche', '/images/porshe.png');

-- --------------------------------------------------------

-- Products
CREATE TABLE products (
    productId   INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    description TEXT,
    imageURL    TEXT,
    doorCount   INT,
    price       DECIMAL(10, 2),
    typeId      INT,
    colorId     INT,
    brandId     INT,
    FOREIGN KEY (typeId)     REFERENCES types(typeId),
    FOREIGN KEY (colorId)    REFERENCES colors(colorId),
    FOREIGN KEY (brandId)    REFERENCES brands(brandId)
);

-- --------------------------------------------------------

-- PurchaseProducts
CREATE TABLE purchaseProducts (
    purchaseId INT,
    productId  INT,
    quantity   INT,
    PRIMARY KEY (purchaseId, productId),
    FOREIGN KEY (purchaseId) REFERENCES purchases(purchaseId) ON DELETE CASCADE,
    FOREIGN KEY (productId)  REFERENCES products(productId)   ON DELETE CASCADE
);

-- --------------------------------------------------------

COMMIT;
