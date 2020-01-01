CREATE TABLE items
(
itemCode INT PRIMARY KEY NOT NULL,
itemName VARCHAR(200) NOT NULL,
itemDescription VARCHAR(1024) NOT NULL,
itemQuantity INT NOT NULL,
itemPhoto BYTEA (200)
);