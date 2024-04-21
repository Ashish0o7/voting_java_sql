CREATE DATABASE voting_management;

USE voting_management;
DROP TABLE IF EXISTS users;

-- Recreate the users table with the modified schema
-- CREATE TABLE users (
--     id INT AUTO_INCREMENT PRIMARY KEY,
--     email VARCHAR(255) UNIQUE NOT NULL, -- Change user_id to email
--     password VARCHAR(100) NOT NULL,
--     full_name VARCHAR(255) NOT NULL, -- Add full_name field
--     voter_id_proof_path VARCHAR(255) NOT NULL
-- );
INSERT INTO users (email, password, full_name, voter_id_proof_path) VALUES ('test@example.com', 'password123', 'John Doe', '/path/to/voter_id_proof.jpg');
INSERT INTO users (email, password, full_name, voter_id_proof_path) VALUES ('123@gmail.com', 'password123', 'test user', '/path/to/voter_id_qqproof.jpg');
CREATE TABLE votes (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       user_email VARCHAR(255),
                       voted_party VARCHAR(255),
                       timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

SELECT * FROM users;
SELECT * FROM votes;


CREATE TABLE users (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       email VARCHAR(255) UNIQUE NOT NULL,
                       password VARCHAR(100) NOT NULL,
                       full_name VARCHAR(255) NOT NULL,
                       aadhar_card_number VARCHAR(20) UNIQUE NOT NULL, -- Change to aadhar card number
                       pan_card_number VARCHAR(10) UNIQUE NOT NULL -- Add pan card number
);

-- Insert the modified user data, reserve the already present users
INSERT INTO users (email, password, full_name, aadhar_card_number, pan_card_number)
VALUES ('test@example.com', 'password123', 'John Doe', '123456789012', 'ABCDE1234F');

INSERT INTO users (email, password, full_name, aadhar_card_number, pan_card_number)
VALUES ('123@gmail.com', 'password123', 'test user', '987654321098', 'XYZW0987A');