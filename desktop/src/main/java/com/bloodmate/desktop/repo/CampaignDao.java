package com.bloodmate.desktop.repo;

import com.bloodmate.desktop.model.Campaign;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CampaignDao {
    private final Connection connection;

    public CampaignDao(Connection connection) {
        this.connection = connection;
        ensureTable();
    }

    private void ensureTable() {
        String sql = "CREATE TABLE IF NOT EXISTS campaigns (" +
            "id VARCHAR(64) PRIMARY KEY," +
            "title VARCHAR(200) NOT NULL," +
            "description TEXT," +
            "start_date DATE NOT NULL," +
            "end_date DATE NOT NULL," +
            "location VARCHAR(200) NOT NULL," +
            "city VARCHAR(100) NOT NULL," +
            "state VARCHAR(100) NOT NULL," +
            "target_units INT DEFAULT 50," +
            "collected_units INT DEFAULT 0," +
            "status ENUM('PLANNED', 'ACTIVE', 'COMPLETED', 'CANCELLED') DEFAULT 'PLANNED'," +
            "organizer VARCHAR(100) NOT NULL," +
            "contact_number VARCHAR(50)," +
            "created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
            "participant_count INT DEFAULT 0," +
            "requirements TEXT," +
            "incentives TEXT," +
            "INDEX idx_status (status)," +
            "INDEX idx_dates (start_date, end_date)," +
            "INDEX idx_city (city)" +
            ")";
        try (Statement st = connection.createStatement()) {
            st.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create campaigns table", e);
        }
    }

    public List<Campaign> findAll() {
        List<Campaign> list = new ArrayList<>();
        String sql = "SELECT * FROM campaigns ORDER BY start_date DESC, created_date DESC";
        try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapResultSetToCampaign(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch campaigns", e);
        }
        return list;
    }

    public List<Campaign> findByStatus(String status) {
        List<Campaign> list = new ArrayList<>();
        String sql = "SELECT * FROM campaigns WHERE status = ? ORDER BY start_date ASC";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, status);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapResultSetToCampaign(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch campaigns by status", e);
        }
        return list;
    }

    public List<Campaign> findUpcoming() {
        List<Campaign> list = new ArrayList<>();
        String sql = "SELECT * FROM campaigns WHERE start_date >= CURDATE() AND status IN ('PLANNED', 'ACTIVE') ORDER BY start_date ASC";
        try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapResultSetToCampaign(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch upcoming campaigns", e);
        }
        return list;
    }

    public List<Campaign> findByCity(String city) {
        List<Campaign> list = new ArrayList<>();
        String sql = "SELECT * FROM campaigns WHERE city = ? ORDER BY start_date DESC";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, city);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapResultSetToCampaign(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch campaigns by city", e);
        }
        return list;
    }

    public boolean insert(Campaign campaign) {
        String id = (campaign.getId() == null || campaign.getId().isEmpty())
                ? UUID.randomUUID().toString()
                : campaign.getId();
        campaign.setId(id);

        String sql = "INSERT INTO campaigns (id, title, description, start_date, end_date, location, " +
                "city, state, target_units, collected_units, status, organizer, contact_number, " +
                "participant_count, requirements, incentives) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.setString(2, campaign.getTitle());
            ps.setString(3, campaign.getDescription());
            ps.setDate(4, Date.valueOf(campaign.getStartDate()));
            ps.setDate(5, Date.valueOf(campaign.getEndDate()));
            ps.setString(6, campaign.getLocation());
            ps.setString(7, campaign.getCity());
            ps.setString(8, campaign.getState());
            ps.setInt(9, campaign.getTargetUnits());
            ps.setInt(10, campaign.getCollectedUnits());
            ps.setString(11, campaign.getStatus());
            ps.setString(12, campaign.getOrganizer());
            ps.setString(13, campaign.getContactNumber());
            ps.setInt(14, campaign.getParticipantCount());
            ps.setString(15, campaign.getRequirements());
            ps.setString(16, campaign.getIncentives());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Campaign campaign) {
        String sql = "UPDATE campaigns SET title=?, description=?, start_date=?, end_date=?, location=?, " +
                "city=?, state=?, target_units=?, collected_units=?, status=?, organizer=?, " +
                "contact_number=?, participant_count=?, requirements=?, incentives=? WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, campaign.getTitle());
            ps.setString(2, campaign.getDescription());
            ps.setDate(3, Date.valueOf(campaign.getStartDate()));
            ps.setDate(4, Date.valueOf(campaign.getEndDate()));
            ps.setString(5, campaign.getLocation());
            ps.setString(6, campaign.getCity());
            ps.setString(7, campaign.getState());
            ps.setInt(8, campaign.getTargetUnits());
            ps.setInt(9, campaign.getCollectedUnits());
            ps.setString(10, campaign.getStatus());
            ps.setString(11, campaign.getOrganizer());
            ps.setString(12, campaign.getContactNumber());
            ps.setInt(13, campaign.getParticipantCount());
            ps.setString(14, campaign.getRequirements());
            ps.setString(15, campaign.getIncentives());
            ps.setString(16, campaign.getId());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(String id) {
        String sql = "DELETE FROM campaigns WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateCollectedUnits(String id, int collectedUnits) {
        String sql = "UPDATE campaigns SET collected_units = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, collectedUnits);
            ps.setString(2, id);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateStatus(String id, String status) {
        String sql = "UPDATE campaigns SET status = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setString(2, id);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Campaign mapResultSetToCampaign(ResultSet rs) throws SQLException {
        Campaign campaign = new Campaign();
        campaign.setId(rs.getString("id"));
        campaign.setTitle(rs.getString("title"));
        campaign.setDescription(rs.getString("description"));
        Date startDate = rs.getDate("start_date");
        Date endDate = rs.getDate("end_date");
        if (startDate != null) campaign.setStartDate(startDate.toLocalDate());
        if (endDate != null) campaign.setEndDate(endDate.toLocalDate());
        campaign.setLocation(rs.getString("location"));
        campaign.setCity(rs.getString("city"));
        campaign.setState(rs.getString("state"));
        campaign.setTargetUnits(rs.getInt("target_units"));
        campaign.setCollectedUnits(rs.getInt("collected_units"));
        campaign.setStatus(rs.getString("status"));
        campaign.setOrganizer(rs.getString("organizer"));
        campaign.setContactNumber(rs.getString("contact_number"));
        Timestamp createdDate = rs.getTimestamp("created_date");
        if (createdDate != null) campaign.setCreatedDate(createdDate.toLocalDateTime());
        campaign.setParticipantCount(rs.getInt("participant_count"));
        campaign.setRequirements(rs.getString("requirements"));
        campaign.setIncentives(rs.getString("incentives"));
        return campaign;
    }
}
