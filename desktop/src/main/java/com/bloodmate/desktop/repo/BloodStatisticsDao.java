package com.bloodmate.desktop.repo;

import com.bloodmate.desktop.model.BloodStatistics;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BloodStatisticsDao {
    private final Connection connection;

    public BloodStatisticsDao(Connection connection) {
        if (connection == null) {
            throw new IllegalArgumentException("Database connection cannot be null");
        }
        this.connection = connection;
        ensureTable();
    }

    /** Ensures the blood_statistics table exists **/
    private void ensureTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS blood_statistics (
                id VARCHAR(64) PRIMARY KEY,
                report_date DATE NOT NULL,
                total_donors INT DEFAULT 0,
                total_recipients INT DEFAULT 0,
                total_blood_units INT DEFAULT 0,
                blood_group_distribution TEXT,
                expiring_soon INT DEFAULT 0,
                active_campaigns INT DEFAULT 0,
                emergency_requests INT DEFAULT 0,
                a_positive_units INT DEFAULT 0,
                a_negative_units INT DEFAULT 0,
                b_positive_units INT DEFAULT 0,
                b_negative_units INT DEFAULT 0,
                ab_positive_units INT DEFAULT 0,
                ab_negative_units INT DEFAULT 0,
                o_positive_units INT DEFAULT 0,
                o_negative_units INT DEFAULT 0,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            );
            """;
        try (Statement st = connection.createStatement()) {
            st.execute(sql);
            // Create index separately (for cross-DB compatibility)
            st.execute("CREATE INDEX IF NOT EXISTS idx_report_date ON blood_statistics (report_date)");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to ensure blood_statistics table", e);
        }
    }

    /** Returns all statistics, newest first **/
    public List<BloodStatistics> findAll() {
        List<BloodStatistics> list = new ArrayList<>();
        String sql = "SELECT * FROM blood_statistics ORDER BY report_date DESC";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapResultSetToBloodStatistics(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch all blood statistics", e);
        }
        return list;
    }

    /** Returns statistics from the last N days **/
    public List<BloodStatistics> findRecent(int days) {
        if (days < 0) throw new IllegalArgumentException("Days cannot be negative");
        List<BloodStatistics> list = new ArrayList<>();

        // MySQL: DATE_SUB(CURDATE(), INTERVAL ? DAY)
        // SQLite: date('now', '-' || ? || ' days')
        String sql = "SELECT * FROM blood_statistics WHERE report_date >= DATE('now', '-' || ? || ' days') ORDER BY report_date DESC";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, days);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToBloodStatistics(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch recent blood statistics", e);
        }
        return list;
    }

    /** Returns the latest report **/
    public BloodStatistics findLatest() {
        String sql = "SELECT * FROM blood_statistics ORDER BY report_date DESC LIMIT 1";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                return mapResultSetToBloodStatistics(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch latest blood statistics", e);
        }
    }

    /** Inserts a new record **/
    public boolean insert(BloodStatistics stats) {
        validate(stats);
        String id = (stats.getId() == null || stats.getId().isBlank())
                ? UUID.randomUUID().toString() : stats.getId();
        stats.setId(id);

        String sql = """
            INSERT INTO blood_statistics (
                id, report_date, total_donors, total_recipients, total_blood_units,
                blood_group_distribution, expiring_soon, active_campaigns, emergency_requests,
                a_positive_units, a_negative_units, b_positive_units, b_negative_units,
                ab_positive_units, ab_negative_units, o_positive_units, o_negative_units
            ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)
            """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.setDate(2, Date.valueOf(stats.getReportDate()));
            ps.setInt(3, stats.getTotalDonors());
            ps.setInt(4, stats.getTotalRecipients());
            ps.setInt(5, stats.getTotalBloodUnits());
            ps.setString(6, stats.getBloodGroupDistribution() == null ? "{}" : stats.getBloodGroupDistribution());
            ps.setInt(7, stats.getExpiringSoon());
            ps.setInt(8, stats.getActiveCampaigns());
            ps.setInt(9, stats.getEmergencyRequests());
            ps.setInt(10, stats.getAPositiveUnits());
            ps.setInt(11, stats.getANegativeUnits());
            ps.setInt(12, stats.getBPositiveUnits());
            ps.setInt(13, stats.getBNegativeUnits());
            ps.setInt(14, stats.getABPositiveUnits());
            ps.setInt(15, stats.getABNegativeUnits());
            ps.setInt(16, stats.getOPositiveUnits());
            ps.setInt(17, stats.getONegativeUnits());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert blood statistics", e);
        }
    }

    /** Updates an existing record **/
    public boolean update(BloodStatistics stats) {
        validate(stats);
        if (stats.getId() == null || stats.getId().isBlank()) {
            throw new IllegalArgumentException("Cannot update record without ID");
        }

        String sql = """
            UPDATE blood_statistics SET
                report_date=?, total_donors=?, total_recipients=?, total_blood_units=?,
                blood_group_distribution=?, expiring_soon=?, active_campaigns=?, emergency_requests=?,
                a_positive_units=?, a_negative_units=?, b_positive_units=?, b_negative_units=?,
                ab_positive_units=?, ab_negative_units=?, o_positive_units=?, o_negative_units=?,
                updated_at=CURRENT_TIMESTAMP
            WHERE id=?
            """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(stats.getReportDate()));
            ps.setInt(2, stats.getTotalDonors());
            ps.setInt(3, stats.getTotalRecipients());
            ps.setInt(4, stats.getTotalBloodUnits());
            ps.setString(5, stats.getBloodGroupDistribution());
            ps.setInt(6, stats.getExpiringSoon());
            ps.setInt(7, stats.getActiveCampaigns());
            ps.setInt(8, stats.getEmergencyRequests());
            ps.setInt(9, stats.getAPositiveUnits());
            ps.setInt(10, stats.getANegativeUnits());
            ps.setInt(11, stats.getBPositiveUnits());
            ps.setInt(12, stats.getBNegativeUnits());
            ps.setInt(13, stats.getABPositiveUnits());
            ps.setInt(14, stats.getABNegativeUnits());
            ps.setInt(15, stats.getOPositiveUnits());
            ps.setInt(16, stats.getONegativeUnits());
            ps.setString(17, stats.getId());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update blood statistics", e);
        }
    }

    /** Deletes a record **/
    public boolean delete(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }
        String sql = "DELETE FROM blood_statistics WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete blood statistics", e);
        }
    }

    /** Maps a DB row to a model **/
    private BloodStatistics mapResultSetToBloodStatistics(ResultSet rs) throws SQLException {
        BloodStatistics stats = new BloodStatistics();
        stats.setId(rs.getString("id"));
        stats.setReportDate(rs.getDate("report_date").toLocalDate());
        stats.setTotalDonors(rs.getInt("total_donors"));
        stats.setTotalRecipients(rs.getInt("total_recipients"));
        stats.setTotalBloodUnits(rs.getInt("total_blood_units"));
        stats.setBloodGroupDistribution(rs.getString("blood_group_distribution"));
        stats.setExpiringSoon(rs.getInt("expiring_soon"));
        stats.setActiveCampaigns(rs.getInt("active_campaigns"));
        stats.setEmergencyRequests(rs.getInt("emergency_requests"));
        stats.setAPositiveUnits(rs.getInt("a_positive_units"));
        stats.setANegativeUnits(rs.getInt("a_negative_units"));
        stats.setBPositiveUnits(rs.getInt("b_positive_units"));
        stats.setBNegativeUnits(rs.getInt("b_negative_units"));
        stats.setABPositiveUnits(rs.getInt("ab_positive_units"));
        stats.setABNegativeUnits(rs.getInt("ab_negative_units"));
        stats.setOPositiveUnits(rs.getInt("o_positive_units"));
        stats.setONegativeUnits(rs.getInt("o_negative_units"));
        return stats;
    }

    /** Basic data validation **/
    private void validate(BloodStatistics stats) {
        if (stats == null) throw new IllegalArgumentException("BloodStatistics cannot be null");
        if (stats.getReportDate() == null) throw new IllegalArgumentException("Report date cannot be null");
        if (stats.getTotalDonors() < 0 || stats.getTotalRecipients() < 0 || stats.getTotalBloodUnits() < 0) {
            throw new IllegalArgumentException("Values cannot be negative");
        }
    }
}
