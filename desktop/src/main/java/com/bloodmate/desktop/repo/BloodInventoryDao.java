package com.bloodmate.desktop.repo;

import com.bloodmate.desktop.model.BloodInventory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BloodInventoryDao {
	private final Connection connection;

	public BloodInventoryDao(Connection connection) {
		this.connection = connection;
	}

	public List<BloodInventory> findAll() {
		List<BloodInventory> list = new ArrayList<>();
		String sql = "SELECT id, blood_group, status, storage_location FROM blood_inventory ORDER BY blood_group";
		try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(sql)) {
			while (rs.next()) {
				BloodInventory bi = new BloodInventory();
				bi.setId(rs.getString("id"));
				bi.setBloodGroup(rs.getString("blood_group"));
				bi.setStatus(rs.getString("status"));
				bi.setLocation(rs.getString("storage_location"));
				list.add(bi);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return list;
	}

	public boolean insert(BloodInventory inventory) {
		String id = inventory.getId() == null || inventory.getId().isEmpty() ? UUID.randomUUID().toString() : inventory.getId();
		inventory.setId(id);
		String sql = "INSERT INTO blood_inventory (id, blood_group, status, storage_location) VALUES (?,?,?,?)";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, id);
			ps.setString(2, inventory.getBloodGroup());
			ps.setString(3, inventory.getStatus());
			ps.setString(4, inventory.getLocation());
			return ps.executeUpdate() == 1;
		} catch (SQLException e) {
			return false;
		}
	}
	
	public List<BloodInventory> findExpiringSoon(int days) {
		List<BloodInventory> list = new ArrayList<>();
		// This is a mock implementation since we don't have actual expiry dates in the database
		// In a real implementation, this would query for blood units expiring within the specified days
		return list;
	}
	
	public List<BloodInventory> findByBloodGroup(String bloodGroup) {
		List<BloodInventory> list = new ArrayList<>();
		String sql = "SELECT id, blood_group, status, storage_location FROM blood_inventory WHERE blood_group = ? ORDER BY blood_group";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, bloodGroup);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				BloodInventory bi = new BloodInventory();
				bi.setId(rs.getString("id"));
				bi.setBloodGroup(rs.getString("blood_group"));
				bi.setStatus(rs.getString("status"));
				bi.setLocation(rs.getString("storage_location"));
				list.add(bi);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return list;
	}
}