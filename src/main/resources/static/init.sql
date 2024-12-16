-- Create the database
CREATE DATABASE IF NOT EXISTS blogger;

-- Use the database
USE blogger;

-- Create the users table
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY, -- Auto-incrementing ID
    username VARCHAR(50) NOT NULL UNIQUE, -- Username, must be unique
    email VARCHAR(100) NOT NULL UNIQUE, -- Email, must be unique
    password_hash VARCHAR(255) NOT NULL, -- Hashed password
    dob_string VARCHAR(20), -- Date of Birth (stored as string if needed)
    date_joined DATETIME DEFAULT CURRENT_TIMESTAMP -- Date when the account was created
);