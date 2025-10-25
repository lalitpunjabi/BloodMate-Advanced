package com.bloodmate.desktop.dao;

import com.bloodmate.desktop.model.EmergencyRequest;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EmergencyRequestDao {
    private final Connection connection;

    public EmergencyRequestDao(Connection connection) {
        this.connection = connection;
        ensureTable();
    }

    private void ensureTable() {
        String sql = "CREATE TABLE IF NOT EXISTS emergency_requests (" +
            "id VARCHAR(64) PRIMARY KEY," +
            "patient_name VARCHAR(100) NOT NULL," +
            "blood_group VARCHAR(5) NOT NULL," +
            "hospital VARCHAR(100)," +
            "doctor VARCHAR(100)," +
            "contact_number VARCHAR(20)," +
            "urgency_level VARCHAR(20)," +
            "location VARCHAR(100)," +
            "notes TEXT," +
            "status VARCHAR(20)," +
            "request_time VARCHAR(30)," +
            "response_time VARCHAR(30)," +
            "fulfillment_time VARCHAR(30)," +
            "assigned_unit VARCHAR(64)" +
            ")";
        try (Statement st = connection.createStatement()) {
            st.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create emergency_requests table", e);
        }
    }

    public List<EmergencyRequest> findAll() {
        List<EmergencyRequest> list = new ArrayList<>();
        String sql = "SELECT * FROM emergency_requests ORDER BY request_time DESC";
        try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                EmergencyRequest er = new EmergencyRequest();
                er.setId(rs.getString("id"));
                er.setPatientName(rs.getString("patient_name"));
                er.setBloodGroup(rs.getString("blood_group"));
                er.setHospital(rs.getString("hospital"));
                er.setDoctor(rs.getString("doctor"));
                er.setContactNumber(rs.getString("contact_number"));
                er.setUrgencyLevel(rs.getString("urgency_level"));
                er.setLocation(rs.getString("location"));
                er.setNotes(rs.getString("notes"));
                er.setStatus(rs.getString("status"));
                er.setRequestTime(java.time.LocalDateTime.parse(rs.getString("request_time")));
                if (rs.getString("response_time") != null) {
                    er.setResponseTime(java.time.LocalDateTime.parse(rs.getString("response_time")));
                }
                if (rs.getString("fulfillment_time") != null) {
                    er.setFulfillmentTime(java.time.LocalDateTime.parse(rs.getString("fulfillment_time")));
                }
                er.setAssignedUnit(rs.getString("assigned_unit"));
                list.add(er);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public boolean insert(EmergencyRequest request) {
        String id = request.getId() == null || request.getId().isEmpty() ? UUID.randomUUID().toString() : request.getId();
        request.setId(id);
        String sql = "INSERT INTO emergency_requests (id, patient_name, blood_group, hospital, doctor, " +
            "contact_number, urgency_level, location, notes, status, request_time, response_time, fulfillment_time, assigned_unit) " +
            "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.setString(2, request.getPatientName());
            ps.setString(3, request.getBloodGroup());
            ps.setString(4, request.getHospital());
            ps.setString(5, request.getDoctor());
            ps.setString(6, request.getContactNumber());
            ps.setString(7, request.getUrgencyLevel());
            ps.setString(8, request.getLocation());
            ps.setString(9, request.getNotes());
            ps.setString(10, request.getStatus());
            ps.setString(11, request.getRequestTime().toString());
            ps.setString(12, request.getResponseTime() != null ? request.getResponseTime().toString() : null);
            ps.setString(13, request.getFulfillmentTime() != null ? request.getFulfillmentTime().toString() : null);
            ps.setString(14, request.getAssignedUnit());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean assignUnit(String requestId, String unitId) {
        String sql = "UPDATE emergency_requests SET assigned_unit = ?, status = 'ASSIGNED', response_time = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, unitId);
            ps.setString(2, java.time.LocalDateTime.now().toString());
            ps.setString(3, requestId);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean fulfillRequest(String requestId) {
        String sql = "UPDATE emergency_requests SET status = 'FULFILLED', fulfillment_time = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, java.time.LocalDateTime.now().toString());
            ps.setString(2, requestId);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            return false;
        }
    }
}