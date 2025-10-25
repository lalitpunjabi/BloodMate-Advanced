-- BloodMate MySQL Database Initialization Script
-- This script creates the complete database schema for BloodMate Blood Bank Management System

-- Create database if it doesn't exist
CREATE DATABASE IF NOT EXISTS bloodmate 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

USE bloodmate;

-- ==================================================
-- CORE TABLES
-- ==================================================

-- Donors table (enhanced version)
CREATE TABLE IF NOT EXISTS donors (
    id VARCHAR(64) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE,
    phone VARCHAR(50),
    blood_group VARCHAR(5) NOT NULL,
    date_of_birth DATE,
    gender ENUM('MALE', 'FEMALE', 'OTHER'),
    address TEXT,
    city VARCHAR(100),
    state VARCHAR(100),
    postal_code VARCHAR(20),
    country VARCHAR(100) DEFAULT 'India',
    eligibility_status ENUM('ELIGIBLE', 'TEMPORARILY_INELIGIBLE', 'PERMANENTLY_INELIGIBLE') DEFAULT 'ELIGIBLE',
    last_donation_date DATE,
    total_donations INT DEFAULT 0,
    weight_kg DECIMAL(5,2),
    height_cm INT,
    medical_conditions TEXT,
    emergency_contact_name VARCHAR(100),
    emergency_contact_phone VARCHAR(50),
    reward_points INT DEFAULT 0,
    tier_level ENUM('BRONZE', 'SILVER', 'GOLD', 'PLATINUM') DEFAULT 'BRONZE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_blood_group (blood_group),
    INDEX idx_eligibility (eligibility_status),
    INDEX idx_city (city),
    INDEX idx_last_donation (last_donation_date)
);

-- Recipients table
CREATE TABLE IF NOT EXISTS recipients (
    id VARCHAR(64) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150),
    phone VARCHAR(50),
    blood_group VARCHAR(5) NOT NULL,
    urgency_level ENUM('LOW', 'MEDIUM', 'HIGH', 'CRITICAL') DEFAULT 'MEDIUM',
    required_units INT NOT NULL,
    hospital_name VARCHAR(200),
    doctor_name VARCHAR(100),
    medical_condition TEXT,
    request_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    required_by_date DATETIME,
    status ENUM('PENDING', 'MATCHED', 'FULFILLED', 'CANCELLED') DEFAULT 'PENDING',
    matched_donors JSON,
    address TEXT,
    city VARCHAR(100),
    state VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_blood_group (blood_group),
    INDEX idx_urgency (urgency_level),
    INDEX idx_status (status),
    INDEX idx_required_date (required_by_date)
);

-- Blood Inventory table
CREATE TABLE IF NOT EXISTS blood_inventory (
    id VARCHAR(64) PRIMARY KEY,
    blood_group VARCHAR(5) NOT NULL,
    units_available INT NOT NULL DEFAULT 0,
    units_reserved INT NOT NULL DEFAULT 0,
    expiry_date DATE NOT NULL,
    collection_date DATE NOT NULL,
    storage_location VARCHAR(100),
    temperature DECIMAL(4,2),
    status ENUM('AVAILABLE', 'RESERVED', 'EXPIRED', 'USED') DEFAULT 'AVAILABLE',
    donor_id VARCHAR(64),
    batch_number VARCHAR(100),
    blood_bank_id VARCHAR(64),
    quality_score DECIMAL(3,2) DEFAULT 10.00,
    tested_for_diseases BOOLEAN DEFAULT FALSE,
    test_results JSON,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (donor_id) REFERENCES donors(id) ON DELETE SET NULL,
    INDEX idx_blood_group (blood_group),
    INDEX idx_expiry (expiry_date),
    INDEX idx_status (status),
    INDEX idx_collection_date (collection_date)
);

