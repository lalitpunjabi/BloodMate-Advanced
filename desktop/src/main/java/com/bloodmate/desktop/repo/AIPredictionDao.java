package com.bloodmate.desktop.repo;

import com.bloodmate.desktop.model.AIPrediction;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AIPredictionDao {
    private final Connection connection;

    public AIPredictionDao(Connection connection) {
        this.connection = connection;
        ensureTable();
    }

    /** Ensures that the AI Predictions table exists in the database */
    private void ensureTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS ai_predictions (
                id VARCHAR(64) PRIMARY KEY,
                prediction_type VARCHAR(50) NOT NULL,
                prediction_date DATE NOT NULL,
                generated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                blood_group VARCHAR(5),
                predicted_units INT DEFAULT 0,
                confidence_score DECIMAL(5,4) DEFAULT 0.0000,
                insights TEXT,
                recommendations TEXT,
                data_source VARCHAR(100),
                INDEX idx_prediction_type (prediction_type),
                INDEX idx_prediction_date (prediction_date DESC),
                INDEX idx_blood_group (blood_group)
            )
        """;
        try (Statement st = connection.createStatement()) {
            st.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("❌ Failed to create ai_predictions table", e);
        }
    }

    /** Returns all AI predictions ordered by date (latest first). */
    public List<AIPrediction> findAll() {
        String sql = "SELECT * FROM ai_predictions ORDER BY prediction_date DESC, generated_at DESC";
        List<AIPrediction> list = new ArrayList<>();
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapResultSetToAIPrediction(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("❌ Failed to fetch predictions", e);
        }
        return list;
    }

    /** Returns predictions by type (e.g., demand, donation, etc.) */
    public List<AIPrediction> findByType(String predictionType) {
        String sql = "SELECT * FROM ai_predictions WHERE prediction_type = ? ORDER BY prediction_date DESC";
        List<AIPrediction> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, predictionType);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapResultSetToAIPrediction(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("❌ Failed to fetch predictions by type", e);
        }
        return list;
    }

    /** Returns predictions filtered by blood group */
    public List<AIPrediction> findByBloodGroup(String bloodGroup) {
        String sql = "SELECT * FROM ai_predictions WHERE blood_group = ? ORDER BY prediction_date DESC";
        List<AIPrediction> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, bloodGroup);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapResultSetToAIPrediction(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("❌ Failed to fetch predictions by blood group", e);
        }
        return list;
    }

    /** Returns predictions from the past `days` days */
    public List<AIPrediction> findRecent(int days) {
        String sql = """
            SELECT * FROM ai_predictions
            WHERE prediction_date >= DATE_SUB(CURDATE(), INTERVAL ? DAY)
            ORDER BY prediction_date DESC
        """;
        List<AIPrediction> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, days);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapResultSetToAIPrediction(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("❌ Failed to fetch recent predictions", e);
        }
        return list;
    }

    /** Inserts a new prediction record into the database */
    public boolean insert(AIPrediction prediction) {
        String id = (prediction.getId() == null || prediction.getId().isEmpty())
                ? UUID.randomUUID().toString()
                : prediction.getId();
        prediction.setId(id);

        String sql = """
            INSERT INTO ai_predictions (
                id, prediction_type, prediction_date, generated_at, blood_group,
                predicted_units, confidence_score, insights, recommendations, data_source
            ) VALUES (?,?,?,?,?,?,?,?,?,?)
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.setString(2, prediction.getPredictionType());
            ps.setDate(3, Date.valueOf(
                    prediction.getPredictionDate() != null ? prediction.getPredictionDate() : LocalDate.now()));
            ps.setTimestamp(4, Timestamp.valueOf(
                    prediction.getGeneratedAt() != null ? prediction.getGeneratedAt() : LocalDateTime.now()));
            ps.setString(5, prediction.getBloodGroup());
            ps.setInt(6, prediction.getPredictedUnits());
            ps.setDouble(7, prediction.getConfidenceScore());
            ps.setString(8, prediction.getInsights());
            ps.setString(9, prediction.getRecommendations());
            ps.setString(10, prediction.getDataSource());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            System.err.println("❌ Insert failed: " + e.getMessage());
            return false;
        }
    }

    /** Updates an existing prediction record */
    public boolean update(AIPrediction prediction) {
        String sql = """
            UPDATE ai_predictions SET
                prediction_type=?, prediction_date=?, generated_at=?, blood_group=?,
                predicted_units=?, confidence_score=?, insights=?, recommendations=?, data_source=?
            WHERE id=?
        """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, prediction.getPredictionType());
            ps.setDate(2, prediction.getPredictionDate() != null ? Date.valueOf(prediction.getPredictionDate()) : null);
            ps.setTimestamp(3, prediction.getGeneratedAt() != null ? Timestamp.valueOf(prediction.getGeneratedAt()) : null);
            ps.setString(4, prediction.getBloodGroup());
            ps.setInt(5, prediction.getPredictedUnits());
            ps.setDouble(6, prediction.getConfidenceScore());
            ps.setString(7, prediction.getInsights());
            ps.setString(8, prediction.getRecommendations());
            ps.setString(9, prediction.getDataSource());
            ps.setString(10, prediction.getId());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            System.err.println("❌ Update failed: " + e.getMessage());
            return false;
        }
    }

    /** Deletes a prediction record by ID */
    public boolean delete(String id) {
        String sql = "DELETE FROM ai_predictions WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            System.err.println("❌ Delete failed: " + e.getMessage());
            return false;
        }
    }

    /** Helper: maps a ResultSet row to an AIPrediction model object */
    private AIPrediction mapResultSetToAIPrediction(ResultSet rs) throws SQLException {
        AIPrediction prediction = new AIPrediction();
        prediction.setId(rs.getString("id"));
        prediction.setPredictionType(rs.getString("prediction_type"));
        prediction.setPredictionDate(rs.getDate("prediction_date").toLocalDate());

        Timestamp generatedAt = rs.getTimestamp("generated_at");
        if (generatedAt != null)
            prediction.setGeneratedAt(generatedAt.toLocalDateTime());

        prediction.setBloodGroup(rs.getString("blood_group"));
        prediction.setPredictedUnits(rs.getInt("predicted_units"));
        prediction.setConfidenceScore(rs.getDouble("confidence_score"));
        prediction.setInsights(rs.getString("insights"));
        prediction.setRecommendations(rs.getString("recommendations"));
        prediction.setDataSource(rs.getString("data_source"));
        return prediction;
    }
}
