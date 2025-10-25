package com.bloodmate.desktop.dao;

import com.bloodmate.desktop.model.BloodStatistics;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BloodStatisticsDao {
    private final Connection connection;

    public BloodStatisticsDao(Connection connection) {
        this.connection = connection;
        ensureTable();
    }

    private void ensureTable() {
        String sql = "CREATE TABLE IF NOT EXISTS blood_statistics (" +
            "id VARCHAR(64) PRIMARY KEY," +
            "report_date VARCHAR(20)," +
            "total_donors INTEGER," +
            "total_recipients INTEGER," +
            "total_blood_units INTEGER," +
            "active_campaigns INTEGER," +
            "a_positive_units INTEGER," +
            "a_negative_units INTEGER," +
            "b_positive_units INTEGER," +
            "b_negative_units INTEGER," +
            "ab_positive_units INTEGER," +
            "ab_negative_units INTEGER," +
            "o_positive_units INTEGER," +
            "o_negative_units INTEGER," +
            "expiring_soon INTEGER," +
            "emergency_requests INTEGER" +
            ")";
        try (Statement st = connection.createStatement()) {
            st.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create blood_statistics table", e);
        }
    }

    public List<BloodStatistics> findAll() {
        List<BloodStatistics> list = new ArrayList<>();
        String sql = "SELECT * FROM blood_statistics ORDER BY report_date DESC";
        try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                BloodStatistics bs = new BloodStatistics();
                bs.setId(rs.getString("id"));
                bs.setReportDate(java.time.LocalDate.parse(rs.getString("report_date")));
                bs.setTotalDonors(rs.getInt("total_donors"));
                bs.setTotalRecipients(rs.getInt("total_recipients"));
                bs.setTotalBloodUnits(rs.getInt("total_blood_units"));
                bs.setActiveCampaigns(rs.getInt("active_campaigns"));
                bs.setAPositiveUnits(rs.getInt("a_positive_units"));
                bs.setANegativeUnits(rs.getInt("a_negative_units"));
                bs.setBPositiveUnits(rs.getInt("b_positive_units"));
                bs.setBNegativeUnits(rs.getInt("b_negative_units"));
                bs.setABPositiveUnits(rs.getInt("ab_positive_units"));
                bs.setABNegativeUnits(rs.getInt("ab_negative_units"));
                bs.setOPositiveUnits(rs.getInt("o_positive_units"));
                bs.setONegativeUnits(rs.getInt("o_negative_units"));
                bs.setExpiringSoon(rs.getInt("expiring_soon"));
                bs.setEmergencyRequests(rs.getInt("emergency_requests"));
                list.add(bs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public boolean insert(BloodStatistics statistics) {
        String id = statistics.getId() == null || statistics.getId().isEmpty() ? UUID.randomUUID().toString() : statistics.getId();
        statistics.setId(id);
        String sql = "INSERT INTO blood_statistics (id, report_date, total_donors, total_recipients, total_blood_units, " +
            "active_campaigns, a_positive_units, a_negative_units, b_positive_units, b_negative_units, " +
            "ab_positive_units, ab_negative_units, o_positive_units, o_negative_units, expiring_soon, emergency_requests) " +
            "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.setString(2, statistics.getReportDate().toString());
            ps.setInt(3, statistics.getTotalDonors());
            ps.setInt(4, statistics.getTotalRecipients());
            ps.setInt(5, statistics.getTotalBloodUnits());
            ps.setInt(6, statistics.getActiveCampaigns());
            ps.setInt(7, statistics.getAPositiveUnits());
            ps.setInt(8, statistics.getANegativeUnits());
            ps.setInt(9, statistics.getBPositiveUnits());
            ps.setInt(10, statistics.getBNegativeUnits());
            ps.setInt(11, statistics.getABPositiveUnits());
            ps.setInt(12, statistics.getABNegativeUnits());
            ps.setInt(13, statistics.getOPositiveUnits());
            ps.setInt(14, statistics.getONegativeUnits());
            ps.setInt(15, statistics.getExpiringSoon());
            ps.setInt(16, statistics.getEmergencyRequests());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            return false;
        }
    }
}