-- Donation Campaigns table
CREATE TABLE IF NOT EXISTS campaigns (
    id VARCHAR(64) PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    location VARCHAR(500),
    address TEXT,
    city VARCHAR(100),
    state VARCHAR(100),
    target_units INT DEFAULT 0,
    collected_units INT DEFAULT 0,
    target_donors INT DEFAULT 0,
    registered_donors INT DEFAULT 0,
    campaign_type ENUM('REGULAR', 'EMERGENCY', 'COMMUNITY', 'CORPORATE') DEFAULT 'REGULAR',
    status ENUM('PLANNED', 'ACTIVE', 'COMPLETED', 'CANCELLED') DEFAULT 'PLANNED',
    organizer_name VARCHAR(100),
    organizer_contact VARCHAR(50),
    special_requirements TEXT,
    rewards_offered BOOLEAN DEFAULT FALSE,
    reward_points_per_donation INT DEFAULT 10,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_dates (start_date, end_date),
    INDEX idx_city (city),
    INDEX idx_status (status),
    INDEX idx_type (campaign_type)
);

-- Donations table (transaction records)
CREATE TABLE IF NOT EXISTS donations (
    id VARCHAR(64) PRIMARY KEY,
    donor_id VARCHAR(64) NOT NULL,
    campaign_id VARCHAR(64),
    donation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    blood_group VARCHAR(5) NOT NULL,
    units_donated DECIMAL(4,2) NOT NULL DEFAULT 1.00,
    hemoglobin_level DECIMAL(4,2),
    blood_pressure VARCHAR(20),
    pulse_rate INT,
    temperature DECIMAL(4,2),
    weight_at_donation DECIMAL(5,2),
    pre_donation_tests JSON,
    post_donation_notes TEXT,
    medical_officer VARCHAR(100),
    location VARCHAR(200),
    reward_points_earned INT DEFAULT 10,
    status ENUM('COMPLETED', 'INCOMPLETE', 'REJECTED') DEFAULT 'COMPLETED',
    rejection_reason TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (donor_id) REFERENCES donors(id) ON DELETE CASCADE,
    FOREIGN KEY (campaign_id) REFERENCES campaigns(id) ON DELETE SET NULL,
    INDEX idx_donor (donor_id),
    INDEX idx_campaign (campaign_id),
    INDEX idx_date (donation_date),
    INDEX idx_blood_group (blood_group)
);


-- ==================================================
-- SYSTEM TABLES
-- ==================================================

-- Audit Log table
CREATE TABLE IF NOT EXISTS audit_log (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(64),
    action VARCHAR(100) NOT NULL,
    table_name VARCHAR(100),
    record_id VARCHAR(64),
    old_values JSON,
    new_values JSON,
    ip_address VARCHAR(45),
    user_agent TEXT,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_user (user_id),
    INDEX idx_action (action),
    INDEX idx_timestamp (timestamp)
);

