-- BloodMate Database Initialization Script
-- This script creates sample data for development and testing

USE bloodmate;

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
