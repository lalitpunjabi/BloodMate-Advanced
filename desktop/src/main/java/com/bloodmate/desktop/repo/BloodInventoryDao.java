package com.bloodmate.desktop.repo;

import com.bloodmate.desktop.model.BloodInventory;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BloodInventoryDao {
    private final Connection connection;

    public BloodInventoryDao(Connection connection) {
        this.connection = connection;
        ensureTable();
    }

    private void ensureTable() {
        String sql = "CREATE TABLE IF NOT EXISTS blood_inventory (" +
            "id VARCHAR(64) PRIMARY KEY," +
            "blood_group VARCHAR(5) NOT NULL," +
            "bag_id VARCHAR(50) UNIQUE NOT NULL," +
            "donation_date DATE NOT NULL," +
            "expiry_date DATE NOT NULL," +
            "status ENUM('AVAILABLE', 'RESERVED', 'USED', 'EXPIRED', 'DISCARDED') DEFAULT 'AVAILABLE'," +
            "volume DECIMAL(5,2) DEFAULT 450.0," +
            "location VARCHAR(100) NOT NULL," +
            "temperature DECIMAL(4,1) DEFAULT 4.0," +
            "quality_score INT DEFAULT 100," +
            "donor_id VARCHAR(64)," +
            "last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP," +
            "notes TEXT," +
            "is_reserved BOOLEAN DEFAULT FALSE," +
            "reserved_for VARCHAR(64)," +
            "test_results TEXT," +
            "INDEX idx_blood_group (blood_group)," +
            "INDEX idx_status (status)," +
            "INDEX idx_expiry_date (expiry_date)," +
            "INDEX idx_location (location)" +
            ")";
        try (Statement st = connection.createStatement()) {
            st.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create blood_inventory table", e);
        }
    }

    public List<BloodInventory> findAll() {
        List<BloodInventory> list = new ArrayList<>();
        String sql = "SELECT * FROM blood_inventory ORDER BY expiry_date ASC, blood_group";
        try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapResultSetToBloodInventory(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<BloodInventory> findByBloodGroup(String bloodGroup) {
        List<BloodInventory> list = new ArrayList<>();
        String sql = "SELECT * FROM blood_inventory WHERE blood_group = ? ORDER BY expiry_date ASC";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, bloodGroup);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapResultSetToBloodInventory(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<BloodInventory> findExpiringSoon(int days) {
        List<BloodInventory> list = new ArrayList<>();
        String sql = "SELECT * FROM blood_inventory WHERE expiry_date <= DATE_ADD(CURDATE(), INTERVAL ? DAY) ORDER BY expiry_date ASC";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, days);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapResultSetToBloodInventory(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<BloodInventory> findByLocation(String location) {
        List<BloodInventory> list = new ArrayList<>();
        String sql = "SELECT * FROM blood_inventory WHERE location = ? ORDER BY blood_group, expiry_date ASC";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, location);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapResultSetToBloodInventory(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public int getAvailableUnitsCount(String bloodGroup) {
        String sql = "SELECT COUNT(*) FROM blood_inventory WHERE blood_group = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, bloodGroup);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public boolean insert(BloodInventory inventory) {
        String id = (inventory.getId() == null || inventory.getId().isEmpty()) ? UUID.randomUUID().toString() : inventory.getId();
        inventory.setId(id);

        if (inventory.getBagId() == null || inventory.getBagId().isEmpty()) {
            inventory.setBagId("BAG-" + System.currentTimeMillis());
        }

        String sql = "INSERT INTO blood_inventory (id, blood_group, bag_id, donation_date, expiry_date, status, volume, location, temperature, quality_score, donor_id, notes, is_reserved, reserved_for, test_results) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.setString(2, inventory.getBloodGroup());
            ps.setString(3, inventory.getBagId());
            ps.setDate(4, Date.valueOf(inventory.getDonationDate() != null ? inventory.getDonationDate() : LocalDate.now()));
            ps.setDate(5, Date.valueOf(inventory.getExpiryDate() != null ? inventory.getExpiryDate() : LocalDate.now().plusDays(42)));
            ps.setString(6, inventory.getStatus() != null ? inventory.getStatus() : "AVAILABLE");
            ps.setDouble(7, inventory.getVolume());
            ps.setString(8, inventory.getLocation() != null ? inventory.getLocation() : "Storage-A");
            ps.setDouble(9, inventory.getTemperature());
            ps.setInt(10, inventory.getQualityScore());
            ps.setString(11, inventory.getDonorId());
            ps.setString(12, inventory.getNotes());
            ps.setBoolean(13, inventory.getIsReserved());
            ps.setString(14, inventory.getReservedFor());
            ps.setString(15, inventory.getTestResults());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(BloodInventory inventory) {
        String sql = "UPDATE blood_inventory SET blood_group=?, bag_id=?, donation_date=?, expiry_date=?, status=?, volume=?, location=?, temperature=?, quality_score=?, donor_id=?, notes=?, is_reserved=?, reserved_for=?, test_results=? WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, inventory.getBloodGroup());
            ps.setString(2, inventory.getBagId());
            ps.setDate(3, inventory.getDonationDate() != null ? Date.valueOf(inventory.getDonationDate()) : null);
            ps.setDate(4, inventory.getExpiryDate() != null ? Date.valueOf(inventory.getExpiryDate()) : null);
            ps.setString(5, inventory.getStatus());
            ps.setDouble(6, inventory.getVolume());
            ps.setString(7, inventory.getLocation());
            ps.setDouble(8, inventory.getTemperature());
            ps.setInt(9, inventory.getQualityScore());
            ps.setString(10, inventory.getDonorId());
            ps.setString(11, inventory.getNotes());
            ps.setBoolean(12, inventory.getIsReserved());
            ps.setString(13, inventory.getReservedFor());
            ps.setString(14, inventory.getTestResults());
            ps.setString(15, inventory.getId());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean delete(String id) {
        String sql = "DELETE FROM blood_inventory WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean reserveBloodUnit(String id, String reservedFor) {
        String sql = "UPDATE blood_inventory SET is_reserved = TRUE, reserved_for = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, reservedFor);
            ps.setString(2, id);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            return false;
        }
    }

    private BloodInventory mapResultSetToBloodInventory(ResultSet rs) throws SQLException {
        BloodInventory inventory = new BloodInventory();
        inventory.setId(rs.getString("id"));
        inventory.setBloodGroup(rs.getString("blood_group"));
        // Some deployments may have older schemas; guard optional columns.
        inventory.setBagId(getStringSafely(rs, "bag_id", rs.getString("id")));

        // Prefer donation_date; fall back to collection_date or null.
        Date donationDate = getDateSafely(rs, "donation_date");
        if (donationDate == null) donationDate = getDateSafely(rs, "collection_date");
        if (donationDate != null) inventory.setDonationDate(donationDate.toLocalDate());

        Date expiryDate = getDateSafely(rs, "expiry_date");
        if (expiryDate != null) inventory.setExpiryDate(expiryDate.toLocalDate());

        inventory.setStatus(getStringSafely(rs, "status", "AVAILABLE"));
        inventory.setVolume(getDoubleSafely(rs, "volume", 450.0));
        inventory.setLocation(getStringSafely(rs, "location", getStringSafely(rs, "storage_location", "Storage-A")));
        inventory.setTemperature(getDoubleSafely(rs, "temperature", 4.0));
        inventory.setQualityScore(getIntSafely(rs, "quality_score", 100));
        inventory.setDonorId(getStringSafely(rs, "donor_id", null));
        inventory.setNotes(getStringSafely(rs, "notes", null));
        inventory.setIsReserved(getBooleanSafely(rs, "is_reserved", false));
        inventory.setReservedFor(getStringSafely(rs, "reserved_for", null));
        inventory.setTestResults(getStringSafely(rs, "test_results", null));
        Timestamp lastUpdated = getTimestampSafely(rs, "last_updated");
        if (lastUpdated != null) inventory.setLastUpdated(lastUpdated.toLocalDateTime());
        return inventory;
    }

    private boolean hasColumn(ResultSet rs, String column) {
        try {
            rs.findColumn(column);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    private String getStringSafely(ResultSet rs, String column, String defaultValue) throws SQLException {
        return hasColumn(rs, column) ? rs.getString(column) : defaultValue;
    }

    private Date getDateSafely(ResultSet rs, String column) throws SQLException {
        return hasColumn(rs, column) ? rs.getDate(column) : null;
    }

    private Timestamp getTimestampSafely(ResultSet rs, String column) throws SQLException {
        return hasColumn(rs, column) ? rs.getTimestamp(column) : null;
    }

    private double getDoubleSafely(ResultSet rs, String column, double defaultValue) throws SQLException {
        return hasColumn(rs, column) ? rs.getDouble(column) : defaultValue;
    }

    private int getIntSafely(ResultSet rs, String column, int defaultValue) throws SQLException {
        return hasColumn(rs, column) ? rs.getInt(column) : defaultValue;
    }

    private boolean getBooleanSafely(ResultSet rs, String column, boolean defaultValue) throws SQLException {
        return hasColumn(rs, column) ? rs.getBoolean(column) : defaultValue;
    }
}
