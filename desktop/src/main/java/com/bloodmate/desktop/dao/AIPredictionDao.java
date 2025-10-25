package com.bloodmate.desktop.dao;

import com.bloodmate.desktop.model.AIPrediction;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AIPredictionDao {
    private final Connection connection;

    public AIPredictionDao(Connection connection) {
        this.connection = connection;
        ensureTable();
    }

    private void ensureTable() {
        String sql = "CREATE TABLE IF NOT EXISTS ai_predictions (" +
            "id VARCHAR(64) PRIMARY KEY," +
            "prediction_type VARCHAR(50) NOT NULL," +
            "prediction_date VARCHAR(20)," +
            "blood_group VARCHAR(5)," +
            "predicted_units INTEGER," +
            "confidence_score DOUBLE," +
            "insights TEXT," +
            "recommendations TEXT," +
            "data_source VARCHAR(100)" +
            ")";
        try (Statement st = connection.createStatement()) {
            st.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create ai_predictions table", e);
        }
    }

    public List<AIPrediction> findAll() {
        List<AIPrediction> list = new ArrayList<>();
        String sql = "SELECT * FROM ai_predictions ORDER BY prediction_date DESC";
        try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                AIPrediction ap = new AIPrediction();
                ap.setId(rs.getString("id"));
                ap.setPredictionType(rs.getString("prediction_type"));
                ap.setPredictionDate(java.time.LocalDate.parse(rs.getString("prediction_date")));
                ap.setBloodGroup(rs.getString("blood_group"));
                ap.setPredictedUnits(rs.getInt("predicted_units"));
                ap.setConfidenceScore(rs.getDouble("confidence_score"));
                ap.setInsights(rs.getString("insights"));
                ap.setRecommendations(rs.getString("recommendations"));
                ap.setDataSource(rs.getString("data_source"));
                list.add(ap);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public boolean insert(AIPrediction prediction) {
        String id = prediction.getId() == null || prediction.getId().isEmpty() ? UUID.randomUUID().toString() : prediction.getId();
        prediction.setId(id);
        String sql = "INSERT INTO ai_predictions (id, prediction_type, prediction_date, blood_group, " +
            "predicted_units, confidence_score, insights, recommendations, data_source) " +
            "VALUES (?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.setString(2, prediction.getPredictionType());
            ps.setString(3, prediction.getPredictionDate().toString());
            ps.setString(4, prediction.getBloodGroup());
            ps.setInt(5, prediction.getPredictedUnits());
            ps.setDouble(6, prediction.getConfidenceScore());
            ps.setString(7, prediction.getInsights());
            ps.setString(8, prediction.getRecommendations());
            ps.setString(9, prediction.getDataSource());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            return false;
        }
    }
}