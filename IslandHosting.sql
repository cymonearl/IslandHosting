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
    log_id INT PRIMARY KEY,
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

CREATE VIEW UserOrders AS
SELECT 
    o.order_id, 
    o.user_id, 
    u.username, 
    s.name AS server_name, 
    o.start_date, 
    o.end_date, 
    o.total_amount, 
    o.status AS order_status 
FROM Orders o 
JOIN Users u ON o.user_id = u.user_id 
JOIN Servers s ON o.server_id = s.server_id; 

CREATE VIEW UnresolvedTickets AS 
SELECT  
    t.ticket_id, 
    t.user_id, 
    u.username, 
    t.server_id, 
    s.name AS server_name, 
    t.subject, 
    t.description, 
    t.priority, 
    t.status, 
    t.created_at 
FROM Support_Tickets t 
JOIN Users u ON t.user_id = u.user_id 
JOIN Servers s ON t.server_id = s.server_id 
WHERE t.status IN ('open', 'in_progress'); 

### Triggers ###