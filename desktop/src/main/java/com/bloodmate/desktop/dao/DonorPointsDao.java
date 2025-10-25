package com.bloodmate.desktop.dao;

import com.bloodmate.desktop.model.DonorPoints;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DonorPointsDao {
    private final Connection connection;

    public DonorPointsDao(Connection connection) {
        this.connection = connection;
        ensureTable();
    }

    private void ensureTable() {
        String sql = "CREATE TABLE IF NOT EXISTS donor_points (" +
            "id VARCHAR(64) PRIMARY KEY," +
            "donor_name VARCHAR(100) NOT NULL," +
            "points VARCHAR(10) NOT NULL," +
            "badge VARCHAR(50)," +
            "last_activity VARCHAR(50)" +
            ")";
        try (Statement st = connection.createStatement()) {
            st.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create donor_points table", e);
        }
    }

    public List<DonorPoints> findAll() {
        List<DonorPoints> list = new ArrayList<>();
        String sql = "SELECT id, donor_name, points, badge, last_activity FROM donor_points ORDER BY points DESC";
        try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                DonorPoints dp = new DonorPoints();
                dp.setId(rs.getString("id"));
                dp.setDonorName(rs.getString("donor_name"));
                dp.setPoints(Integer.parseInt(rs.getString("points")));
                dp.setBadge(rs.getString("badge"));
                dp.setLastActivity(rs.getString("last_activity"));
                list.add(dp);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public boolean insert(DonorPoints donorPoints) {
        String id = donorPoints.getId() == null || donorPoints.getId().isEmpty() ? UUID.randomUUID().toString() : donorPoints.getId();
        donorPoints.setId(id);
        String sql = "INSERT INTO donor_points (id, donor_name, points, badge, last_activity) VALUES (?,?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.setString(2, donorPoints.getDonorName());
            ps.setInt(3, donorPoints.getPoints());
            ps.setString(4, donorPoints.getBadge());
            ps.setString(5, donorPoints.getLastActivity());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            return false;
        }
    }
}