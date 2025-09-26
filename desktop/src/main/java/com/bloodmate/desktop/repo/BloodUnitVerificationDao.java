package com.bloodmate.desktop.repo;

import com.bloodmate.desktop.model.BloodUnitVerification;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class BloodUnitVerificationDao {
    private final Connection connection;

    public BloodUnitVerificationDao(Connection connection) {
        this.connection = connection;
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        String createTableSQL = """
            CREATE TABLE IF NOT EXISTS blood_unit_verifications (
                id VARCHAR(36) PRIMARY KEY,
                blood_bag_id VARCHAR(50) NOT NULL,
                donor_id VARCHAR(36),
                donor_name VARCHAR(100),
                blood_group VARCHAR(10),
                collection_date TIMESTAMP,
                verification_date TIMESTAMP NOT NULL,
                verification_status VARCHAR(20) DEFAULT 'PENDING',
                verifier VARCHAR(100),
                location VARCHAR(100),
                hash VARCHAR(64),
                previous_hash VARCHAR(64),
                signature VARCHAR(256),
                notes TEXT,
                INDEX idx_blood_bag_id (blood_bag_id),
                INDEX idx_donor_id (donor_id),
                INDEX idx_verification_status (verification_status),
                INDEX idx_verification_date (verification_date)
            )
            """;

        try (PreparedStatement stmt = connection.prepareStatement(createTableSQL)) {
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean insert(BloodUnitVerification verification) {
        // Generate hash if not already set
        if (verification.getHash() == null || verification.getHash().isEmpty()) {
            verification.setHash(generateHash(verification));
        }
        
        String insertSQL = """
            INSERT INTO blood_unit_verifications (
                id, blood_bag_id, donor_id, donor_name, blood_group, collection_date,
                verification_date, verification_status, verifier, location, hash, previous_hash, signature, notes
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

        try (PreparedStatement stmt = connection.prepareStatement(insertSQL)) {
            stmt.setString(1, verification.getId());
            stmt.setString(2, verification.getBloodBagId());
            stmt.setString(3, verification.getDonorId());
            stmt.setString(4, verification.getDonorName());
            stmt.setString(5, verification.getBloodGroup());
            stmt.setTimestamp(6, verification.getCollectionDate() != null ? 
                Timestamp.valueOf(verification.getCollectionDate()) : null);
            stmt.setTimestamp(7, Timestamp.valueOf(verification.getVerificationDate()));
            stmt.setString(8, verification.getVerificationStatus());
            stmt.setString(9, verification.getVerifier());
            stmt.setString(10, verification.getLocation());
            stmt.setString(11, verification.getHash());
            stmt.setString(12, verification.getPreviousHash());
            stmt.setString(13, verification.getSignature());
            stmt.setString(14, verification.getNotes());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(BloodUnitVerification verification) {
        // Regenerate hash when updating
        verification.setHash(generateHash(verification));
        
        String updateSQL = """
            UPDATE blood_unit_verifications SET
                blood_bag_id = ?, donor_id = ?, donor_name = ?, blood_group = ?, collection_date = ?,
                verification_date = ?, verification_status = ?, verifier = ?, location = ?, 
                hash = ?, previous_hash = ?, signature = ?, notes = ?
            WHERE id = ?
            """;

        try (PreparedStatement stmt = connection.prepareStatement(updateSQL)) {
            stmt.setString(1, verification.getBloodBagId());
            stmt.setString(2, verification.getDonorId());
            stmt.setString(3, verification.getDonorName());
            stmt.setString(4, verification.getBloodGroup());
            stmt.setTimestamp(5, verification.getCollectionDate() != null ? 
                Timestamp.valueOf(verification.getCollectionDate()) : null);
            stmt.setTimestamp(6, Timestamp.valueOf(verification.getVerificationDate()));
            stmt.setString(7, verification.getVerificationStatus());
            stmt.setString(8, verification.getVerifier());
            stmt.setString(9, verification.getLocation());
            stmt.setString(10, verification.getHash());
            stmt.setString(11, verification.getPreviousHash());
            stmt.setString(12, verification.getSignature());
            stmt.setString(13, verification.getNotes());
            stmt.setString(14, verification.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(String id) {
        String deleteSQL = "DELETE FROM blood_unit_verifications WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(deleteSQL)) {
            stmt.setString(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Optional<BloodUnitVerification> findById(String id) {
        String selectSQL = "SELECT * FROM blood_unit_verifications WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(selectSQL)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToVerification(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public List<BloodUnitVerification> findAll() {
        List<BloodUnitVerification> verificationList = new ArrayList<>();
        String selectSQL = "SELECT * FROM blood_unit_verifications ORDER BY verification_date DESC";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL)) {

            while (rs.next()) {
                verificationList.add(mapResultSetToVerification(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return verificationList;
    }

    public List<BloodUnitVerification> findByBloodBagId(String bloodBagId) {
        List<BloodUnitVerification> verificationList = new ArrayList<>();
        String selectSQL = "SELECT * FROM blood_unit_verifications WHERE blood_bag_id = ? ORDER BY verification_date DESC";

        try (PreparedStatement stmt = connection.prepareStatement(selectSQL)) {
            stmt.setString(1, bloodBagId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    verificationList.add(mapResultSetToVerification(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return verificationList;
    }

    public List<BloodUnitVerification> findByDonorId(String donorId) {
        List<BloodUnitVerification> verificationList = new ArrayList<>();
        String selectSQL = "SELECT * FROM blood_unit_verifications WHERE donor_id = ? ORDER BY verification_date DESC";

        try (PreparedStatement stmt = connection.prepareStatement(selectSQL)) {
            stmt.setString(1, donorId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    verificationList.add(mapResultSetToVerification(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return verificationList;
    }

    public List<BloodUnitVerification> findByVerificationStatus(String status) {
        List<BloodUnitVerification> verificationList = new ArrayList<>();
        String selectSQL = "SELECT * FROM blood_unit_verifications WHERE verification_status = ? ORDER BY verification_date DESC";

        try (PreparedStatement stmt = connection.prepareStatement(selectSQL)) {
            stmt.setString(1, status);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    verificationList.add(mapResultSetToVerification(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return verificationList;
    }

    public List<BloodUnitVerification> findRecentVerifications(int limit) {
        List<BloodUnitVerification> verificationList = new ArrayList<>();
        String selectSQL = "SELECT * FROM blood_unit_verifications ORDER BY verification_date DESC LIMIT ?";

        try (PreparedStatement stmt = connection.prepareStatement(selectSQL)) {
            stmt.setInt(1, limit);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    verificationList.add(mapResultSetToVerification(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return verificationList;
    }

    public List<BloodUnitVerification> findVerificationsInTimeRange(LocalDateTime start, LocalDateTime end) {
        List<BloodUnitVerification> verificationList = new ArrayList<>();
        String selectSQL = "SELECT * FROM blood_unit_verifications WHERE verification_date BETWEEN ? AND ? ORDER BY verification_date DESC";

        try (PreparedStatement stmt = connection.prepareStatement(selectSQL)) {
            stmt.setTimestamp(1, Timestamp.valueOf(start));
            stmt.setTimestamp(2, Timestamp.valueOf(end));
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    verificationList.add(mapResultSetToVerification(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return verificationList;
    }

    public boolean verifyBloodUnit(String bloodBagId) {
        // Find the latest verification for this blood bag
        List<BloodUnitVerification> verifications = findByBloodBagId(bloodBagId);
        if (verifications.isEmpty()) {
            return false;
        }
        
        BloodUnitVerification latest = verifications.get(0);
        // In a real blockchain system, we would verify the hash chain
        // For this implementation, we'll just mark it as verified
        latest.setVerificationStatus("VERIFIED");
        latest.setVerificationDate(LocalDateTime.now());
        return update(latest);
    }

    public boolean isBloodUnitVerified(String bloodBagId) {
        List<BloodUnitVerification> verifications = findByBloodBagId(bloodBagId);
        if (verifications.isEmpty()) {
            return false;
        }
        
        BloodUnitVerification latest = verifications.get(0);
        return "VERIFIED".equals(latest.getVerificationStatus());
    }

    private BloodUnitVerification mapResultSetToVerification(ResultSet rs) throws SQLException {
        BloodUnitVerification verification = new BloodUnitVerification();
        verification.setId(rs.getString("id"));
        verification.setBloodBagId(rs.getString("blood_bag_id"));
        verification.setDonorId(rs.getString("donor_id"));
        verification.setDonorName(rs.getString("donor_name"));
        verification.setBloodGroup(rs.getString("blood_group"));
        verification.setCollectionDate(rs.getTimestamp("collection_date") != null ? 
            rs.getTimestamp("collection_date").toLocalDateTime() : null);
        verification.setVerificationDate(rs.getTimestamp("verification_date").toLocalDateTime());
        verification.setVerificationStatus(rs.getString("verification_status"));
        verification.setVerifier(rs.getString("verifier"));
        verification.setLocation(rs.getString("location"));
        verification.setHash(rs.getString("hash"));
        verification.setPreviousHash(rs.getString("previous_hash"));
        verification.setSignature(rs.getString("signature"));
        verification.setNotes(rs.getString("notes"));
        return verification;
    }
    
    private String generateHash(BloodUnitVerification verification) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String data = verification.getBloodBagId() + 
                         verification.getDonorId() + 
                         verification.getDonorName() + 
                         verification.getBloodGroup() + 
                         verification.getCollectionDate() + 
                         verification.getVerificationDate() + 
                         verification.getVerificationStatus() + 
                         verification.getVerifier() + 
                         verification.getLocation() + 
                         verification.getPreviousHash() + 
                         verification.getSignature() + 
                         verification.getNotes();
            
            byte[] hashBytes = digest.digest(data.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }
}