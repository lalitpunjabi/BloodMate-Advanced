package com.bloodmate.desktop.repo;

import com.bloodmate.desktop.model.DonorPoints;
import com.bloodmate.desktop.db.Db;

import java.sql.*;
import java.time.LocalDateTime;
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
            "donor_id VARCHAR(64) NOT NULL," +
            "donor_name VARCHAR(100) NOT NULL," +
            "points INT DEFAULT 0," +
            "badge VARCHAR(50)," +
            "last_activity VARCHAR(200)," +
            "last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP," +
            "INDEX idx_donor_id (donor_id)," +
            "INDEX idx_points (points DESC)" +
            ")";
        try (Statement st = connection.createStatement()) {
            st.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create donor_points table", e);
        }
    }

    public List<DonorPoints> findAll() {
        List<DonorPoints> list = new ArrayList<>();
        String sql = "SELECT * FROM donor_points ORDER BY points DESC";
        try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                DonorPoints dp = mapResultSetToDonorPoints(rs);
                list.add(dp);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<DonorPoints> findTopDonors(int limit) {
        List<DonorPoints> list = new ArrayList<>();
        String sql = "SELECT * FROM donor_points ORDER BY points DESC LIMIT ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DonorPoints dp = mapResultSetToDonorPoints(rs);
                list.add(dp);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public DonorPoints findByDonorId(String donorId) {
        String sql = "SELECT * FROM donor_points WHERE donor_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, donorId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultSetToDonorPoints(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public boolean insert(DonorPoints donorPoints) {
        String id = donorPoints.getId() == null || donorPoints.getId().isEmpty() ? 
                   UUID.randomUUID().toString() : donorPoints.getId();
        donorPoints.setId(id);
        
        String sql = "INSERT INTO donor_points (id, donor_id, donor_name, points, badge, last_activity) " +
                    "VALUES (?,?,?,?,?,?)";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.setString(2, donorPoints.getDonorId());
            ps.setString(3, donorPoints.getDonorName());
            ps.setInt(4, donorPoints.getPoints());
            ps.setString(5, donorPoints.getBadge());
            ps.setString(6, donorPoints.getLastActivity());
            
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(DonorPoints donorPoints) {
        String sql = "UPDATE donor_points SET donor_name=?, points=?, badge=?, last_activity=?, last_updated=CURRENT_TIMESTAMP WHERE id=?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, donorPoints.getDonorName());
            ps.setInt(2, donorPoints.getPoints());
            ps.setString(3, donorPoints.getBadge());
            ps.setString(4, donorPoints.getLastActivity());
            ps.setString(5, donorPoints.getId());
            
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean updatePoints(String donorId, int points, String activity) {
        String sql = "UPDATE donor_points SET points = points + ?, last_activity = ?, last_updated = CURRENT_TIMESTAMP WHERE donor_id = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, points);
            ps.setString(2, activity);
            ps.setString(3, donorId);
            
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean delete(String id) {
        String sql = "DELETE FROM donor_points WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            return false;
        }
    }

    private DonorPoints mapResultSetToDonorPoints(ResultSet rs) throws SQLException {
        DonorPoints dp = new DonorPoints();
        dp.setId(rs.getString("id"));
        dp.setDonorId(rs.getString("donor_id"));
        dp.setDonorName(rs.getString("donor_name"));
        dp.setPoints(rs.getInt("points"));
        dp.setBadge(rs.getString("badge"));
        dp.setLastActivity(rs.getString("last_activity"));
        
        Timestamp timestamp = rs.getTimestamp("last_updated");
        if (timestamp != null) {
            dp.setLastUpdated(timestamp.toLocalDateTime());
        }
        
        return dp;
    }
}