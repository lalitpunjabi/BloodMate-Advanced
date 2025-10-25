package com.bloodmate.desktop.dao;

import com.bloodmate.desktop.model.SensorData;
import java.sql.*;
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
            "timestamp VARCHAR(30) NOT NULL," +
            "blood_bag_id VARCHAR(64)," +
            "status VARCHAR(20)" +
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
                SensorData sd = new SensorData();
                sd.setId(rs.getString("id"));
                sd.setSensorId(rs.getString("sensor_id"));
                sd.setSensorType(rs.getString("sensor_type"));
                sd.setValue(rs.getDouble("value"));
                sd.setUnit(rs.getString("unit"));
                sd.setLocation(rs.getString("location"));
                sd.setTimestamp(java.time.LocalDateTime.parse(rs.getString("timestamp")));
                sd.setBloodBagId(rs.getString("blood_bag_id"));
                sd.setStatus(rs.getString("status"));
                list.add(sd);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public boolean insert(SensorData sensorData) {
        String id = sensorData.getId() == null || sensorData.getId().isEmpty() ? UUID.randomUUID().toString() : sensorData.getId();
        sensorData.setId(id);
        String sql = "INSERT INTO sensor_data (id, sensor_id, sensor_type, value, unit, location, timestamp, blood_bag_id, status) " +
            "VALUES (?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.setString(2, sensorData.getSensorId());
            ps.setString(3, sensorData.getSensorType());
            ps.setDouble(4, sensorData.getValue());
            ps.setString(5, sensorData.getUnit());
            ps.setString(6, sensorData.getLocation());
            ps.setString(7, sensorData.getTimestamp().toString());
            ps.setString(8, sensorData.getBloodBagId());
            ps.setString(9, sensorData.getStatus());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            return false;
        }
    }
}