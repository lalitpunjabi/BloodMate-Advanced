package com.bloodmate.desktop.dao;

import com.bloodmate.desktop.model.BloodUnitVerification;
import java.sql.*;
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
            "verification_date VARCHAR(30) NOT NULL," +
            "verified_by VARCHAR(100)," +
            "status VARCHAR(20)," +
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
        String sql = "SELECT * FROM blood_unit_verifications ORDER BY timestamp DESC";
        try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                BloodUnitVerification buv = new BloodUnitVerification();
                buv.setId(rs.getString("id"));
                buv.setBloodUnitId(rs.getString("blood_unit_id"));
                buv.setVerificationHash(rs.getString("verification_hash"));
                buv.setVerificationDate(java.time.LocalDateTime.parse(rs.getString("timestamp")));
                buv.setVerifiedBy(rs.getString("verifier"));
                buv.setStatus(rs.getString("status"));
                list.add(buv);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public boolean insert(BloodUnitVerification verification) {
        String id = verification.getId() == null || verification.getId().isEmpty() ? UUID.randomUUID().toString() : verification.getId();
        verification.setId(id);
        String sql = "INSERT INTO blood_unit_verifications (id, blood_unit_id, verification_hash, verification_date, verified_by, status, notes) " +
            "VALUES (?,?,?,?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.setString(2, verification.getBloodUnitId());
            ps.setString(3, verification.getVerificationHash());
            ps.setString(4, verification.getVerificationDate().toString());
            ps.setString(5, verification.getVerifiedBy());
            ps.setString(6, verification.getStatus());
            ps.setString(7, verification.getNotes());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            return false;
        }
    }
}