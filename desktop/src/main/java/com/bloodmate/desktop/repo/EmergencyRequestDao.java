package com.bloodmate.desktop.repo;

import com.bloodmate.desktop.model.EmergencyRequest;
import com.bloodmate.desktop.db.Db;

import java.sql.*;
import java.time.LocalDateTime;
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
            "hospital VARCHAR(200) NOT NULL," +
            "doctor VARCHAR(100) NOT NULL," +
            "contact_number VARCHAR(50)," +
            "urgency_level ENUM('LOW', 'MEDIUM', 'HIGH', 'CRITICAL') DEFAULT 'HIGH'," +
            "status ENUM('PENDING', 'ASSIGNED', 'FULFILLED', 'CANCELLED') DEFAULT 'PENDING'," +
            "request_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
            "response_time TIMESTAMP NULL," +
            "fulfillment_time TIMESTAMP NULL," +
            "assigned_unit VARCHAR(64) NULL," +
            "notes TEXT," +
            "location VARCHAR(200)," +
            "INDEX idx_status (status)," +
            "INDEX idx_urgency (urgency_level)," +
            "INDEX idx_blood_group (blood_group)," +
            "INDEX idx_request_time (request_time DESC)" +
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
                EmergencyRequest request = mapResultSetToEmergencyRequest(rs);
                list.add(request);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<EmergencyRequest> findByStatus(String status) {
        List<EmergencyRequest> list = new ArrayList<>();
        String sql = "SELECT * FROM emergency_requests WHERE status = ? ORDER BY request_time DESC";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, status);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                EmergencyRequest request = mapResultSetToEmergencyRequest(rs);
                list.add(request);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<EmergencyRequest> findByUrgencyLevel(String urgencyLevel) {
        List<EmergencyRequest> list = new ArrayList<>();
        String sql = "SELECT * FROM emergency_requests WHERE urgency_level = ? AND status = 'PENDING' ORDER BY request_time DESC";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, urgencyLevel);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                EmergencyRequest request = mapResultSetToEmergencyRequest(rs);
                list.add(request);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<EmergencyRequest> findByBloodGroup(String bloodGroup) {
        List<EmergencyRequest> list = new ArrayList<>();
        String sql = "SELECT * FROM emergency_requests WHERE blood_group = ? AND status = 'PENDING' ORDER BY urgency_level DESC, request_time ASC";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, bloodGroup);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                EmergencyRequest request = mapResultSetToEmergencyRequest(rs);
                list.add(request);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<EmergencyRequest> findPending() {
        List<EmergencyRequest> list = new ArrayList<>();
        String sql = "SELECT * FROM emergency_requests WHERE status = 'PENDING' ORDER BY urgency_level DESC, request_time ASC";
        try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                EmergencyRequest request = mapResultSetToEmergencyRequest(rs);
                list.add(request);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public boolean insert(EmergencyRequest request) {
        String id = request.getId() == null || request.getId().isEmpty() ? 
                   UUID.randomUUID().toString() : request.getId();
        request.setId(id);
        
        String sql = "INSERT INTO emergency_requests (" +
                    "id, patient_name, blood_group, hospital, doctor, contact_number, " +
                    "urgency_level, status, request_time, response_time, fulfillment_time, " +
                    "assigned_unit, notes, location) " +
                    "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.setString(2, request.getPatientName());
            ps.setString(3, request.getBloodGroup());
            ps.setString(4, request.getHospital());
            ps.setString(5, request.getDoctor());
            ps.setString(6, request.getContactNumber());
            ps.setString(7, request.getUrgencyLevel() != null ? request.getUrgencyLevel() : "HIGH");
            ps.setString(8, request.getStatus() != null ? request.getStatus() : "PENDING");
            ps.setTimestamp(9, request.getRequestTime() != null ? Timestamp.valueOf(request.getRequestTime()) : Timestamp.valueOf(LocalDateTime.now()));
            ps.setTimestamp(10, request.getResponseTime() != null ? Timestamp.valueOf(request.getResponseTime()) : null);
            ps.setTimestamp(11, request.getFulfillmentTime() != null ? Timestamp.valueOf(request.getFulfillmentTime()) : null);
            ps.setString(12, request.getAssignedUnit());
            ps.setString(13, request.getNotes());
            ps.setString(14, request.getLocation());
            
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(EmergencyRequest request) {
        String sql = "UPDATE emergency_requests SET " +
                    "patient_name=?, blood_group=?, hospital=?, doctor=?, contact_number=?, " +
                    "urgency_level=?, status=?, request_time=?, response_time=?, fulfillment_time=?, " +
                    "assigned_unit=?, notes=?, location=? WHERE id=?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, request.getPatientName());
            ps.setString(2, request.getBloodGroup());
            ps.setString(3, request.getHospital());
            ps.setString(4, request.getDoctor());
            ps.setString(5, request.getContactNumber());
            ps.setString(6, request.getUrgencyLevel());
            ps.setString(7, request.getStatus());
            ps.setTimestamp(8, request.getRequestTime() != null ? Timestamp.valueOf(request.getRequestTime()) : null);
            ps.setTimestamp(9, request.getResponseTime() != null ? Timestamp.valueOf(request.getResponseTime()) : null);
            ps.setTimestamp(10, request.getFulfillmentTime() != null ? Timestamp.valueOf(request.getFulfillmentTime()) : null);
            ps.setString(11, request.getAssignedUnit());
            ps.setString(12, request.getNotes());
            ps.setString(13, request.getLocation());
            ps.setString(14, request.getId());
            
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean delete(String id) {
        String sql = "DELETE FROM emergency_requests WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean assignUnit(String requestId, String unitId) {
        String sql = "UPDATE emergency_requests SET status = 'ASSIGNED', assigned_unit = ?, response_time = CURRENT_TIMESTAMP WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, unitId);
            ps.setString(2, requestId);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean fulfillRequest(String requestId) {
        String sql = "UPDATE emergency_requests SET status = 'FULFILLED', fulfillment_time = CURRENT_TIMESTAMP WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, requestId);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            return false;
        }
    }

    private EmergencyRequest mapResultSetToEmergencyRequest(ResultSet rs) throws SQLException {
        EmergencyRequest request = new EmergencyRequest();
        request.setId(rs.getString("id"));
        request.setPatientName(rs.getString("patient_name"));
        request.setBloodGroup(rs.getString("blood_group"));
        request.setHospital(rs.getString("hospital"));
        request.setDoctor(rs.getString("doctor"));
        request.setContactNumber(rs.getString("contact_number"));
        request.setUrgencyLevel(rs.getString("urgency_level"));
        request.setStatus(rs.getString("status"));
        
        Timestamp requestTime = rs.getTimestamp("request_time");
        if (requestTime != null) {
            request.setRequestTime(requestTime.toLocalDateTime());
        }
        
        Timestamp responseTime = rs.getTimestamp("response_time");
        if (responseTime != null) {
            request.setResponseTime(responseTime.toLocalDateTime());
        }
        
        Timestamp fulfillmentTime = rs.getTimestamp("fulfillment_time");
        if (fulfillmentTime != null) {
            request.setFulfillmentTime(fulfillmentTime.toLocalDateTime());
        }
        
        request.setAssignedUnit(rs.getString("assigned_unit"));
        request.setNotes(rs.getString("notes"));
        request.setLocation(rs.getString("location"));
        
        return request;
    }
}