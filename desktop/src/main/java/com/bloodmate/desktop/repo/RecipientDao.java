package com.bloodmate.desktop.repo;

import com.bloodmate.desktop.model.Recipient;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RecipientDao {
    private final Connection connection;

    public RecipientDao(Connection connection) {
        this.connection = connection;
        ensureTable();
    }

    private void ensureTable() {
        String sql = "CREATE TABLE IF NOT EXISTS recipients (" +
            "id VARCHAR(64) PRIMARY KEY," +
            "patient_name VARCHAR(100) NOT NULL," +
            "blood_group VARCHAR(5) NOT NULL," +
            "medical_facility VARCHAR(200) NOT NULL," +
            "doctor_name VARCHAR(100) NOT NULL," +
            "contact_number VARCHAR(50)," +
            "urgency_level ENUM('LOW', 'MEDIUM', 'HIGH', 'CRITICAL') DEFAULT 'MEDIUM'," +
            "status ENUM('PENDING', 'APPROVED', 'FULFILLED', 'CANCELLED') DEFAULT 'PENDING'," +
            "date_of_birth DATE," +
            "medical_history TEXT," +
            "units_required INT NOT NULL DEFAULT 1," +
            "request_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
            "required_by TIMESTAMP," +
            "reason TEXT," +
            "location VARCHAR(200)" +
            ")";
        try (Statement st = connection.createStatement()) {
            st.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create recipients table", e);
        }
    }

    public List<Recipient> findAll() {
        List<Recipient> list = new ArrayList<>();
        String sql = "SELECT * FROM recipients ORDER BY request_date DESC";
        try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Recipient r = mapResultSetToRecipient(rs);
                list.add(r);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<Recipient> findByBloodGroup(String bloodGroup) {
        List<Recipient> list = new ArrayList<>();
        String sql = "SELECT * FROM recipients WHERE blood_group = ? AND status = 'PENDING' ORDER BY urgency_level DESC, request_date ASC";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, bloodGroup);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Recipient r = mapResultSetToRecipient(rs);
                list.add(r);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<Recipient> findByUrgencyLevel(String urgencyLevel) {
        List<Recipient> list = new ArrayList<>();
        String sql = "SELECT * FROM recipients WHERE urgency_level = ? AND status = 'PENDING' ORDER BY request_date ASC";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, urgencyLevel);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Recipient r = mapResultSetToRecipient(rs);
                list.add(r);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public boolean insert(Recipient recipient) {
        String id = recipient.getId() == null || recipient.getId().isEmpty() ? 
                   UUID.randomUUID().toString() : recipient.getId();
        recipient.setId(id);
        
        String sql = "INSERT INTO recipients (id, patient_name, blood_group, medical_facility, " +
                    "doctor_name, contact_number, urgency_level, status, date_of_birth, " +
                    "medical_history, units_required, required_by, reason, location) " +
                    "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.setString(2, recipient.getPatientName());
            ps.setString(3, recipient.getBloodGroup());
            ps.setString(4, recipient.getMedicalFacility());
            ps.setString(5, recipient.getDoctorName());
            ps.setString(6, recipient.getContactNumber());
            ps.setString(7, recipient.getUrgencyLevel() != null ? recipient.getUrgencyLevel() : "MEDIUM");
            ps.setString(8, recipient.getStatus() != null ? recipient.getStatus() : "PENDING");
            ps.setDate(9, recipient.getDateOfBirth() != null ? Date.valueOf(recipient.getDateOfBirth()) : null);
            ps.setString(10, recipient.getMedicalHistory());
            ps.setInt(11, recipient.getUnitsRequired());
            ps.setTimestamp(12, recipient.getRequiredBy() != null ? Timestamp.valueOf(recipient.getRequiredBy()) : null);
            ps.setString(13, recipient.getReason());
            ps.setString(14, recipient.getLocation());
            
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean update(Recipient recipient) {
        String sql = "UPDATE recipients SET patient_name=?, blood_group=?, medical_facility=?, " +
                    "doctor_name=?, contact_number=?, urgency_level=?, status=?, date_of_birth=?, " +
                    "medical_history=?, units_required=?, required_by=?, reason=?, location=? " +
                    "WHERE id=?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, recipient.getPatientName());
            ps.setString(2, recipient.getBloodGroup());
            ps.setString(3, recipient.getMedicalFacility());
            ps.setString(4, recipient.getDoctorName());
            ps.setString(5, recipient.getContactNumber());
            ps.setString(6, recipient.getUrgencyLevel());
            ps.setString(7, recipient.getStatus());
            ps.setDate(8, recipient.getDateOfBirth() != null ? Date.valueOf(recipient.getDateOfBirth()) : null);
            ps.setString(9, recipient.getMedicalHistory());
            ps.setInt(10, recipient.getUnitsRequired());
            ps.setTimestamp(11, recipient.getRequiredBy() != null ? Timestamp.valueOf(recipient.getRequiredBy()) : null);
            ps.setString(12, recipient.getReason());
            ps.setString(13, recipient.getLocation());
            ps.setString(14, recipient.getId());
            
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean delete(String id) {
        String sql = "DELETE FROM recipients WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            return false;
        }
    }

    private Recipient mapResultSetToRecipient(ResultSet rs) throws SQLException {
        Recipient r = new Recipient();
        r.setId(rs.getString("id"));
        r.setPatientName(rs.getString("patient_name"));
        r.setBloodGroup(rs.getString("blood_group"));
        r.setMedicalFacility(rs.getString("medical_facility"));
        r.setDoctorName(rs.getString("doctor_name"));
        r.setContactNumber(rs.getString("contact_number"));
        r.setUrgencyLevel(rs.getString("urgency_level"));
        r.setStatus(rs.getString("status"));
        
        Date dob = rs.getDate("date_of_birth");
        if (dob != null) {
            r.setDateOfBirth(dob.toLocalDate());
        }
        
        r.setMedicalHistory(rs.getString("medical_history"));
        r.setUnitsRequired(rs.getInt("units_required"));
        
        Timestamp requestDate = rs.getTimestamp("request_date");
        if (requestDate != null) {
            r.setRequestDate(requestDate.toLocalDateTime());
        }
        
        Timestamp requiredBy = rs.getTimestamp("required_by");
        if (requiredBy != null) {
            r.setRequiredBy(requiredBy.toLocalDateTime());
        }
        
        r.setReason(rs.getString("reason"));
        r.setLocation(rs.getString("location"));
        
        return r;
    }
}