package com.bloodmate.desktop.repo;

import com.bloodmate.desktop.model.VoiceCommand;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VoiceCommandDao {
    private final Connection connection;

    public VoiceCommandDao(Connection connection) {
        this.connection = connection;
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        String createTableSQL = """
            CREATE TABLE IF NOT EXISTS voice_commands (
                id VARCHAR(64) PRIMARY KEY,
                command_text TEXT NOT NULL,
                recognized_text TEXT,
                action VARCHAR(100),
                timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                user_id VARCHAR(64),
                status VARCHAR(20) DEFAULT 'PENDING',
                result TEXT,
                INDEX idx_user (user_id),
                INDEX idx_timestamp (timestamp),
                INDEX idx_status (status)
            )
            """;

        try (PreparedStatement stmt = connection.prepareStatement(createTableSQL)) {
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean insert(VoiceCommand command) {
        String insertSQL = """
            INSERT INTO voice_commands (
                id, command_text, recognized_text, action, timestamp, user_id, status, result
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            """;

        try (PreparedStatement stmt = connection.prepareStatement(insertSQL)) {
            stmt.setString(1, command.getId());
            stmt.setString(2, command.getCommandText());
            stmt.setString(3, command.getRecognizedText());
            stmt.setString(4, command.getAction());
            stmt.setTimestamp(5, command.getTimestamp() != null ? Timestamp.valueOf(command.getTimestamp()) : null);
            stmt.setString(6, command.getUserId());
            stmt.setString(7, command.getStatus());
            stmt.setString(8, command.getResult());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(VoiceCommand command) {
        String updateSQL = """
            UPDATE voice_commands SET
                command_text = ?, recognized_text = ?, action = ?, timestamp = ?, 
                user_id = ?, status = ?, result = ?
            WHERE id = ?
            """;

        try (PreparedStatement stmt = connection.prepareStatement(updateSQL)) {
            stmt.setString(1, command.getCommandText());
            stmt.setString(2, command.getRecognizedText());
            stmt.setString(3, command.getAction());
            stmt.setTimestamp(4, command.getTimestamp() != null ? Timestamp.valueOf(command.getTimestamp()) : null);
            stmt.setString(5, command.getUserId());
            stmt.setString(6, command.getStatus());
            stmt.setString(7, command.getResult());
            stmt.setString(8, command.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(String id) {
        String deleteSQL = "DELETE FROM voice_commands WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(deleteSQL)) {
            stmt.setString(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Optional<VoiceCommand> findById(String id) {
        String selectSQL = "SELECT * FROM voice_commands WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(selectSQL)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToVoiceCommand(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public List<VoiceCommand> findAll() {
        List<VoiceCommand> commandList = new ArrayList<>();
        String selectSQL = "SELECT * FROM voice_commands ORDER BY timestamp DESC";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL)) {

            while (rs.next()) {
                commandList.add(mapResultSetToVoiceCommand(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commandList;
    }

    public List<VoiceCommand> findByUserId(String userId) {
        List<VoiceCommand> commandList = new ArrayList<>();
        String selectSQL = "SELECT * FROM voice_commands WHERE user_id = ? ORDER BY timestamp DESC";

        try (PreparedStatement stmt = connection.prepareStatement(selectSQL)) {
            stmt.setString(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    commandList.add(mapResultSetToVoiceCommand(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commandList;
    }

    public List<VoiceCommand> findByStatus(String status) {
        List<VoiceCommand> commandList = new ArrayList<>();
        String selectSQL = "SELECT * FROM voice_commands WHERE status = ? ORDER BY timestamp DESC";

        try (PreparedStatement stmt = connection.prepareStatement(selectSQL)) {
            stmt.setString(1, status);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    commandList.add(mapResultSetToVoiceCommand(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commandList;
    }

    public List<VoiceCommand> findRecentCommands(int limit) {
        List<VoiceCommand> commandList = new ArrayList<>();
        String selectSQL = "SELECT * FROM voice_commands ORDER BY timestamp DESC LIMIT ?";

        try (PreparedStatement stmt = connection.prepareStatement(selectSQL)) {
            stmt.setInt(1, limit);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    commandList.add(mapResultSetToVoiceCommand(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commandList;
    }

    private VoiceCommand mapResultSetToVoiceCommand(ResultSet rs) throws SQLException {
        VoiceCommand command = new VoiceCommand();
        command.setId(rs.getString("id"));
        command.setCommandText(rs.getString("command_text"));
        command.setRecognizedText(rs.getString("recognized_text"));
        command.setAction(rs.getString("action"));
        command.setTimestamp(rs.getTimestamp("timestamp") != null ? 
            rs.getTimestamp("timestamp").toLocalDateTime() : null);
        command.setUserId(rs.getString("user_id"));
        command.setStatus(rs.getString("status"));
        command.setResult(rs.getString("result"));
        return command;
    }
}