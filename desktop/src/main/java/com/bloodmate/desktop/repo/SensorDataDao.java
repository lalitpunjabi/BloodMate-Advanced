package com.bloodmate.desktop.repo;

import com.bloodmate.desktop.model.SensorData;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SensorDataDao {
    private final Connection connection;

    public SensorDataDao(Connection connection) {
        this.connection = connection;
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        String createTableSQL = """
            CREATE TABLE IF NOT EXISTS sensor_data (
                id INTEGER PRIMARY KEY AUTO_INCREMENT,
                sensor_id VARCHAR(50) NOT NULL,
                sensor_type VARCHAR(50) NOT NULL,
                value DOUBLE NOT NULL,
                unit VARCHAR(20) NOT NULL,
                timestamp TIMESTAMP NOT NULL,
                location VARCHAR(100),
                status VARCHAR(20) DEFAULT 'ACTIVE',
                blood_bag_id VARCHAR(50),
                INDEX idx_sensor_id (sensor_id),
                INDEX idx_sensor_type (sensor_type),
                INDEX idx_timestamp (timestamp),
                INDEX idx_blood_bag_id (blood_bag_id)
            )
            """;

        try (PreparedStatement stmt = connection.prepareStatement(createTableSQL)) {
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean insert(SensorData sensorData) {
        String insertSQL = """
            INSERT INTO sensor_data (
                sensor_id, sensor_type, value, unit, timestamp, location, status, blood_bag_id
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            """;

        try (PreparedStatement stmt = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, sensorData.getSensorId());
            stmt.setString(2, sensorData.getSensorType());
            stmt.setDouble(3, sensorData.getValue());
            stmt.setString(4, sensorData.getUnit());
            stmt.setTimestamp(5, Timestamp.valueOf(sensorData.getTimestamp()));
            stmt.setString(6, sensorData.getLocation());
            stmt.setString(7, sensorData.getStatus());
            stmt.setString(8, sensorData.getBloodBagId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        sensorData.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(SensorData sensorData) {
        String updateSQL = """
            UPDATE sensor_data SET
                sensor_id = ?, sensor_type = ?, value = ?, unit = ?,
                timestamp = ?, location = ?, status = ?, blood_bag_id = ?
            WHERE id = ?
            """;

        try (PreparedStatement stmt = connection.prepareStatement(updateSQL)) {
            stmt.setString(1, sensorData.getSensorId());
            stmt.setString(2, sensorData.getSensorType());
            stmt.setDouble(3, sensorData.getValue());
            stmt.setString(4, sensorData.getUnit());
            stmt.setTimestamp(5, Timestamp.valueOf(sensorData.getTimestamp()));
            stmt.setString(6, sensorData.getLocation());
            stmt.setString(7, sensorData.getStatus());
            stmt.setString(8, sensorData.getBloodBagId());
            stmt.setInt(9, sensorData.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(int id) {
        String deleteSQL = "DELETE FROM sensor_data WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(deleteSQL)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Optional<SensorData> findById(int id) {
        String selectSQL = "SELECT * FROM sensor_data WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(selectSQL)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToSensorData(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public List<SensorData> findAll() {
        List<SensorData> sensorDataList = new ArrayList<>();
        String selectSQL = "SELECT * FROM sensor_data ORDER BY timestamp DESC";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL)) {

            while (rs.next()) {
                sensorDataList.add(mapResultSetToSensorData(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sensorDataList;
    }

    public List<SensorData> findBySensorId(String sensorId) {
        List<SensorData> sensorDataList = new ArrayList<>();
        String selectSQL = "SELECT * FROM sensor_data WHERE sensor_id = ? ORDER BY timestamp DESC";

        try (PreparedStatement stmt = connection.prepareStatement(selectSQL)) {
            stmt.setString(1, sensorId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    sensorDataList.add(mapResultSetToSensorData(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sensorDataList;
    }

    public List<SensorData> findBySensorType(String sensorType) {
        List<SensorData> sensorDataList = new ArrayList<>();
        String selectSQL = "SELECT * FROM sensor_data WHERE sensor_type = ? ORDER BY timestamp DESC";

        try (PreparedStatement stmt = connection.prepareStatement(selectSQL)) {
            stmt.setString(1, sensorType);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    sensorDataList.add(mapResultSetToSensorData(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sensorDataList;
    }

    public List<SensorData> findByBloodBagId(String bloodBagId) {
        List<SensorData> sensorDataList = new ArrayList<>();
        String selectSQL = "SELECT * FROM sensor_data WHERE blood_bag_id = ? ORDER BY timestamp DESC";

        try (PreparedStatement stmt = connection.prepareStatement(selectSQL)) {
            stmt.setString(1, bloodBagId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    sensorDataList.add(mapResultSetToSensorData(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sensorDataList;
    }

    public List<SensorData> findRecentData(int limit) {
        List<SensorData> sensorDataList = new ArrayList<>();
        String selectSQL = "SELECT * FROM sensor_data ORDER BY timestamp DESC LIMIT ?";

        try (PreparedStatement stmt = connection.prepareStatement(selectSQL)) {
            stmt.setInt(1, limit);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    sensorDataList.add(mapResultSetToSensorData(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sensorDataList;
    }

    public List<SensorData> findDataInTimeRange(LocalDateTime start, LocalDateTime end) {
        List<SensorData> sensorDataList = new ArrayList<>();
        String selectSQL = "SELECT * FROM sensor_data WHERE timestamp BETWEEN ? AND ? ORDER BY timestamp DESC";

        try (PreparedStatement stmt = connection.prepareStatement(selectSQL)) {
            stmt.setTimestamp(1, Timestamp.valueOf(start));
            stmt.setTimestamp(2, Timestamp.valueOf(end));
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    sensorDataList.add(mapResultSetToSensorData(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sensorDataList;
    }

    public List<SensorData> findAlertData(double minValue, double maxValue) {
        List<SensorData> sensorDataList = new ArrayList<>();
        String selectSQL = "SELECT * FROM sensor_data WHERE value < ? OR value > ? ORDER BY timestamp DESC";

        try (PreparedStatement stmt = connection.prepareStatement(selectSQL)) {
            stmt.setDouble(1, minValue);
            stmt.setDouble(2, maxValue);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    sensorDataList.add(mapResultSetToSensorData(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sensorDataList;
    }

    private SensorData mapResultSetToSensorData(ResultSet rs) throws SQLException {
        SensorData sensorData = new SensorData();
        sensorData.setId(rs.getInt("id"));
        sensorData.setSensorId(rs.getString("sensor_id"));
        sensorData.setSensorType(rs.getString("sensor_type"));
        sensorData.setValue(rs.getDouble("value"));
        sensorData.setUnit(rs.getString("unit"));
        sensorData.setTimestamp(rs.getTimestamp("timestamp").toLocalDateTime());
        sensorData.setLocation(rs.getString("location"));
        sensorData.setStatus(rs.getString("status"));
        sensorData.setBloodBagId(rs.getString("blood_bag_id"));
        return sensorData;
    }
}