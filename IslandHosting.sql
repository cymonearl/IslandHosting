show databases;

create database island_hosting_database;

use island_hosting_database;

CREATE TABLE Users (
    user_id INT PRIMARY KEY,
    username VARCHAR(50) UNIQUE,
    email VARCHAR(100) UNIQUE,
    password_hash VARCHAR(255),
    full_name VARCHAR(100),
    contact_number VARCHAR(20),
    address TEXT,
    created_at TIMESTAMP,
    last_login TIMESTAMP,
    status ENUM('active', 'inactive')
);

CREATE TABLE Servers (
    server_id INT PRIMARY KEY,
    name VARCHAR(100),
    hardware_type VARCHAR(50),
    ram_gb INT,
    storage_gb INT,
    price_per_month DECIMAL(10,2),
    location VARCHAR(50),
    status ENUM('available', 'occupied', 'maintenance'),
    specs TEXT,
    created_at TIMESTAMP
);

CREATE TABLE Orders (
    order_id INT PRIMARY KEY,
    user_id INT,
    server_id INT,
    start_date DATE,
    end_date DATE,
    total_amount DECIMAL(10,2),
    status ENUM('pending', 'completed', 'cancelled', 'expired'),
    created_at TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users(user_id),
    FOREIGN KEY (server_id) REFERENCES Servers(server_id)
);

CREATE TABLE Payments (
    payment_id INT PRIMARY KEY,
    order_id INT,
    amount DECIMAL(10,2),
    payment_method VARCHAR(50),
    transaction_id VARCHAR(100),
    payment_status ENUM('pending', 'completed', 'failed', 'refunded'),
    payment_date TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES Orders(order_id)
);

CREATE TABLE ServerBackups (
    backup_id INT PRIMARY KEY,
    server_id INT,
    backup_date TIMESTAMP,
    backup_size_gb DECIMAL(10,2),
    status ENUM('in_progress', 'completed', 'failed'),
    storage_location VARCHAR(255),
    FOREIGN KEY (server_id) REFERENCES Servers(server_id)
);

CREATE TABLE Support_Tickets (
    ticket_id INT PRIMARY KEY,
    user_id INT,
    server_id INT,
    subject VARCHAR(200),
    description TEXT,
    priority ENUM('low', 'medium', 'high'),
    status ENUM('open', 'in_progress', 'resolved', 'closed'),
    created_at TIMESTAMP,
    resolved_at TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users(user_id),
    FOREIGN KEY (server_id) REFERENCES Servers(server_id)
);

CREATE TABLE Audit_Logs (
    log_id INT PRIMARY,
    user_id INT,
    action_type VARCHAR(50),
    description TEXT,
    ip_address VARCHAR(45),
    timestamp TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);

### UserView ###

CREATE VIEW ActiveUsers AS
SELECT user_id, username, email, full_name, contact_number, address
FROM Users
WHERE status = 'active';

CREATE VIEW AvailableServers AS 
SELECT server_id, name, hardware_type, ram_gb, storage_gb, price_per_month, location, specs 
FROM Servers 
WHERE status = 'available'; 

### Triggers ###

DELIMITER //
CREATE TRIGGER update_server_status_occupied
AFTER INSERT ON Orders
FOR EACH ROW
BEGIN
    UPDATE Servers
    SET status = 'occupied'
    WHERE server_id = NEW.server_id;
END
//
DELIMITER ;

DELIMITER //
CREATE TRIGGER update_server_status_available
AFTER DELETE ON Orders
FOR EACH ROW
BEGIN
    UPDATE Servers
    SET status = 'available'
    WHERE server_id = OLD.server_id;
END
//

DELIMITER //
CREATE TRIGGER resolve_issue
BEFORE UPDATE ON Support_Tickets
FOR EACH ROW
BEGIN
    IF NEW.status = 'closed' OR NEW.status = 'resolved' THEN
        SET NEW.resolved_at = NOW();
    END IF;
END;
//
DELIMITER ;


### Procedures ###

DELIMITER //
CREATE PROCEDURE logOrderMade (IN log_id INT, IN user_id INT, IN order_id INT, IN server_id INT, IN start_date DATE, IN end_date DATE, IN total_amount DECIMAL(10,2))
BEGIN
    INSERT INTO Audit_Logs (log_id, user_id, action_type, description, ip_address, timestamp)
    VALUES (log_id, user_id, 'Order Made', 'Order made successfully', '127.0.0.1', NOW());

    INSERT INTO Orders (order_id, user_id, server_id, start_date, end_date, total_amount, status, created_at)
    VALUES (order_id, user_id, server_id, start_date, end_date, total_amount, 'pending', NOW());
END;
//
DELIMITER ;

DELIMITER //
CREATE PROCEDURE payOrder (IN payment_id INT, IN order_id INT, IN amount DECIMAL(10,2), IN payment_method VARCHAR(50), IN transaction_id VARCHAR(100), IN payment_status ENUM('pending', 'completed', 'failed', 'refunded'))
BEGIN
    INSERT INTO Payments (payment_id, order_id, amount, payment_method, transaction_id, payment_status, payment_date)
    VALUES (payment_id, order_id, amount, payment_method, transaction_id, payment_status, NOW());
END;
//
DELIMITER ;

DELIMITER //
CREATE PROCEDURE deleteOtherData (IN id INT)
BEGIN
    -- Update the status of servers associated with the user's orders to 'available'
    UPDATE Servers 
    SET status = 'available' 
    WHERE server_id IN (SELECT server_id FROM Orders WHERE user_id = id);

    -- Delete records from related tables
    DELETE FROM Payments WHERE order_id IN (SELECT order_id FROM Orders WHERE user_id = id);
    DELETE FROM Orders WHERE user_id = id;
    DELETE FROM Support_Tickets WHERE user_id = id;
    DELETE FROM Audit_Logs WHERE user_id = id;
END;
//
DELIMITER ;