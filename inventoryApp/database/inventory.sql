DROP TABLE IF EXISTS items;
DROP TABLE IF EXISTS neededItems;

CREATE TABLE items
(
	itemCode INT PRIMARY KEY NOT NULL,
	itemName VARCHAR(200) NOT NULL,
	itemDescription VARCHAR(1024) NOT NULL,
	itemQuantity INT NOT NULL,
	itemPhoto BYTEA (2000)
);

CREATE TABLE neededItems
(	
	itemCode INT PRIMARY KEY NOT NULL,
	itemName VARCHAR(200) NOT NULL,
	itemDescription VARCHAR(1024) NOT NULL,
	itemQuantity INT NOT NULL,
	itemPhoto BYTEA  (2000)
)

INSERT INTO items



//inventory db
//table: items: code, name, description, quantity, picture
//table: 
//table: neededItems: code, name, description, quantity, picture