-- BloodMate Database Initialization Script
-- This script creates sample data for development and testing

USE bloodmate;

-- Create tables if not exist
CREATE TABLE IF NOT EXISTS admins (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    role VARCHAR(20) NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    last_login DATETIME,
    created_at DATETIME,
    updated_at DATETIME
);

CREATE TABLE IF NOT EXISTS donors (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    gender VARCHAR(10) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    address VARCHAR(255) NOT NULL,
    city VARCHAR(50) NOT NULL,
    state VARCHAR(50) NOT NULL,
    blood_group VARCHAR(5) NOT NULL,
    last_donation_date DATE,
    reward_points INT DEFAULT 0,
    is_eligible BOOLEAN DEFAULT TRUE,
    created_at DATETIME,
    updated_at DATETIME
);

CREATE TABLE IF NOT EXISTS recipients (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    hospital VARCHAR(100) NOT NULL,
    contact_person VARCHAR(100) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    email VARCHAR(100) NOT NULL,
    address VARCHAR(255) NOT NULL,
    city VARCHAR(50) NOT NULL,
    state VARCHAR(50) NOT NULL,
    required_blood_group VARCHAR(5) NOT NULL,
    units_required INT NOT NULL,
    request_status VARCHAR(20) NOT NULL,
    priority VARCHAR(20) NOT NULL,
    request_date DATETIME,
    required_by_date DATETIME,
    notes VARCHAR(1000),
    created_at DATETIME,
    updated_at DATETIME
);

CREATE TABLE IF NOT EXISTS campaigns (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    description VARCHAR(1000) NOT NULL,
    location VARCHAR(100) NOT NULL,
    city VARCHAR(50) NOT NULL,
    state VARCHAR(50) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    organizer VARCHAR(100) NOT NULL,
    organizer_contact VARCHAR(30) NOT NULL,
    organizer_email VARCHAR(100) NOT NULL,
    target_donors INT NOT NULL,
    current_donors INT DEFAULT 0,
    target_blood_groups VARCHAR(100) NOT NULL,
    status VARCHAR(20) NOT NULL,
    campaign_type VARCHAR(20) NOT NULL,
    special_requirements VARCHAR(500),
    created_at DATETIME,
    updated_at DATETIME
);

CREATE TABLE IF NOT EXISTS blood_inventory (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    blood_group VARCHAR(5) NOT NULL,
    units_available INT NOT NULL,
    units_reserved INT DEFAULT 0,
    units_expired INT DEFAULT 0,
    expiry_date DATE NOT NULL,
    donation_date DATE,
    source_type VARCHAR(20) NOT NULL,
    storage_location VARCHAR(100),
    temperature DOUBLE,
    notes VARCHAR(500),
    created_at DATETIME,
    updated_at DATETIME
);

CREATE TABLE IF NOT EXISTS rewards (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    donor_id BIGINT NOT NULL,
    points INT NOT NULL,
    tier VARCHAR(30),
    points_earned INT DEFAULT 0,
    points_spent INT DEFAULT 0,
    total_donations INT DEFAULT 0,
    last_donation_date DATETIME,
    created_at DATETIME,
    updated_at DATETIME,
    FOREIGN KEY (donor_id) REFERENCES donors(id)
);

-- Insert sample blood groups data
INSERT INTO blood_inventory (blood_group, units_available, units_reserved, units_expired, expiry_date, donation_date, source_type, storage_location, temperature, notes, created_at, updated_at) VALUES
('A+', 150, 20, 5, DATE_ADD(CURDATE(), INTERVAL 30 DAY), CURDATE(), 'DONOR', 'Central Blood Bank', 4.0, 'Regular donation', NOW(), NOW()),
('A-', 45, 10, 2, DATE_ADD(CURDATE(), INTERVAL 25 DAY), CURDATE(), 'DONOR', 'Central Blood Bank', 4.0, 'Emergency stock', NOW(), NOW()),
('B+', 120, 15, 3, DATE_ADD(CURDATE(), INTERVAL 28 DAY), CURDATE(), 'DONOR', 'Central Blood Bank', 4.0, 'Regular donation', NOW(), NOW()),
('B-', 35, 8, 1, DATE_ADD(CURDATE(), INTERVAL 22 DAY), CURDATE(), 'DONOR', 'Central Blood Bank', 4.0, 'Emergency stock', NOW(), NOW()),
('AB+', 80, 12, 2, DATE_ADD(CURDATE(), INTERVAL 32 DAY), CURDATE(), 'DONOR', 'Central Blood Bank', 4.0, 'Regular donation', NOW(), NOW()),
('AB-', 25, 5, 0, DATE_ADD(CURDATE(), INTERVAL 20 DAY), CURDATE(), 'DONOR', 'Central Blood Bank', 4.0, 'Critical stock', NOW(), NOW()),
('O+', 200, 25, 8, DATE_ADD(CURDATE(), INTERVAL 35 DAY), CURDATE(), 'DONOR', 'Central Blood Bank', 4.0, 'Regular donation', NOW(), NOW()),
('O-', 60, 15, 2, DATE_ADD(CURDATE(), INTERVAL 18 DAY), CURDATE(), 'DONOR', 'Central Blood Bank', 4.0, 'Emergency stock', NOW(), NOW());

-- Insert sample campaigns
INSERT INTO campaigns (title, description, location, city, state, start_date, end_date, organizer, organizer_contact, organizer_email, target_donors, current_donors, target_blood_groups, status, campaign_type, special_requirements, created_at, updated_at) VALUES
('Summer Blood Drive 2024', 'Annual summer blood donation campaign to maintain blood bank levels', 'Central Park', 'New York', 'NY', DATE_ADD(CURDATE(), INTERVAL 7 DAY), DATE_ADD(CURDATE(), INTERVAL 14 DAY), 'Red Cross', '+1-555-0101', 'redcross@example.com', 500, 0, 'All Blood Groups', 'UPCOMING', 'COMMUNITY', 'Bring ID and eat before donating', NOW(), NOW()),
('Corporate Blood Challenge', 'Friendly competition between companies to donate blood', 'Business District', 'Los Angeles', 'CA', CURDATE(), DATE_ADD(CURDATE(), INTERVAL 30 DAY), 'LA Chamber of Commerce', '+1-555-0102', 'chamber@la.com', 1000, 150, 'All Blood Groups', 'ACTIVE', 'CORPORATE', 'Company ID required', NOW(), NOW()),
('Emergency O- Drive', 'Critical need for O- blood type donors', 'Emergency Center', 'Chicago', 'IL', CURDATE(), DATE_ADD(CURDATE(), INTERVAL 3 DAY), 'Chicago Medical Center', '+1-555-0103', 'emergency@chicago.com', 200, 45, 'O-', 'ACTIVE', 'EMERGENCY', 'Urgent - O- donors only', NOW(), NOW());

-- Insert sample admin user
INSERT INTO admins (username, password, full_name, email, role, is_active, created_at, updated_at) VALUES
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDa', 'System Administrator', 'admin@bloodmate.com', 'SUPER_ADMIN', true, NOW(), NOW());

-- Note: The password hash above is for 'admin123' - change in production!
