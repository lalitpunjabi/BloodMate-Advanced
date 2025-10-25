package com.bloodmate.desktop.dao;

import com.bloodmate.desktop.model.SensorData;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SensorDataDao {
    private final Connection connection;

    public SensorDataDao(Connection connection) {
        this.connection = connection;
        ensureTable();
    }

    private void ensureTable() {
        String sql = "CREATE TABLE IF NOT EXISTS sensor_data (" +
            "id VARCHAR(64) PRIMARY KEY," +
            "sensor_id VARCHAR(50) NOT NULL," +
            "sensor_type VARCHAR(30) NOT NULL," +
            "value DOUBLE NOT NULL," +
            "unit VARCHAR(10) NOT NULL," +
            "location VARCHAR(100)," +
            "timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
            "blood_bag_id VARCHAR(64)," +
            "status VARCHAR(20) DEFAULT 'ACTIVE'" +
            ")";
        try (Statement st = connection.createStatement()) {
            st.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create sensor_data table", e);
        }
    }

    public List<SensorData> findAll() {
        List<SensorData> list = new ArrayList<>();
        String sql = "SELECT * FROM sensor_data ORDER BY timestamp DESC";
        try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapResultSetToSensorData(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch sensor data", e);
        }
        return list;
    }

    public SensorData findById(String id) {
        String sql = "SELECT * FROM sensor_data WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultSetToSensorData(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch sensor data by ID", e);
        }
        return null;
    }

    public boolean insert(SensorData sensorData) {
        // Generate a new UUID if ID is not provided
        String id = sensorData.getId() == null || sensorData.getId().isEmpty()
            ? UUID.randomUUID().toString()
            : sensorData.getId();
        sensorData.setId(id);

        String sql = "INSERT INTO sensor_data " +
            "(id, sensor_id, sensor_type, value, unit, location, timestamp, blood_bag_id, status) " +
            "VALUES (?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.setString(2, sensorData.getSensorId());
            ps.setString(3, sensorData.getSensorType());
            ps.setDouble(4, sensorData.getValue());
            ps.setString(5, sensorData.getUnit());
            ps.setString(6, sensorData.getLocation());
            ps.setTimestamp(7, Timestamp.valueOf(sensorData.getTimestamp() != null
                ? sensorData.getTimestamp()
                : LocalDateTime.now()));
            ps.setString(8, sensorData.getBloodBagId());
            ps.setString(9, sensorData.getStatus());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert sensor data", e);
        }
    }

    public boolean update(SensorData sensorData) {
        String sql = "UPDATE sensor_data SET " +
            "sensor_id=?, sensor_type=?, value=?, unit=?, location=?, timestamp=?, blood_bag_id=?, status=? " +
            "WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, sensorData.getSensorId());
            ps.setString(2, sensorData.getSensorType());
            ps.setDouble(3, sensorData.getValue());
            ps.setString(4, sensorData.getUnit());
            ps.setString(5, sensorData.getLocation());
            ps.setTimestamp(6, Timestamp.valueOf(sensorData.getTimestamp()));
            ps.setString(7, sensorData.getBloodBagId());
            ps.setString(8, sensorData.getStatus());
            ps.setString(9, sensorData.getId());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update sensor data", e);
        }
    }

    public boolean delete(String id) {
        String sql = "DELETE FROM sensor_data WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete sensor data", e);
        }
    }

    private SensorData mapResultSetToSensorData(ResultSet rs) throws SQLException {
        SensorData sd = new SensorData();
        sd.setId(rs.getString("id"));
        sd.setSensorId(rs.getString("sensor_id"));
        sd.setSensorType(rs.getString("sensor_type"));
        sd.setValue(rs.getDouble("value"));
        sd.setUnit(rs.getString("unit"));
        sd.setLocation(rs.getString("location"));
        Timestamp ts = rs.getTimestamp("timestamp");
        if (ts != null) {
            sd.setTimestamp(ts.toLocalDateTime());
        }
        sd.setBloodBagId(rs.getString("blood_bag_id"));
        sd.setStatus(rs.getString("status"));
        return sd;
    }
}