-- System Configuration table
CREATE TABLE IF NOT EXISTS system_config (
    config_key VARCHAR(100) PRIMARY KEY,
    config_value TEXT,
    description TEXT,
    category VARCHAR(50),
    is_public BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- ==================================================
-- INITIAL DATA POPULATION
-- ==================================================

-- Insert default system configuration
INSERT IGNORE INTO system_config (config_key, config_value, description, category, is_public) VALUES
('app.name', 'BloodMate', 'Application name', 'general', TRUE),
('app.version', '2.0.0', 'Application version', 'general', TRUE),
('blood.expiry_days', '42', 'Default blood expiry days', 'inventory', FALSE),
('donation.min_interval_days', '56', 'Minimum days between donations', 'donation', FALSE),
('donor.min_age', '18', 'Minimum donor age', 'donor', TRUE),
('donor.max_age', '65', 'Maximum donor age', 'donor', TRUE),
('donor.min_weight_kg', '50', 'Minimum donor weight in kg', 'donor', TRUE),
('ai.model_version', '1.0', 'Current AI model version', 'ai', FALSE),
('blockchain.network', 'ethereum', 'Blockchain network', 'blockchain', FALSE),
('iot.temperature_min', '2.0', 'Minimum storage temperature', 'iot', FALSE),
('iot.temperature_max', '6.0', 'Maximum storage temperature', 'iot', FALSE);

-- Insert sample blood groups for reference
INSERT IGNORE INTO donors (id, name, email, phone, blood_group, eligibility_status, total_donations, reward_points, tier_level) VALUES
('sample-donor-1', 'John Smith', 'john.smith@email.com', '+91-9876543210', 'O+', 'ELIGIBLE', 5, 50, 'SILVER'),
('sample-donor-2', 'Priya Sharma', 'priya.sharma@email.com', '+91-9876543211', 'A+', 'ELIGIBLE', 3, 30, 'BRONZE'),
('sample-donor-3', 'Raj Patel', 'raj.patel@email.com', '+91-9876543212', 'B-', 'ELIGIBLE', 8, 80, 'GOLD'),
('sample-donor-4', 'Sarah Johnson', 'sarah.johnson@email.com', '+91-9876543213', 'AB+', 'ELIGIBLE', 2, 20, 'BRONZE'),
('sample-donor-5', 'Mohammed Ali', 'mohammed.ali@email.com', '+91-9876543214', 'O-', 'ELIGIBLE', 12, 120, 'PLATINUM');

-- Insert sample blood inventory
INSERT IGNORE INTO blood_inventory (id, blood_group, units_available, expiry_date, collection_date, storage_location, status, quality_score) VALUES
('inv-001', 'O+', 25, DATE_ADD(CURDATE(), INTERVAL 30 DAY), DATE_SUB(CURDATE(), INTERVAL 5 DAY), 'Storage-A1', 'AVAILABLE', 9.8),
('inv-002', 'A+', 18, DATE_ADD(CURDATE(), INTERVAL 25 DAY), DATE_SUB(CURDATE(), INTERVAL 8 DAY), 'Storage-A2', 'AVAILABLE', 9.5),
('inv-003', 'B+', 12, DATE_ADD(CURDATE(), INTERVAL 35 DAY), DATE_SUB(CURDATE(), INTERVAL 3 DAY), 'Storage-B1', 'AVAILABLE', 9.9),
('inv-004', 'AB+', 8, DATE_ADD(CURDATE(), INTERVAL 28 DAY), DATE_SUB(CURDATE(), INTERVAL 6 DAY), 'Storage-B2', 'AVAILABLE', 9.7),
('inv-005', 'O-', 15, DATE_ADD(CURDATE(), INTERVAL 32 DAY), DATE_SUB(CURDATE(), INTERVAL 4 DAY), 'Storage-C1', 'AVAILABLE', 10.0),
('inv-006', 'A-', 10, DATE_ADD(CURDATE(), INTERVAL 20 DAY), DATE_SUB(CURDATE(), INTERVAL 10 DAY), 'Storage-C2', 'AVAILABLE', 9.3),
('inv-007', 'B-', 6, DATE_ADD(CURDATE(), INTERVAL 38 DAY), DATE_SUB(CURDATE(), INTERVAL 2 DAY), 'Storage-D1', 'AVAILABLE', 9.6),
('inv-008', 'AB-', 4, DATE_ADD(CURDATE(), INTERVAL 26 DAY), DATE_SUB(CURDATE(), INTERVAL 7 DAY), 'Storage-D2', 'AVAILABLE', 9.4);

-- Insert sample campaign
INSERT IGNORE INTO campaigns (id, title, description, start_date, end_date, location, city, state, target_units, collected_units, status, organizer_name) VALUES
('camp-001', 'World Blood Donor Day Drive', 'Annual blood donation drive on World Blood Donor Day', CURDATE(), DATE_ADD(CURDATE(), INTERVAL 7 DAY), 'City Hospital Complex', 'Mumbai', 'Maharashtra', 100, 25, 'ACTIVE', 'Dr. Anil Kumar');

-- Create database views for commonly used queries
CREATE OR REPLACE VIEW donor_statistics AS
SELECT 
    blood_group,
    COUNT(*) as total_donors,
    SUM(total_donations) as total_donations_made,
    AVG(reward_points) as avg_reward_points,
    COUNT(CASE WHEN eligibility_status = 'ELIGIBLE' THEN 1 END) as eligible_donors
FROM donors 
GROUP BY blood_group;

CREATE OR REPLACE VIEW inventory_summary AS
SELECT 
    blood_group,
    SUM(units_available) as total_units,
    SUM(units_reserved) as reserved_units,
    COUNT(*) as total_batches,
    MIN(expiry_date) as earliest_expiry,
    AVG(quality_score) as avg_quality
FROM blood_inventory 
WHERE status = 'AVAILABLE'
GROUP BY blood_group;

CREATE OR REPLACE VIEW campaign_performance AS
SELECT 
    c.id,
    c.title,
    c.target_units,
    c.collected_units,
    COUNT(d.id) as total_donations,
    (c.collected_units / c.target_units * 100) as completion_percentage
FROM campaigns c 
LEFT JOIN donations d ON c.id = d.campaign_id 
GROUP BY c.id;

-- Create stored procedures for common operations
DELIMITER //

CREATE PROCEDURE IF NOT EXISTS GetBloodAvailability()
BEGIN
    SELECT 
        blood_group,
        SUM(units_available) as available_units,
        COUNT(*) as batch_count,
        MIN(expiry_date) as next_expiry
    FROM blood_inventory 
    WHERE status = 'AVAILABLE' AND expiry_date > CURDATE()
    GROUP BY blood_group
    ORDER BY available_units DESC;
END //

CREATE PROCEDURE IF NOT EXISTS CheckDonorEligibility(IN donor_id VARCHAR(64))
BEGIN
    SELECT 
        d.name,
        d.blood_group,
        d.eligibility_status,
        d.last_donation_date,
        CASE 
            WHEN d.last_donation_date IS NULL THEN 'Eligible - First time donor'
            WHEN DATEDIFF(CURDATE(), d.last_donation_date) >= 56 THEN 'Eligible'
            ELSE CONCAT('Not eligible - ', 56 - DATEDIFF(CURDATE(), d.last_donation_date), ' days remaining')
        END as eligibility_message
    FROM donors d 
    WHERE d.id = donor_id;
END //

DELIMITER ;

-- Create triggers for audit logging and automatic updates
DELIMITER //

CREATE TRIGGER IF NOT EXISTS donors_audit_insert
AFTER INSERT ON donors
FOR EACH ROW
BEGIN
    INSERT INTO audit_log (user_id, action, table_name, record_id, new_values)
    VALUES (NULL, 'INSERT', 'donors', NEW.id, JSON_OBJECT(
        'name', NEW.name,
        'email', NEW.email,
        'blood_group', NEW.blood_group
    ));
END //

CREATE TRIGGER IF NOT EXISTS donors_audit_update
AFTER UPDATE ON donors
FOR EACH ROW
BEGIN
    INSERT INTO audit_log (user_id, action, table_name, record_id, old_values, new_values)
    VALUES (NULL, 'UPDATE', 'donors', NEW.id, 
        JSON_OBJECT('name', OLD.name, 'email', OLD.email),
        JSON_OBJECT('name', NEW.name, 'email', NEW.email)
    );
END //

CREATE TRIGGER IF NOT EXISTS update_donor_stats
AFTER INSERT ON donations
FOR EACH ROW
BEGIN
    UPDATE donors 
    SET 
        total_donations = total_donations + 1,
        last_donation_date = NEW.donation_date,
        reward_points = reward_points + NEW.reward_points_earned
    WHERE id = NEW.donor_id;
END //

DELIMITER ;

-- Grant appropriate permissions (adjust as needed)
-- GRANT ALL PRIVILEGES ON bloodmate.* TO 'bloodmate_user'@'localhost' IDENTIFIED BY 'secure_password';
-- FLUSH PRIVILEGES;

SELECT 'BloodMate MySQL Database initialization completed successfully!' as message;