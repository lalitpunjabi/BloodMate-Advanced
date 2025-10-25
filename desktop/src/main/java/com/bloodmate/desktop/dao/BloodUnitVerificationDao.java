package com.bloodmate.desktop.dao;

import com.bloodmate.desktop.model.BloodUnitVerification;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BloodUnitVerificationDao {
    private final Connection connection;

    public BloodUnitVerificationDao(Connection connection) {
        this.connection = connection;
        ensureTable();
    }

    private void ensureTable() {
        String sql = "CREATE TABLE IF NOT EXISTS blood_unit_verifications (" +
            "id VARCHAR(64) PRIMARY KEY," +
            "blood_unit_id VARCHAR(64) NOT NULL," +
            "verification_hash VARCHAR(128) NOT NULL," +
            "verification_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
            "verified_by VARCHAR(100)," +
            "status VARCHAR(20) DEFAULT 'PENDING'," +
            "notes TEXT" +
            ")";
        try (Statement st = connection.createStatement()) {
            st.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create blood_unit_verifications table", e);
        }
    }

    public List<BloodUnitVerification> findAll() {
        List<BloodUnitVerification> list = new ArrayList<>();
        String sql = "SELECT * FROM blood_unit_verifications ORDER BY verification_date DESC";
        try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapResultSetToVerification(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public BloodUnitVerification findById(String id) {
        String sql = "SELECT * FROM blood_unit_verifications WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultSetToVerification(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public boolean insert(BloodUnitVerification verification) {
        String id = verification.getId() == null || verification.getId().isEmpty()
            ? UUID.randomUUID().toString()
            : verification.getId();
        verification.setId(id);

        String sql = "INSERT INTO blood_unit_verifications " +
            "(id, blood_unit_id, verification_hash, verification_date, verified_by, status, notes) " +
            "VALUES (?,?,?,?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.setString(2, verification.getBloodUnitId());
            ps.setString(3, verification.getVerificationHash());
            ps.setTimestamp(4, Timestamp.valueOf(verification.getVerificationDate() != null 
                ? verification.getVerificationDate() 
                : LocalDateTime.now()));
            ps.setString(5, verification.getVerifiedBy());
            ps.setString(6, verification.getStatus());
            ps.setString(7, verification.getNotes());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean update(BloodUnitVerification verification) {
        String sql = "UPDATE blood_unit_verifications SET " +
            "blood_unit_id=?, verification_hash=?, verification_date=?, verified_by=?, " +
            "status=?, notes=? WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, verification.getBloodUnitId());
            ps.setString(2, verification.getVerificationHash());
            ps.setTimestamp(3, Timestamp.valueOf(verification.getVerificationDate()));
            ps.setString(4, verification.getVerifiedBy());
            ps.setString(5, verification.getStatus());
            ps.setString(6, verification.getNotes());
            ps.setString(7, verification.getId());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean delete(String id) {
        String sql = "DELETE FROM blood_unit_verifications WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            return false;
        }
    }

    private BloodUnitVerification mapResultSetToVerification(ResultSet rs) throws SQLException {
        BloodUnitVerification v = new BloodUnitVerification();
        v.setId(rs.getString("id"));
        v.setBloodUnitId(rs.getString("blood_unit_id"));
        v.setVerificationHash(rs.getString("verification_hash"));

        Timestamp ts = rs.getTimestamp("verification_date");
        if (ts != null) {
            v.setVerificationDate(ts.toLocalDateTime());
        }

        v.setVerifiedBy(rs.getString("verified_by"));
        v.setStatus(rs.getString("status"));
        v.setNotes(rs.getString("notes"));
        return v;
    }
}
